package propensi.proyek.siRelawan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

import org.springframework.ui.Model;

import propensi.proyek.siRelawan.dto.CatalogMapper;
import propensi.proyek.siRelawan.dto.request.CreateCatalogRequestDTO;
import propensi.proyek.siRelawan.model.Catalog;
import propensi.proyek.siRelawan.model.EnumRole;
import propensi.proyek.siRelawan.model.UserModel;
import propensi.proyek.siRelawan.service.CatalogService;
import propensi.proyek.siRelawan.service.UserService;

import java.util.List;

@Controller
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private CatalogMapper catalogMapper;

    @Autowired
    private UserService userService;

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

    private static boolean isNonEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    private static boolean isNonEmpty(Long value) {
        return value != null && value != 0;
    }

    @GetMapping("catalog/statistik")
    public String statistikPage(Model model) {
        List<Catalog> listCatalog = catalogService.getAllCatalog();

        int notStartedCount = 0;
        int inProgressCount = 0;
        int completedCount = 0;

        for (Catalog catalog : listCatalog) {
            switch (catalog.getStatus()) {
                case NOT_STARTED:
                    notStartedCount++;
                    break;
                case IN_PROGRESS:
                    inProgressCount++;
                    break;
                case COMPLETED:
                    completedCount++;
                    break;
            }
        }

        List<UserModel> listUser = userService.getAllUser();
        int relawanCount = 0;
        int dataCompleteCount = 0;

        for (UserModel user : listUser) {
            if (user.getRole().equals(EnumRole.RELAWAN)) {
                relawanCount++;
            }
            if (isNonEmpty(user.getFullName()) && isNonEmpty(user.getNomorWA())) {
                dataCompleteCount++;
            }
        }

        model.addAttribute("notStartedCount", notStartedCount);
        model.addAttribute("inProgressCount", inProgressCount);
        model.addAttribute("completedCount", completedCount);

        model.addAttribute("relawanCount", relawanCount);
        model.addAttribute("dataCompleteCount", dataCompleteCount);
        return "statistik";
    }

    @GetMapping("catalog/detail-program/{id}")
    public String detailProgram(@PathVariable String id, Model model) {
        Catalog catalog = catalogService.getCatalogById(id);
        String status = "";

        if (catalog.getStatus().equals(Catalog.Status.NOT_STARTED)) {
            status = "Belum dimulai";
        } else if (catalog.getStatus().equals(Catalog.Status.IN_PROGRESS)) {
            status = "Sedang dikerjakan";
        } else {
            status = "Selesai";
        }
        model.addAttribute("program", catalog);
        model.addAttribute("status", status);
        return "catalog/detail-program";
    }

    @GetMapping("catalog/edit-program/{id}")
    public String formUpdateProgram(@PathVariable String id, Model model) {
        Catalog catalog = catalogService.getCatalogById(id);
        String status = String.valueOf(catalog.getStatus());

        model.addAttribute("program", catalog);
        model.addAttribute("status", status);
        return "catalog/edit-program";
    }

    @PostMapping("catalog/edit-program")
    public String updateProgram(@ModelAttribute Catalog catalog, Model model) {
        Catalog updatedCatalog = catalogService.updateCatalog(catalog);

        model.addAttribute("id", catalog.getId());
        return "catalog/edit-program-sukses.html";
    }
    @PostMapping("catalog/delete-program/{id}")
    public String deleteProgram(@PathVariable String id, Model model) {
        catalogService.deleteCatalogById(id);
        return "redirect:/home";
    }
}
