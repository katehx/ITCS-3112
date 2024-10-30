import java.time.LocalDate;

public class Task {
    // fields
    String taskDescription = "";
    LocalDate dueDate;
    boolean completed;

    // constructor
    //https://www.w3schools.com/java/java_date.asp - local date syntax
    public Task(int year, int month, int day, String taskDescription){
        this.dueDate = LocalDate.of(year, month, day);
        this.taskDescription = taskDescription;
    }

    // getter methods
        public LocalDate getDueDate(){
            return dueDate;
        }

        public String getTaskDescription(){
            return taskDescription;
        }

        public boolean isCompleted(){
            return completed;
        }

    // setter methods
        public void setDueDate(LocalDate newDueDate){
            this.dueDate = newDueDate;
        }

        public void setTaskDescription(String newTaskDescirption){
            this.taskDescription = newTaskDescirption;
        }

        public void setCompleted(boolean newCompleted){
            this.completed = newCompleted;
            if(newCompleted == true){
                //complete date = now
            }
        }

    // other methods
        @Override
        public String toString(){
            return "Task Description: " + taskDescription + ", Due Date: " + dueDate;
        }
    
}//end class
