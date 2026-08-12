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
public class CurrentAdmissionDTO {

    private String hospitalName;
    private String specialty;
    private String roomCode;
    private String patientName;
    private LocalDateTime admission;

}
