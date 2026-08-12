package teste.hospital.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import teste.hospital.dto.admission.*;
import teste.hospital.dto.room.RoomResponseDTO;
import teste.hospital.enums.AdmissionStatus;
import teste.hospital.enums.BedStatus;
import teste.hospital.model.AdmissionLog;
import teste.hospital.model.Bed;
import teste.hospital.model.Patient;
import teste.hospital.model.Room;
import teste.hospital.repository.AdmissionLogRepository;
import teste.hospital.repository.BedRepository;
import teste.hospital.repository.PatientRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

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
        bed.setStatus(BedStatus.IN_PREPARATION);
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

    public RoomResponseDTO findRoomByPatient(Long patientId) {
        AdmissionLog admissionLog = admissionLogRepository.findByPatient_IdAndStatus(patientId, AdmissionStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Paciente não está internado"));

        Room room = admissionLog.getBed().getRoom();

        return new RoomResponseDTO(
                room.getId(),
                room.getRoomCode(),
                room.getStatus().name(),
                room.getWard().getId()
        );
    }

    public CurrentAdmissionDTO currentAdmissionDTO(Long patientId) {
        AdmissionLog admissionLog = admissionLogRepository.findByPatient_IdAndStatus(patientId, AdmissionStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Paciente não está internado"));

        Room room = admissionLog.getBed().getRoom();

        return new CurrentAdmissionDTO(
                room.getWard().getHospital().getName(),
                room.getWard().getSpecialty(),
                room.getRoomCode(),
                admissionLog.getPatient().getName(),
                admissionLog.getAdmissionAt()
        );
    }

    public Page<HistoricAdmissionDTO> findAdmissionHistory(Long patientId, Pageable pageable) {
        Page<AdmissionLog> admissionLogs = admissionLogRepository.findByPatient_Id(patientId, pageable);

        return admissionLogs.map(log -> new HistoricAdmissionDTO(
              log.getPatient().getName(),
              log.getBed().getRoom().getWard().getSpecialty(),
              log.getAdmissionAt(),
              log.getDischargeAt()
        ));
    }

    public List<ActivePatientDTO> findActivePatientsGrouped() {
        List<AdmissionLog> activeLogs = admissionLogRepository.findByStatus(AdmissionStatus.ACTIVE);

        return activeLogs.stream()
                .map(log -> new ActivePatientDTO(
                        log.getPatient().getName(),
                        log.getBed().getRoom().getWard().getSpecialty(),
                        log.getAdmissionAt(),
                        ChronoUnit.DAYS.between(log.getAdmissionAt(), LocalDateTime.now())
                ))
                .sorted(Comparator.comparing(ActivePatientDTO::getSpecialty)
                        .thenComparing(ActivePatientDTO::getPatientName))
                .toList();
    }

    public List<BedHistoryDTO> findBedHistory(Long bedId) {
        List<AdmissionLog> logs = admissionLogRepository.findByBed_Id(bedId);

        return logs.stream()
                .map(log -> new BedHistoryDTO(
                        log.getBed().getBedNumber(),
                        log.getPatient().getName(),
                        log.getAdmissionAt(),
                        log.getDischargeAt()
                ))
                .toList();
    }
}
