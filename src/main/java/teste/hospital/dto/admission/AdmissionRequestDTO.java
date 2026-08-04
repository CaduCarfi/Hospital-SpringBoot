package teste.hospital.dto.admission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AdmissionRequestDTO {

    private Long patientId;

    private Long bedId;
}