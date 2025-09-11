package com._6.TaskBoard.Repository;

import com._6.TaskBoard.Entity.Task;
import com._6.TaskBoard.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByCreatedBy(User user);
    List<Task> findByCreatedByOrAssignedToUsers(User createdBy, User assignedToUsers);

    @Query(value = "SELECT t.id AS task_id, t.description, t.created_at, t.updated_at, t.due_date, t.status, t.title, ta.user_id, t.created_by " +
            "FROM tasks t " +
            "JOIN task_assignees ta ON t.id = ta.task_id " +
            "WHERE ta.user_id = :userId", nativeQuery = true)
    List<Object[]> findTasksAssignedToUser(@Param("userId") Integer userId);
}
