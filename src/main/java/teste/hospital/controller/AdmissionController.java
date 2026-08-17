package teste.hospital.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.hospital.dto.admission.*;
import teste.hospital.dto.room.RoomResponseDTO;
import teste.hospital.service.AdmissionService;

import java.util.List;

@RestController
@RequestMapping("/admissions")
public class AdmissionController {

    private final AdmissionService admissionService;

    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @PostMapping
    public ResponseEntity<AdmissionResponseDTO> admit(@RequestBody AdmissionRequestDTO dto) {
        AdmissionResponseDTO admission = admissionService.admit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(admission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdmissionResponseDTO> discharge(@PathVariable Long id) {
        AdmissionResponseDTO admission = admissionService.discharge(id);
        return ResponseEntity.ok(admission);
    }

    @GetMapping("/currentAdmission/{id}")
    public ResponseEntity<CurrentAdmissionDTO> currentAdmissionDTO(@PathVariable Long id) {
        CurrentAdmissionDTO admissionDTO = admissionService.currentAdmissionDTO(id);
        return ResponseEntity.ok(admissionDTO);
    }

    @GetMapping("/admissionHistory/{id}")
    public ResponseEntity<Page<HistoricAdmissionDTO>> findAdmissionHistory(@PathVariable Long id, Pageable pageable) {
        Page<HistoricAdmissionDTO> historicAdmissionDTO = admissionService.findAdmissionHistory(id, pageable);
        return ResponseEntity.ok(historicAdmissionDTO);
    }

    @GetMapping("/activePatients")
    public ResponseEntity<List<ActivePatientDTO>> findActivePatientsGrouped() {
        List<ActivePatientDTO> activePatientDTO = admissionService.findActivePatientsGrouped();
        return ResponseEntity.ok(activePatientDTO);
    }

    @GetMapping("/bedHistory/{id}")
    public ResponseEntity<List<BedHistoryDTO>> findBedHistory(@PathVariable Long id) {
        List<BedHistoryDTO> bedHistoryDTO = admissionService.findBedHistory(id);
        return ResponseEntity.ok(bedHistoryDTO);
    }

    @GetMapping("/roomPatient/{id}")
    public ResponseEntity<RoomResponseDTO> findRoomByPatient(@PathVariable Long id) {
        RoomResponseDTO roomResponseDTO = admissionService.findRoomByPatient(id);
        return ResponseEntity.ok(roomResponseDTO);
    }
}
