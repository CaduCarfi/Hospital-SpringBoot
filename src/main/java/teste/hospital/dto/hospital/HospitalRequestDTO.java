package teste.hospital.dto.hospital;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.processing.Pattern;
import teste.hospital.dto.ward.WardRequestDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HospitalRequestDTO {

    private String name;
    private String phone;
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    private String cnpj;
    private List<WardRequestDTO> wards;
}
