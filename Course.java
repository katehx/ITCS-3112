import java.util.ArrayList;

public class Course {
    // Fields
    private String courseName;
    private int courseID;
    private int creditNum;
    private final ArrayList<AssignmentType> assignmentTypes;
    private final ArrayList<Assignment> assignments;

    // Constructor 
    public Course(String courseName, int courseID, int creditNum) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.creditNum = creditNum;
        this.assignmentTypes = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    // Method to add an AssignmentType
    public void addAssignmentType(AssignmentType assignmentType) {
        assignmentTypes.add(assignmentType);
    }

    // Method to add an Assignment
    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    // Getters
    public String getCourseName() {
        return courseName;
    }

    public int getCourseID() {
        return courseID;
    }

    public int getCreditNum() {
        return creditNum;
    }

    public ArrayList<AssignmentType> getAssignmentTypes() {
        return assignmentTypes;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    // Setters
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
   
    public void setCreditNum(int creditNum) {
        this.creditNum = creditNum;
    }

}
