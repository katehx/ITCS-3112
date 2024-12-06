
public class User{
    private String name;
    private final TaskManager taskManager;
    private final SchoolManager schoolManager;

    // Constructor
    public User(String name) {
        this.name = name;
        this.taskManager = new TaskManager();
        this.schoolManager = new SchoolManager();
    }

    // Name getter
    public String getName() {
        return name;
    }

    // Name Setter
    public void setName(String name) {
        this.name = name;
    }

    // Task Manager getter
    public TaskManager getTaskManager() {
        return taskManager;
    }

    // School Manager getter 
    public SchoolManager getSchoolManager() {
        return schoolManager;
    }
}
