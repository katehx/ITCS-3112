import java.util.ArrayList;

public class SchoolManager extends TaskManager {
    private final ArrayList<Semester> semesters;

    public SchoolManager() {
        super(); // Call TaskManager constructor
        this.semesters = new ArrayList<>();
    }

    // Add a semester
    public void addSemester(Semester semester) {
        semesters.add(semester);
    }

    // Get a semester by year and season
    public Semester getSemester(int year, String season) {
        for (Semester semester : semesters) {
            if (semester.getYear() == year && semester.getSeason().equalsIgnoreCase(season)) {
                return semester;
            }
        }
        return null; // Semester not found    
    }

    // Delete a semester
    public boolean deleteSemester(int year, String season) {
        for (Semester semester : semesters) {
            if (semester.getYear() == year && semester.getSeason().equalsIgnoreCase(season)) {
                return semesters.remove(semester);
            }
        }
        return false; // Semester not found
    }    

    // Get all semesters
    public ArrayList<Semester> getSemesters() {
        return semesters;
    }

    // Add a course to a specific semester
    public boolean addCourseToSemester(int year, String season, Course course) {
        for (Semester semester : semesters) {
            if (semester.getYear() == year && semester.getSeason().equalsIgnoreCase(season)) {
                semester.addCourse(course);
                return true;
            }
        }
        return false; // Semester not found
    }

    // Delete a course from a specific semester
    public boolean deleteCourseFromSemester(int year, String season, String courseName) {
        for (Semester semester : semesters) {
            if (semester.getYear() == year && semester.getSeason().equalsIgnoreCase(season)) {
                return semester.deleteCourse(courseName);
            }
        }
        return false; // Semester not found
    }

}
