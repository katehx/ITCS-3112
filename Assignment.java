public class Assignment extends Task {
    // Additional field specific to Assignment
    private String assignmentType;

    // Constructor
    public Assignment(int year, int month, int day, String taskDescription, String assignmentType) {
        // Call the superclass (Task) constructor
        super(year, month, day, taskDescription);
        this.assignmentType = assignmentType;
    }

    // Getter and Setter for assignmentType
    public String getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(String assignmentType) {
        this.assignmentType = assignmentType;
    }

}