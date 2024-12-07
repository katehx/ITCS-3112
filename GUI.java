import java.awt.*;
import javax.swing.*;

public class GUI {
    private final JFrame frame;
    private JPanel panel;
    private JButton studentTaskViewButton, generalTaskViewButton, quitProgram;
    private User user;

    // Split into different classes to help keep file size down
    private SchoolTaskView schoolTaskView;
    private GeneralTaskView generalTaskView;

    public GUI() {
        // Set up the main frame 
        frame = new JFrame("Task Manager");

        // Prompt for the user's name
        String userName = JOptionPane.showInputDialog(frame, "What is your name:");
        if (userName == null || userName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name cannot be empty. Exiting.");
            System.exit(0);
        }

        // Create the user object with provided name
        this.user = new User(userName);

        // Initialize SchoolTaskView and GeneralTaskView
        schoolTaskView = new SchoolTaskView(frame, user, this);
        generalTaskView = new GeneralTaskView(frame, user, this);

        // Show the main menu
        showMainMenu();
    }

    public void showMainMenu() {
        // Set up the main menu frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#CCDBDC"));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        // Add greeting label at the top
        JLabel greetingLabel = new JLabel("Hello " + user.getName() + ", welcome to your personal task manager!", JLabel.CENTER);
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        greetingLabel.setForeground(Color.decode("#263D42"));
        panel.add(greetingLabel, BorderLayout.NORTH);
    
        // Set up the button panel
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        buttonPanel.setBackground(Color.decode("#CCDBDC"));
    
        // General Task View button
        generalTaskViewButton = createStyledButton("General Task View", "#283845", "#63C7B2", "#8E6C88");
        generalTaskViewButton.addActionListener(e -> generalTaskView.showGeneralTaskView());
        buttonPanel.add(generalTaskViewButton);
    
        // Student Task View button
        studentTaskViewButton = createStyledButton("Student Task View", "#283845", "#63C7B2", "#8E6C88");
        studentTaskViewButton.addActionListener(e -> schoolTaskView.showStudentTaskView());
        buttonPanel.add(studentTaskViewButton);
    
        // Quit button
        quitProgram = createStyledButton("Quit", "#283845", "#63C7B2", "#8E6C88");
        quitProgram.addActionListener(e -> System.exit(0));
        buttonPanel.add(quitProgram);
    
        // Add the button panel to the center
        panel.add(buttonPanel, BorderLayout.CENTER);
    
        // Add the panel to the frame and display it
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.setVisible(true);
    }
    
    // Method to make button styling process easier
    private JButton createStyledButton(String text, String backgroundColor, String textColor, String hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setBackground(Color.decode(backgroundColor)); 
        button.setForeground(Color.decode(textColor));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.decode(hoverColor), 2));
    
        // Change button color on hover
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
    
}
