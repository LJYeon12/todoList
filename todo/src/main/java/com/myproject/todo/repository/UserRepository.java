package com.myproject.todo.repository;

import com.myproject.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //  JpaRepository<User, Long> : Long -> 기본키로 조회
}
