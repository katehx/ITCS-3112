import java.time.LocalDate;

public class Task {
    //fields
    String description = "";
    LocalDate dueDate;


    // constructor
    public Task(int year, int month, int day, String description){ //Course course,
        this.dueDate = LocalDate.of(year, month, day);
        this.description = description;
    }

    //https://www.w3schools.com/java/java_date.asp - local date syntax

    public static void main(String[] args) {
        //LocalDate dueDate = LocalDate.of(2004, 3, 29);
        //System.out.println("Can I get this to print? " + dueDate);

        Task new1 = new Task(2004, 3, 29, "Do laundry");
        System.out.println("due date: " + new1.dueDate);
        System.out.println("description: " + new1.description);

    }
}//end class



// import java.time.LocalDateTime; // Import the LocalDateTime class
// import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class

// public class Task {
//     public class Main {
//   public static void main(String[] args) {
//     LocalDateTime myDateObj = LocalDateTime.now();
//     System.out.println("Before formatting: " + myDateObj);
//     DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

//     String formattedDate = myDateObj.format(myFormatObj);
//     System.out.println("After formatting: " + formattedDate);
//   }
// }}