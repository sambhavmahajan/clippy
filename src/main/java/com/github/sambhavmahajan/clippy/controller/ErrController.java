package com.github.sambhavmahajan.clippy.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrController implements ErrorController {
    @RequestMapping("/error")
    public String handle(HttpServletRequest request, Model model) {
        model.addAttribute("message",
                request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
        model.addAttribute("status",
                request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        return "error";
    }
}
