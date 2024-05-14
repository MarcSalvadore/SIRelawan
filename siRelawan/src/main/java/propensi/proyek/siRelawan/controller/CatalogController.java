package propensi.proyek.siRelawan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.Valid;

import org.springframework.ui.Model;

import propensi.proyek.siRelawan.dto.CatalogMapper;
import propensi.proyek.siRelawan.dto.request.CreateCatalogRequestDTO;
import propensi.proyek.siRelawan.model.Catalog;
import propensi.proyek.siRelawan.model.EnumRole;
import propensi.proyek.siRelawan.model.UserModel;
import propensi.proyek.siRelawan.service.CatalogService;
import propensi.proyek.siRelawan.service.UserService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class CatalogController {              
    @Autowired
    private CatalogService catalogService;

    @Autowired
    private CatalogMapper catalogMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String currentUsername = (String) session.getAttribute("currentUser");

        // Memasukkan data dari user yang login
        model.addAttribute("currentUsername", currentUsername);
        model.addAttribute("listCatalog", catalogService.getAllCatalog());

        return "index";
    }

    // @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping("catalog/create")
    public String formAddCatalog(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = session.getAttribute("currentRole").toString();
        if (role.equals("ADMIN") || role.equals("SUPERADMIN")) {
            // Membuat DTO baru sebagai isian form pengguna
            var catalogDTO = new CreateCatalogRequestDTO();

            model.addAttribute("catalog", catalogDTO);
            model.addAttribute("role", session.getAttribute("currentRole"));

            return "form";
        } else {
            return "error/403";
        }
    }

    @PostMapping("catalog/create")
    public String addCatalog(@Valid @ModelAttribute CreateCatalogRequestDTO catalogDTO, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = session.getAttribute("currentRole").toString();
        if (role.equals("ADMIN") || role.equals("SUPERADMIN")) {
            var catalog = catalogMapper.createCatalogRequestDTOToCatalog(catalogDTO);
            // Memanggil Service Add
            catalogService.createCatalog(catalog);

            // Add variabel id catalog ke 'id' untuk dirender di thymeleaf
            model.addAttribute("id", catalog.getId());

            // Add variabel nama ke 'nama' untuk dirender di thymeleaf
            model.addAttribute("nama", catalog.getNama());
            model.addAttribute("role", session.getAttribute("currentRole"));

            return "success-create-catalog";
        } else {
            return "error/403";
        }
    }

    private static boolean isNonEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    private static boolean isNonEmpty(Long value) {
        return value != null && value != 0;
    }

    // @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("catalog/statistik")
    public String statistikPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = session.getAttribute("currentRole").toString();
        if (role.equals("SUPERADMIN")) {
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
            int dataNotCompleteCount = 0;

            for (UserModel user : listUser) {
                if (user.getRole().equals(EnumRole.RELAWAN)) {
                    relawanCount++;
                }

                if (isNonEmpty(user.getNIK()) && isNonEmpty(user.getNPWP()) && isNonEmpty(user.getNoRekening())) {
                    dataCompleteCount++;
                }

            }

            if (dataCompleteCount >= relawanCount) {
                dataNotCompleteCount = dataCompleteCount - relawanCount;
            } else {
                dataNotCompleteCount = relawanCount - dataCompleteCount;
            }

            model.addAttribute("notStartedCount", notStartedCount);
            model.addAttribute("inProgressCount", inProgressCount);
            model.addAttribute("completedCount", completedCount);

            model.addAttribute("relawanCount", relawanCount);
            model.addAttribute("dataCompleteCount", dataCompleteCount);
            model.addAttribute("dataNotCompleteCount", dataNotCompleteCount);
            model.addAttribute("role", session.getAttribute("currentRole"));

            return "statistik";
        } else {
            return "error/403";
        }
    }

    @GetMapping("catalog/detail-program/{id}")
    public String detailProgram(@PathVariable String id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
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
        model.addAttribute("role", session.getAttribute("currentRole"));
        return "catalog/detail-program";
    }

    // @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping("catalog/edit-program/{id}")
    public String formUpdateProgram(@PathVariable String id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = session.getAttribute("currentRole").toString();
        if (role.equals("ADMIN") || role.equals("SUPERADMIN")) {
            Catalog catalog = catalogService.getCatalogById(id);
            String status = String.valueOf(catalog.getStatus());

            model.addAttribute("program", catalog);
            model.addAttribute("status", status);
            model.addAttribute("role", session.getAttribute("currentRole"));

            return "catalog/edit-program";
        } else {
            return "error/403";
        }
    }

    @PostMapping("catalog/edit-program")
    public String updateProgram(@ModelAttribute Catalog catalog, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = session.getAttribute("currentRole").toString();
        if (role.equals("ADMIN") || role.equals("SUPERADMIN")) {
            Catalog updatedCatalog = catalogService.updateCatalog(catalog);

            model.addAttribute("id", catalog.getId());
            return "catalog/edit-program-sukses.html";
        } else {
            return "error/403";
        }
    }

    @PostMapping("catalog/delete-program/{id}")
    public String deleteProgram(@PathVariable String id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = session.getAttribute("currentRole").toString();
        if (role.equals("ADMIN") || role.equals("SUPERADMIN")) {
            catalogService.deleteCatalogById(id);
            return "redirect:/home";
        } else {
            return "error/403";
        }
    }

    @GetMapping("catalog/leaderboard")
    public String showLeaderboard(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String currentUsername = (String) session.getAttribute("currentUser");
        String role = session.getAttribute("currentRole").toString();
        if (role.equals("SUPERADMIN") || role.equals("RELAWAN")) {
            int currentUserPoint = userService.getUserPoint(currentUsername);
            // Memasukkan data dari user yang login
            model.addAttribute("currentUsername", currentUsername);
            model.addAttribute("currentUserPoints", currentUserPoint);

            List<UserModel> users = userService.getAllUser();
            // Mengurutkan user dengan poin tertinggi ke terendah
            Collections.sort(users, Comparator.comparingInt(UserModel::getPoin).reversed());
            model.addAttribute("users", users);
            model.addAttribute("role", session.getAttribute("currentRole"));

            return "poinRelawan";
        } else {
            return "error/403";
        }
    }

    @GetMapping("catalog/target-program")
    public String targetProgram(Model model) {
        model.addAttribute("programs", catalogService.getAllCatalog());
        return "target-program";
    }


    @GetMapping("/catalog/addpoint")
    public String showAddPointsForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();

        model.addAttribute("role", session.getAttribute("currentRole"));
        return "addpoint";
    }
    
    @PostMapping("catalog/addpoint")
    public ModelAndView addPoints(@RequestParam int points, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        String currentUsername = (String) session.getAttribute("currentUser");
        
        // Validate that the points to be added are not negative
        if (points < 0) {
            modelAndView.addObject("error", "Failed to add points: Points cannot be negative.");
            modelAndView.setViewName("catalog/addpoint");
            return modelAndView;
        }
        try {
            // Method for adding current user point
            userService.accumulatePoin(currentUsername, points);
            
            // Success message
            modelAndView.addObject("message", "Points added successfully");
            
            // Redirect to the leaderboard page
            modelAndView.setViewName("redirect:/catalog/leaderboard");
            return modelAndView;
        } catch (Exception e) {
            // Handle exceptions and add error message
            modelAndView.addObject("error", "Failed to add points: " + e.getMessage());
            modelAndView.setViewName("catalog/addpoint");
        }
        return modelAndView;
    }

    @GetMapping("catalog/target-program")
    public String targetProgram(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();

        model.addAttribute("role", session.getAttribute("currentRole"));
        model.addAttribute("programs", catalogService.getAllCatalog());
        return "target-program";
    }
}
