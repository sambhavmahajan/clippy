package com.github.sambhavmahajan.clippy.controller;

import com.github.sambhavmahajan.clippy.dto.AppUserDTO;
import com.github.sambhavmahajan.clippy.dto.RegisterDTO;
import com.github.sambhavmahajan.clippy.service.AppUserService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AuthController {
    private AppUserService appUserService;
    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("error", null);
        model.addAttribute("registerDTO",  new RegisterDTO());
        model.addAttribute("success", null);
        return "register";
    }
    @PostMapping("/register")
    public String registerPost(Model model, @ModelAttribute RegisterDTO registerDTO) {
        if(appUserService.getUserByUsername(registerDTO.getUsername()) != null) {
            model.addAttribute("error", "User with username "
                    + registerDTO.getUsername() + " already exists");
            model.addAttribute("registerDTO",  registerDTO);
            model.addAttribute("success", null);
        } else if(registerDTO.getPassword().length() <= 7) {
            model.addAttribute("error", "Password should be of atleast 8 characters");
            model.addAttribute("registerDTO",  registerDTO);
            model.addAttribute("success", null);
        } else {
            AppUserDTO dto = new  AppUserDTO();
            dto.setUsername(registerDTO.getUsername());
            dto.setPassword(registerDTO.getPassword());
            appUserService.saveUser(dto);
            model.addAttribute("registerDTO", registerDTO);
            model.addAttribute("error", null);
            model.addAttribute("success", "User registered successfully");
        }
        return "register";
    }
}
