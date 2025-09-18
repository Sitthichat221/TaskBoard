package com._6.TaskBoard.Controllers;

import com._6.TaskBoard.DTO.AssignTaskDTO;
import com._6.TaskBoard.DTO.TaskDTO;
import com._6.TaskBoard.Entity.Task;
import com._6.TaskBoard.Services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        Task task = taskService.createTask(taskDTO);
        return ResponseEntity.ok(task);
    }

    @PostMapping("/assign")
    public ResponseEntity<Task> assignTask(@RequestBody AssignTaskDTO assignTaskDTO) {
        Task task = taskService.assignTask(assignTaskDTO);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/mytasks")
    public ResponseEntity<List<Task>> getMyTasks() {
        List<Task> tasks = taskService.getTasksForUser();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/assigned")
    public ResponseEntity<List<Object[]>> getAssignedTasks() {
        List<Object[]> tasks = taskService.getTasksAssignedToUser();
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("Task deleted successfully");
    }

    @PutMapping("/updateStatus/{taskId}")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Integer taskId, @RequestBody String newStatus) {
        Task updatedTask = taskService.updateTaskStatus(taskId, newStatus);
        return ResponseEntity.ok(updatedTask);
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer taskId, @RequestBody TaskDTO taskDTO) {
        Task updatedTask = taskService.updateTask(taskId, taskDTO);
        return ResponseEntity.ok(updatedTask);
    }


}

