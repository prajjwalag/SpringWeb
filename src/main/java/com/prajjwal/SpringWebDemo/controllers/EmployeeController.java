package com.prajjwal.SpringWebDemo.controllers;

import com.prajjwal.SpringWebDemo.dto.EmployeeDTO;
import com.prajjwal.SpringWebDemo.entities.EmployeeEntity;
import com.prajjwal.SpringWebDemo.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


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
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false, name="inputAge") Integer age,
                                                @RequestParam(required = false) String sortBy) {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee) {
        return employeeService.createNewEmployee(inputEmployee);
    }

    @PutMapping("/{employeeId}")
    public EmployeeDTO updateEmployeeById(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long employeeId) {
        return employeeService.updateEmployeeById(employeeId, employeeDTO);
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
