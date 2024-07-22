package com.prajjwal.SpringWebDemo.controllers;

import com.prajjwal.SpringWebDemo.dto.EmployeeDTO;
import com.prajjwal.SpringWebDemo.entities.EmployeeEntity;
import com.prajjwal.SpringWebDemo.exception.ResourceNotFoundException;
import com.prajjwal.SpringWebDemo.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


//RequestMapping is optional here.
@RestController
@RequestMapping(path = "/employees")
//@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false, name="inputAge") Integer age,
                                                @RequestParam(required = false) String sortBy) {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee) {
        EmployeeDTO employeeDTO = employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(employeeDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO employeeDTO, @PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId, employeeDTO));
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeesPartialDetailsById(@RequestBody Map<String, Object> updates, @PathVariable Long employeeId) {
        EmployeeDTO employeeDTO = employeeService.updateEmployeesPartialDetailsById(updates, employeeId);
        if(employeeDTO == null) return ResponseEntity.badRequest().build();
        else {
            return ResponseEntity.ok(employeeDTO);
        }
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long employeeId) {
        Boolean deleted = employeeService.deleteEmployeeById(employeeId);
        if(deleted)
            return ResponseEntity.ok("User Deleted");
        else {
            return ResponseEntity.badRequest().build();
        }
    }


//    Path is optional, and when we hit employees, it will be directed here.
//    @GetMapping
//    public String ListAllUsers() {
//        return "List of all users";
//    }
//
//    //PostMapping, when Post request is hit, this function will be called.
//    @PostMapping
//    public String ListAllUsersbyPost() {
//        return "Hitting the POST Mapping URL";
//    }
//
//
//    //We can not have duplicate mapping i.e. we cannot have two functions with same mapping.
//    //If we do not give name to pathvariiable we have to match the string that we have used in getmapping.
//    @GetMapping("/{employeeId}")
//    public EmployeeDTO getEmployeeByEmployeeId(@PathVariable Long employeeId) {
//        return new EmployeeDTO(employeeId, "Anuj", "prajjwal.agrahari16@gmail.com", 23, LocalDate.of(2022, 1, 17), true);
//    }
//
//    //Path variable is optional, and we can define it implicitly also, without using path as in second line.
//    @GetMapping(path = "searchById/{employeeId}")
//    public EmployeeDTO getEmployeeById(@PathVariable(name= "employeeId") Long id) {
//        return new EmployeeDTO(id, "Prajjwal", "prajjwal.agrahari16@gmail.com", 23, LocalDate.of(2022, 1, 17), true);
//    }
//
//
//    //RequestParams
//    //Parameters passed as query can be access by @RequestParam and by default they are reuiqred.
//    @GetMapping("employeeById")
//    public String SortingAndPagination(@RequestParam(required = false) Integer age, @RequestParam(name = "sortBy", required = false) String sort){
//        return "This method will return age and sorting method" + age +sort;
//    }

}
