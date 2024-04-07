package propensi.proyek.siRelawan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCatalogRequestDTO {
    private String nama;
    private java.sql.Date tanggalMulai;
    private String deskripsi;
    private String status;
}
