
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.*;

public class SchoolTaskView {

    private final JFrame frame;
    private JPanel panel;
    private final User user;
    private final GUI gui; // Reference to the main GUI class

    public SchoolTaskView(JFrame mainFrame, User user, GUI gui) {
        this.frame = mainFrame; // Reference to the main application frame
        this.user = user;
        this.gui = gui; // Store the reference to the GUI instance
    }

    //**************************************** MENUS ****************************************//
    // Student task view
    public void showStudentTaskView() {
        // Set up the panel with a BorderLayout
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#CCDBDC"));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add label at the top
        JLabel label = new JLabel("Student Task View", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.decode("#263D42"));
        panel.add(label, BorderLayout.NORTH);

        // Set up the button panel
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        buttonPanel.setBackground(Color.decode("#CCDBDC"));

        // Add buttons
        JButton currentSemesterAssignmentsButton = createStyledButton("Show Current Semester Assignments", "#283845", "#63C7B2", "#8E6C88");
        currentSemesterAssignmentsButton.addActionListener(e -> showCurrentSemesterAssignments());
        buttonPanel.add(currentSemesterAssignmentsButton);

        JButton specificCourseAssignmentsButton = createStyledButton("Show Specific Course Assignments", "#283845", "#63C7B2", "#8E6C88");
        specificCourseAssignmentsButton.addActionListener(e -> showSpecificCourseAssignments());
        buttonPanel.add(specificCourseAssignmentsButton);

        JButton assignmentsByDateButton = createStyledButton("Show Assignments Sorted by Date", "#283845", "#63C7B2", "#8E6C88");
        assignmentsByDateButton.addActionListener(e -> showAssignmentsSortedByDate());
        buttonPanel.add(assignmentsByDateButton);

        JButton priority = createStyledButton("Show Assignments Sorted by Priority", "#283845", "#63C7B2", "#8E6C88");
        priority.addActionListener(e -> showAssignmentsByPriority());
        buttonPanel.add(priority);

        JButton viewCompletedAssignmentsButton = createStyledButton("View Completed Assignments", "#283845", "#63C7B2", "#8E6C88");
        viewCompletedAssignmentsButton.addActionListener(e -> showCompletedAssignments());
        buttonPanel.add(viewCompletedAssignmentsButton);

        JButton addAssignmentButton = createStyledButton("Add Assignment", "#283845", "#63C7B2", "#8E6C88");
        addAssignmentButton.addActionListener(e -> addAssignment());
        buttonPanel.add(addAssignmentButton);

        JButton completionDataButton = createStyledButton("Show Completion Data", "#283845", "#63C7B2", "#8E6C88");
        completionDataButton.addActionListener(e -> showCompletionData());
        buttonPanel.add(completionDataButton);

        JButton backButton = createStyledButton("Back to Main Menu", "#283845", "#63C7B2", "#8E6C88");
        backButton.addActionListener(e -> gui.showMainMenu());
        buttonPanel.add(backButton);

        // Add the button panel to the center
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Sample student view setup
        setupDefaultStudent();

        // Update the frame with the new panel
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    // Creates button with styling
    private JButton createStyledButton(String text, String backgroundColor, String textColor, String hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20)); // Adjust text size
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
    // Show list of semesters to user and then display assignment list for semester with checkboxes
    private void showCurrentSemesterAssignments() {
        // Get the list of semesters
        ArrayList<Semester> semesters = user.getSchoolManager().getSemesters();
        if (semesters.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No semesters available.");
            return;
        }

        // Create an array of semester options for the dropdown
        String[] semesterOptions = new String[semesters.size()];
        for (int i = 0; i < semesters.size(); i++) {
            Semester semester = semesters.get(i);
            semesterOptions[i] = semester.getSeason() + " " + semester.getYear();
        }

        // Show a dropdown to select a semester
        String selectedSemester = (String) JOptionPane.showInputDialog(
                frame,
                "Select a semester:",
                "Choose Semester",
                JOptionPane.QUESTION_MESSAGE,
                null,
                semesterOptions,
                semesterOptions[0]
        );

        if (selectedSemester == null || selectedSemester.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No semester selected.");
            return;
        }

        // Parse the selected semester for usage later
        String[] semesterParts = selectedSemester.split(" ");
        String season = semesterParts[0];
        int year = Integer.parseInt(semesterParts[1]);

        // Retrieve assignments for the selected semester
        Semester semester = user.getSchoolManager().getSemester(year, season);
        if (semester == null) {
            JOptionPane.showMessageDialog(frame, "Selected semester not found.");
            return;
        }

        ArrayList<Task> currentSemesterAssignments = semester.listAllAssignments();

        // Get the incomplete assignments
        ArrayList<Task> incompleteAssignments = new ArrayList<>();
        for (Task task : currentSemesterAssignments) {
            if (!task.isCompleted()) {
                incompleteAssignments.add(task);
            }
        }

        if (incompleteAssignments.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No incomplete assignments for the selected semester.");
            return;
        }

        // Set up a new frame for the assignments with checkboxes
        JFrame taskFrame = new JFrame("Incomplete Assignments");
        taskFrame.setSize(400, 400);
        taskFrame.setLocationRelativeTo(frame);

        // Checkbox panel
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        JCheckBox[] checkboxes = new JCheckBox[incompleteAssignments.size()];

        // Add assignments to checkboxes
        for (int i = 0; i < incompleteAssignments.size(); i++) {
            Task task = incompleteAssignments.get(i);
            checkboxes[i] = new JCheckBox(task.getTaskDescription() + " (Due: " + task.getDueDate() + ")");
            checkboxPanel.add(checkboxes[i]);
        }

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);

