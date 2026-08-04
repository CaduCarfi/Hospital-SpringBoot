package teste.hospital.dto.ward;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WardRequestDTO {

    private String specialty;
    private Integer roomCount;
    private Integer bedsPerRoom;
}
