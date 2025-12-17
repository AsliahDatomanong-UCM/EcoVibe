import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;

public class SignupPanel extends JPanel {

    private EcoVibeMain mainFrame;
    private JTextField fullNameField, usernameField, emailField;
    private JPasswordField passwordField, confirmField;
    private JCheckBox termsBox;

    // Colors
    private final Color NEON_GREEN = new Color(57, 255, 20);
    private final Color TEXT_DARK = new Color(50, 50, 50);
    private final Color TEXT_GRAY = new Color(150, 150, 150);

    public SignupPanel(EcoVibeMain mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridLayout(1, 2)); // Split screen

        // 1. Left Panel (Visual Branding)
        add(createLeftPanel());

        // 2. Right Panel (Form)
        add(createRightPanel());
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Angled Gradient
                GradientPaint gp = new GradientPaint(0, 0, new Color(20, 40, 20), getWidth(), getHeight(), new Color(40, 80, 40));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());
        
        JPanel brandContent = new JPanel(new GridLayout(2, 1));
        brandContent.setOpaque(false);
        
        // Logo Handling
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
        
        JLabel slogan = new JLabel("Join the Movement", SwingConstants.CENTER);
        slogan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        slogan.setForeground(new Color(200, 200, 200));
        
        brandContent.add(logo);
        brandContent.add(slogan);
        
        panel.add(brandContent);
        return panel;
    }

    private JPanel createRightPanel() {
        // Dynamic Background Panel
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            public Color getBackground() {
                return SettingsPanel.isDarkMode ? SettingsPanel.BG_DARK : Color.WHITE;
            }
        };
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 40, 5, 40);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // Header
        JLabel title = new JLabel("Create Account") {
            @Override
            public Color getForeground() {
                return SettingsPanel.isDarkMode ? SettingsPanel.TEXT_DARK_MODE : TEXT_DARK;
            }
        };
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 40, 20, 40);
        panel.add(title, gbc);

        // Fields
        fullNameField = addField(panel, "Full Name", gbc);
        usernameField = addField(panel, "Username", gbc);
        emailField = addField(panel, "Email", gbc);
        passwordField = addPasswordField(panel, "Password", gbc);
        confirmField = addPasswordField(panel, "Confirm Password", gbc);

        // Terms
        termsBox = new JCheckBox("I agree to the Terms & Conditions") {
            @Override
            public Color getForeground() {
                return SettingsPanel.isDarkMode ? SettingsPanel.TEXT_DARK_MODE : TEXT_DARK;
            }
            @Override
            public Color getBackground() {
                return SettingsPanel.isDarkMode ? SettingsPanel.BG_DARK : Color.WHITE;
            }
        };
        termsBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        termsBox.setFocusPainted(false);
        gbc.gridy++;
        gbc.insets = new Insets(10, 40, 10, 40);
        panel.add(termsBox, gbc);

        // Register Button
        JButton regBtn = new JButton("Sign Up");
        styleButton(regBtn);
        regBtn.addActionListener(e -> register());
        
        gbc.gridy++;
        gbc.ipady = 10;
        gbc.insets = new Insets(10, 40, 10, 40);
        panel.add(regBtn, gbc);

        // Back to Login
        JButton backBtn = new JButton("Already have an account? Sign In");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        backBtn.setForeground(new Color(40, 120, 40));
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> mainFrame.showLogin());
        
        gbc.gridy++;
        gbc.ipady = 0;
        panel.add(backBtn, gbc);

        return panel;
    }

    private JTextField addField(JPanel panel, String labelText, GridBagConstraints gbc) {
        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 0, 40);
        JLabel label = new JLabel(labelText) {
            @Override
            public Color getForeground() {
                return SettingsPanel.isDarkMode ? new Color(180, 180, 180) : TEXT_GRAY;
            }
        };
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        panel.add(label, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 10, 40);
        JTextField field = createMatteField();
        panel.add(field, gbc);
        return field;
    }

    private JPasswordField addPasswordField(JPanel panel, String labelText, GridBagConstraints gbc) {
        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 0, 40);
        JLabel label = new JLabel(labelText) {
            @Override
            public Color getForeground() {
                return SettingsPanel.isDarkMode ? new Color(180, 180, 180) : TEXT_GRAY;
            }
        };
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        panel.add(label, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 40, 10, 40);
        JPasswordField field = createMattePasswordField();
        panel.add(field, gbc);
        return field;
    }

    private JTextField createMatteField() {
        JTextField field = new JTextField() {
            @Override
            public Color getBackground() {
                return SettingsPanel.isDarkMode ? new Color(60, 63, 65) : Color.WHITE;
            }
            @Override
            public Color getForeground() {
                return SettingsPanel.isDarkMode ? Color.WHITE : TEXT_DARK;
            }
            @Override
            public Color getCaretColor() {
                return SettingsPanel.isDarkMode ? Color.WHITE : Color.BLACK;
            }
        };
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(2, 0, 5, 0)
        ));
        
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, NEON_GREEN.darker()),
                    BorderFactory.createEmptyBorder(2, 0, 5, 0)
                ));
            }
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
                    BorderFactory.createEmptyBorder(2, 0, 5, 0)
                ));
            }
        });
        return field;
    }

    private JPasswordField createMattePasswordField() {
        JPasswordField field = new JPasswordField() {
            @Override
            public Color getBackground() {
                return SettingsPanel.isDarkMode ? new Color(60, 63, 65) : Color.WHITE;
            }
            @Override
            public Color getForeground() {
                return SettingsPanel.isDarkMode ? Color.WHITE : TEXT_DARK;
            }
            @Override
            public Color getCaretColor() {
                return SettingsPanel.isDarkMode ? Color.WHITE : Color.BLACK;
            }
        };
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(2, 0, 5, 0)
        ));
        
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, NEON_GREEN.darker()),
                    BorderFactory.createEmptyBorder(2, 0, 5, 0)
                ));
            }
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
                    BorderFactory.createEmptyBorder(2, 0, 5, 0)
                ));
            }
        });
        return field;
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(NEON_GREEN);
        btn.setForeground(new Color(30, 30, 30));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btn.getModel().isRollover() ? new Color(80, 255, 60) : NEON_GREEN);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 25, 25);
                super.paint(g, c);
            }
        });
    }

    private void register() {
        String fullName = fullNameField.getText().trim();
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirm = new String(confirmField.getPassword());

        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!termsBox.isSelected()) {
            JOptionPane.showMessageDialog(this, "You must agree to the Terms & Conditions", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Use DataManager to register
        boolean success = DataManager.getInstance().registerUser(username, password, fullName, email);
        if (success) {
            JOptionPane.showMessageDialog(this, "Success! Please Sign In.");
            mainFrame.showLogin();
        } else {
            JOptionPane.showMessageDialog(this, "Username already taken", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}