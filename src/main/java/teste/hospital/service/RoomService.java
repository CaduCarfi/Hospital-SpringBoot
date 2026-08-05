package teste.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teste.hospital.dto.room.RoomRequestDTO;
import teste.hospital.dto.room.RoomResponseDTO;
import teste.hospital.enums.RoomStatus;
import teste.hospital.model.Room;
import teste.hospital.model.Ward;
import teste.hospital.repository.RoomRepository;
import teste.hospital.repository.WardRepository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final WardRepository wardRepository;

    public RoomService(RoomRepository roomRepository, WardRepository wardRepository) {
        this.roomRepository = roomRepository;
        this.wardRepository = wardRepository;
    }

    public Optional<RoomResponseDTO> create(RoomRequestDTO dto) {
        Ward ward = wardRepository.findById(dto.getWardId())
                .orElseThrow(() -> new RuntimeException("Ward não encontrada"));

        Room room = new Room();
        room.setWard(ward);
        room.setStatus(RoomStatus.FREE);

        long quantidadeAtual = roomRepository.countByWard(ward);
        String prefixo = ward.getSpecialty().substring(0, 4).toUpperCase();
        room.setRoom_code(prefixo + "-" + (quantidadeAtual + 1));

        Room saved = roomRepository.save(room);
        return toResponseDTO(saved);
    }

    public List<RoomResponseDTO> findAll() {
        return roomRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Optional<RoomResponseDTO> findById(Long id) {
        return roomRepository.findById(id)
                .map(this::toResponseDTO);
    }

    public RoomResponseDTO update(Long id, RoomRequestDTO dto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room não encontrado"));

        Ward ward = wardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ward não encontrado"));

        room.setWard(ward);

        Room updated = roomRepository.save(room);
        return toResponseDTO(updated);
    }

    public void delete(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room não encontrado"));

        if (!room.getBeds().isEmpty()) {
            throw new RuntimeException("Não é possivel excluir quarto com leitos cadastrados");
        }
        roomRepository.deleteById(id);
    }

    private RoomResponseDTO toResponseDTO(Room room) {
        return new RoomResponseDTO(
                room.getId(),
                room.getRoom_code(),
                room.getStatus().name(),
                room.getWard().getId()
        );
    }
}
