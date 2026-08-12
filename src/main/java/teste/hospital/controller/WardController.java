package teste.hospital.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.hospital.dto.ward.WardRequestDTO;
import teste.hospital.dto.ward.WardResponseDTO;
import teste.hospital.service.HospitalService;
import teste.hospital.service.RoomService;
import teste.hospital.service.WardService;

import java.util.List;

@RestController
@RequestMapping("/wards")
public class WardController {

    private final WardService wardService;

    public WardController(WardService wardService) {
        this.wardService = wardService;
    }

    @PostMapping
    public ResponseEntity<WardResponseDTO> criar(@RequestBody @Valid WardRequestDTO dto) {
        WardResponseDTO ward = wardService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ward);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WardResponseDTO> buscar(@PathVariable Long id) {
        WardResponseDTO ward = wardService.findById(id);
        return ResponseEntity.ok(ward);
    }

    @GetMapping
    public ResponseEntity<List<WardResponseDTO>> listar() {
        List<WardResponseDTO> ward = wardService.findAll();
        return ResponseEntity.ok(ward);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WardResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid WardRequestDTO dto) {
        WardResponseDTO ward = wardService.update(id, dto);
        return ResponseEntity.ok(ward);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        wardService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
