import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Signup extends JPanel {

    private JPanel mainPanel;
    private JPanel sidePanel;
    private JLabel titleLabel;
    private JTextField fullNameField;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JCheckBox termsCheckBox;
    private JButton signupButton;

    private ArrayList<String> usernames;
    private ArrayList<String> passwords;

    public Signup(ArrayList<String> usernames, ArrayList<String> passwords) {
        this.usernames = usernames;
        this.passwords = passwords;
        initComponents();
    }

    private void initComponents() {
        mainPanel = new JPanel();
        sidePanel = new JPanel();
        titleLabel = new JLabel();
        fullNameField = new JTextField();
        usernameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        termsCheckBox = new JCheckBox();
        signupButton = new JButton();

        setBackground(new Color(56, 105, 28));
        mainPanel.setBackground(new Color(56, 105, 28));

        titleLabel.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setText("SIGN UP");

        fullNameField.setText("Full Name");
        usernameField.setText("Username");
        emailField.setText("Email");
        passwordField.setText("Password");
        confirmPasswordField.setText("Confirm Password");

        termsCheckBox.setText("I agree with the Terms and Conditions");
        termsCheckBox.setForeground(Color.WHITE);
        termsCheckBox.setOpaque(false);

        signupButton.setText("SIGN UP");
        signupButton.setFont(new Font("Arial", Font.PLAIN, 18));
        signupButton.setBackground(new Color(102, 187, 106));
        signupButton.addActionListener(e -> signupAction());

        GroupLayout mainLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainLayout);
        mainLayout.setAutoCreateGaps(true);
        mainLayout.setAutoCreateContainerGaps(true);

        mainLayout.setHorizontalGroup(
            mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 158, 158)
                .addComponent(fullNameField, 303, 303, 303)
                .addComponent(usernameField, 303, 303, 303)
                .addComponent(emailField, 303, 303, 303)
                .addGroup(mainLayout.createSequentialGroup()
                    .addComponent(passwordField, 136, 136, 136)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(confirmPasswordField, 136, 136, 136))
                .addComponent(termsCheckBox)
                .addComponent(signupButton, 204, 204, 204)
        );

        mainLayout.setVerticalGroup(
            mainLayout.createSequentialGroup()
                .addComponent(titleLabel, 54, 54, 54)
                .addComponent(fullNameField, 31, 31, 31)
                .addComponent(usernameField, 31, 31, 31)
                .addComponent(emailField, 31, 31, 31)
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField, 31, 31, 31)
                    .addComponent(confirmPasswordField, 31, 31, 31))
                .addComponent(termsCheckBox)
                .addComponent(signupButton)
        );

        sidePanel.setLayout(new GridBagLayout());
        ImageIcon icon = new ImageIcon(getClass().getResource("/imgs/ImageIcon3.jpg"));
        JLabel imageLabel = new JLabel(icon);
        sidePanel.add(imageLabel);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(sidePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sidePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }

    private void signupAction() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
        String email = emailField.getText().trim();
        String fullName = fullNameField.getText().trim();

        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!");
            return;
        }

        if (!termsCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(this, "You must agree to the terms!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        if (usernames.contains(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists!");
            return;
        }

        usernames.add(username);
        passwords.add(password);

        JOptionPane.showMessageDialog(this, "Signup successful!");

        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setContentPane(new Login(usernames, passwords));
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);

        SwingUtilities.getWindowAncestor(this).dispose();
    }
}
