package teste.hospital.dto.admission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionResponseDTO {

    private Long id;
    private String patientName;
    private Integer bedNumber;
    private String status;
    private LocalDateTime admissionAt;
    private LocalDateTime dischargeAt;
}
