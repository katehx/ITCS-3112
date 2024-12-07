import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class TaskManager {
    private final ArrayList<Task> taskList;

    // Constructor
    public TaskManager() {
        taskList = new ArrayList<>();
    }

    // Add a task to task list
    public void addTask(Task task) {
        taskList.add(task);
    }

    // Remove a task
    public boolean removeTaskByIndex(int index) {
        if (index >= 0 && index < taskList.size()) {
            taskList.remove(index);
            return true; // Successfully removed
        }
        return false; // Invalid index
    }

    // Get all tasks from task list
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    // Get all incomplete tasks
    public ArrayList<Task> getIncompleteTaskList() {
        ArrayList<Task> incompleteTasks = new ArrayList<>(); 
        for (Task task : taskList) { 
            if (!task.isCompleted()) { 
                incompleteTasks.add(task);
            }
        }
        return incompleteTasks;
    }

    // Mark task as completed by index in task list
    public boolean markTaskAsCompleted(int index) {
        if (index >= 0 && index < taskList.size()) {
            Task task = taskList.get(index);
            task.setCompleted(true);
            task.setCompletionDate(LocalDate.now()); // Set the current date as the completion date
            return true;
        }
        return false;
    }

    // Get all completed tasks
    public ArrayList<Task> getCompletedTaskList() {
        ArrayList<Task> completedTasks = new ArrayList<>(); 
        for (Task task : taskList) { 
            if (task.isCompleted()) { 
                completedTasks.add(task); 
            }
        }
        return completedTasks; 
    }

    // Sort task list by due date
    public ArrayList<Task> getTasksSortedByDate() {
        ArrayList<Task> sortedTasks = new ArrayList<>(taskList);
        sortedTasks.sort(Comparator.comparing(Task::getDueDate));
        return sortedTasks;
    }

}