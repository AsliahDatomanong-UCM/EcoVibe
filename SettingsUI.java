/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.settingsui;

/**
 *
 * @author Asliah
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class SettingsUI extends JFrame {
    private boolean darkMode = false;
    private JPanel mainPanel;
    private JPanel sidebar;
    private JPanel contentPanel;
    private ToggleButton toggleButton;
    
    private final Color LIGHT_BG = new Color(248, 248, 248);
    private final Color LIGHT_SIDEBAR = Color.WHITE;
    private final Color LIGHT_TEXT = new Color(33, 33, 33);
    private final Color DARK_BG = new Color(30, 30, 30);
    private final Color DARK_SIDEBAR = new Color(40, 40, 40);
    private final Color DARK_TEXT = new Color(230, 230, 230);
    private final Color GREEN_ACCENT = new Color(76, 139, 67);
    
    public SettingsUI() {
        setTitle("EcoVibe - Settings");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new BorderLayout());
        
        createSidebar();
        
        createContentPanel();
        
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        applyTheme();
    }
    
    private void createSidebar() {
        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(250, 600));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)));
        
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        logoPanel.setOpaque(false);
        
        JLabel logo = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon("C:/Users/Asliah/Pictures/green_leaf_recycle_sign1.png");
            Image img = logoIcon.getImage();
            Image resizedImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(resizedImg));
        } catch (Exception e) {
            logo.setText("🌿");
            logo.setFont(new Font("Arial", Font.PLAIN, 32));
        }
        
        JLabel title = new JLabel("EcoVibe");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(GREEN_ACCENT);
        
        logoPanel.add(logo);
        logoPanel.add(title);
        
        sidebar.add(logoPanel);
        
        sidebar.add(createMenuItem("icons/dashboard.png", "Dashboard", false));
        sidebar.add(createMenuItem("icons/joined.png", "Joined Events", false));
        sidebar.add(createMenuItem("icons/posted.png", "Posted Events", false));
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(createMenuItem("icons/settings.png", "Settings", true));
        sidebar.add(Box.createVerticalStrut(20));
    }
    
    private JPanel createMenuItem(String iconPath, String text, boolean selected) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        item.setMaximumSize(new Dimension(250, 10));
        item.setOpaque(true);
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel iconLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(iconPath);
            // Resize icon (adjust size as needed)
            Image img = icon.getImage();
            Image resizedImg = img.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(resizedImg));
        } catch (Exception e) {

            String fallbackIcon = "•";
            if (text.equals("Dashboard")) fallbackIcon = "📊";
            else if (text.equals("Joined Events")) fallbackIcon = "🎯";
            else if (text.equals("Posted Events")) fallbackIcon = "♻️";
            else if (text.equals("Settings")) fallbackIcon = "⚙️";
            
            iconLabel.setText(fallbackIcon);
            iconLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        }
        
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        item.add(iconLabel);
        item.add(textLabel);
        
        if (selected) {
            item.setBackground(new Color(76, 139, 67, 30));
        } else {
            item.setBackground(null);
        }
        
        // Hover effect
        item.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!selected) {
                    item.setBackground(new Color(200, 200, 200, 50));
                }
            }
            public void mouseExited(MouseEvent e) {
                if (!selected) {
                    item.setBackground(null);
                }
            }
        });
        
        return item;
    }
    
    private void createContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        
        JLabel settingsTitle = new JLabel("Settings");
        settingsTitle.setFont(new Font("Arial", Font.BOLD, 32));
        settingsTitle.setBounds(50, 30, 300, 40);
        contentPanel.add(settingsTitle);
        
        JLabel appearanceTitle = new JLabel("Appearance");
        appearanceTitle.setFont(new Font("Arial", Font.BOLD, 24));
        appearanceTitle.setForeground(GREEN_ACCENT);
        appearanceTitle.setBounds(50, 100, 300, 35);
        contentPanel.add(appearanceTitle);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(50, 140, 650, 1);
        separator.setForeground(new Color(220, 220, 220));
        contentPanel.add(separator);
        
        JLabel darkModeLabel = new JLabel("Dark Mode");
        darkModeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        darkModeLabel.setBounds(50, 160, 150, 30);
        contentPanel.add(darkModeLabel);
        
        toggleButton = new ToggleButton();
        toggleButton.setBounds(630, 160, 70, 30);
        toggleButton.addActionListener(e -> toggleDarkMode());
        contentPanel.add(toggleButton);
    }
    
    private void toggleDarkMode() {
        darkMode = !darkMode;
        applyTheme();
    }
    
    private void applyTheme() {
        Color bgColor = darkMode ? DARK_BG : LIGHT_BG;
        Color sidebarColor = darkMode ? DARK_SIDEBAR : LIGHT_SIDEBAR;
        Color textColor = darkMode ? DARK_TEXT : LIGHT_TEXT;
        
        mainPanel.setBackground(bgColor);
        sidebar.setBackground(sidebarColor);
        contentPanel.setBackground(bgColor);
        
        updateComponentColors(sidebar, textColor, sidebarColor);
        updateComponentColors(contentPanel, textColor, bgColor);
        
        repaint();
    }
    
    private void updateComponentColors(Container container, Color textColor, Color bgColor) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (!label.getForeground().equals(GREEN_ACCENT)) {
                    label.setForeground(textColor);
                }
            } else if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                if (panel.isOpaque() && panel.getBackground() == null) {
                    panel.setBackground(bgColor);
                }
                updateComponentColors(panel, textColor, bgColor);
            }
        }
    }
    
    // Custom Toggle Button
    class ToggleButton extends JButton {
        private boolean state = false;
        private final Color BG_OFF = new Color(200, 200, 200);
        private final Color BG_ON = GREEN_ACCENT;
        
        public ToggleButton() {
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int w = getWidth();
            int h = getHeight();
            
            // Draw background
            g2.setColor(state ? BG_ON : BG_OFF);
            g2.fillRoundRect(0, 0, w, h, h, h);
            
            // Draw circle
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
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SettingsUI().setVisible(true);
        });
    }
}