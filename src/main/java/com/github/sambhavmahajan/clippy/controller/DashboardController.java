package com.github.sambhavmahajan.clippy.controller;

import com.github.sambhavmahajan.clippy.dto.ClipboardDTO;
import com.github.sambhavmahajan.clippy.model.AppUser;
import com.github.sambhavmahajan.clippy.model.Clipboard;
import com.github.sambhavmahajan.clippy.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping(value={"/dashboard", "/", ""})
public class DashboardController {
    private AppUserService appUserService;
    public DashboardController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
    public void injectDashboard(Model model, Principal principal) {
        AppUser usr = appUserService.getUserByUsername(principal.getName());
        while(usr == null) usr = appUserService.getUserByUsername(principal.getName());
        model.addAttribute("clips", usr.getClips());
        model.addAttribute("history", usr.getHistory());
        model.addAttribute("username", usr.getUsername());
    }
    @GetMapping
    public String dashboard(Model model, Principal principal, @RequestParam(required = false) String uuid) {
        System.out.println("I am at dashboard");
        if (uuid != null && !uuid.isEmpty()) {
            System.out.println("User exists");
            Clipboard clip = appUserService.getClipByUuid(uuid.trim());
            String content = "No content found!";
            if(clip != null) {
                content = clip.getContent();
                appUserService.addHistory(clip, LocalDateTime.now(), principal);
                model.addAttribute("copiedContent", "Content: " +
                        appUserService.getClipByUuid(uuid).getContent());
            } else {
                model.addAttribute("error",
                        uuid + " is an invalid code");
            }
        }
        injectDashboard(model, principal);
        return "dashboard";
    }
    @PostMapping
    public String dashboardPostClip(Model model, Principal principal, @RequestParam String content) {
        injectDashboard(model, principal);
        ClipboardDTO clipboard = new ClipboardDTO();
        clipboard.setContent(content);
        String code = appUserService.addClip(clipboard, principal);
        model.addAttribute("copiedContent", "Code: " + code);
        return "dashboard";
    }
}
