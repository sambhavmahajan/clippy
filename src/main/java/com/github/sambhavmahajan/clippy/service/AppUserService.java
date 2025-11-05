package com.github.sambhavmahajan.clippy.service;

import com.github.sambhavmahajan.clippy.dto.AppUserDTO;
import com.github.sambhavmahajan.clippy.dto.ClipboardDTO;
import com.github.sambhavmahajan.clippy.model.AppUser;
import com.github.sambhavmahajan.clippy.model.Clipboard;
import com.github.sambhavmahajan.clippy.repo.AppUserRepo;
import com.github.sambhavmahajan.clippy.repo.ClipboardRepo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserService implements UserDetailsService {
    private AppUserRepo repo;
    private ClipboardRepo clipRepo;
    private PasswordEncoder passwordEncoder;
    public AppUserService(AppUserRepo repo, ClipboardRepo clipRepo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.clipRepo = clipRepo;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public AppUser saveUser(AppUserDTO appUserDTO) {
        AppUser usr = new AppUser(appUserDTO.getUsername(), passwordEncoder.encode(appUserDTO.getPassword()));
        repo.save(usr);
        return usr;
    }
    @Transactional
    public String addClip(ClipboardDTO clipDTO, Principal principal) {
        Clipboard clip = new Clipboard();
        clip.setContent(clipDTO.getContent());
        AppUser usr = repo.findByUsername(principal.getName()).orElseThrow(() ->
                        new UsernameNotFoundException(principal.getName()));
        clip.setOwner(usr);
        usr.addClip(clip);
        clip.setUuid(UUID.randomUUID().toString().substring(0, 5).toLowerCase());
        repo.save(usr);
        return clip.getUuid();
    }
    public void addHistory(Clipboard clip, LocalDateTime tme, Principal principal) {
        AppUser usr = repo.findByUsername(principal.getName()).orElseThrow(() ->
                        new UsernameNotFoundException(principal.getName()));
        usr.addHistory(tme.toString() + " : " + clip.getContent());
        repo.save(usr);
    }
    public List<String> getAllHistory(Principal principal) {
        AppUser usr = repo.findByUsername(principal.getName()).orElseThrow(() ->
                        new UsernameNotFoundException(principal.getName()));
        return usr.getHistory();
    }
    public List<Clipboard> getAllClips(Principal principal) {
        AppUser usr = repo.findByUsername(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException(principal.getName())
        );
        return usr.getClips();
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
    public AppUser getUserByUsername(String name) {
        Optional<AppUser> usr = repo.findByUsername(name);
        if(usr.isPresent()) return usr.get();
        return null;
    }
    public Clipboard getClipByUuid(String uuid) {
        Optional<Clipboard> clip = clipRepo.findByUuid(uuid);
        if(clip.isPresent()) return clip.get();
        return null;
    }
}
