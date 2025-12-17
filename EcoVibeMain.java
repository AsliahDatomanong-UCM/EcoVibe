import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EcoVibeMain extends JFrame {

    private static CardLayout rootLayout = new CardLayout();
    private static JPanel rootPanel = new JPanel(rootLayout);
    private CardLayout contentLayout = new CardLayout();
    private JPanel contentPanel = new JPanel(contentLayout);
    private JPanel[] navItems;
    private AnimatedToggle themeToggle; 
    
    // Panel References
    private DashboardPanel dashboardPanel;
    private JointEventsPanel jointEventsPanel;
    private PostedEventsPanel postedEventsPanel;
    private SettingsPanel settingsPanel;

    public static void main(String[] args) {
        DataManager.getInstance(); 
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
            new EcoVibeMain().setVisible(true);
        });
    }

    public EcoVibeMain() {
        setTitle("EcoVibe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);

        rootPanel.add(new LoginPanel(this), "LOGIN");
        rootPanel.add(new SignupPanel(this), "SIGNUP");
        rootPanel.add(createMainAppPanel(), "APP");

        add(rootPanel);
        rootLayout.show(rootPanel, "LOGIN");
    }

    public void showSignup() { rootLayout.show(rootPanel, "SIGNUP"); }
    public void showLogin() { rootLayout.show(rootPanel, "LOGIN"); }
    
    public void showApp() { 
        rootLayout.show(rootPanel, "APP");
        updateTheme();
    }
    
    private void toggleGlobalTheme() {
        SettingsPanel.isDarkMode = !SettingsPanel.isDarkMode;
        themeToggle.animateToggle(SettingsPanel.isDarkMode);
        updateTheme();
    }
    
    private void updateTheme() {
        SettingsPanel.reapplyTheme(this);
    }
    
    public void showPage(String pageName) {
        contentLayout.show(contentPanel, pageName);
        
        if (pageName.equals("Posted Events") && postedEventsPanel != null) postedEventsPanel.refresh();
        if (pageName.equals("Joined Events") && jointEventsPanel != null) jointEventsPanel.refresh();
        
        if (navItems != null) {
            for (JPanel nav : navItems) updateNavItemColor(nav, false);
            for (JPanel nav : navItems) {
                Component c = nav.getComponent(0);
                if (c instanceof JLabel && ((JLabel)c).getText().equals(pageName)) {
                    updateNavItemColor(nav, true);
                }
            }
        }
        SettingsPanel.reapplyTheme(contentPanel);
    }

    private JPanel createMainAppPanel() {
        JPanel mainApp = new JPanel(new BorderLayout());
        mainApp.add(createSidebar(), BorderLayout.WEST);
        
        JPanel centralPanel = new JPanel(new BorderLayout());
        
        // --- Top Header with Animated Toggle ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(15, 30, 15, 30));
        headerPanel.setOpaque(false); 
        
        themeToggle = new AnimatedToggle();
        themeToggle.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { toggleGlobalTheme(); }
        });
        
        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightHeader.setOpaque(false);
        rightHeader.add(themeToggle);
        
        headerPanel.add(rightHeader, BorderLayout.EAST);
        centralPanel.add(headerPanel, BorderLayout.NORTH);
        
        // --- Content ---
        dashboardPanel = new DashboardPanel(this);
        jointEventsPanel = new JointEventsPanel();
        postedEventsPanel = new PostedEventsPanel();
        settingsPanel = new SettingsPanel(); 
        
        contentPanel.add(dashboardPanel, "Dashboard");
        contentPanel.add(jointEventsPanel, "Joined Events");
        contentPanel.add(postedEventsPanel, "Posted Events");
        contentPanel.add(settingsPanel, "Settings");

        centralPanel.add(contentPanel, BorderLayout.CENTER);
        mainApp.add(centralPanel, BorderLayout.CENTER);
        return mainApp;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel() {
            @Override
            public Color getBackground() {
                return SettingsPanel.isDarkMode ? SettingsPanel.BG_DARK : Color.WHITE;
            }
        };
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(260, getHeight())); 
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)));

        // --- Logo with Image Support ---
        JLabel logo = new JLabel();
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setBorder(new EmptyBorder(40, 10, 40, 10));
        
        ImageIcon logoIcon = null;
        try {
            // Try loading as local file
            logoIcon = new ImageIcon("green_leaf_recycle_sign1.png");
            
            // If file load failed (width -1), try getResource
            if (logoIcon.getIconWidth() == -1) {
                URL imgUrl = getClass().getResource("/green_leaf_recycle_sign1.png");
                if (imgUrl != null) {
                    logoIcon = new ImageIcon(imgUrl);
                } else {
                    imgUrl = getClass().getResource("green_leaf_recycle_sign1.png");
                    if (imgUrl != null) logoIcon = new ImageIcon(imgUrl);
                }
            }
        } catch (Exception e) {
            // Fallback handled below
        }

        if (logoIcon != null && logoIcon.getIconWidth() > 0) {
            Image img = logoIcon.getImage();
            Image resizedImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH); 
            logo.setIcon(new ImageIcon(resizedImg));
            logo.setText(" EcoVibe"); // Text next to icon
            logo.setFont(new Font("Segoe UI", Font.BOLD, 26)); 
            logo.setForeground(SettingsPanel.GREEN_LIGHT); 
        } else {
            // Fallback Text
            logo.setText("♻ EcoVibe");
            logo.setFont(new Font("Segoe UI", Font.BOLD, 26)); 
            logo.setForeground(SettingsPanel.GREEN_LIGHT); 
        }
        
        sidebar.add(logo);

        navItems = new JPanel[]{
            createNavItem("Dashboard", true),
            createNavItem("Joined Events", false),
            createNavItem("Posted Events", false),
            createNavItem("Settings", false)
        };

        for (JPanel item : navItems) sidebar.add(item);
        
        sidebar.add(Box.createVerticalGlue());
        
        JPanel logoutBtn = createNavItem("Logout", false);
        logoutBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { showLogin(); }
        });
        sidebar.add(logoutBtn);

        return sidebar;
    }

    private JPanel createNavItem(String name, boolean isActive) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 15));
        item.setMaximumSize(new Dimension(260, 55));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updateNavItemColor(item, isActive);

        JLabel label = new JLabel(name);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        item.add(label);

        if (!name.equals("Logout")) {
            item.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    showPage(name);
                }
            });
        }
        return item;
    }

    private void updateNavItemColor(JPanel item, boolean isActive) {
        boolean dark = SettingsPanel.isDarkMode;
        Color bgActive = dark ? new Color(45, 45, 45) : new Color(235, 245, 235);
        Color textActive = dark ? SettingsPanel.GREEN_DARK : SettingsPanel.GREEN_LIGHT;
        
        item.setBackground(isActive ? bgActive : (dark ? SettingsPanel.BG_DARK : Color.WHITE));
        if (item.getComponentCount() > 0) {
            ((JLabel) item.getComponent(0)).setForeground(isActive ? textActive : (dark ? SettingsPanel.TEXT_DARK_MODE : new Color(60, 60, 60)));
        }
    }
    
    // --- Custom Animated Toggle ---
    private class AnimatedToggle extends JComponent {
        private float animationProgress = 0f; // 0.0 (Sun) -> 1.0 (Moon)
        private Timer timer;
        
        public AnimatedToggle() {
            setPreferredSize(new Dimension(60, 30));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        public void animateToggle(boolean toDark) {
            if (timer != null && timer.isRunning()) timer.stop();
            timer = new Timer(10, e -> {
                boolean done = false;
                if (toDark) {
                    animationProgress += 0.1f;
                    if (animationProgress >= 1.0f) { animationProgress = 1.0f; done = true; }
                } else {
                    animationProgress -= 0.1f;
                    if (animationProgress <= 0.0f) { animationProgress = 0.0f; done = true; }
                }
                repaint();
                if (done) ((Timer)e.getSource()).stop();
            });
            timer.start();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Background (Day Sky Blue -> Night Black)
            Color daySky = new Color(135, 206, 235);
            Color nightSky = new Color(20, 20, 30);
            g2.setColor(interpolateColor(daySky, nightSky, animationProgress));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            
            // Icon (Sun Yellow -> Moon White)
            Color sunColor = Color.YELLOW;
            Color moonColor = Color.WHITE;
            g2.setColor(interpolateColor(sunColor, moonColor, animationProgress));
            
            int startX = 4;
            int endX = getWidth() - 26;
            int currentX = startX + (int)((endX - startX) * animationProgress);
            
            g2.fillOval(currentX, 4, 22, 22);
            
            // Moon Shadow Effect (Eclipse)
            if (animationProgress > 0.5f) {
                g2.setColor(interpolateColor(daySky, nightSky, animationProgress));
                g2.fillOval(currentX - 6, 4, 22, 22);
            }
        }
        
        private Color interpolateColor(Color c1, Color c2, float ratio) {
            int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * ratio);
            int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * ratio);
            int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * ratio);
            return new Color(r, g, b);
        }
    }
}