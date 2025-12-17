import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginPanel extends JPanel {

    private EcoVibeMain mainFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private final Color NEON_GREEN = new Color(57, 255, 20);
    private final Color TEXT_DARK = new Color(50, 50, 50);
    private final Color TEXT_GRAY = new Color(150, 150, 150);

    public LoginPanel(EcoVibeMain mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridLayout(1, 2));
        add(createLeftPanel());
        add(createRightPanel());
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(20, 40, 20), getWidth(), getHeight(), new Color(40, 80, 40));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());
        
        JPanel brandContent = new JPanel(new GridLayout(2, 1));
        brandContent.setOpaque(false);
        
        JLabel logo = new JLabel();
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon logoIcon = new ImageIcon("green_leaf_recycle_sign1.png");
            if (logoIcon.getIconWidth() > 0) {
                 Image img = logoIcon.getImage();
                 Image resizedImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH); 
                 logo.setIcon(new ImageIcon(resizedImg));
            } else {
                 logo.setText("♻ EcoVibe");
                 logo.setFont(new Font("Segoe UI", Font.BOLD, 48));
                 logo.setForeground(NEON_GREEN);
            }
        } catch (Exception e) {
             logo.setText("♻ EcoVibe");
             logo.setFont(new Font("Segoe UI", Font.BOLD, 48));
             logo.setForeground(NEON_GREEN);
        }
        
        JLabel slogan = new JLabel("Sustainable Enterprise Solutions", SwingConstants.CENTER);
        slogan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        slogan.setForeground(new Color(200, 200, 200));
        
        brandContent.add(logo);
        brandContent.add(slogan);
        panel.add(brandContent);
        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new GridBagLayout()) {
            public Color getBackground() { return SettingsPanel.isDarkMode ? SettingsPanel.BG_DARK : Color.WHITE; }
        };
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 40, 10, 40);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel title = new JLabel("Welcome Back") {
            public Color getForeground() { return SettingsPanel.isDarkMode ? SettingsPanel.TEXT_DARK_MODE : TEXT_DARK; }
        };
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        
        gbc.gridy = 0; gbc.insets = new Insets(0, 40, 10, 40);
        panel.add(title, gbc);
        
        gbc.gridy++; gbc.insets = new Insets(0, 40, 40, 40);
        JLabel sub = new JLabel("Please enter your details to sign in.");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sub.setForeground(TEXT_GRAY);
        panel.add(sub, gbc);

        gbc.gridy++; gbc.insets = new Insets(0, 40, 5, 40);
        panel.add(createLabel("Username"), gbc);
        
        usernameField = createField(false);
        gbc.gridy++; gbc.insets = new Insets(0, 40, 20, 40);
        panel.add(usernameField, gbc);

        gbc.gridy++; gbc.insets = new Insets(0, 40, 5, 40);
        panel.add(createLabel("Password"), gbc);
        
        passwordField = (JPasswordField)createField(true);
        gbc.gridy++; gbc.insets = new Insets(0, 40, 30, 40);
        panel.add(passwordField, gbc);

        JButton loginBtn = new JButton("Sign In");
        styleButton(loginBtn);
        loginBtn.addActionListener(e -> attemptLogin());
        
        gbc.gridy++;
        gbc.ipady = 10;
        panel.add(loginBtn, gbc);

        // --- Sign Up Link ---
        JPanel signupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signupPanel.setOpaque(false);
        
        JLabel noAccountLabel = new JLabel("Don't have an account?");
        noAccountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        noAccountLabel.setForeground(TEXT_GRAY);
        signupPanel.add(noAccountLabel);
        
        JButton signupLink = new JButton("Sign up now");
        signupLink.setFont(new Font("Segoe UI", Font.BOLD, 12));
        signupLink.setForeground(new Color(40, 120, 40)); // Darker green text
        signupLink.setContentAreaFilled(false);
        signupLink.setBorderPainted(false);
        signupLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupLink.addActionListener(e -> mainFrame.showSignup());
        
        signupPanel.add(signupLink);
        
        gbc.gridy++;
        gbc.ipady = 0;
        gbc.insets = new Insets(10, 40, 0, 40);
        panel.add(signupPanel, gbc);

        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(TEXT_GRAY);
        return l;
    }

    private JTextField createField(boolean isPassword) {
        JTextField field = isPassword ? new JPasswordField() : new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(5, 0, 10, 0)
        ));
        
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, NEON_GREEN.darker()),
                    BorderFactory.createEmptyBorder(5, 0, 10, 0)
                ));
            }
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
                    BorderFactory.createEmptyBorder(5, 0, 10, 0)
                ));
            }
        });
        return field;
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btn.getModel().isRollover() ? new Color(80, 255, 60) : NEON_GREEN);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 25, 25);
                super.paint(g, c);
            }
        });
        btn.setForeground(new Color(30, 30, 30));
    }

    private void attemptLogin() {
        if (DataManager.getInstance().validateLogin(usernameField.getText(), new String(passwordField.getPassword()))) {
            mainFrame.showApp();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
        }
    }
}