import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;
import java.awt.*;
import java.awt.event.*;

// ===================== ECO VIBE APP =====================
public class EcoVibeApp extends JFrame {

    private static final Color DARK_GREEN = new Color(56, 142, 60);
    private static final Color LIGHT_GRAY = new Color(230, 230, 230);
    private static final Color BORDER_GRAY = new Color(220, 220, 220);

    private JPanel sidebar;
    private JPanel mainContent;
    private CardLayout cardLayout;

    private boolean darkMode = false;

    public EcoVibeApp() {
        setTitle("EcoVibe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        sidebar = createSidebar();
        mainContent = createMainContent();

        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);
        applyTheme();
    }

    // ------------------- SIDEBAR -------------------
    private JPanel createSidebar() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(240, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER_GRAY));

        // Logo
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(Color.WHITE);
        top.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel logo = new JLabel("EcoVibe");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logo.setForeground(DARK_GREEN);
        top.add(logo);
        panel.add(top, BorderLayout.NORTH);

        // Menu items
        JPanel menu = new JPanel();
        menu.setBackground(Color.WHITE);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(10, 20, 10, 20));

        menu.add(createSidebarButton("Dashboard"));
        menu.add(Box.createVerticalStrut(10));
        menu.add(createSidebarButton("Joined Events"));
        menu.add(Box.createVerticalStrut(10));
        menu.add(createSidebarButton("Posted Events"));
        menu.add(Box.createVerticalGlue());
        menu.add(createSidebarButton("Settings"));

        panel.add(menu, BorderLayout.CENTER);

        return panel;
    }

    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.addActionListener(e -> cardLayout.show(mainContent, text));
        return btn;
    }

    // ------------------- MAIN CONTENT -------------------
    private JPanel createMainContent() {
        cardLayout = new CardLayout();
        JPanel panel = new JPanel(cardLayout);

        panel.add(createDashboardPanel(), "Dashboard");
        panel.add(createJoinedPanel(), "Joined Events");
        panel.add(createPostedPanel(), "Posted Events");
        panel.add(createSettingsPanel(), "Settings");

        return panel;
    }

    // Dashboard Panel
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Greeting
        RoundedPanel greeting = new RoundedPanel(30, DARK_GREEN);
        greeting.setLayout(new FlowLayout(FlowLayout.LEFT));
        greeting.setBorder(new EmptyBorder(18, 25, 18, 25));
        greeting.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        JLabel hi = new JLabel("Hi, user123!");
        hi.setForeground(Color.WHITE);
        hi.setFont(new Font("Segoe UI", Font.BOLD, 16));
        greeting.add(hi);
        panel.add(greeting);
        panel.add(Box.createVerticalStrut(30));

        // Form
        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(new EmptyBorder(0, 120, 0, 120));
        form.add(new RoundedTextField("Name of the Event", LIGHT_GRAY));
        form.add(Box.createVerticalStrut(15));

        JPanel row = new JPanel(new GridLayout(1, 2, 15, 0));
        row.setBackground(Color.WHITE);
        row.add(new RoundedTextField("Date", LIGHT_GRAY));
        row.add(new RoundedTextField("Location", LIGHT_GRAY));
        form.add(row);
        form.add(Box.createVerticalStrut(25));

        RoundedPanel upload = new RoundedPanel(30, LIGHT_GRAY);
        upload.setPreferredSize(new Dimension(0, 220));
        upload.setLayout(new GridBagLayout());
        JLabel uploadLabel = new JLabel("📷 Upload Photo");
        uploadLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        upload.add(uploadLabel);
        form.add(upload);

        panel.add(form);
        return panel;
    }

    // Joined Events Panel
    private JPanel createJoinedPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.add(new JLabel("Joined Events Section"));
        return panel;
    }

    // Posted Events Panel
    private JPanel createPostedPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.add(new JLabel("Posted Events Section"));
        return panel;
    }

    // Settings Panel
    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel settingsTitle = new JLabel("Settings");
        settingsTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        settingsTitle.setBounds(50, 30, 300, 40);
        panel.add(settingsTitle);

        JLabel appearanceTitle = new JLabel("Appearance");
        appearanceTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        appearanceTitle.setForeground(DARK_GREEN);
        appearanceTitle.setBounds(50, 100, 300, 35);
        panel.add(appearanceTitle);

        JSeparator separator = new JSeparator();
        separator.setBounds(50, 140, 650, 1);
        separator.setForeground(BORDER_GRAY);
        panel.add(separator);

        JLabel darkModeLabel = new JLabel("Dark Mode");
        darkModeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        darkModeLabel.setBounds(50, 160, 150, 30);
        panel.add(darkModeLabel);

        ToggleButton toggleButton = new ToggleButton();
        toggleButton.setBounds(220, 160, 70, 30);
        toggleButton.addActionListener(e -> {
            darkMode = !darkMode;
            applyTheme();
        });
        panel.add(toggleButton);

        return panel;
    }

    // ------------------- THEME -------------------
    private void applyTheme() {
        Color bg = darkMode ? Color.DARK_GRAY : Color.WHITE;
        Color fg = darkMode ? Color.WHITE : Color.BLACK;
        getContentPane().setBackground(bg);
        sidebar.setBackground(darkMode ? Color.GRAY : Color.WHITE);

        updateColorsRecursive(mainContent, fg, bg);
        updateColorsRecursive(sidebar, fg, sidebar.getBackground());
        repaint();
    }

    private void updateColorsRecursive(Container container, Color fg, Color bg) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JPanel) {
                comp.setBackground(bg);
                updateColorsRecursive((Container) comp, fg, bg);
            }
            if (comp instanceof JLabel) comp.setForeground(fg);
            if (comp instanceof JButton) comp.setForeground(fg);
        }
    }

    // ------------------- CUSTOM COMPONENTS -------------------
    static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color bg;

        RoundedPanel(int radius, Color bg) {
            this.radius = radius;
            this.bg = bg;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    static class RoundedTextField extends JTextField {
        private final Color bg;
        private final String placeholder;

        RoundedTextField(String placeholder, Color bg) {
            this.placeholder = placeholder;
            this.bg = bg;
            setText(placeholder);
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setBorder(new EmptyBorder(10, 15, 10, 15));
            setOpaque(false);

            addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (getText().equals(placeholder)) setText("");
                }

                public void focusLost(FocusEvent e) {
                    if (getText().isEmpty()) setText(placeholder);
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    class ToggleButton extends JButton {
        private boolean state = false;

        ToggleButton() {
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            g2.setColor(state ? DARK_GREEN : LIGHT_GRAY);
            g2.fillRoundRect(0, 0, w, h, h, h);

            g2.setColor(Color.WHITE);
            int circleSize = h - 6;
            int circleX = state ? w - circleSize - 3 : 3;
            g2.fillOval(circleX, 3, circleSize, circleSize);

            g2.dispose();
        }

        @Override
        protected void processMouseEvent(MouseEvent e) {
            if (e.getID() == MouseEvent.MOUSE_CLICKED) {
                state = !state;
                repaint();
            }
            super.processMouseEvent(e);
        }
    }

    // ------------------- MAIN -------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EcoVibeApp().setVisible(true));
    }
}
