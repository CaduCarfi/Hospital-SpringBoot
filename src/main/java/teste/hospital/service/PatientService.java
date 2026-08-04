package teste.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private HospitalRepository hospitalRepository;

    public Optional<PatientResponseDTO> creat(PatientRequestDTO dto) {
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setCpf(dto.getCpf());
        patient.setPhone(dto.getPhone());

        Patient saved = patientRepository.save(patient);

        return toResponseDTO(saved);
    }

    public List<Optional<PatientResponseDTO>> findAll() {
        return patientRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Optional<PatientResponseDTO> toResponseDTO(Patient patient) {
        return Optional.of(new PatientResponseDTO(
                patient.getId(),
                patient.getCpf(),
                patient.getName(),
                patient.getPhone()
        ));
    }

    public Optional<PatientResponseDTO> update(Long id, PatientRequestDTO dto) {
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
