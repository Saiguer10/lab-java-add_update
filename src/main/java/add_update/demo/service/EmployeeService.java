package add_update.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import add_update.demo.entity.Employee;
import add_update.demo.repository.EmployeeRepository;
import jakarta.validation.Valid;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllDoctors() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getDoctorById(Integer employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public List<Employee> getDoctorsByStatus(String status) {
        return employeeRepository.findByStatus(status);
    }

    public List<Employee> getDoctorsByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    public Employee addDoctor(@Valid Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> updateDoctorStatus(Integer employeeId, String status) {
        String normalizedStatus = status.toUpperCase();
        if (!normalizedStatus.equals("ON") &&
                !normalizedStatus.equals("OFF") &&
                !normalizedStatus.equals("ON_CALL")) {
            return Optional.empty();
        }

        return employeeRepository.findById(employeeId)
                .map(doctor -> {
                    doctor.setStatus(normalizedStatus);
                    return employeeRepository.save(doctor);
                });
    }

    public Optional<Employee> updateDoctorDepartment(Integer employeeId, String department) {
        return employeeRepository.findById(employeeId)
                .map(doctor -> {
                    doctor.setDepartment(department);
                    return employeeRepository.save(doctor);
                });
    }
}
