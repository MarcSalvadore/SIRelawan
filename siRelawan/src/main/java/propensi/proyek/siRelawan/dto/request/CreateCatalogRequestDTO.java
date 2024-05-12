package propensi.proyek.siRelawan.dto.request;

import jakarta.persistence.Column;
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
    private Long targetDana;
    private String websiteURL;
    private String campaignSource = "relawan";
    private String campaignMedium = "link";
}
