package propensi.proyek.siRelawan.dto;

import org.mapstruct.Mapper;

import propensi.proyek.siRelawan.dto.request.CreateCatalogRequestDTO;
import propensi.proyek.siRelawan.model.Catalog;

@Mapper(componentModel = "spring")
public interface CatalogMapper {

    Catalog createCatalogRequestDTOToCatalog(CreateCatalogRequestDTO createCatalogRequestDTO);

}
