package teste.hospital.dto.admission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BedHistoryDTO {
    private int bedNumber;
    private String patientName;
    private LocalDateTime admissionAt;
    private LocalDateTime dischargeAt;
}