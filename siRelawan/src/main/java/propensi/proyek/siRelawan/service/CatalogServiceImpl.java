package propensi.proyek.siRelawan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.proyek.siRelawan.model.Catalog;
import propensi.proyek.siRelawan.repository.CatalogDb;
import java.util.List;

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
}
