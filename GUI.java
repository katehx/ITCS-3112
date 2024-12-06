import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;

public class GUI {
    private JLabel label;
    private JFrame frame;
    private JPanel panel;
    private JButton studentTaskViewButton, generalTaskViewButton, quitProgram, viewTasksButton, addTasksButton, completionDataButton, viewDateSortTaskButton;
    private User user;

    public GUI() {
        frame = new JFrame("Task Manager");

        String userName = JOptionPane.showInputDialog(frame, "What is your name:");
        // Check if name was entered
        if (userName == null || userName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name cannot be empty. Exiting.");
            System.exit(0);
        }
        // Create user with input name
        this.user = new User(userName);
        
        addDefaultTasks();

        showMainMenu();
    }

    private void showMainMenu() {
        // Frame setup
        frame = new JFrame("Task Manager - Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        // Panel setup
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        Color customColor = new Color(173, 216, 230); // Light blueish color
        panel.setBackground(customColor);

        // General Task View
        generalTaskViewButton = new JButton("General Task View");
        generalTaskViewButton.addActionListener(e -> showGeneralTaskView());
        panel.add(generalTaskViewButton);

        // Student Task View
        studentTaskViewButton = new JButton("Student Task View");
        studentTaskViewButton.addActionListener(e -> showStudentTaskView());
        panel.add(studentTaskViewButton);

        // Quit
        quitProgram = new JButton("Quit");
        quitProgram.addActionListener(e -> System.exit(0));
        panel.add(quitProgram);

        // Make panel visible
        frame.add(panel);
        frame.setVisible(true);
    }

    // General Task View
    private void showGeneralTaskView() {
        panel.removeAll();
    
        JLabel label = new JLabel("General Task View", JLabel.CENTER);
        panel.add(label);
    
        JButton viewTasksButton = new JButton("View tasks");
        viewTasksButton.addActionListener(e -> showTasks());
        panel.add(viewTasksButton);
    
        JButton viewDateSortTaskButton = new JButton("View Tasks Sorted by Date");
        viewDateSortTaskButton.addActionListener(e -> showTasksSortedByDate());
        panel.add(viewDateSortTaskButton);
    
        JButton viewCompletedTasksButton = new JButton("View Completed Tasks");
        viewCompletedTasksButton.addActionListener(e -> showCompletedTasks());
        panel.add(viewCompletedTasksButton);
    
        JButton addTasksButton = new JButton("Add tasks");
        addTasksButton.addActionListener(e -> addTasks());
        panel.add(addTasksButton);
    
        JButton completionDataButton = new JButton("View task completion data");
        completionDataButton.addActionListener(e -> showTaskCompletionData());
        panel.add(completionDataButton);
    
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> showMainMenu());
        panel.add(backButton);
    
        panel.revalidate();
        panel.repaint();
    }

    

    private void showStudentTaskView() {
        // Clear panel
        panel.removeAll();

        // Add new components for Student Task View
        JLabel label = new JLabel("Student Task View", JLabel.CENTER);
        panel.add(label);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> showMainMenu());
        panel.add(backButton);

