package propensi.proyek.siRelawan.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import propensi.proyek.siRelawan.model.Catalog;

@Repository
@Transactional
public interface CatalogDb extends JpaRepository<Catalog, UUID> {
    
}
