import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SettingsPanel extends JPanel {
    
    // Global State for Theme (Still kept here for centralized access)
    public static boolean isDarkMode = false;
    
    // Global Colors
    public static final Color GREEN_LIGHT = new Color(39, 174, 96);
    public static final Color BG_LIGHT = new Color(240, 242, 245);
    public static final Color CARD_LIGHT = Color.WHITE;
    public static final Color TEXT_LIGHT_MODE = new Color(33, 37, 41);
    public static final Color SUBTEXT_LIGHT = new Color(108, 117, 125);
    public static final Color BORDER_LIGHT = new Color(222, 226, 230);

    public static final Color GREEN_DARK = new Color(57, 255, 20);
    public static final Color BG_DARK = new Color(24, 25, 26);
    public static final Color CARD_DARK = new Color(36, 37, 38);
    public static final Color TEXT_DARK_MODE = new Color(228, 230, 235);
    public static final Color SUBTEXT_DARK = new Color(176, 179, 184);
    public static final Color BORDER_DARK = new Color(62, 64, 66);

    public SettingsPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 40, 30, 40));

        // Main Scrollable Container
        JPanel content = new JPanel(new GridBagLayout()) {
            public Color getBackground() { return isDarkMode ? BG_DARK : BG_LIGHT; }
        };
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 25, 0);

        // Header
        JLabel title = new JLabel("Settings & Profile") {
            public Color getForeground() { return isDarkMode ? TEXT_DARK_MODE : TEXT_LIGHT_MODE; }
        };
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        content.add(title, gbc);

        // 1. Profile Card
        gbc.gridy++;
        content.add(createProfileCard(), gbc);

        // 2. Preferences Card
        gbc.gridy++;
        content.add(createPreferencesCard(), gbc);
        
        // 3. Account Actions
        gbc.gridy++;
        content.add(createAccountActionsCard(), gbc);

        // Spacer
        gbc.gridy++; gbc.weighty = 1.0;
        content.add(Box.createGlue(), gbc);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null); scroll.setOpaque(false); scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        setBackground(isDarkMode ? BG_DARK : BG_LIGHT);
        super.paintComponent(g);
    }

    private JPanel createProfileCard() {
        RoundedPanel card = new RoundedPanel(20);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(25, 30, 25, 30));
        
        // Left: Avatar
        JLabel avatar = new JLabel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(GREEN_LIGHT);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 36));
                FontMetrics fm = g2.getFontMetrics();
                String init = "A"; // Mock Initial
                g2.drawString(init, (getWidth()-fm.stringWidth(init))/2, (getHeight()+fm.getAscent())/2 - 5);
            }
        };
        avatar.setPreferredSize(new Dimension(80, 80));
        
        // Center: Info
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(new EmptyBorder(0, 20, 0, 0));
        
        JLabel name = new JLabel("Admin User") { public Color getForeground() { return isDarkMode ? TEXT_DARK_MODE : TEXT_LIGHT_MODE; } };
        name.setFont(new Font("Segoe UI", Font.BOLD, 22));
        
        JLabel email = new JLabel("admin@ecovibe.com") { public Color getForeground() { return isDarkMode ? SUBTEXT_DARK : SUBTEXT_LIGHT; } };
        email.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        infoPanel.add(name);
        infoPanel.add(email);
        
        // Right: Edit Button
        JButton editBtn = new JButton("Edit Profile");
        styleButton(editBtn, false);
        
        card.add(avatar, BorderLayout.WEST);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(editBtn, BorderLayout.EAST);
        
        return card;
    }

    private JPanel createPreferencesCard() {
        RoundedPanel card = new RoundedPanel(20);
        card.setLayout(new GridLayout(0, 1, 0, 15));
        card.setBorder(new EmptyBorder(25, 30, 25, 30));
        
        JLabel header = new JLabel("Preferences") { public Color getForeground() { return isDarkMode ? GREEN_DARK : GREEN_LIGHT; } };
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        card.add(header);
        
        card.add(createSwitchRow("Email Notifications", "Receive updates about your events"));
        card.add(createSwitchRow("Public Profile", "Allow others to find you"));
        card.add(createSwitchRow("Data Saver", "Reduce image quality to save data"));
        
        return card;
    }
    
    private JPanel createSwitchRow(String title, String desc) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        JPanel textGroup = new JPanel(new GridLayout(2, 1));
        textGroup.setOpaque(false);
        JLabel t = new JLabel(title) { public Color getForeground() { return isDarkMode ? TEXT_DARK_MODE : TEXT_LIGHT_MODE; } };
        t.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel d = new JLabel(desc) { public Color getForeground() { return isDarkMode ? SUBTEXT_DARK : SUBTEXT_LIGHT; } };
        d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textGroup.add(t); textGroup.add(d);
        
        JCheckBox toggle = new JCheckBox();
        toggle.setOpaque(false);
        
        panel.add(textGroup, BorderLayout.CENTER);
        panel.add(toggle, BorderLayout.EAST);
        return panel;
    }
    
    private JPanel createAccountActionsCard() {
        RoundedPanel card = new RoundedPanel(20);
        card.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        card.setBorder(new EmptyBorder(25, 30, 25, 30));
        
        JButton passBtn = new JButton("Change Password");
        styleButton(passBtn, false);
        
        JButton delBtn = new JButton("Delete Account");
        styleButton(delBtn, true); // Danger style
        
        card.add(passBtn);
        card.add(delBtn);
        return card;
    }
    
    private void styleButton(JButton btn, boolean isDanger) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setContentAreaFilled(false);
        
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = isDanger ? new Color(220, 53, 69) : (isDarkMode ? new Color(60, 60, 60) : new Color(230, 230, 230));
                Color fg = isDanger ? Color.WHITE : (isDarkMode ? TEXT_DARK_MODE : TEXT_LIGHT_MODE);
                
                if (btn.getModel().isRollover()) bg = bg.brighter();
                
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 10, 10);
                
                btn.setForeground(fg);
                super.paint(g, c);
            }
        });
        btn.setPreferredSize(new Dimension(160, 40));
    }

    // --- Helper Methods ---
    public static void reapplyTheme(Component root) {
        // Recursively apply theme to all components
        applyThemeRecursively(root);
        
        if (root instanceof JComponent) {
            ((JComponent)root).revalidate();
            ((JComponent)root).repaint();
        }
    }

    private static void applyThemeRecursively(Component c) {
        // Skip custom toggle components if any (handled elsewhere or custom painted)
        if (c.getClass().getName().contains("CustomToggle")) return;

        Color bg = isDarkMode ? BG_DARK : BG_LIGHT;
        Color text = isDarkMode ? TEXT_DARK_MODE : TEXT_LIGHT_MODE;
        Color subText = isDarkMode ? SUBTEXT_DARK : SUBTEXT_LIGHT;
        Color accent = isDarkMode ? GREEN_DARK : GREEN_LIGHT;
        Color border = isDarkMode ? BORDER_DARK : BORDER_LIGHT;

        // Backgrounds
        if (c instanceof JPanel && !(c.getClass().getName().contains("RoundedPanel")) && !(c instanceof SettingsPanel)) {
            // Apply background if it's opaque, assuming standard panels follow the theme
            if (c.isOpaque()) {
                c.setBackground(bg);
            }
        }

        // Labels
        if (c instanceof JLabel) {
            JLabel lbl = (JLabel) c;
            String txt = lbl.getText();
            if (txt != null) {
                if (txt.contains("EcoVibe") || txt.equals("Appearance") || txt.contains("Joined") || txt.contains("Posted")) {
                    lbl.setForeground(accent);
                } else if (txt.length() > 40) { // Heuristic for description text
                    lbl.setForeground(subText);
                } else {
                    lbl.setForeground(text);
                }
            }
        } 
        
        // Text Fields
        if (c instanceof JTextField) {
            c.setBackground(isDarkMode ? new Color(60, 60, 60) : Color.WHITE);
            c.setForeground(text);
            ((JTextField) c).setCaretColor(text);
        }
        
        // Checkboxes
        if (c instanceof JCheckBox) {
            c.setForeground(text);
        }
        
        // Separators
        if (c instanceof JSeparator) {
            c.setForeground(border);
        }

        // ScrollPanes
        if (c instanceof JScrollPane) {
            ((JScrollPane) c).getViewport().setBackground(bg);
            ((JScrollPane) c).setBorder(BorderFactory.createEmptyBorder());
        }

        // Recursion
        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents()) {
                applyThemeRecursively(child);
            }
        }
    }

    private class RoundedPanel extends JPanel {
        private int radius;
        public RoundedPanel(int r) { this.radius = r; setOpaque(false); }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(isDarkMode ? CARD_DARK : CARD_LIGHT);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.setColor(isDarkMode ? BORDER_DARK : BORDER_LIGHT);
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
        }
    }
}