package com.prajjwal.SpringWebDemo.services;

import com.prajjwal.SpringWebDemo.dto.EmployeeDTO;
import com.prajjwal.SpringWebDemo.entities.EmployeeEntity;
import com.prajjwal.SpringWebDemo.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        return mapper.map(employeeEntity, EmployeeDTO.class);
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
}
