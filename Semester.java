import java.util.ArrayList;

public class Semester {
    // Fields
    ArrayList<Course> semester = new ArrayList<>();
    int year;
    String season = "";

    // Constructor
    public Semester(int year, String season, ArrayList<Course> semester){
        this.year = year;
        this.season = season;
        this.semester = semester;
    }

    // Getter methods
        public int getYear(){
            return year;
        }

        public String getSeason(){
            return season;
        }

        public ArrayList<Course> getSemester(){
            return semester;
        }

    // Setter methods
        public void setYear(int newYear){
            this.year = newYear;
        }

        public void setSeason(String newSeason){
            this.season = newSeason;
        }    
    
        public void setSemester(ArrayList<Course> newSemester){
            this.semester = newSemester;
        }
    
}
