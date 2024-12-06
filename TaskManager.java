import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    public List<Task> getIncompleteTaskList() {
        List<Task> incompleteTasks = new ArrayList<>(); 
        for (Task task : taskList) { 
            if (!task.isCompleted()) { 
                incompleteTasks.add(task);
            }
        }
        return incompleteTasks;
    }

    // Get all completed tasks
    public List<Task> getCompletedTaskList() {
        List<Task> completedTasks = new ArrayList<>(); 
        for (Task task : taskList) { 
            if (task.isCompleted()) { 
                completedTasks.add(task); 
            }
        }
        return completedTasks; 
    }

    // Mark task as completed by index in task list
    public boolean markTaskAsCompleted(int index) {
        if (index >= 0 && index < taskList.size()) {
            Task task = taskList.get(index);
            task.setCompleted(true);
            return true; // Successfully marked as completed
        }
        return false; // Invalid index
    }

    // Sort task list by due date
    public List<Task> getTasksSortedByDate() {
        List<Task> sortedTasks = new ArrayList<>(taskList);
        sortedTasks.sort(Comparator.comparing(Task::getDueDate));
        return sortedTasks;
    }
}
