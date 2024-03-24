package propensi.proyek.siRelawan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

import org.springframework.ui.Model;

import propensi.proyek.siRelawan.dto.CatalogMapper;
import propensi.proyek.siRelawan.dto.request.CreateCatalogRequestDTO;
import propensi.proyek.siRelawan.service.CatalogService;

@Controller
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private CatalogMapper catalogMapper;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("listCatalog", catalogService.getAllCatalog());
        return "index";
    }

    @GetMapping("catalog/create")
    public String formAddCatalog(Model model) {
        // Membuat DTO baru sebagai isian form pengguna
        var catalogDTO = new CreateCatalogRequestDTO();

        model.addAttribute("catalog", catalogDTO);
        return "form";
    }

    @PostMapping("catalog/create")
    public String addCatalog(@Valid @ModelAttribute CreateCatalogRequestDTO catalogDTO, Model model) {

        var catalog = catalogMapper.createCatalogRequestDTOToCatalog(catalogDTO);
        // Memanggil Service Add
        catalogService.createCatalog(catalog);

        // Add variabel id catalog ke 'id' untuk dirender di thymeleaf
        model.addAttribute("id", catalog.getId());

        // Add variabel nama ke 'nama' untuk dirender di thymeleaf
        model.addAttribute("nama", catalog.getNama());

        return "success-create-catalog";
    }
}
