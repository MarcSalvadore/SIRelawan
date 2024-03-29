package propensi.proyek.siRelawan.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance
@Table(name = "users")
public class UserModel {
    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @Column(name = "full_Name", nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "email", nullable = false)
    @Email
    private String email;

    @NotNull
    @Column(name = "nomor_WA", nullable = false)
    private Long nomorWA;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumRole role;

    @NotNull
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @NotNull
    @Column(name = "poin", nullable = false)
    private int poin = 0;
}
