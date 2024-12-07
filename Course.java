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

    // Add an AssignmentType
    public void addAssignmentType(AssignmentType assignmentType) {
        for (AssignmentType existingType : assignmentTypes) { // Check if assignment type already exists
            if (existingType.getName().equalsIgnoreCase(assignmentType.getName())) {
                System.out.println("Assignment type already exists: " + assignmentType.getName());
                return;
            }
        }
        assignmentTypes.add(assignmentType);
    }

    // Get a specific AssignmentType by name
    public AssignmentType getAssignmentTypeByName(String name) {
        for (AssignmentType assignmentType : assignmentTypes) {
            if (assignmentType.getName().equalsIgnoreCase(name)) {
                return assignmentType;
            }
        }
        return null; // Not found
    }

    // Add an Assignment
    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    // Get assignments by AssignmentType
    public ArrayList<Assignment> getAssignmentsByType(String typeName) {
        ArrayList<Assignment> filteredAssignments = new ArrayList<>();
        for (Assignment assignment : assignments) {
            if (assignment.getAssignmentType().equalsIgnoreCase(typeName)) {
                filteredAssignments.add(assignment);
            }
        }
        return filteredAssignments;
    }

    // Calculate grade weight for an assignment type
    public double calculateAssignmentWeight(String typeName) {
        AssignmentType type = getAssignmentTypeByName(typeName);
        if (type == null) {
            throw new IllegalArgumentException("Assignment type not found: " + typeName);
        }

        double typeWeight = type.getWeight(); // Overall weight of the assignment type
        int totalAssignmentsInType = getAssignmentsByType(typeName).size(); // Find how many assignments of the type exist

        if (totalAssignmentsInType == 0) {
            throw new IllegalArgumentException("No assignments found for type: " + typeName);
        }

        return typeWeight / totalAssignmentsInType; // Weight divided by assignments of type gives rough estimate of grade percentage by assignment
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
