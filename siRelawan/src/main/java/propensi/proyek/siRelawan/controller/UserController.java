package propensi.proyek.siRelawan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import propensi.proyek.siRelawan.dto.request.CreateUserRequestDTO;
import propensi.proyek.siRelawan.dto.request.LoginUserRequestDTO;
import propensi.proyek.siRelawan.model.UserModel;
import propensi.proyek.siRelawan.repository.UserDb;
import propensi.proyek.siRelawan.security.jwt.JwtUtils;
import propensi.proyek.siRelawan.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class UserController {
    @Autowired
    UserService userService;
    
    // @Autowired
    // JwtUtils jwtUtils;
    
    @GetMapping("/")
    public String main() {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String formRegister(Model model) {
        var user = new CreateUserRequestDTO();
        model.addAttribute("userDTO", user);
        return "user/register";
    }

    @PostMapping("/register")
    private String registerUser(@Valid @ModelAttribute("userDTO") CreateUserRequestDTO userRequestDTO,
                                BindingResult bindingResult,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Registration failed. Please try again.");
            return "user/register";
        }
        userService.createUser(userRequestDTO);
        redirectAttributes.addFlashAttribute("success", "Registration successful!");
        return "redirect:/login";
    } 

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("loginReq", new LoginUserRequestDTO());
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginRequest") @Valid LoginUserRequestDTO loginRequest,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        boolean isAuthenticated = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (isAuthenticated) {
            // Redirect to home.html if isAuthenticated return True
            model.addAttribute("username", loginRequest.getUsername());
            return "redirect:/home";
        } else {
            // Add error message and redirect back to login page
            redirectAttributes.addFlashAttribute("error", "Incorrect username or password");
            return "redirect:/login";
        }
    }
    
    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
    
}
