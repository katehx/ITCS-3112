import java.util.ArrayList;

public class Course {
    // Required fields
    private String courseName;
    private int courseID;
    private int creditNum;
    private final ArrayList<AssignmentType> assignmentTypes;
    private final ArrayList<Assignment> assignments;

    // Optional fields
    private String professorName;
    private String professorEmail;
    private String professorOffice;
    private String professorOfficeHours;
    private String meetingTimes;
    private String meetingLocation;

    // Constructor with only required fields
    public Course(String courseName, int courseID, int creditNum, ArrayList<Assignment> assignments) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.creditNum = creditNum;
        this.assignmentTypes = new ArrayList<>();
        this.assignments = assignments != null ? assignments : new ArrayList<>();
        this.professorName = null;
        this.professorEmail = null;
        this.professorOffice = null;
        this.professorOfficeHours = null;
        this.meetingTimes = null;
        this.meetingLocation = null;
    }

    // Constructor with required fields + professor name and email
    public Course(String courseName, int courseID, int creditNum, ArrayList<Assignment> assignments, String professorName, String professorEmail) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.creditNum = creditNum;
        this.assignmentTypes = new ArrayList<>();
        this.assignments = assignments != null ? assignments : new ArrayList<>();
        this.professorName = professorName;
        this.professorEmail = professorEmail;
        this.professorOffice = null;
        this.professorOfficeHours = null;
        this.meetingTimes = null;
        this.meetingLocation = null;
    }

    // Constructor with required fields + professor name
    public Course(String courseName, int courseID, int creditNum, ArrayList<Assignment> assignments, String professorName) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.creditNum = creditNum;
        this.assignmentTypes = new ArrayList<>();
        this.assignments = assignments != null ? assignments : new ArrayList<>();
        this.professorName = professorName;
        this.professorEmail = null;
        this.professorOffice = null;
        this.professorOfficeHours = null;
        this.meetingTimes = null;
        this.meetingLocation = null;
    }

    // Constructor with required fields + professor email
    public Course(String courseName, int courseID, int creditNum, ArrayList<Assignment> assignments, String professorEmail) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.creditNum = creditNum;
        this.assignmentTypes = new ArrayList<>();
        this.assignments = assignments != null ? assignments : new ArrayList<>();
        this.professorName = null;
        this.professorEmail = professorEmail;
        this.professorOffice = null;
        this.professorOfficeHours = null;
        this.meetingTimes = null;
        this.meetingLocation = null;
    }

    // Constructor with required fields + professor name, email, office
    public Course(String courseName, int courseID, int creditNum, ArrayList<Assignment> assignments, String professorName, String professorEmail, String professorOffice) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.creditNum = creditNum;
        this.assignmentTypes = new ArrayList<>();
        this.assignments = assignments != null ? assignments : new ArrayList<>();
        this.professorName = professorName;
        this.professorEmail = professorEmail;
        this.professorOffice = professorOffice;
        this.professorOfficeHours = null;
        this.meetingTimes = null;
        this.meetingLocation = null;
    }

    // Constructor with required fields + meeting time
    public Course(String courseName, int courseID, int creditNum, ArrayList<Assignment> assignments, String meetingTimes) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.creditNum = creditNum;
        this.assignmentTypes = new ArrayList<>();
        this.assignments = assignments != null ? assignments : new ArrayList<>();
        this.professorName = null;
        this.professorEmail = null;
        this.professorOffice = null;
        this.professorOfficeHours = null;
        this.meetingTimes = meetingTimes;
        this.meetingLocation = null;
    }

    // Constructor with required fields + meeting time and meeting location
    public Course(String courseName, int courseID, int creditNum, ArrayList<Assignment> assignments, String meetingTimes, String meetingLocation) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.creditNum = creditNum;
        this.assignmentTypes = new ArrayList<>();
        this.assignments = assignments != null ? assignments : new ArrayList<>();
        this.professorName = null;
        this.professorEmail = null;
        this.professorOffice = null;
        this.professorOfficeHours = null;
        this.meetingTimes = meetingTimes;
        this.meetingLocation = meetingLocation;
    }

    // Method to add an AssignmentType
    public void addAssignmentType(AssignmentType assignmentType) {
        assignmentTypes.add(assignmentType);
    }

    // Method to add an Assignment
    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    // Getters and Setters for all variables

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getCreditNum() {
        return creditNum;
    }

    public void setCreditNum(int creditNum) {
        this.creditNum = creditNum;
    }

    public ArrayList<AssignmentType> getAssignmentTypes() {
        return assignmentTypes;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getProfessorEmail() {
        return professorEmail;
    }

    public void setProfessorEmail(String professorEmail) {
        this.professorEmail = professorEmail;
    }

    public String getProfessorOffice() {
        return professorOffice;
    }

    public void setProfessorOffice(String professorOffice) {
        this.professorOffice = professorOffice;
    }

    public String getProfessorOfficeHours() {
        return professorOfficeHours;
    }

    public void setProfessorOfficeHours(String professorOfficeHours) {
        this.professorOfficeHours = professorOfficeHours;
    }

    public String getMeetingTimes() {
        return meetingTimes;
    }

    public void setMeetingTimes(String meetingTimes) {
        this.meetingTimes = meetingTimes;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }
}
