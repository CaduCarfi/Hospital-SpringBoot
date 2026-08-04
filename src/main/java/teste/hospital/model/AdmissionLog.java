package teste.hospital.model;

import jakarta.persistence.*;
import lombok.*;
import teste.hospital.enums.AdmissionStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "admission")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdmissionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bed_id")
    private Bed bed;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private AdmissionStatus status;
    private LocalDateTime admissionAt;
    private LocalDateTime dischargeAt;

}
