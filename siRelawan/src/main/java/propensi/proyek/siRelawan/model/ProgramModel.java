package propensi.proyek.siRelawan.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import jakarta.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Program")
@Entity
public class ProgramModel implements Serializable {
    @Id
    @NotNull
    @Column(name= "id_program", nullable = false)
    private String id;

    @NotNull
    @Column(name = "nama_program", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "status_program", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED
    }
}
