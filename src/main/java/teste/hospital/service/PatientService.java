package teste.hospital.service;

import org.springframework.stereotype.Service;
import teste.hospital.dto.patient.PatientRequestDTO;
import teste.hospital.dto.patient.PatientResponseDTO;
import teste.hospital.model.Patient;
import teste.hospital.repository.HospitalRepository;
import teste.hospital.repository.PatientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponseDTO create(PatientRequestDTO dto) {
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setCpf(dto.getCpf());
        patient.setPhone(dto.getPhone());

        Patient saved = patientRepository.save(patient);

        return toResponseDTO(saved);
    }

    public PatientResponseDTO findById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passiente não existe"));
        return toResponseDTO(patient);
    }

    public List<PatientResponseDTO> findAll() {
        return patientRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public PatientResponseDTO toResponseDTO(Patient patient) {
        return new PatientResponseDTO(
                patient.getId(),
                patient.getName(),
                patient.getCpf(),
                patient.getPhone()
        );
    }

    public PatientResponseDTO update(Long id, PatientRequestDTO dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient não encontrado"));

        patient.setPhone(dto.getPhone());
        patient.setName(dto.getName());
        patient.setCpf(dto.getCpf());

        Patient updated = patientRepository.save(patient);
        return toResponseDTO(updated);
    }

    public void delete(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient não encontrado");
        }
        patientRepository.deleteById(id);
    }
}
