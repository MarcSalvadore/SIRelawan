package propensi.proyek.siRelawan.model;

import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "catalog")
@Getter
@Setter
@NoArgsConstructor

public class Catalog {
    @Id
    private UUID id = UUID.randomUUID();

    @Size(max = 255)
    @Column(name = "nama", nullable = false, length = 255)
    private String nama;

    @NotNull
    @Column(name="tanggal_mulai", nullable = false)
    private java.sql.Date tanggalMulai;

    @Column(name = "deskripsi", nullable = false)
    private String deskripsi;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED
    }
}
