package propensi.proyek.siRelawan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import propensi.proyek.siRelawan.dto.request.CreateUserRequestDTO;
import propensi.proyek.siRelawan.dto.request.LoginUserRequestDTO;
import propensi.proyek.siRelawan.dto.request.UpdateUserRequestDTO;
import propensi.proyek.siRelawan.model.UserModel;
import propensi.proyek.siRelawan.service.UserService;



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
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        boolean isAuthenticated = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (isAuthenticated) {
            // Redirect to home.html if isAuthenticated return True
            HttpSession session = request.getSession();
            // Store current user in session
            session.setAttribute("currentUser", loginRequest.getUsername());
            session.setAttribute("currentRole", userService.getCurrentUser(loginRequest.getUsername()).getRole().toString());
            redirectAttributes.addFlashAttribute("success", "Login Successful!");
            return "redirect:/home";
        } else {
            // Add error message and redirect back to login page
            redirectAttributes.addFlashAttribute("warning", "Incorrect username or password");
            return "redirect:/login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
        // Invalidate the session to delete associated user at previous session
        session.invalidate();
        
        // Redirect to login page with a success message
        redirectAttributes.addFlashAttribute("success", "You have been logged out successfully.");
        return "redirect:/login";
    }

    @PostMapping("/user/delete")
    public String deleteAccount(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        String currentUsername = (String) session.getAttribute("currentUser");
        
        // Perform delete account operation
        userService.deleteUser(currentUsername);
        
        // Invalidate the session to log out the user
        session.invalidate();
        
        redirectAttributes.addFlashAttribute("success", "Your account has been successfully deleted.");
        return "redirect:/login";
    }
    

    @GetMapping("user/profile")
    public String formUpdate(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String currentUsername = (String) session.getAttribute("currentUser");
        UserModel currentUserData = userService.getCurrentUser(currentUsername);
        model.addAttribute("accountData", currentUserData);
        return "user/profileAccount";
    }

    @PostMapping("/user/profile")
    public String updateAccount(@RequestParam(required = false) Long NIK,
                                @RequestParam(required = false) Long NPWP,
                                @RequestParam(required = false) Long noRekening,
                                HttpServletRequest request,
                                RedirectAttributes redirectAttributes, 
                                UpdateUserRequestDTO updateUserRequestDTO,
                                Model model) {
        HttpSession session = request.getSession();
        String currentUsername = (String) session.getAttribute("currentUser");

        // Get current user data
        UserModel currentUserData = userService.getCurrentUser(currentUsername);
        updateUserRequestDTO.setEmail(currentUserData.getEmail());
        updateUserRequestDTO.setFullName(currentUserData.getFullName());
        updateUserRequestDTO.setUsername(currentUserData.getUsername());
        updateUserRequestDTO.setPassword(currentUserData.getPassword());
        updateUserRequestDTO.setNomorWA(currentUserData.getNomorWA());

        // Validate NIK, NPWP, and noRekening
        boolean isValid = true;

        if (NIK != null && NIK.toString().length() != 16) {
            redirectAttributes.addFlashAttribute("warning", "NIK must be 16 digits long.");
            isValid = false;
        }

        if (NPWP != null && NPWP.toString().length() != 15) {
            redirectAttributes.addFlashAttribute("warning", "NPWP must be 15 digits long.");
            isValid = false;
        }

        if (noRekening.toString().length() == 11 || noRekening.toString().length() == 14) {
            redirectAttributes.addFlashAttribute("warning", "Bank account number cannot be 11 or 14 digits");
            isValid = false;
        }

        if (noRekening != null && noRekening.toString().length() < 10 || noRekening.toString().length() > 16) {
            redirectAttributes.addFlashAttribute("warning", "Bank account number must be 10 - 16 digits");
            isValid = false;
        }

        // If any validation fails, redirect back to the profile page with warnings
        if (!isValid) {
            return "redirect:/user/profile";
        }

        // Update if all
        updateUserRequestDTO.setNik(NIK);
        updateUserRequestDTO.setNpwp(NPWP);
        updateUserRequestDTO.setNoRekening(noRekening);
        // Save the updated user data
        userService.updateUser(currentUsername, updateUserRequestDTO);

        // Add the updated user data to the model
        model.addAttribute("accountData", currentUserData);
        redirectAttributes.addFlashAttribute("success", "Account Updated!");

        // Redirect back to the profile page
        return "redirect:/user/profile";
    }
}
