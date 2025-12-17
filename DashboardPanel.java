import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DashboardPanel extends JPanel {

    private EcoVibeMain mainFrame;

    public DashboardPanel(EcoVibeMain mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 40, 30, 40));
        
        JPanel contentContainer = new JPanel(new GridBagLayout()) {
            @Override
            public Color getBackground() { return SettingsPanel.isDarkMode ? SettingsPanel.BG_DARK : SettingsPanel.BG_LIGHT; }
        };
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 25, 0);

        // Welcome
        JLabel title = new JLabel("Dashboard") { 
            @Override
            public Color getForeground() { return SettingsPanel.isDarkMode ? SettingsPanel.TEXT_DARK_MODE : SettingsPanel.TEXT_LIGHT_MODE; } 
        };
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        contentContainer.add(title, gbc);
        
        gbc.gridy++;
        JLabel sub = new JLabel("Welcome back to EcoVibe! Here's your impact overview.") { 
            @Override
            public Color getForeground() { return SettingsPanel.isDarkMode ? new Color(180, 180, 180) : new Color(100, 100, 100); } 
        };
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentContainer.add(sub, gbc);

        // Stats
        gbc.gridy++;
        JPanel stats = new JPanel(new GridLayout(1, 3, 20, 0));
        stats.setOpaque(false);
        stats.add(createStatCard("Total Events", "12", "📅"));
        stats.add(createStatCard("Trees Planted", "45", "🌳"));
        stats.add(createStatCard("Impact Score", "98%", "♻️"));
        contentContainer.add(stats, gbc);

        // Quick Actions
        gbc.gridy++; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        contentContainer.add(createMainContentSection(), gbc);

        JScrollPane scroll = new JScrollPane(contentContainer);
        scroll.setBorder(null); scroll.setOpaque(false); scroll.getViewport().setOpaque(false);
        add(scroll, BorderLayout.CENTER);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        setBackground(SettingsPanel.isDarkMode ? SettingsPanel.BG_DARK : SettingsPanel.BG_LIGHT);
        super.paintComponent(g);
    }

    private JPanel createStatCard(String title, String value, String icon) {
        RoundedPanel card = new RoundedPanel(15);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel val = new JLabel(value) { 
            @Override
            public Color getForeground() { return SettingsPanel.isDarkMode ? SettingsPanel.GREEN_DARK : SettingsPanel.GREEN_LIGHT; } 
        };
        val.setFont(new Font("Segoe UI", Font.BOLD, 28));
        
        JLabel lbl = new JLabel(title) { 
            @Override
            public Color getForeground() { return SettingsPanel.isDarkMode ? new Color(200, 200, 200) : new Color(80, 80, 80); } 
        };
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JPanel txt = new JPanel(new GridLayout(2, 1)); txt.setOpaque(false);
        txt.add(val); txt.add(lbl);
        card.add(txt, BorderLayout.CENTER);
        
        JLabel ico = new JLabel(icon); ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        card.add(ico, BorderLayout.EAST);
        return card;
    }

    private JPanel createMainContentSection() {
        RoundedPanel card = new RoundedPanel(20);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(25, 30, 25, 30));
        
        JLabel title = new JLabel("Quick Actions") { 
            @Override
            public Color getForeground() { return SettingsPanel.isDarkMode ? SettingsPanel.TEXT_DARK_MODE : SettingsPanel.TEXT_LIGHT_MODE; } 
        };
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        card.add(title, BorderLayout.NORTH);
        
        JPanel grid = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        grid.setOpaque(false); grid.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        // Smaller Buttons
        JButton createBtn = createActionButton("Create New Event", "+");
        createBtn.addActionListener(e -> openCreateEventDialog());
        grid.add(createBtn);
        
        JButton findBtn = createActionButton("Find Events", "🔍");
        findBtn.addActionListener(e -> mainFrame.showPage("Posted Events")); // Navigate to search
        grid.add(findBtn);
        
        card.add(grid, BorderLayout.CENTER);
        card.setPreferredSize(new Dimension(0, 180));
        return card;
    }
    
    private JButton createActionButton(String title, String icon) {
        JButton btn = new JButton(title);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false); // Make transparent
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 45)); // Smaller fixed size
        
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btn.getModel().isRollover() ? new Color(80, 255, 60) : SettingsPanel.GREEN_LIGHT);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 25, 25);
                super.paint(g, c);
            }
        });
        return btn;
    }

    // --- Create Event Dialog ---
    private void openCreateEventDialog() {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Create New Event", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(450, 580);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(SettingsPanel.GREEN_LIGHT);
        header.setBorder(new EmptyBorder(20, 25, 20, 25));
        JLabel title = new JLabel("Create New Event");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);
        mainPanel.add(header, BorderLayout.NORTH);
        
        // Form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 8, 0); // Spacing between label and field
        
        JTextField nameField = addDialogField(form, "Event Name", gbc);
        JTextField dateField = addDialogField(form, "Date (e.g., Dec 25, 2025)", gbc);
        JTextField locField = addDialogField(form, "Location", gbc);
        
        // Description Area
        gbc.insets = new Insets(15, 0, 5, 0);
        JLabel descLabel = new JLabel("Description");
        descLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        descLabel.setForeground(Color.GRAY);
        form.add(descLabel, gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 15, 0);
        JTextArea descField = new JTextArea(4, 20);
        descField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descField.setLineWrap(true);
        descField.setWrapStyleWord(true);
        descField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JScrollPane scrollDesc = new JScrollPane(descField);
        scrollDesc.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        form.add(scrollDesc, gbc);
        
        // Photo Upload
        gbc.gridy++;
        JButton photoBtn = new JButton("Upload Photo");
        styleDialogButton(photoBtn, new Color(240, 240, 240), Color.DARK_GRAY);
        final String[] photoPath = {null};
        photoBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                photoPath[0] = fc.getSelectedFile().getAbsolutePath();
                photoBtn.setText("✓ " + fc.getSelectedFile().getName());
                photoBtn.setBackground(new Color(220, 255, 220));
            }
        });
        form.add(photoBtn, gbc);
        
        mainPanel.add(form, BorderLayout.CENTER);
        
        // Footer Buttons
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(0, 25, 10, 25));
        
        JButton cancelBtn = new JButton("Cancel");
        styleDialogButton(cancelBtn, Color.WHITE, Color.GRAY);
        cancelBtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        JButton saveBtn = new JButton("Create Event");
        styleDialogButton(saveBtn, SettingsPanel.GREEN_LIGHT, Color.WHITE);
        saveBtn.addActionListener(e -> {
            if(nameField.getText().isEmpty() || dateField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Name and Date required!");
                return;
            }
            Event newEvent = new Event(nameField.getText(), dateField.getText(), "User Event", "1", "Active", locField.getText(), descField.getText());
            newEvent.setImagePath(photoPath[0]);
            DataManager.getInstance().addEvent(newEvent);
            JOptionPane.showMessageDialog(dialog, "Event Created!");
            dialog.dispose();
        });
        
        footer.add(cancelBtn);
        footer.add(saveBtn);
        mainPanel.add(footer, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
    private JTextField addDialogField(JPanel panel, String labelText, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(Color.GRAY);
        panel.add(label, gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 15, 0); // Space after input
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        panel.add(field, gbc);
        
        gbc.gridy++; // Prepare for next row
        return field;
    }
    
    private void styleDialogButton(JButton btn, Color bg, Color fg) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Remove default border painting for cleaner look if custom bg is set
        if (bg.equals(SettingsPanel.GREEN_LIGHT)) {
             btn.setBorderPainted(false);
        }
        btn.setPreferredSize(new Dimension(140, 40));
    }

    private class RoundedPanel extends JPanel {
        private final int radius;
        private float shadowOpacity = 0.1f;
        public RoundedPanel(int r) { this.radius = r; setOpaque(false); }
        public void setShadowOpacity(float o) { this.shadowOpacity = o; repaint(); }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            boolean dark = SettingsPanel.isDarkMode;
            g2.setColor(new Color(0, 0, 0, (int)(255 * Math.min(dark ? shadowOpacity + 0.1f : shadowOpacity, 1.0f))));
            g2.fillRoundRect(3, 3, getWidth()-6, getHeight()-6, radius, radius);
            g2.setColor(dark ? SettingsPanel.CARD_DARK : SettingsPanel.CARD_LIGHT);
            g2.fillRoundRect(0, 0, getWidth()-6, getHeight()-6, radius, radius);
        }
    }
}