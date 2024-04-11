package propensi.proyek.siRelawan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import propensi.proyek.siRelawan.model.UserModel;
import propensi.proyek.siRelawan.service.UserService;

@ControllerAdvice
public class BaseController {
    @Autowired
    UserService userService;

    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpServletRequest request, HttpSession session) {
        // Assuming you have a service that retrieves the logged-in user
        session = request.getSession();
        String currentUsername = (String) session.getAttribute("currentUser");
        UserModel user = userService.getCurrentUser(currentUsername);
        model.addAttribute("user", user);
    }
}
