package teste.hospital.dto.patient;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.processing.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequestDTO {

    private String name;
    @Pattern(regexp = "\\d{id}", message = "CPF deve conter 11 dígitos")
    private String cpf;
    private String phone;

}
