import java.util.ArrayList;

public class Semester {
    // fields
    ArrayList<Course> semester = new ArrayList<>(Course);
    int year;
    String season = "";

    // constructor
    public Semester(int year, String season, ArrayList<Course> semester){
        this.year = year;
        this.season = season;
        this.semester = semester;
    }

    // getter methods
        public int getYear(){
            return year;
        }

        public String getSeason(){
            return season;
        }

        public ArrayList<Course> getSemester(){
            return semester;
        }

    // setter methods
        public void setYear(int newYear){
            this.year = newYear;
        }

        public void setSeason(String newSeason){
            this.season = newSeason;
        }    
    
        public void setSemester(ArrayList<Course> newSemester){
            this.semester = newSemester;
        }

    // other methods
        //@Override
        // public String toString(){
        //     return "Task Description: " + taskDescription + ", Due Date: " + dueDate;
        // }
    
}//end class
