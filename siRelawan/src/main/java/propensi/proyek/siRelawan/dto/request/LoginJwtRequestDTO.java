package propensi.proyek.siRelawan.dto.request;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginJwtRequestDTO {
    private String username;
    private String fullName;
}
