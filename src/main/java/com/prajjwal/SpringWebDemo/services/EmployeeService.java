package com.prajjwal.SpringWebDemo.services;

import com.prajjwal.SpringWebDemo.dto.EmployeeDTO;
import com.prajjwal.SpringWebDemo.entities.EmployeeEntity;
import com.prajjwal.SpringWebDemo.repositories.EmployeeRepository;
import org.aspectj.asm.IModelFilter;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return employeeEntity.map(employeeEntity1 -> mapper.map(employeeEntity1, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> mapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
        EmployeeEntity employeeEntity = employeeRepository.save(mapper.map(inputEmployee, EmployeeEntity.class));
        return mapper.map(employeeEntity, EmployeeDTO.class);
    }

    //Will Search the Employee by Id and if the Employee does not exist against that ID,
    //Then, we will create that employee, or else we will update the existing employee.
    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = mapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        EmployeeEntity savedEmplooyee = employeeRepository.save(employeeEntity);
        return mapper.map(savedEmplooyee, EmployeeDTO.class);


    }

    public Boolean deleteEmployeeById(Long employeeId) {
        Boolean exists = employeeRepository.existsById(employeeId);
        if(!exists) return false;
        employeeRepository.deleteById(employeeId);
        return true;
    }

    //1. Searching if user exist by Id.
    //2. If not then return.
    //3. If it exist, the code not go inside if block and will proceed further.
    //4. Searching Employee by Id and extracting all the values in Entity class.
    //5. And now, we will now use our input Map update to update the particular values that are sent by user instead of whole object.
    //6. TO do that, we need to run for each iterator for map update.
    //7. Using ReflectionUtils, we are searching the field that is passed by user.
    //8. If it exist it will return a object of field.
    //9. Our fields in Entity class is private, so we need to change accessibility to true, so that we can directly update the value.
    //10. Again using the reflection utils, we setField and pass the field that is need to be updated,
    //    Object of the class that is being updated,a dn the value we want to set.
    //11. And we are saving the Update Object to our DB using Repository, and c0onvertingthe employeeEntity to EMployee DTO and returning it.

    public EmployeeDTO updateEmployeesPartialDetailsById(Map<String, Object> updates, Long employeeId) {
        if(!employeeRepository.existsById(employeeId)) {
            return null;
        }
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        updates.forEach((key, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class, key);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });
        return mapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }
}
