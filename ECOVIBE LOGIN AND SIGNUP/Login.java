import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Login extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;

    private ArrayList<String> usernames;
    private ArrayList<String> passwords;

    public Login(ArrayList<String> usernames, ArrayList<String> passwords) {
        this.usernames = usernames;
        this.passwords = passwords;
        initComponents();
    }

    private void initComponents() {
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(new Color(56, 105, 28));

        JLabel titleLabel = new JLabel("ECOVIBE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        usernameField = new JTextField("username");
        usernameField.setBorder(null);

        passwordField = new JPasswordField("password");

        loginButton = new JButton("LOGIN");
        loginButton.setBackground(new Color(102, 187, 106));
        loginButton.addActionListener(e -> loginAction());

        JLabel accountLabel = new JLabel("Don't Have an Account?");
        accountLabel.setForeground(Color.WHITE);
        accountLabel.setFont(new Font("Arial", Font.PLAIN, 10));

        signupButton = new JButton("Sign Up Now!");
        signupButton.setFont(new Font("Arial", Font.BOLD, 10));
        signupButton.setForeground(Color.WHITE);
        signupButton.setBackground(new Color(56, 105, 28));
        signupButton.setBorder(null);
        signupButton.addActionListener(e -> openSignup());

        GroupLayout loginLayout = new GroupLayout(loginPanel);
        loginPanel.setLayout(loginLayout);
        loginLayout.setAutoCreateGaps(true);
        loginLayout.setAutoCreateContainerGaps(true);

        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                .addGroup(loginLayout.createSequentialGroup()
                    .addComponent(accountLabel)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(signupButton))
        );

        loginLayout.setVerticalGroup(
            loginLayout.createSequentialGroup()
                .addGap(80)
                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(30)
                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addGap(10)
                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addGap(15)
                .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addGap(10)
                .addGroup(loginLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(accountLabel)
                    .addComponent(signupButton))
        );

        JPanel imagePanel = new JPanel(new BorderLayout());
        ImageIcon icon = new ImageIcon(getClass().getResource("/imgs/ImageIcon2.jpg"));
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(loginPanel, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                    .addComponent(imagePanel, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(loginPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imagePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        this.setPreferredSize(new Dimension(660, 450));
    }

    private void loginAction() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!");
            return;
        }

        int index = usernames.indexOf(username);
        if (index != -1 && passwords.get(index).equals(password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            JFrame mainFrame = new JFrame("Main Page");
            mainFrame.setSize(600, 400);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.add(new JLabel("Welcome, " + username + "!", SwingConstants.CENTER));
            mainFrame.setVisible(true);
            SwingUtilities.getWindowAncestor(this).dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password");
        }
    }

    private void openSignup() {
        JFrame signupFrame = new JFrame("Signup");
        signupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signupFrame.setContentPane(new Signup(usernames, passwords));
        signupFrame.pack();
        signupFrame.setLocationRelativeTo(null);
        signupFrame.setVisible(true);
        SwingUtilities.getWindowAncestor(this).dispose();
    }

    public static void main(String[] args) {
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<String> passwords = new ArrayList<>();
        usernames.add("admin");
        passwords.add("1234");

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new Login(usernames, passwords));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
