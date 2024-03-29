package propensi.proyek.siRelawan.dto.request;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserRequestDTO {
    @NotBlank(message = "Nama tidak boleh kosong")
    private String fullName;

    @NotBlank(message = "Username tidak boleh kosong")
    private String username;

    @NotBlank(message = "Password tidak boleh kosong")
    private String password;

    @NotBlank(message = "Email tidak boleh kosong")
    private String email;
    
    @NotNull(message = "Nomor WA tidak boleh kosong")
    private Long nomorWA;
}
