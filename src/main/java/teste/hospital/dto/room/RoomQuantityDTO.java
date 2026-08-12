package teste.hospital.dto.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomQuantityDTO {

    private String specialty;
    private int occupiedCount;
    private int freeCount;
    private int totalCount;
}
