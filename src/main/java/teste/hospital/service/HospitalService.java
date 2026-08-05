package teste.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teste.hospital.dto.hospital.HospitalRequestDTO;
import teste.hospital.dto.hospital.HospitalResponseDTO;
import teste.hospital.model.Hospital;
import teste.hospital.repository.HospitalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public Optional<HospitalResponseDTO> create(HospitalRequestDTO dto) {
       Hospital hospital = new Hospital();
       hospital.setName(dto.getName());
       hospital.setPhone(dto.getPhone());
       hospital.setCnpj(dto.getCnpj());

       Hospital saved = hospitalRepository.save(hospital);

       return toResponseDTO(saved);
    }

    public List<Optional<HospitalResponseDTO>> findAll() {
        return hospitalRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Optional<HospitalResponseDTO> toResponseDTO(Hospital hospital) {
        return Optional.of(new HospitalResponseDTO(
                hospital.getId(),
                hospital.getName(),
                hospital.getPhone(),
                hospital.getCnpj()
        ));
    }

    public Optional<HospitalResponseDTO> update(Long id, HospitalRequestDTO dto) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital não encontrado"));

        hospital.setName(dto.getName());
        hospital.setPhone(dto.getPhone());
        hospital.setCnpj(dto.getCnpj());

        Hospital updated = hospitalRepository.save(hospital);
        return toResponseDTO(updated);
    }

    public void delete(Long id) {
        if (!hospitalRepository.existsById(id)) {
            throw new RuntimeException("Hospital não encontrado");
        }
        hospitalRepository.deleteById(id);
    }
}
