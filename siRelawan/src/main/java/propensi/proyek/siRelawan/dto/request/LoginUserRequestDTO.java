package propensi.proyek.siRelawan.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUserRequestDTO {
    private String username;
    private String password;
}
