import java.util.ArrayList;

public class Semester {
    private final ArrayList<Course> courses;
    private final int year;
    private final String season;

    // Constructor
    public Semester(int year, String season) {
        this.year = year;
        this.season = season;
        this.courses = new ArrayList<>();
    }

    // Getter methods
    public int getYear() {
        return year;
    }

    public String getSeason() {
        return season;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    // Add a course
    public void addCourse(Course course) {
        courses.add(course);
    }

    // Delete a course by name
    public boolean deleteCourse(String courseName) {
        for (Course course : courses) {
            if (course.getCourseName().equalsIgnoreCase(courseName)) {
                return courses.remove(course);
            }
        }
        return false; // Course not found
    }
    
    // Get course by name
    public Course getCourseByName(String courseName) {
        for (Course course : courses) {
            if (course.getCourseName().equalsIgnoreCase(courseName)) {
                return course;
            }
        }
        return null; // Course not found
    }

    // List all assignments for the semester
    public ArrayList<Task> listAllAssignments() {
        ArrayList<Task> assignments = new ArrayList<>();
        for (Course course : courses) {
            assignments.addAll(course.getAssignments());
        }
        return assignments;
    }

}
