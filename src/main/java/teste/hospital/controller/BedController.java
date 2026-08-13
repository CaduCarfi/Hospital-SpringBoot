package teste.hospital.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.hospital.dto.bed.BedRequestDTO;
import teste.hospital.dto.bed.BedResponseDTO;
import teste.hospital.service.BedService;

import java.util.List;

@RestController
@RequestMapping("/beds")
public class BedController {

    private final BedService bedService;

    public BedController(BedService bedService) {
        this.bedService = bedService;
    }

    @PostMapping
    public ResponseEntity<BedResponseDTO> criar(@RequestBody @Valid BedRequestDTO dto) {
        BedResponseDTO bed = bedService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bed);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BedResponseDTO> buscar(@PathVariable Long id) {
        BedResponseDTO bed = bedService.findById(id);
        return ResponseEntity.ok(bed);
    }

    @GetMapping
    public ResponseEntity<List<BedResponseDTO>> listar() {
        List<BedResponseDTO> bed = bedService.findAll();
        return ResponseEntity.ok(bed);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BedResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid BedRequestDTO dto) {
        BedResponseDTO bed = bedService.update(id, dto);
        return ResponseEntity.ok(bed);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        bedService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<BedResponseDTO>> findAvailableBedsBySpecialty(@RequestParam String specialty) {
        List<BedResponseDTO> bed = bedService.findAvailableBedsBySpecialty(specialty);
        return ResponseEntity.ok(bed);
    }
}
