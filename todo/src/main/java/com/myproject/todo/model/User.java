package com.myproject.todo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY : Auto Increment
    private Long id;

    private String email;

    @Enumerated(EnumType.STRING)
    private State state;

    private String password;

    private String providerId;
    private String provider;

    private boolean quit;
    private LocalDateTime quitDate;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() { // protected 사용 이유 : JPA 가 메서드에 접근하기 위해 필요하다.
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
