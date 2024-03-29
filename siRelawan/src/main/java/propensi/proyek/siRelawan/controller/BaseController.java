package propensi.proyek.siRelawan.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import propensi.proyek.siRelawan.security.jwt.JwtUtils;
import propensi.proyek.siRelawan.service.UserService;

@Controller
public class BaseController {
    @Autowired
    UserService userService;

    // @Autowired
    // JwtUtils jwtUtils;
}
