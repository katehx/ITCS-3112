import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;

public class GeneralTaskView {
    private final JFrame frame;
    private JPanel panel;
    private final User user;
    private final GUI gui;

    // Constructor
    public GeneralTaskView(JFrame mainFrame, User user, GUI gui) {
        this.frame = mainFrame; 
        this.user = user;
        this.gui = gui; 
    }

    //**************************************** MENU ****************************************//

    public void showGeneralTaskView() {
        // Set up the panel with a BorderLayout
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#CCDBDC")); 
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add label at the top
        JLabel label = new JLabel("General Task View", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.decode("#263D42"));
        panel.add(label, BorderLayout.NORTH);

        // Set up the button panel
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        buttonPanel.setBackground(Color.decode("#CCDBDC"));

        // Add buttons
        JButton viewTasksButton = createStyledButton("View Tasks", "#283845", "#63C7B2", "#8E6C88");
        viewTasksButton.addActionListener(e -> showTasks());
        buttonPanel.add(viewTasksButton);

        JButton viewDateSortTaskButton = createStyledButton("View Tasks Sorted by Date", "#283845", "#63C7B2", "#8E6C88");
        viewDateSortTaskButton.addActionListener(e -> showTasksSortedByDate());
        buttonPanel.add(viewDateSortTaskButton);

        JButton viewCompletedTasksButton = createStyledButton("View Completed Tasks", "#283845", "#63C7B2", "#8E6C88");
        viewCompletedTasksButton.addActionListener(e -> showCompletedTasks());
        buttonPanel.add(viewCompletedTasksButton);

        JButton addTasksButton = createStyledButton("Add Tasks", "#283845", "#63C7B2", "#8E6C88");
        addTasksButton.addActionListener(e -> addTasks());
        buttonPanel.add(addTasksButton);

        JButton completionDataButton = createStyledButton("View Task Completion Data", "#283845", "#63C7B2", "#8E6C88");
        completionDataButton.addActionListener(e -> showTaskCompletionData());
        buttonPanel.add(completionDataButton);

        JButton backButton = createStyledButton("Back to Main Menu", "#283845", "#63C7B2", "#8E6C88");
        backButton.addActionListener(e -> gui.showMainMenu());
        buttonPanel.add(backButton);

        // Add the button panel to the center
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Add deafult tasks
        addDefaultTasks();

        // Update the frame with the new panel
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    // Creates button with styling settings
    private JButton createStyledButton(String text, String backgroundColor, String textColor, String hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setBackground(Color.decode(backgroundColor));
        button.setForeground(Color.decode(textColor));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.decode(hoverColor), 2));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode(hoverColor));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode(backgroundColor));
            }
        });

        return button;
    }

    //**************************************** FUNCTIONALITY ****************************************//
    
    // Show unsorted incomplete tasks with ability to check off tasks
    private void showTasks() {
        JFrame taskFrame = new JFrame("Incomplete Tasks");
        taskFrame.setSize(400, 400);
        taskFrame.setLocationRelativeTo(frame);

        // Checkbox panel for marking tasks as incomplete
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

        // Get incomplete tasks
        ArrayList<Task> incompleteTasks = user.getTaskManager().getIncompleteTaskList();
        JCheckBox[] checkboxes = new JCheckBox[incompleteTasks.size()]; // Create checkbox array the size of the incomplete tasks array list

        // Loop through incomplete tasks and add data to checkboxes array
        for (int i = 0; i < incompleteTasks.size(); i++) {
            Task task = incompleteTasks.get(i);
            checkboxes[i] = new JCheckBox(task.getTaskDescription() + " (Due: " + task.getDueDate() + ")");
            checkboxPanel.add(checkboxes[i]);
        }

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);

        // Complete button - notes any tasks that had the checkbox and will mark those tasks as complete
        JButton completeButton = new JButton("Mark as Completed");
        completeButton.addActionListener(e -> {
            for (int i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].isSelected()) {
                    Task task = incompleteTasks.get(i);
                    int actualIndex = user.getTaskManager().getTaskList().indexOf(task);
                    user.getTaskManager().markTaskAsCompleted(actualIndex);
                }
            }
            JOptionPane.showMessageDialog(taskFrame, "Selected tasks marked as completed!");
            taskFrame.dispose(); // Close after marking tasks as completed
        });

        // Exit button to leave incomplete task list
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> taskFrame.dispose()); // remove task frame

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(completeButton);
        buttonPanel.add(exitButton);

        taskFrame.add(scrollPane, BorderLayout.CENTER);
        taskFrame.add(buttonPanel, BorderLayout.SOUTH);
        taskFrame.setVisible(true);
    }

    // Show task list sorted by date with check boxes to mark tasks as complete
    private void showTasksSortedByDate() {
        JFrame taskFrame = new JFrame("Incomplete Tasks Sorted by Date");
        taskFrame.setSize(400, 400);
        taskFrame.setLocationRelativeTo(frame);

        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

        // Get incomplete tasks sorted by date
        ArrayList<Task> incompleteTasks = user.getTaskManager().getTasksSortedByDate();
        JCheckBox[] checkboxes = new JCheckBox[incompleteTasks.size()]; // Create checkbox array the size of the incomplete tasks array list
 
        // Loop through sorted incomplete tasks and add data to checkbox array for display
        for (int i = 0; i < incompleteTasks.size(); i++) {
            Task task = incompleteTasks.get(i);
            checkboxes[i] = new JCheckBox(task.getTaskDescription() + " (Due: " + task.getDueDate() + ")");
            checkboxPanel.add(checkboxes[i]);
        }

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);

        // Complete button - notes any tasks that had the checkbox and will mark those tasks as complete
        JButton completeButton = new JButton("Mark as Completed");
        completeButton.addActionListener(e -> {
            for (int i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].isSelected()) {
                    Task task = incompleteTasks.get(i);
                    int actualIndex = user.getTaskManager().getTaskList().indexOf(task);
                    user.getTaskManager().markTaskAsCompleted(actualIndex);
                }
            }
            JOptionPane.showMessageDialog(taskFrame, "Selected tasks marked as completed!");
            taskFrame.dispose(); // Close after marking tasks as completed
        });

        // Exit if no new tasks need to be marked as complete
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> taskFrame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(completeButton);
        buttonPanel.add(exitButton);

        taskFrame.add(scrollPane, BorderLayout.CENTER);
        taskFrame.add(buttonPanel, BorderLayout.SOUTH);
        taskFrame.setVisible(true);
    }

    // View all tasks that have already been completed
    private void showCompletedTasks() {
        JFrame taskFrame = new JFrame("Completed Tasks");
        taskFrame.setSize(400, 400);
        taskFrame.setLocationRelativeTo(frame);
    
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
    
        ArrayList<Task> completedTasks = user.getTaskManager().getCompletedTaskList();
        if (completedTasks.isEmpty()) {
            taskPanel.add(new JLabel("No completed tasks."));
        } else {
            for (Task task : completedTasks) { // Loop through collected completed tasks list and format data to show the task and when it was completed
                String completionText = " (Completed on: " + (task.getCompletionDate() != null ? task.getCompletionDate() : "Unknown") + ")";
                taskPanel.add(new JLabel(task.getTaskDescription() + completionText));
            }
        }
    
        JScrollPane scrollPane = new JScrollPane(taskPanel);
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> taskFrame.dispose());
    
        taskFrame.add(scrollPane, BorderLayout.CENTER);
        taskFrame.add(closeButton, BorderLayout.SOUTH);
        taskFrame.setVisible(true);
    }
    
    // Add new tasks to the to do list
    private void addTasks() {
        String description = JOptionPane.showInputDialog(frame, "Enter Task Description:"); // Pop up to get task description 
        if (description == null || description.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Task description cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dueDateInput = JOptionPane.showInputDialog(frame, "Enter Due Date (YYYY-MM-DD):"); // Pop up to get due date
        try {
            LocalDate dueDate = LocalDate.parse(dueDateInput);
            Task newTask = new Task(dueDate.getYear(), dueDate.getMonthValue(), dueDate.getDayOfMonth(), description); // Creating a new task with the gathered info
            user.getTaskManager().addTask(newTask);
            JOptionPane.showMessageDialog(frame, "Task added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Summary of task competion data
    private void showTaskCompletionData() {
        // Get data to use for stats
        int totalTasks = user.getTaskManager().getTaskList().size();
        int completedTasks = user.getTaskManager().getCompletedTaskList().size();
        int remainingTasks = totalTasks - completedTasks;
        // If there are more than 0 tasks calculate percentage for how many tasks have been completed / remain
        double completionPercentage = totalTasks > 0 ? (completedTasks * 100.0 / totalTasks) : 0.0;
        double remainingPercentage = totalTasks > 0 ? (remainingTasks * 100.0 / totalTasks) : 0.0;
    
        int overdueTasks = 0;
        int tasksDueNextWeek = 0;
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7); // Used for calculating how many tasks due in the next week
    
        // Analyze incomplete tasks
        ArrayList<Task> incompleteTasks = user.getTaskManager().getIncompleteTaskList();
        for (Task task : incompleteTasks) {
            if (task.getDueDate().isBefore(today)) { // Loop through incomplete tasks to check if task is overdue
                overdueTasks++; // Add to toal if overdue
            }
            if (task.getDueDate().isAfter(today) && task.getDueDate().isBefore(nextWeek)) { // Loop through incomplete tasks to check if task is due within the next week
                tasksDueNextWeek++; // Add to total if within range
            }
        }
    
        // Formatted output of task completion data
        String message = String.format(
            "Task Completion Data:\n" +
            "Total Tasks: %d\n" +
            "Completed Tasks: %d (%.2f%%)\n" +
            "Remaining Tasks: %d (%.2f%%)\n" +
            "Overdue Tasks: %d\n" +
            "Tasks Due in the Next Week: %d",
            totalTasks, completedTasks, completionPercentage,
            remainingTasks, remainingPercentage,
            overdueTasks, tasksDueNextWeek
        );
    
        JOptionPane.showMessageDialog(frame, message, "Task Completion Data", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Default tasks for demo of the software
    private void addDefaultTasks() {
        user.getTaskManager().addTask(new Task(2024, 11, 28, "Drop package at post office"));
        user.getTaskManager().addTask(new Task(2024, 11, 3, "Wash car"));
        user.getTaskManager().addTask(new Task(2024, 12, 5, "Do the dishes"));
        user.getTaskManager().addTask(new Task(2022, 6, 17, "Replace wheel on bike"));
        user.getTaskManager().addTask(new Task(2025, 2, 5, "Schedule dentist appointment"));
        user.getTaskManager().addTask(new Task(2023, 12, 1, "Go for a run"));
        user.getTaskManager().addTask(new Task(2025, 3, 29, "Renew car registration"));
        user.getTaskManager().addTask(new Task(2024, 12, 2, "Call mom"));
        user.getTaskManager().addTask(new Task(2024, 12, 9, "Go grocery shopping"));
        user.getTaskManager().addTask(new Task(2024, 12, 24, "Christmas party"));
        user.getTaskManager().addTask(new Task(2024, 12, 14, "Take cat to the vet"));
    }

}
