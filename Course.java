import java.util.ArrayList;

public class Course {
    // fields
    String courseName = "";
    String courseID = "";
    int creditNum;
    ArrayList<Assignment> assignments = new ArrayList<Asignment>;


    // constructor
    public Course(String courseName, String courseID, int creditNum, ArrayList<Assignment> assignments){
        this.courseName = courseName;
        this.courseID = courseID;
        this.creditNum = creditNum;
        this.assignments = assignments;
    }

}//end class