        // Complete button to mark selected tasks as complete
        JButton completeButton = new JButton("Mark as Completed");
        completeButton.addActionListener(e -> {
            for (int i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].isSelected()) {
                    Task task = incompleteAssignments.get(i);
                    task.setCompleted(true);
                    task.setCompletionDate(LocalDate.now());
                }
            }
            JOptionPane.showMessageDialog(taskFrame, "Selected tasks marked as completed!");
            taskFrame.dispose(); // Close the task frame
        });

        // Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> taskFrame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(completeButton);
        buttonPanel.add(exitButton);

        // Add components to the frame
        taskFrame.add(scrollPane, BorderLayout.CENTER);
        taskFrame.add(buttonPanel, BorderLayout.SOUTH);
        taskFrame.setVisible(true);
    }

    // Show list of courses to user and then display assignment list for course with checkboxes
    private void showSpecificCourseAssignments() {
        // Get the list of courses
        ArrayList<Course> courses = new ArrayList<>();

        // Iterate over all semesters to collect courses
        for (Semester semester : user.getSchoolManager().getSemesters()) {
            courses.addAll(semester.getCourses());
        }

        // Create an array of course options for the dropdown
        String[] courseOptions = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            courseOptions[i] = course.getCourseName();
        }

        // Show a dropdown to select a course
        String selectedCourse = (String) JOptionPane.showInputDialog(
                frame,
                "Select a course:",
                "Choose Course",
                JOptionPane.QUESTION_MESSAGE,
                null,
                courseOptions,
                courseOptions[0]
        );

        if (selectedCourse == null || selectedCourse.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No course selected.");
            return;
        }

        // Retrieve the selected course
        Course course = null;
        for (Course c : courses) {
            if (c.getCourseName().equalsIgnoreCase(selectedCourse)) {
                course = c;
                break;
            }
        }

        if (course == null) {
            JOptionPane.showMessageDialog(frame, "Selected course not found.");
            return;
        }

        ArrayList<Assignment> courseAssignments = course.getAssignments();

        // Get the incomplete assignments
        ArrayList<Task> incompleteAssignments = new ArrayList<>();
        for (Task task : courseAssignments) {
            if (!task.isCompleted()) {
                incompleteAssignments.add(task);
            }
        }

        if (incompleteAssignments.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No incomplete assignments for the selected course.");
            return;
        }

        // Set up a new frame for the assignments with checkboxes
        JFrame taskFrame = new JFrame("Incomplete Assignments for " + course.getCourseName());
        taskFrame.setSize(400, 400);
        taskFrame.setLocationRelativeTo(frame);

        // Checkbox panel
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        JCheckBox[] checkboxes = new JCheckBox[incompleteAssignments.size()];

        // Add assignments to checkboxes
        for (int i = 0; i < incompleteAssignments.size(); i++) {
            Task task = incompleteAssignments.get(i);
            checkboxes[i] = new JCheckBox(task.getTaskDescription() + " (Due: " + task.getDueDate() + ")");
            checkboxPanel.add(checkboxes[i]);
        }

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);

        // Complete button to mark selected tasks as complete
        JButton completeButton = new JButton("Mark as Completed");
        completeButton.addActionListener(e -> {
            for (int i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].isSelected()) {
                    Task task = incompleteAssignments.get(i);
                    task.setCompleted(true);
                    task.setCompletionDate(LocalDate.now());
                }
            }
            JOptionPane.showMessageDialog(taskFrame, "Selected tasks marked as completed!");
            taskFrame.dispose(); // Close the task frame
        });

        // Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> taskFrame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(completeButton);
        buttonPanel.add(exitButton);

        // Add components to the frame
        taskFrame.add(scrollPane, BorderLayout.CENTER);
        taskFrame.add(buttonPanel, BorderLayout.SOUTH);
        taskFrame.setVisible(true);
    }

    // Show assignments for a selected course sorted by date
    private void showAssignmentsSortedByDate() {
        // Get the list of courses
        ArrayList<Course> courses = new ArrayList<>();

        // Iterate over all semesters to collect courses
        for (Semester semester : user.getSchoolManager().getSemesters()) {
            courses.addAll(semester.getCourses());
        }

        // Create an array of course options for the dropdown
        String[] courseOptions = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            courseOptions[i] = course.getCourseName();
        }

        // Show a dropdown to select a course
        String selectedCourse = (String) JOptionPane.showInputDialog(
                frame,
                "Select a course:",
                "Choose Course",
                JOptionPane.QUESTION_MESSAGE,
                null,
                courseOptions,
                courseOptions[0]
        );

        if (selectedCourse == null || selectedCourse.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No course selected.");
            return;
        }

        // Retrieve the selected course
        Course course = null;
        for (Course c : courses) {
            if (c.getCourseName().equalsIgnoreCase(selectedCourse)) {
                course = c;
                break;
            }
        }

        if (course == null) {
            JOptionPane.showMessageDialog(frame, "Selected course not found.");
            return;
        }

        ArrayList<Assignment> courseAssignments = course.getAssignments();

        // Get the incomplete assignments and sort by date
        ArrayList<Task> incompleteAssignments = new ArrayList<>();
        for (Task task : courseAssignments) {
            if (!task.isCompleted()) {
                incompleteAssignments.add(task);
            }
        }

        if (incompleteAssignments.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No incomplete assignments for the selected course.");
            return;
        }

        // Sort incomplete assignments by due date
        incompleteAssignments.sort(Comparator.comparing(Task::getDueDate));

        // Set up a new frame for the assignments with checkboxes
        JFrame taskFrame = new JFrame("Incomplete Assignments for " + course.getCourseName());
        taskFrame.setSize(400, 400);
        taskFrame.setLocationRelativeTo(frame);

        // Checkbox panel
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        JCheckBox[] checkboxes = new JCheckBox[incompleteAssignments.size()];

        // Add assignments to checkboxes
        for (int i = 0; i < incompleteAssignments.size(); i++) {
            Task task = incompleteAssignments.get(i);
            checkboxes[i] = new JCheckBox(task.getTaskDescription() + " (Due: " + task.getDueDate() + ")");
            checkboxPanel.add(checkboxes[i]);
        }

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);

        // Complete button to mark selected tasks as complete
        JButton completeButton = new JButton("Mark as Completed");
        completeButton.addActionListener(e -> {
            for (int i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].isSelected()) {
                    Task task = incompleteAssignments.get(i);
                    task.setCompleted(true);
                    task.setCompletionDate(LocalDate.now());
                }
            }
            JOptionPane.showMessageDialog(taskFrame, "Selected tasks marked as completed!");
            taskFrame.dispose(); // Close the task frame
        });

        // Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> taskFrame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(completeButton);
        buttonPanel.add(exitButton);

        // Add components to the frame
        taskFrame.add(scrollPane, BorderLayout.CENTER);
        taskFrame.add(buttonPanel, BorderLayout.SOUTH);
        taskFrame.setVisible(true);
    }

    // Show user various feedback about completion of assignments
    private void showCompletionData() {
        // Collect all assignments for calculations
        ArrayList<Assignment> allAssignments = new ArrayList<>();
        for (Semester semester : user.getSchoolManager().getSemesters()) {
            for (Course course : semester.getCourses()) {
                allAssignments.addAll(course.getAssignments());
            }
        }

        // Calculate statistics
        int totalAssignments = allAssignments.size();
        int completedAssignments = 0;
        int overdueAssignments = 0;
        int assignmentsDueNextWeek = 0;

        for (Assignment assignment : allAssignments) {
            if (assignment.isCompleted()) { // Is the assignment complete?
                completedAssignments++;
            } else {
                if (assignment.getDueDate().isBefore(LocalDate.now())) { //Is the assignment overdue?
                    overdueAssignments++;
                } else if (!assignment.getDueDate().isAfter(LocalDate.now().plusWeeks(1))) {//Is the assignment due in the next week?
                    assignmentsDueNextWeek++;
                }
            }
        }

        int remainingAssignments = totalAssignments - completedAssignments;
        double completionPercentage = totalAssignments > 0
                ? (completedAssignments * 100.0 / totalAssignments)
                : 0.0;

        double remainingPercentage = totalAssignments > 0
                ? (remainingAssignments * 100.0 / totalAssignments)
                : 0.0;

        // Display the statistics
        String message = String.format(
                "Completion Data:\n"
                + "Total Assignments: %d\n"
                + "Completed Assignments: %d\n"
                + "Remaining Assignments: %d\n"
                + "Overdue Assignments: %d\n"
                + "Assignments Due Next Week: %d\n"
                + "Completion Percentage: %.2f%%\n"
                + "Remaining Percentage: %.2f%%",
                totalAssignments, completedAssignments, remainingAssignments, overdueAssignments,
                assignmentsDueNextWeek, completionPercentage, remainingPercentage
        );

        JOptionPane.showMessageDialog(frame, message, "School Completion Data", JOptionPane.INFORMATION_MESSAGE);
    }

    // Show completed assignments for a selected course
    private void showCompletedAssignments() {
        // Get the list of courses
        ArrayList<Course> courses = new ArrayList<>();

        // Iterate over all semesters to collect courses
        for (Semester semester : user.getSchoolManager().getSemesters()) {
            courses.addAll(semester.getCourses());
        }

        // Create an array of course options for the dropdown
        String[] courseOptions = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            courseOptions[i] = course.getCourseName();
        }

        // Show a dropdown to select a course
        String selectedCourse = (String) JOptionPane.showInputDialog(
                frame,
                "Select a course:",
                "Choose Course",
                JOptionPane.QUESTION_MESSAGE,
                null,
                courseOptions,
                courseOptions[0]
        );

        if (selectedCourse == null || selectedCourse.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No course selected.");
            return;
        }

        // Retrieve the selected course
        Course course = null;
        for (Course c : courses) {
            if (c.getCourseName().equalsIgnoreCase(selectedCourse)) {
                course = c;
                break;
            }
        }

        if (course == null) {
            JOptionPane.showMessageDialog(frame, "Selected course not found.");
            return;
        }

        ArrayList<Assignment> courseAssignments = course.getAssignments();

        // Get the completed assignments
        ArrayList<Task> completedAssignments = new ArrayList<>();
        for (Task task : courseAssignments) {
            if (task.isCompleted()) {
                completedAssignments.add(task);
            }
        }

        if (completedAssignments.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No completed assignments for the selected course.");
            return;
        }

        // Create a string to display completed assignments
        StringBuilder assignmentsText = new StringBuilder("Completed Assignments for " + course.getCourseName() + ":\n");
        for (Task task : completedAssignments) {
            assignmentsText.append(task.getTaskDescription())
                    .append(" (Completed on: ").append(task.getCompletionDate())
                    .append(")\n");
        }

        // Display the completed assignments
        JOptionPane.showMessageDialog(frame, assignmentsText.toString());
    }

    // Add an assignment to selected course
    private void addAssignment() {
        // Get the list of semesters
        ArrayList<Semester> semesters = user.getSchoolManager().getSemesters();
        if (semesters.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No semesters available.");
            return;
        }

        // Create an array of semester options for the dropdown
        String[] semesterOptions = new String[semesters.size()];
        for (int i = 0; i < semesters.size(); i++) {
            Semester semester = semesters.get(i);
            semesterOptions[i] = semester.getSeason() + " " + semester.getYear();
        }

        // Show a dropdown to select a semester
        String selectedSemester = (String) JOptionPane.showInputDialog(
                frame,
                "Select a semester:",
                "Choose Semester",
                JOptionPane.QUESTION_MESSAGE,
                null,
                semesterOptions,
                semesterOptions[0]
        );

        if (selectedSemester == null || selectedSemester.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No semester selected.");
            return;
        }

        // Parse the selected semester
        String[] semesterParts = selectedSemester.split(" ");
        String season = semesterParts[0];
        int year = Integer.parseInt(semesterParts[1]);

        // Retrieve the selected semester
        Semester semester = user.getSchoolManager().getSemester(year, season);
        if (semester == null) {
            JOptionPane.showMessageDialog(frame, "Selected semester not found.");
            return;
        }

        // Get the courses for the selected semester
        ArrayList<Course> courses = new ArrayList<>(semester.getCourses());
        if (courses.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No courses available for the selected semester.");
            return;
        }

        // Create an array of course options for the dropdown
        String[] courseOptions = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            courseOptions[i] = courses.get(i).getCourseName();
        }

        // Show a dropdown to select a course
        String selectedCourse = (String) JOptionPane.showInputDialog(
                frame,
                "Select a course:",
                "Choose Course",
                JOptionPane.QUESTION_MESSAGE,
                null,
                courseOptions,
                courseOptions[0]
        );

        if (selectedCourse == null || selectedCourse.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No course selected.");
            return;
        }

        // Retrieve the selected course
        Course course = null;
        for (Course c : courses) {
            if (c.getCourseName().equalsIgnoreCase(selectedCourse)) {
                course = c;
                break;
            }
        }

        if (course == null) {
            JOptionPane.showMessageDialog(frame, "Selected course not found.");
            return;
        }

        // Gather assignment details
        String description = JOptionPane.showInputDialog(frame, "Enter assignment description:");
        if (description == null || description.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Assignment description cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dueDateInput = JOptionPane.showInputDialog(frame, "Enter due date (YYYY-MM-DD):");
        try {
            LocalDate dueDate = LocalDate.parse(dueDateInput);

            String typeName = JOptionPane.showInputDialog(frame, "Enter assignment type (e.g., Homework, Exam):");
            if (typeName == null || typeName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Assignment type cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            AssignmentType assignmentType = course.getAssignmentTypeByName(typeName);
            if (assignmentType == null) {
                JOptionPane.showMessageDialog(frame, "Assignment type not found in the course. Please add it first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create and add the assignment
            Assignment newAssignment = new Assignment(dueDate.getYear(), dueDate.getMonthValue(), dueDate.getDayOfMonth(), description, typeName);
            course.addAssignment(newAssignment);

            JOptionPane.showMessageDialog(frame, "Assignment added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Get list of assignments sorted by priority based off grade weight
    private void showAssignmentsByPriority() {
        // Get the list of semesters
        ArrayList<Semester> semesters = user.getSchoolManager().getSemesters();
        if (semesters.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No semesters available.");
            return;
        }

        // Create an array of semester options for the dropdown
        String[] semesterOptions = new String[semesters.size()];
        for (int i = 0; i < semesters.size(); i++) {
            Semester semester = semesters.get(i);
            semesterOptions[i] = semester.getSeason() + " " + semester.getYear();
        }

        // Show a dropdown to select a semester
        String selectedSemester = (String) JOptionPane.showInputDialog(
                frame,
                "Select a semester:",
                "Choose Semester",
                JOptionPane.QUESTION_MESSAGE,
                null,
                semesterOptions,
                semesterOptions[0]
        );

        if (selectedSemester == null || selectedSemester.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No semester selected.");
            return;
        }

        // Parse the selected semester
        String[] semesterParts = selectedSemester.split(" ");
        String season = semesterParts[0];
        int year = Integer.parseInt(semesterParts[1]);

        // Retrieve assignments for the selected semester
        Semester semester = user.getSchoolManager().getSemester(year, season);
        if (semester == null) {
            JOptionPane.showMessageDialog(frame, "Selected semester not found.");
            return;
        }

        ArrayList<Task> allAssignments = semester.listAllAssignments();
        ArrayList<Assignment> incompleteAssignments = new ArrayList<>();

        // Filter to get only incomplete assignments
        for (Task task : allAssignments) {
            if (!task.isCompleted() && task instanceof Assignment) {
                incompleteAssignments.add((Assignment) task);
            }
        }

        // Calculate priority and sort assignments
        incompleteAssignments.sort((a1, a2) -> {
            double weight1 = calculateAssignmentPriority(a1, semester);
            double weight2 = calculateAssignmentPriority(a2, semester);
            if (weight1 > weight2) {
                return -1; // Higher weight comes earlier in the list
            } else if (weight1 < weight2) {
                return 1; // Lower weight comes later in the list
            } else {
                return 0; // Equal weights
            }
        });
        
        if (incompleteAssignments.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No incomplete assignments for the selected semester.");
            return;
        }

        // Display sorted assignments
        StringBuilder assignmentsText = new StringBuilder("Assignments by Priority:\n");
        for (Assignment assignment : incompleteAssignments) {
            double priority = calculateAssignmentPriority(assignment, semester);
            assignmentsText.append(assignment.getTaskDescription())
                    .append(" (Due: ").append(assignment.getDueDate())
                    .append(", Priority: ").append(String.format("%.2f", priority))
                    .append(")\n");
        }
        JOptionPane.showMessageDialog(frame, assignmentsText.toString());
    }

    // Helper method to calculate priority
    private double calculateAssignmentPriority(Assignment assignment, Semester semester) {
        Course course = null;
        for (Course c : semester.getCourses()) {
            if (c.getAssignments().contains(assignment)) {
                course = c;
                break;
            }
        }

        if (course == null) {
            return 0.0;
        }

        String assignmentType = assignment.getAssignmentType();
        AssignmentType type = course.getAssignmentTypeByName(assignmentType);

        if (type == null) {
            return 0.0;
        }

        double weight = type.getWeight();
        int numAssignmentsInType = course.getAssignmentsByType(assignmentType).size();

        return weight / (numAssignmentsInType > 0 ? numAssignmentsInType : 1); // Avoid division by zero
    }

    // Default data for demo
    private void setupDefaultStudent() {
        user.getSchoolManager().addSemester(new Semester(2024, "Spring"));
        user.getSchoolManager().addSemester(new Semester(2024, "Summer"));
        user.getSchoolManager().addSemester(new Semester(2024, "Fall"));
        user.getSchoolManager().addCourseToSemester(2024, "Fall", new Course("OOP", 3112, 3));
        user.getSchoolManager().addCourseToSemester(2024, "Fall", new Course("Security", 3200, 3));
        user.getSchoolManager().addCourseToSemester(2024, "Fall", new Course("OS and Networking", 3146, 3));
        user.getSchoolManager().addCourseToSemester(2024, "Fall", new Course("Web Dev", 3135, 3));
        user.getSchoolManager().addCourseToSemester(2024, "Fall", new Course("Operating Systems", 1234, 4));
        user.getSchoolManager().addCourseToSemester(2024, "Fall", new Course("Linear Algebra", 2121, 3));
        user.getSchoolManager().addCourseToSemester(2024, "Fall", new Course("Database", 4231, 3));
        user.getSchoolManager().addCourseToSemester(2024, "Fall", new Course("Statistics", 1237, 4));
        user.getSchoolManager().addCourseToSemester(2024, "Fall", new Course("English", 3523, 3));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignmentType(new AssignmentType("Homework", .2));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignmentType(new AssignmentType("Project", .5));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignmentType(new AssignmentType("Quizzes", .25));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignmentType(new AssignmentType("Attendance", .05));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignment(new Assignment(2024, 12, 7, "Homework 1", "Homework"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignment(new Assignment(2024, 12, 14, "Homework 2", "Homework"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignment(new Assignment(2024, 12, 15, "Homework 3", "Homework"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignment(new Assignment(2024, 12, 25, "Homework 4", "Homework"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignment(new Assignment(2024, 12, 2, "Homework 5", "Homework"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignment(new Assignment(2024, 12, 4, "Homework 6", "Homework"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignment(new Assignment(2024, 12, 30, "Homework 7", "Homework"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignment(new Assignment(2024, 12, 17, "Homework 8", "Homework"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignment(new Assignment(2024, 12, 19, "Homework 9", "Homework"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignment(new Assignment(2024, 12, 29, "Quiz 1", "Quizzes"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignment(new Assignment(2024, 12, 2, "Quiz 2", "Quizzes"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignment(new Assignment(2024, 12, 25, "Quiz 3", "Quizzes"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OOP").addAssignment(new Assignment(2024, 12, 1, "Final Project", "Project"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Security").addAssignment(new Assignment(2024, 12, 3, "Lab 1", "Lab"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Security").addAssignment(new Assignment(2024, 12, 7, "Midterm Exam", "Exam"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Security").addAssignment(new Assignment(2024, 12, 12, "Project Report", "Project"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Security").addAssignment(new Assignment(2024, 12, 20, "Final Exam", "Exam"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Security").addAssignment(new Assignment(2024, 12, 15, "Homework 1", "Homework"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OS and Networking").addAssignment(new Assignment(2024, 12, 5, "Lab 1", "Lab"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OS and Networking").addAssignment(new Assignment(2024, 12, 10, "Project Design Document", "Project"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OS and Networking").addAssignment(new Assignment(2024, 12, 17, "Midterm Exam", "Exam"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("OS and Networking").addAssignment(new Assignment(2024, 12, 25, "Final Exam", "Exam"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Web Dev").addAssignment(new Assignment(2024, 12, 2, "Assignment 1: HTML", "Homework"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Web Dev").addAssignment(new Assignment(2024, 12, 8, "Assignment 2: CSS", "Homework"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Web Dev").addAssignment(new Assignment(2024, 12, 15, "Final Project", "Project"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Web Dev").addAssignment(new Assignment(2024, 12, 20, "Exam", "Exam"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Operating Systems").addAssignment(new Assignment(2024, 12, 5, "Lab 1", "Lab"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Operating Systems").addAssignment(new Assignment(2024, 12, 10, "Homework 1", "Homework"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Operating Systems").addAssignment(new Assignment(2024, 12, 20, "Final Exam", "Exam"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Linear Algebra").addAssignment(new Assignment(2024, 12, 1, "Quiz 1", "Quizzes"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Linear Algebra").addAssignment(new Assignment(2024, 12, 8, "Homework 1", "Homework"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Linear Algebra").addAssignment(new Assignment(2024, 12, 20, "Final Exam", "Exam"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Database").addAssignment(new Assignment(2024, 12, 4, "Lab 1", "Lab"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Database").addAssignment(new Assignment(2024, 12, 10, "Homework 1", "Homework"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Database").addAssignment(new Assignment(2024, 12, 18, "Final Exam", "Exam"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Statistics").addAssignment(new Assignment(2024, 12, 3, "Quiz 1", "Quizzes"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Statistics").addAssignment(new Assignment(2024, 12, 9, "Homework 1", "Homework"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("Statistics").addAssignment(new Assignment(2024, 12, 20, "Final Exam", "Exam"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("English").addAssignment(new Assignment(2024, 12, 5, "Essay Draft", "Essay"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("English").addAssignment(new Assignment(2024, 12, 15, "Final Essay", "Essay"));
        user.getSchoolManager().getSemester(2024, "Fall").getCourseByName("English").addAssignment(new Assignment(2024, 12, 25, "Final Exam", "Exam"));
    }

}
