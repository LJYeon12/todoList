package com.myproject.todo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    private boolean isCompleted;

    @Column(nullable = false)
    private LocalDateTime dueTime; // 만약 시간이 정해지지 않을 경우 00:00으로 설정

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Todo(String title, String description, LocalDateTime dueTime) {
        this.title = title;
        this.description = description;
        this.dueTime = dueTime;
    }
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
