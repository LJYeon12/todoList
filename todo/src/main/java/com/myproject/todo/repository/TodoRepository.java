package com.myproject.todo.repository;

import com.myproject.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByIsCompletedOrderByUpdatedAtDesc(boolean isCompleted);
    @Query("SELECT t FROM Todo t " +
            "WHERE t.dueTime >= :startOfToday AND t.dueTime <= :endOfToday " +
            "ORDER BY t.isCompleted ASC, t.updatedAt DESC")
    List<Todo> findAllByDueTime(LocalDateTime start, LocalDateTime end);

    @Query("select t from Todo t " +
            "order by " +
            "case when t.dueTime >= :startOfToday and t.dueTime <= :endOfToday then 0 " + // 0은 우선순위를 의미, 0이 최우선 순위
            "when t.dueTime > :endOfToday then 1 " +
            "else 2 end, " + // end : 조건문이 끝나는 것을 의미
            "t.isCompleted asc, " + // 미완료 일정이 완료 일정보다 우선
            "t.updatedAt desc") // 같은 미완료 또는 완료 일정에서는 updatedAt을 기쥰우로 내림차순)
    List<Todo> findAllTodosWithPriority(@Param("startOfToday") LocalDateTime startOfToday, @Param("endOfToday")LocalDateTime endOfToday);

    @Modifying // 이 어노테이션을 이용해 직접 업데이트 하자 -> Patch를 사용할 때 유리하다.
    @Query("update Todo t set t.title = :title, t.isCompleted = :isCompleted, t.dueTime = :dueTime where t.id = :id")
    void updateTodo(@Param("id") Long id,
                    @Param("title") String title,
                    @Param("isCompleted") boolean isCompleted,
                    @Param("dueTime") LocalDateTime dueTime);
}
