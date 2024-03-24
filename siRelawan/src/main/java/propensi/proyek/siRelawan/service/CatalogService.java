package propensi.proyek.siRelawan.service;
import java.util.List;

import propensi.proyek.siRelawan.model.Catalog;

public interface CatalogService {
    void createCatalog(Catalog catalog);

    List<Catalog> getAllCatalog();
}
