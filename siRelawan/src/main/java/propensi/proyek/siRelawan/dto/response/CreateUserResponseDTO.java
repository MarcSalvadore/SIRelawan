package propensi.proyek.siRelawan.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponseDTO {
    private UUID id;
    private String fullName;
    private String email;
    private Long nomorWA;
    private String username;
    private String password;
    private Boolean isDeleted;
}
