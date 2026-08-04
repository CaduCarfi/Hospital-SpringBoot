package teste.hospital.dto.ward;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teste.hospital.model.Hospital;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WardResponseDTO {

    private Long id;
    private String specialty;
    private Long hospitalId;
}
