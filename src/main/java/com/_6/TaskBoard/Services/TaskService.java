package com._6.TaskBoard.Services;

import com._6.TaskBoard.DTO.AssignTaskDTO;
import com._6.TaskBoard.DTO.TaskDTO;
import com._6.TaskBoard.Entity.Task;
import com._6.TaskBoard.Entity.User;
import com._6.TaskBoard.Repository.TaskRepository;
import com._6.TaskBoard.Repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // สร้างงานใหม่
    public Task createTask(TaskDTO taskDTO) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> creator = userRepository.findByEmail(currentUserEmail);
        Optional<User> assignedTo = taskDTO.getAssignedToEmail() != null ? userRepository.findByEmail(taskDTO.getAssignedToEmail()) : Optional.empty();

        if (creator.isPresent()) {
            Task task = new Task();
            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            task.setStatus(taskDTO.getStatus());
            task.setDueDate(taskDTO.getDueDate());
            task.setCreatedBy(creator.get()); // ผู้สร้างงาน
            assignedTo.ifPresent(user -> task.addAssignedToUser(user)); // มอบหมายผู้รับผิดชอบ
            return taskRepository.save(task);
        }

        throw new RuntimeException("Creator not found");
    }

    // มอบหมายงานให้กับผู้รับผิดชอบหลายคน
    public Task assignTask(AssignTaskDTO assignTaskDTO) {
        Optional<Task> taskOptional = taskRepository.findById(assignTaskDTO.getTaskId());
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();

            List<User> assignedUsers = userRepository.findByEmailIn(assignTaskDTO.getAssignedToEmails());
            if (!assignedUsers.isEmpty()) {
                task.getAssignedToUsers().addAll(assignedUsers); // เพิ่มผู้รับผิดชอบหลายคน
                return taskRepository.save(task);
            } else {
                throw new RuntimeException("Assigned users not found");
            }
        }

        throw new RuntimeException("Task not found");
    }

    public List<Task> getTasksForUser() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> currentUser = userRepository.findByEmail(currentUserEmail);
        if (currentUser.isPresent()) {
            return taskRepository.findByCreatedBy(currentUser.get());
        }
        throw new RuntimeException("User not found");
    }

    public List<Object[]> getTasksAssignedToUser() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer userId = getUserIdFromEmail(currentUserEmail);

        return taskRepository.findTasksAssignedToUser(userId);
    }

    private Integer getUserIdFromEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteTask(Integer taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();

            String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            Integer userId = getUserIdFromEmail(currentUserEmail);

            if (task.getCreatedBy().getId().equals(userId)) {
                taskRepository.delete(task);
            } else {
                throw new RuntimeException("You do not have permission to delete this task");
            }
        } else {
            throw new RuntimeException("Task not found");
        }
    }

    public Task updateTaskStatus(Integer taskId, String newStatus) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();

            task.setStatus(newStatus);

            return taskRepository.save(task);
        }

        throw new RuntimeException("Task not found");
    }

    public Task updateTask(Integer taskId, TaskDTO taskDTO) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new RuntimeException("Task not found");
        }

        Task task = taskOptional.get();

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer userId = getUserIdFromEmail(currentUserEmail);

        if (!task.getCreatedBy().getId().equals(userId)) {
            throw new RuntimeException("You do not have permission to edit this task");
        }

        if (taskDTO.getTitle() != null) task.setTitle(taskDTO.getTitle());
        if (taskDTO.getDescription() != null) task.setDescription(taskDTO.getDescription());
        if (taskDTO.getStatus() != null) task.setStatus(taskDTO.getStatus());
        if (taskDTO.getDueDate() != null) task.setDueDate(taskDTO.getDueDate());

        return taskRepository.save(task);
    }

}
