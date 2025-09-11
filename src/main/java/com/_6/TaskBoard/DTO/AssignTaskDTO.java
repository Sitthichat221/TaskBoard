package com._6.TaskBoard.DTO;

import java.util.List;

public class AssignTaskDTO {

    private Integer taskId;
    private List<String> assignedToEmails; // ใช้ List เพื่อเก็บหลายอีเมล

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public List<String> getAssignedToEmails() {
        return assignedToEmails;
    }

    public void setAssignedToEmails(List<String> assignedToEmails) {
        this.assignedToEmails = assignedToEmails;
    }
}
