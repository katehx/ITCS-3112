// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;
// import java.io.File;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.time.LocalDate;
// import java.util.Scanner;

// public class MainApp {
//     private User user;

//     public static void main(String[] args) {
//         MainApp app = new MainApp();
//         app.start();
//     }

//     public void start() {
//         Scanner scanner = new Scanner(System.in);

//         System.out.println("Welcome to the Task Manager!");
//         System.out.print("Enter your name: ");
//         String name = scanner.nextLine();

//         // Check if a JSON file for the user already exists
//         String filename = name.replace(" ", "_") + "_profile.json";
//         File userFile = new File(filename);

//         if (userFile.exists()) {
//             // Load the existing user profile
//             loadExistingUser(filename);
//         } else {
//             // Create a new user profile
//             System.out.println("No existing profile found. Creating a new user profile.");
//             createNewUser(name);
//         }

//         // Interactive menu for user actions
//         boolean exit = false;
//         while (!exit) {
//             System.out.println("\nMenu:");
//             System.out.println("1. Add Task");
//             System.out.println("2. View Tasks");
//             System.out.println("3. Save and Exit");

//             System.out.print("Enter your choice: ");
//             int choice = scanner.nextInt();
//             scanner.nextLine(); // Consume the newline character

//             switch (choice) {
//                 case 1:
//                     addTask(scanner);
//                     break;
//                 case 2:
//                     viewTasks();
//                     break;
//                 case 3:
//                     saveUserToJSON(user);
//                     exit = true;
//                     System.out.println("Goodbye!");
//                     break;
//                 default:
//                     System.out.println("Invalid choice. Please try again.");
//             }
//         }

//         scanner.close();
//     }

//     private void createNewUser(String name) {
//         // Create the new User object
//         user = new User(name);

//         // Save the user to a JSON file
//         saveUserToJSON(user);

//         System.out.println("User created successfully!");
//     }

//     private void addTask(Scanner scanner) {
//         System.out.print("Enter task description: ");
//         String description = scanner.nextLine();

//         System.out.print("Enter due date (YYYY-MM-DD): ");
//         String dateInput = scanner.nextLine();

//         try {
//             LocalDate dueDate = LocalDate.parse(dateInput);
//             Task newTask = new Task(dueDate.getYear(), dueDate.getMonthValue(), dueDate.getDayOfMonth(), description);

//             // Add the task to the user's TaskManager
//             user.getTaskManager().addTask(newTask);
//             System.out.println("Task added successfully!");
//         } catch (Exception e) {
//             System.out.println("Invalid date format. Task not added.");
//         }
//     }

//     private void viewTasks() {
//         if (user.getTaskManager().getTaskList().isEmpty()) {
//             System.out.println("No tasks available.");
//         } else {
//             System.out.println("\nTasks:");
//             int index = 1;
//             for (Task task : user.getTaskManager().getTaskList()) {
//                 System.out.println(index + ". " + task.getTaskDescription() + " (Due: " + task.getDueDate() + ")");
//                 index++;
//             }
//         }
//     }

//     private void saveUserToJSON(User user) {
//         Gson gson = new GsonBuilder()
//                 .setPrettyPrinting()
//                 .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
//                 .create();
//         String filename = user.getName().replace(" ", "_") + "_profile.json";
    
//         try (FileWriter writer = new FileWriter(filename)) {
//             gson.toJson(user, writer);
//             System.out.println("User profile saved to " + filename);
//         } catch (IOException e) {
//             System.out.println("An error occurred while saving the user profile.");
//             e.printStackTrace();
//         }
//     }
    
//     private void loadExistingUser(String filename) {
//         Gson gson = new GsonBuilder()
//                 .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
//                 .create();
    
//         try (FileReader reader = new FileReader(filename)) {
//             user = gson.fromJson(reader, User.class);
//             System.out.println("Welcome back, " + user.getName() + "!");
//         } catch (IOException e) {
//             System.out.println("An error occurred while loading the user profile.");
//             e.printStackTrace();
//         }
//     }
    
// }
