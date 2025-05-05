package add_update.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import add_update.demo.entity.Employee;
import add_update.demo.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 1. Get all doctors
    @GetMapping
    public ResponseEntity<List<Employee>> getAllDoctors() {
        List<Employee> doctors = employeeService.getAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    // 2. Get doctor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getDoctorById(@PathVariable("id") Integer employeeId) {
        return employeeService.getDoctorById(employeeId)
                .map(doctor -> new ResponseEntity<>(doctor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 3. Get doctors by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Employee>> getDoctorsByStatus(@PathVariable String status) {
        List<Employee> doctors = employeeService.getDoctorsByStatus(status);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    // 4. Get doctors by department
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> getDoctorsByDepartment(@PathVariable String department) {
        List<Employee> doctors = employeeService.getDoctorsByDepartment(department);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    // 5. Add new doctor
    @PostMapping
    public ResponseEntity<Employee> addNewDoctor(@RequestBody Employee employee) {
        Employee newDoctor = employeeService.addDoctor(employee);
        return new ResponseEntity<>(newDoctor, HttpStatus.CREATED);
    }

    // 6. Change doctor status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Employee> changeDoctorStatus(
            @PathVariable("id") Integer employeeId,
            @RequestParam String status) {
        return employeeService.updateDoctorStatus(employeeId, status)
                .map(updatedDoctor -> new ResponseEntity<>(updatedDoctor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    // 7. Update doctor's department
    @PutMapping("/{id}/department")
    public ResponseEntity<Employee> updateDoctorDepartment(
            @PathVariable("id") Integer employeeId,
            @RequestParam String department) { // <-- ahora espera en la URL
        return employeeService.updateDoctorDepartment(employeeId, department)
                .map(updatedDoctor -> new ResponseEntity<>(updatedDoctor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}