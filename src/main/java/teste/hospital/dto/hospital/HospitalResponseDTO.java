package teste.hospital.dto.hospital;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalResponseDTO {

    private Long id;
    private String name;
    private String phone;
    private String cnpj;
}
