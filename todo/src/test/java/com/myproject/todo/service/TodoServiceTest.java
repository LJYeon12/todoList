package com.myproject.todo.service;

import com.myproject.todo.model.Todo;
import com.myproject.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TodoServiceTest {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;
    @BeforeEach
    void setUp() {
        // 테스트용 임시 데이터 삽입
        todoRepository.save(new Todo("test1", null, LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT)));
        todoRepository.save(new Todo("test2", "testtest",LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.MIDNIGHT)));
    }

    @Test
    @DisplayName("일정 생성 성공")
    void createTodoTest1() {
        Todo todo = todoService.createTodo(new Todo("createTodo", "test", LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT)));
        assertThat(todo.getTitle()).isEqualTo("createTodo");
    }

    @Test
    @DisplayName("일정 생성 실패")
    void createTodoTest2() {
        //given
        Todo todo = new Todo(null, "fail", LocalDateTime.now());
        //when
        //then
        assertThrows(DataIntegrityViolationException.class,
                ()-> {todoService.createTodo( new Todo(null, "fail", LocalDateTime.now()));});
    }

    @Test
    @DisplayName("일정 상세 정보 보기")
    void findByTodoTest() {
        // given: 테스트용 데이터 생성
        Todo savedTodo = todoRepository.save(new Todo("Meeting", "Discuss project", LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT)));
        Long todoId = savedTodo.getId(); // 저장된 Todo의 ID 가져오기

        // when: 해당 ID로 Todo 찾기
        Todo foundTodo = todoService.findByTodo(todoId);

        // then: Todo가 올바르게 조회되었는지 검증
        assertThat(foundTodo).isNotNull(); // 조회된 Todo가 null이 아닌지 확인
        assertThat(foundTodo.getId()).isEqualTo(todoId); // ID가 일치하는지 확인
        assertThat(foundTodo.getTitle()).isEqualTo("Meeting"); // 제목이 일치하는지 확인
        assertThat(foundTodo.getDescription()).isEqualTo("Discuss project"); // 설명이 일치하는지 확인
        assertThat(foundTodo.getDueTime()).isEqualTo(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT)); // 마감 날짜가 일치하는지 확인
    }

    @Test
    void updateTodo() {
    }

    @Test
    void deleteTodo() {
    }

    @Test
    void getAllTodos() {
    }

    @Test
    void getTodoByDate() {
    }

    @Test
    void getTodoByIsCompleted() {
    }
}