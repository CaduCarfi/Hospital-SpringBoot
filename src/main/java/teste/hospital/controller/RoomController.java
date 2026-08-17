package teste.hospital.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.hospital.dto.room.RoomAvailabilityDTO;
import teste.hospital.dto.room.RoomQuantityDTO;
import teste.hospital.dto.room.RoomRequestDTO;
import teste.hospital.dto.room.RoomResponseDTO;
import teste.hospital.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<RoomResponseDTO> criar(@RequestBody @Valid RoomRequestDTO dto) {
        RoomResponseDTO room = roomService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> buscar(@PathVariable Long id) {
        RoomResponseDTO room = roomService.findById(id);
        return ResponseEntity.ok(room);
    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> listar() {
        List<RoomResponseDTO> room = roomService.findAll();
        return ResponseEntity.ok(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid RoomRequestDTO dto) {
        RoomResponseDTO room = roomService.update(id, dto);
        return ResponseEntity.ok(room);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoomAvailabilityDTO>> findAvailableRooms() {
        List<RoomAvailabilityDTO> room = roomService.findAvailableRooms();
        return ResponseEntity.ok(room);
    }

    @GetMapping("/quantity")
    public ResponseEntity<List<RoomQuantityDTO>> countRoomsBySpecialty() {
        List<RoomQuantityDTO> room = roomService.countRoomsBySpecialty();
        return ResponseEntity.ok(room);
    }
}
