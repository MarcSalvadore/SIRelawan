package propensi.proyek.siRelawan.service;
import java.util.List;
import java.util.Optional;

import propensi.proyek.siRelawan.model.Catalog;

public interface CatalogService {
    void createCatalog(Catalog catalog);

    List<Catalog> getAllCatalog();

    Catalog getCatalogById(String id);

    void deleteCatalogById(String id);

    Catalog updateCatalog(Catalog catalog);
}
