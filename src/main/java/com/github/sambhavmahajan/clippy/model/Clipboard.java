package com.github.sambhavmahajan.clippy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Clipboard {
    @Id
    @GeneratedValue
    private Long id;
    @Size(max = 500, message = "Clipboard cannot exceed 500 characters")
    @Column(length = 500)
    private String content;
    @ManyToOne
    private AppUser owner;
    @CreationTimestamp
    private Instant timestamp;
    @Column(unique = true)
    private String uuid;
}
