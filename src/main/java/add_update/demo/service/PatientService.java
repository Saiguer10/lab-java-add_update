package add_update.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import add_update.demo.entity.Patient;
import add_update.demo.repository.PatientRepository;
import jakarta.validation.Valid;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // 1. Obtener todos los pacientes
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // 2. Obtener paciente por ID
    public Optional<Patient> getPatientById(Integer patientId) {
        return patientRepository.findById(patientId);
    }

    // 3. Obtener pacientes por rango de fecha de nacimiento
    public List<Patient> getPatientsByDateOfBirthRange(LocalDate startDate, LocalDate endDate) {
        return patientRepository.findByDateOfBirthBetween(startDate, endDate);
    }

    // 4. Obtener pacientes por departamento del doctor que los admitió
    public List<Patient> getPatientsByAdmittingDoctorDepartment(String department) {
        // Asegurarse de que el departamento no sea nulo o vacío
        if (department == null || department.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department cannot be null or empty");
        }
        return patientRepository.findByAdmittingDoctorDepartment(department);
    }

    // 5. Obtener pacientes con doctor cuyo estado es "OFF"
    public List<Patient> getPatientsWithDoctorOffStatus() {
        return patientRepository.findByAdmittingDoctorStatus("OFF");
    }

    // 6. Agregar un nuevo paciente
    public Patient addPatient(@Valid Patient patient) {
        // Validar antes de guardar (aunque la validación @Valid ya debería cubrirlo, siempre es bueno manejar errores)
        if (patient == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient cannot be null");
        }
        return patientRepository.save(patient);
    }

    // 7. Actualizar información del paciente
    public Optional<Patient> updatePatient(Integer patientId, Patient updatedData) {
        return patientRepository.findById(patientId)
                .map(existingPatient -> {
                    // Asegúrate de que todos los atributos sean actualizados correctamente
                    existingPatient.setName(updatedData.getName());
                    existingPatient.setDateOfBirth(updatedData.getDateOfBirth());
                    existingPatient.setAdmittedBy(updatedData.getAdmittedBy());

                    return patientRepository.save(existingPatient);
                });
    }
}
