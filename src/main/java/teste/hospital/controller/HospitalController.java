package teste.hospital.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.hospital.dto.hospital.HospitalRequestDTO;
import teste.hospital.dto.hospital.HospitalResponseDTO;
import teste.hospital.service.HospitalService;

import java.util.List;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @PostMapping
    public ResponseEntity<HospitalResponseDTO> criar(@RequestBody @Valid HospitalRequestDTO dto) {
        HospitalResponseDTO hospital = hospitalService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(hospital);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalResponseDTO> buscar(@PathVariable Long id) {
        HospitalResponseDTO hospital = hospitalService.findById(id);
        return ResponseEntity.ok(hospital);
    }

    @GetMapping
    public ResponseEntity<List<HospitalResponseDTO>> listar() {
        List<HospitalResponseDTO> hospital = hospitalService.findAll();
        return ResponseEntity.ok(hospital);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HospitalResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid HospitalRequestDTO dto) {
        HospitalResponseDTO hospital = hospitalService.update(id, dto);
        return ResponseEntity.ok(hospital);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        hospitalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
