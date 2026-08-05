package teste.hospital.service;

import org.springframework.stereotype.Service;
import teste.hospital.dto.ward.WardRequestDTO;
import teste.hospital.dto.ward.WardResponseDTO;
import teste.hospital.enums.BedStatus;
import teste.hospital.enums.RoomStatus;
import teste.hospital.model.Bed;
import teste.hospital.model.Hospital;
import teste.hospital.model.Room;
import teste.hospital.model.Ward;
import teste.hospital.repository.HospitalRepository;
import teste.hospital.repository.RoomRepository;
import teste.hospital.repository.WardRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WardService {

    private final WardRepository wardRepository;
    private final HospitalRepository hospitalRepository;
    private final RoomRepository roomRepository;

    public WardService(WardRepository wardRepository,
                       HospitalRepository hospitalRepository,
                       RoomRepository roomRepository) {
        this.wardRepository = wardRepository;
        this.hospitalRepository = hospitalRepository;
        this.roomRepository = roomRepository;
    }

    public WardResponseDTO create(WardRequestDTO dto) {
        Hospital hospital = hospitalRepository.findById(dto.getHospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital não encontrado"));

        Ward ward = new Ward();
        ward.setSpecialty(dto.getSpecialty());
        ward.setHospital(hospital);

        Ward saved = wardRepository.save(ward);

        if (dto.getRoomCount() != null) {
            for (int i = 1; i <= dto.getRoomCount(); i++) {
                createRoomWithBeds(saved, dto.getBedsPerRoom());
            }
        }

        return toResponseDTO(saved);
    }

    private void createRoomWithBeds(Ward ward, Integer bedsPerRoom) {
        Room room = new Room();
        room.setWard(ward);
        room.setStatus(RoomStatus.FREE);

        long quantidadeAtual = roomRepository.countByWard(ward);
        String prefixo = ward.getSpecialty().substring(0, 4).toUpperCase();
        room.setRoomCode(prefixo + "-" + (quantidadeAtual + 1));

        Room savedRoom = roomRepository.save(room);

        if (bedsPerRoom != null) {
            for (int i = 1; i <= bedsPerRoom; i++) {
                Bed bed = new Bed();
                bed.setRoom(savedRoom);
                bed.setStatus(BedStatus.UNOCCUPIED);
                bed.setBedNumber(i);
                savedRoom.getBeds().add(bed);
            }
        }
    }

    public List<WardResponseDTO> findAll() {
        return wardRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Optional<WardResponseDTO> findById(Long id) {
        return wardRepository.findById(id)
                .map(this::toResponseDTO);
    }

    public WardResponseDTO update(Long id, WardRequestDTO dto) {
        Ward ward = wardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ward não encontrada"));

        Hospital hospital = hospitalRepository.findById(dto.getHospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital não encontrado"));

        ward.setSpecialty(dto.getSpecialty());
        ward.setHospital(hospital);

        Ward updated = wardRepository.save(ward);
        return toResponseDTO(updated);
    }

    public void delete(Long id) {
        Ward ward = wardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ward não encontrada"));

        if (!ward.getRooms().isEmpty()) {
            throw new RuntimeException("Não é possível excluir ala com quartos cadastrados");
        }

        wardRepository.deleteById(id);
    }

    private WardResponseDTO toResponseDTO(Ward ward) {
        return new WardResponseDTO(
                ward.getId(),
                ward.getSpecialty(),
                ward.getHospital().getId()
        );
    }
}