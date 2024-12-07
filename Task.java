import java.time.LocalDate;

public class Task {
    private String taskDescription;
    private LocalDate dueDate;
    private boolean completed;
    private LocalDate completionDate;

    // Constructor
    public Task(int year, int month, int day, String taskDescription) {
        this.dueDate = LocalDate.of(year, month, day);
        this.taskDescription = taskDescription;
        this.completed = false; // Default to incomplete
    }

    // Getter methods
    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDate getCompletionDate() {
    return completionDate;
}

    // Setter methods
    public void setDueDate(LocalDate newDueDate) {
        this.dueDate = newDueDate;
    }

    public void setTaskDescription(String newTaskDescription) {
        this.taskDescription = newTaskDescription;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    // Check task is overdue
    public boolean isOverdue() {
        return !completed && dueDate.isBefore(LocalDate.now());
    }

}
