package teste.hospital.service;

import org.springframework.stereotype.Service;
import teste.hospital.dto.hospital.HospitalRequestDTO;
import teste.hospital.dto.hospital.HospitalResponseDTO;
import teste.hospital.model.Hospital;
import teste.hospital.repository.HospitalRepository;
import teste.hospital.repository.PatientRepository;

import java.util.List;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;

    public HospitalService(HospitalRepository hospitalRepository, PatientRepository patientRepository) {
        this.hospitalRepository = hospitalRepository;
        this.patientRepository = patientRepository;
    }

    public HospitalResponseDTO create(HospitalRequestDTO dto) {
       Hospital hospital = new Hospital();
       hospital.setName(dto.getName());
       hospital.setPhone(dto.getPhone());
       hospital.setCnpj(dto.getCnpj());

       Hospital saved = hospitalRepository.save(hospital);

       return toResponseDTO(saved);
    }

    public HospitalResponseDTO findById(Long id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital não existe"));
        return toResponseDTO(hospital);
    }

    public List<HospitalResponseDTO> findAll() {
        return hospitalRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public HospitalResponseDTO toResponseDTO(Hospital hospital) {
        return new HospitalResponseDTO(
                hospital.getId(),
                hospital.getName(),
                hospital.getPhone(),
                hospital.getCnpj()
        );
    }

    public HospitalResponseDTO update(Long id, HospitalRequestDTO dto) {
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
