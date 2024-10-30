public class Assignment extends Task {
    // Additional field specific to Assignment
    private AssignmentType assignmentType;

    // Constructor
    public Assignment(int year, int month, int day, String taskDescription, AssignmentType assignmentType) {
        // Call the superclass (Task) constructor
        super(year, month, day, taskDescription);
        this.assignmentType = assignmentType;
    }

    // Getter and Setter for assignmentType
    public AssignmentType getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(AssignmentType assignmentType) {
        this.assignmentType = assignmentType;
    }

    // Override toString to include Assignment-specific details
    @Override
    public String toString() {
        return super.toString() + ", Assignment Type: " + assignmentType.getName();
    }
}
