package propensi.proyek.siRelawan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            redirectAttributes.addFlashAttribute("success", "Login Successful!");
            return "redirect:/home";
        } else {
            // Add error message and redirect back to login page
            redirectAttributes.addFlashAttribute("error", "Incorrect username or password");
            return "user/login";
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

    @GetMapping("user/update")
    public String formUpdate(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String currentUsername = (String) session.getAttribute("currentUser");
        UserModel currentUserData = userService.getCurrentUser(currentUsername);
        model.addAttribute("accountData", currentUserData);
        return "profileAccount";
    }

    @PostMapping("user/update")
    public String updateAccount(@Valid @RequestBody UpdateUserRequestDTO updateUserRequestDTO, 
                                HttpServletRequest request, 
                                RedirectAttributes redirectAttributes, 
                                Model model) {
        HttpSession session = request.getSession();
        String currentUsername = (String) session.getAttribute("currentUser");
        // Update user using UpdateUserRequestDTO
        userService.updateUser(currentUsername, updateUserRequestDTO);
        UserModel currentUserData = userService.getCurrentUser(currentUsername);

        // Add the updated user data to the model
        model.addAttribute("accountData", currentUserData);
        redirectAttributes.addFlashAttribute("success", "Account Updated!");
        // Return the view name
        return "profileAccount";
    }
}
