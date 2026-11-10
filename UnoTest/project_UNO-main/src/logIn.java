import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class logIn {

    private JFrame frame;
    private JTextField txtLogInHere;
    private JTextField txtUsername;
    private JTextField user;
    private JTextField txtPassword;
    private JPasswordField pwd;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    logIn window = new logIn();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public logIn() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(33, 33, 33));
        frame.setBounds(100, 100, 300, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        txtLogInHere = new JTextField();
        txtLogInHere.setEditable(false);
        txtLogInHere.setBackground(new Color(33, 33, 33));
        txtLogInHere.setForeground(new Color(255, 255, 255));
        txtLogInHere.setFont(new Font("Tahoma", Font.BOLD, 10));
        txtLogInHere.setHorizontalAlignment(SwingConstants.CENTER);
        txtLogInHere.setText("LOG IN HERE");
        txtLogInHere.setBounds(99, 30, 96, 18);
        frame.getContentPane().add(txtLogInHere);
        txtLogInHere.setColumns(10);

        txtUsername = new JTextField();
        txtUsername.setEditable(false);
        txtUsername.setText("Username:");
        txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
        txtUsername.setForeground(Color.WHITE);
        txtUsername.setFont(new Font("Tahoma", Font.BOLD, 10));
        txtUsername.setColumns(10);
        txtUsername.setBackground(new Color(33, 33, 33));
        txtUsername.setBounds(99, 97, 96, 18);
        frame.getContentPane().add(txtUsername);

        user = new JTextField();
        user.setBounds(99, 125, 96, 18);
        frame.getContentPane().add(user);
        user.setColumns(10);

        txtPassword = new JTextField();
        txtPassword.setEditable(false);
        txtPassword.setText("Password:");
        txtPassword.setHorizontalAlignment(SwingConstants.CENTER);
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setFont(new Font("Tahoma", Font.BOLD, 10));
        txtPassword.setColumns(10);
        txtPassword.setBackground(new Color(33, 33, 33));
        txtPassword.setBounds(99, 153, 96, 18);
        frame.getContentPane().add(txtPassword);

        pwd = new JPasswordField();
        pwd.setBounds(99, 181, 96, 18);
        frame.getContentPane().add(pwd);
        pwd.setColumns(10);

        JButton verify = new JButton("Verify");
        verify.setFont(new Font("Tahoma", Font.BOLD, 10));
        verify.setBounds(99, 236, 96, 20);
        frame.getContentPane().add(verify);

        JButton btnNewButton = new JButton("New User");
        btnNewButton.setToolTipText("New User");
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnNewButton.setBounds(99, 371, 96, 20);
        frame.getContentPane().add(btnNewButton);

        // Action listener for existing user login
        verify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = user.getText().trim();
                String enteredPassword = new String(pwd.getPassword());

                int userId = DatabaseConnection.validateUser(enteredUsername, enteredPassword);

                if (userId != -1) {
                    System.out.println("Access Granted. User ID: " + userId);
                    frame.dispose();

                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            try {
                                new toDoList(userId);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                } else {
                    System.out.println("Access Denied");
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Access Denied", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for registering a new user and generating a UID
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextField regUsername = new JTextField();
                JPasswordField regPassword = new JPasswordField();
                JTextField regEmail = new JTextField();

                Object[] formFields = {
                    "Username:", regUsername,
                    "Password:", regPassword,
                    "Email:", regEmail
                };

                int option = JOptionPane.showConfirmDialog(
                    frame,
                    formFields,
                    "Create New User",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
                );

                if (option == JOptionPane.OK_OPTION) {
                    String newUsername = regUsername.getText().trim();
                    String newPassword = new String(regPassword.getPassword());
                    String newEmail = regEmail.getText().trim();

                    if (newUsername.isEmpty() || newPassword.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Username and password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int newUid = DatabaseConnection.registerUser(newUsername, newPassword, newEmail);
                    if (newUid != -1) {
                        JOptionPane.showMessageDialog(frame, "Account created successfully!\nYour generated UID is: " + newUid, "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();

                        EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                try {
                                    new toDoList(newUid);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                    } else {
                        JOptionPane.showMessageDialog(frame, "Could not create user. Username may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}