        // Refresh the panel
        panel.revalidate();
        panel.repaint();
    }

    private void showTaskCompletionData() {
        int totalTasks = user.getTaskManager().getTaskList().size();
        int completedTasks = user.getTaskManager().getCompletedTaskList().size();
        int overdueTasks = 0;
        int tasksDueNextWeek = 0;
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
    
        for (Task task : user.getTaskManager().getIncompleteTaskList()) {
            if (task.getDueDate().isBefore(today)) {
                overdueTasks++;
            }
            if (task.getDueDate().isAfter(today) && task.getDueDate().isBefore(nextWeek)) {
                tasksDueNextWeek++;
            }
        }
    
        int remainingTasks = totalTasks - completedTasks;
        double completedPercentage = totalTasks > 0 ? (completedTasks * 100.0 / totalTasks) : 0;
        double remainingPercentage = totalTasks > 0 ? (remainingTasks * 100.0 / totalTasks) : 0;
    
        StringBuilder statsMessage = new StringBuilder("Task Completion Data:\n");
        statsMessage.append("Total tasks: ").append(totalTasks).append("\n");
        statsMessage.append("Tasks overdue: ").append(overdueTasks).append("\n");
        statsMessage.append("Tasks completed: ").append(completedTasks)
                .append(" (").append(String.format("%.2f", completedPercentage)).append("%)\n");
        statsMessage.append("Tasks remaining: ").append(remainingTasks)
                .append(" (").append(String.format("%.2f", remainingPercentage)).append("%)\n");
        statsMessage.append("Tasks due in the next week: ").append(tasksDueNextWeek).append("\n");
    
        JOptionPane.showMessageDialog(frame, statsMessage.toString(), "Task Completion Data", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showCompletedTasks() {
        JFrame taskFrame = new JFrame("Completed Tasks");
        taskFrame.setSize(400, 400);
        taskFrame.setLocationRelativeTo(frame);
    
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
    
        // Get completed tasks
        List<Task> completedTasks = user.getTaskManager().getCompletedTaskList();
    
        if (completedTasks.isEmpty()) {
            taskPanel.add(new JLabel("No completed tasks to show."));
        } else {
            for (Task task : completedTasks) {
                taskPanel.add(new JLabel(task.getTaskDescription() + " (Completed on: " + task.getDueDate() + ")"));
            }
        }
    
        JScrollPane scrollPane = new JScrollPane(taskPanel);
    
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> taskFrame.dispose());
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(exitButton);
    
        taskFrame.add(scrollPane, BorderLayout.CENTER);
        taskFrame.add(buttonPanel, BorderLayout.SOUTH);
    
        taskFrame.setVisible(true);
    }

    // Add tasks to general task list
    private void addTasks() {
        // Input Task Description
        String taskDescription = JOptionPane.showInputDialog(frame, "Enter Task Description:");
        if (taskDescription == null || taskDescription.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Task description cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Input Due Date
        String dueDateInput = JOptionPane.showInputDialog(frame, "Enter Due Date (YYYY-MM-DD):");
        try {
            LocalDate dueDate = LocalDate.parse(dueDateInput); // Parse the input as LocalDate

            // Add task to the user's TaskManager
            Task newTask = new Task(dueDate.getYear(), dueDate.getMonthValue(), dueDate.getDayOfMonth(), taskDescription);
            user.getTaskManager().addTask(newTask);

            JOptionPane.showMessageDialog(frame, "Task added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid date format. Please enter the date as YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // View incomplete tasks
    private void showTasks() {
        JFrame taskFrame = new JFrame("Mark Tasks as Completed");
        taskFrame.setSize(400, 400);
        taskFrame.setLocationRelativeTo(frame);

        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

        List<Task> incompleteTasks = user.getTaskManager().getIncompleteTaskList();

        JCheckBox[] checkboxes = new JCheckBox[incompleteTasks.size()];

        for (int i = 0; i < incompleteTasks.size(); i++) {
            Task task = incompleteTasks.get(i);
            checkboxes[i] = new JCheckBox(task.getTaskDescription() + " (Due: " + task.getDueDate() + ")");
            checkboxPanel.add(checkboxes[i]);
        }

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);

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
            taskFrame.dispose();
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> taskFrame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(completeButton);
        buttonPanel.add(exitButton);

        taskFrame.add(scrollPane, BorderLayout.CENTER);
        taskFrame.add(buttonPanel, BorderLayout.SOUTH);

        taskFrame.setVisible(true);
    }

    private void showTasksSortedByDate() {
        JFrame taskFrame = new JFrame("Tasks Sorted by Date");
        taskFrame.setSize(400, 400);
        taskFrame.setLocationRelativeTo(frame);

        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

        List<Task> sortedTasks = user.getTaskManager().getTasksSortedByDate();

        JCheckBox[] checkboxes = new JCheckBox[sortedTasks.size()];

        for (int i = 0; i < sortedTasks.size(); i++) {
            Task task = sortedTasks.get(i);
            checkboxes[i] = new JCheckBox(task.getTaskDescription() + " (Due: " + task.getDueDate() + ")");
            checkboxPanel.add(checkboxes[i]);
        }

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);

        JButton completeButton = new JButton("Mark as Completed");
        completeButton.addActionListener(e -> {
            for (int i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].isSelected()) {
                    Task task = sortedTasks.get(i);
                    int actualIndex = user.getTaskManager().getTaskList().indexOf(task);
                    user.getTaskManager().markTaskAsCompleted(actualIndex);
                }
            }
            JOptionPane.showMessageDialog(taskFrame, "Selected tasks marked as completed!");
            taskFrame.dispose();
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> taskFrame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(completeButton);
        buttonPanel.add(exitButton);

        taskFrame.add(scrollPane, BorderLayout.CENTER);
        taskFrame.add(buttonPanel, BorderLayout.SOUTH);

        taskFrame.setVisible(true);
    }

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

    // Start the GUI
    public static void main(String[] args) {
        new GUI();
    }
}
