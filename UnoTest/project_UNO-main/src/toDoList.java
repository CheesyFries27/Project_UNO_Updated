import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class toDoList extends JFrame {

	private static final long serialVersionUID = 1L;
	
    private JPanel contentPane;
    private JTextField taskInputField;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private int currentUserId;

    /**
     * Default constructor for standalone preview/testing.
     */
    public toDoList() {
        this(0);
    }

    /**
     * Main constructor accepting the logged-in or newly created User ID.
     */
    public toDoList(int userId) {
        this.currentUserId = userId;
        initialize();
    }

    private void initialize() {
        setTitle("My To-Do List (UID: " + currentUserId + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 350, 500);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(33, 33, 33));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Title header
        JTextField lblTitle = new JTextField();
        lblTitle.setText("YOUR TO-DO LIST (USER #" + currentUserId + ")");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblTitle.setColumns(10);
        lblTitle.setBackground(new Color(33, 33, 33));
        lblTitle.setBounds(50, 20, 240, 20);
        lblTitle.setEditable(false);
        contentPane.add(lblTitle);

        // Scroll pane and task list
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setBackground(new Color(45, 45, 45));
        taskList.setForeground(Color.WHITE);
        taskList.setFont(new Font("Tahoma", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBounds(30, 60, 275, 280);
        contentPane.add(scrollPane);

        // Text field to enter new task
        taskInputField = new JTextField();
        taskInputField.setBounds(30, 360, 275, 25);
        contentPane.add(taskInputField);
        taskInputField.setColumns(10);

        // Add task button
        JButton btnAddTask = new JButton("Add Task");
        btnAddTask.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnAddTask.setBounds(30, 400, 130, 25);
        contentPane.add(btnAddTask);

        // Delete selected task button
        JButton btnDeleteTask = new JButton("Delete Selected");
        btnDeleteTask.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnDeleteTask.setBounds(175, 400, 130, 25);
        contentPane.add(btnDeleteTask);

        // Button listeners
        btnAddTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newTask = taskInputField.getText().trim();
                if (!newTask.isEmpty()) {
                    taskListModel.addElement(newTask);
                    taskInputField.setText("");
                }
            }
        });

        btnDeleteTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskListModel.remove(selectedIndex);
                }
            }
        });

        setVisible(true);
    }

    /**
     * Standalone runner for testing the frame.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    toDoList frame = new toDoList(1);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}