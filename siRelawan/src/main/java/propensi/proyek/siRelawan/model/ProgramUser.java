package propensi.proyek.siRelawan.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "program_user")
@Getter
@Setter
@NoArgsConstructor
public class ProgramUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "catalog_id", nullable = false)
    private Catalog catalog;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    // attribut tambahan bisa ditambah jika diperlukan
}
