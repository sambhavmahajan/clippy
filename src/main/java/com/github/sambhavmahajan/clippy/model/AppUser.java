package com.github.sambhavmahajan.clippy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppUser implements UserDetails {
    @Id
    private String username;
    private String password;
    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
        clips = new ArrayList<>();
        history = new ArrayList<>();
    }
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Clipboard> clips;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> history;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public void addClip(Clipboard clipboard) {
        clips.add(clipboard);
    }
    public void addHistory(String history) {
        this.history.add(history);
    }
}
