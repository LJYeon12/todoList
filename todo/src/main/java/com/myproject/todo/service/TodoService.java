package com.myproject.todo.service;

import com.myproject.todo.model.Todo;
import com.myproject.todo.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Transactional
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo findByTodo(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("일정이 존재하지 않습니다."));
    }
    @Transactional
    public void updateTodo(Long id, String title, boolean isCompleted, LocalDateTime dateTime) {
        todoRepository.updateTodo(id, title, isCompleted, dateTime);
        // repository 에서 자동으로 업데이트 하니 return 할 필요가 없다.
    }

    @Transactional
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
    public List<Todo> getAllTodos() {
        // 리펙터링 나중에 (today 그냥 하나만 호출해서 지정해보자 범위 구하지 않고)
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);
        return todoRepository.findAllTodosWithPriority(startOfDay, endOfDay);
    }

    public List<Todo> getTodoByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return todoRepository.findAllByDueTime(startOfDay, endOfDay);
    } // 특정 날짜 범위 Todo 조회

    public List<Todo> getTodoByIsCompleted(boolean isCompleted)
    {
        return todoRepository.findByIsCompletedOrderByUpdatedAtDesc(isCompleted);
    } // isCompleted 기준으로 todo 조회
}
