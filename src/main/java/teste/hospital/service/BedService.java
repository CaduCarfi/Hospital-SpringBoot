package teste.hospital.service;

import org.springframework.stereotype.Service;
import teste.hospital.dto.bed.BedRequestDTO;
import teste.hospital.dto.bed.BedResponseDTO;
import teste.hospital.enums.BedStatus;
import teste.hospital.model.Bed;
import teste.hospital.model.Room;
import teste.hospital.repository.BedRepository;
import teste.hospital.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BedService {

    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;

    public BedService(BedRepository bedRepository, RoomRepository roomRepository) {
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
    }

    public BedResponseDTO create(BedRequestDTO dto) {
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room não encontrado"));

        Bed bed = new Bed();
        bed.setRoom(room);
        bed.setStatus(BedStatus.UNOCCUPIED);

        long quantidadeAtual = bedRepository.countByRoom(room);
        bed.setBedNumber((int) quantidadeAtual + 1);

        Bed saved = bedRepository.save(bed);
        return toResponseDTO(saved);
    }

    public List<BedResponseDTO> findAll() {
        return bedRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public BedResponseDTO findById(Long id) {
        Bed bed = bedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bed não existe"));
        return toResponseDTO(bed);
    }

    public BedResponseDTO update(Long id, BedRequestDTO dto) {
        Bed bed = bedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bed não encontrada"));

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room não encontrado"));

        bed.setRoom(room);

        Bed updated = bedRepository.save(bed);
        return toResponseDTO(updated);
    }

    public void delete(Long id) {
        Bed bed = bedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bed não encontrada"));

        if (bed.getStatus() == BedStatus.OCCUPIED) {
            throw new RuntimeException("Não é possivel excluir leito ocupado");
        }
        bedRepository.deleteById(id);
    }

    private BedResponseDTO toResponseDTO(Bed bed) {
        return new BedResponseDTO(
                bed.getId(),
                bed.getBedNumber(),
                bed.getStatus().name(),
                bed.getRoom().getId()
        );
    }

    public List<BedResponseDTO> findAvailableBedsBySpecialty(String specialty) {
        List<Bed> beds = bedRepository.findByRoom_Ward_SpecialtyAndStatus(specialty, BedStatus.UNOCCUPIED);
        return beds.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
