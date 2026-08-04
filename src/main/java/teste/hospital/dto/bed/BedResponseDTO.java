package teste.hospital.dto.bed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BedResponseDTO {

    private Long id;
    private Integer bedNumber;
    private String status;
    private Long roomId;
}
