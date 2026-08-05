package teste.hospital.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import teste.hospital.dto.admission.AdmissionRequestDTO;
import teste.hospital.dto.admission.AdmissionResponseDTO;
import teste.hospital.enums.AdmissionStatus;
import teste.hospital.enums.BedStatus;
import teste.hospital.model.AdmissionLog;
import teste.hospital.model.Bed;
import teste.hospital.model.Patient;
import teste.hospital.repository.AdmissionLogRepository;
import teste.hospital.repository.BedRepository;
import teste.hospital.repository.PatientRepository;

import java.time.LocalDateTime;

@Service
public class AdmissionService {

    private final AdmissionLogRepository admissionLogRepository;
    private final BedRepository bedRepository;
    private final PatientRepository patientRepository;


    public AdmissionService(AdmissionLogRepository admissionLogRepository, BedRepository bedRepository, PatientRepository patientRepository) {
        this.admissionLogRepository = admissionLogRepository;
        this.bedRepository = bedRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public AdmissionResponseDTO admit(AdmissionRequestDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Bed bed = bedRepository.findById(dto.getBedId())
                .orElseThrow(() -> new RuntimeException("Leito não encontrado"));

        if (bed.getStatus() != BedStatus.UNOCCUPIED) {
            throw new RuntimeException("Leito não está disponível para internação");
        }

        bed.setStatus(BedStatus.OCCUPIED);
        bedRepository.save(bed);

        AdmissionLog admissionLog = new AdmissionLog();
        admissionLog.setPatient(patient);
        admissionLog.setBed(bed);
        admissionLog.setStatus(AdmissionStatus.ACTIVE);
        admissionLog.setAdmissionAt(LocalDateTime.now());
        admissionLog.setDate(LocalDateTime.now());

        AdmissionLog saved = admissionLogRepository.save(admissionLog);
        return toResponseDTO(saved);
    }

    @Transactional
    public AdmissionResponseDTO discharge(Long admissionId) {
        AdmissionLog admissionLog = admissionLogRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Internação não encontrada"));

        if (admissionLog.getStatus() != AdmissionStatus.ACTIVE) {
            throw new RuntimeException("Essa internação já foi encerrada");
        }

        admissionLog.setStatus(AdmissionStatus.INACTIVE);
        admissionLog.setDischargeAt(LocalDateTime.now());
        admissionLogRepository.save(admissionLog);

        Bed bed = admissionLog.getBed();
        bed.setStatus(BedStatus.UNOCCUPIED);
        bedRepository.save(bed);

        return toResponseDTO(admissionLog);
    }

    private AdmissionResponseDTO toResponseDTO(AdmissionLog log) {
        return new AdmissionResponseDTO(
                log.getId(),
                log.getPatient().getName(),
                log.getBed().getBedNumber(),
                log.getStatus().name(),
                log.getAdmissionAt(),
                log.getDischargeAt()
        );
    }
}
