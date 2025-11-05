package com.github.sambhavmahajan.clippy.repo;

import com.github.sambhavmahajan.clippy.model.Clipboard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClipboardRepo extends JpaRepository<Clipboard, Long> {
    Optional<Clipboard> findById(Long id);
    Optional<Clipboard> findByUuid(String uuid);
}
