package teste.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import teste.hospital.enums.AdmissionStatus;
import teste.hospital.model.AdmissionLog;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface AdmissionLogRepository extends JpaRepository<AdmissionLog, Long> {

    Optional<AdmissionLog> findByPatient_IdAndStatus(Long patientId, AdmissionStatus status);

    Page<AdmissionLog> findByPatient_Id(Long patientId, Pageable pageable);

    List<AdmissionLog> findByStatus(AdmissionStatus status);

    List<AdmissionLog> findByBed_Id(Long bedId);
}
