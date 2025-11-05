package com.github.sambhavmahajan.clippy.repo;

import com.github.sambhavmahajan.clippy.model.AppUser;
import com.github.sambhavmahajan.clippy.model.Clipboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByUsername(String username);
    List<String> findHistoryByUsername(String username);
    List<Clipboard> findClipsByUsername(String username);
}
