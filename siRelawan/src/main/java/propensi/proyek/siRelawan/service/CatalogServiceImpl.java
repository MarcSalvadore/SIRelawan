package propensi.proyek.siRelawan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.proyek.siRelawan.model.Catalog;
import propensi.proyek.siRelawan.repository.CatalogDb;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    CatalogDb catalogDb;

    @Override
    public void createCatalog(Catalog catalog) {
        catalogDb.save(catalog);
    }

    @Override
    public List<Catalog> getAllCatalog(){
        return catalogDb.findAll();
    }

    @Override
    public Catalog getCatalogById(String id) {
        return catalogDb.getReferenceById(UUID.fromString(id));
    }

    @Override
    public Catalog updateCatalog(Catalog catalog) {
        return catalogDb.save(catalog);
    }

    @Override
    public void deleteCatalogById(String id) {
        catalogDb.deleteById(UUID.fromString(id));
    }
}
