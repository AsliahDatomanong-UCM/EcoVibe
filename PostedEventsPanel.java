import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PostedEventsPanel extends JPanel {

    private final JTextField searchField;
    private final JPanel cardsContainer;

    public PostedEventsPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 40, 20, 40));

        // --- Top Bar (Title + Search) ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Posted Events") { 
            @Override
            public Color getForeground() { return SettingsPanel.isDarkMode ? SettingsPanel.TEXT_DARK_MODE : SettingsPanel.TEXT_LIGHT_MODE; } 
        };
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        topPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Search Input Area
        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 15));
        searchBar.setOpaque(false);
        
        // Rounded Search Field
        searchField = new RoundedTextField(20);
        searchField.setText("Search events, locations, or dates...");
        searchField.setPreferredSize(new Dimension(350, 40));
        searchField.setForeground(Color.GRAY);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) { 
                if(searchField.getText().contains("Search")) { 
                    searchField.setText(""); 
                    searchField.setForeground(SettingsPanel.isDarkMode ? Color.WHITE : Color.BLACK); 
                } 
            }
            @Override
            public void focusLost(FocusEvent e) { 
                if(searchField.getText().isEmpty()) { 
                    searchField.setText("Search events, locations, or dates..."); 
                    searchField.setForeground(Color.GRAY); 
                } 
            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { performSearch(); }
            public void removeUpdate(DocumentEvent e) { performSearch(); }
            public void changedUpdate(DocumentEvent e) { performSearch(); }
        });
        
        searchBar.add(searchField);
        
        topPanel.add(searchBar, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // --- Cards Area ---
        cardsContainer = new JPanel(new GridBagLayout()) { 
            @Override
            public Color getBackground() { return SettingsPanel.isDarkMode ? SettingsPanel.BG_DARK : SettingsPanel.BG_LIGHT; } 
        };
        
        JScrollPane scrollPane = new JScrollPane(cardsContainer);
        scrollPane.setBorder(null); scrollPane.setOpaque(false); scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
        
        performSearch();
    }
    
    public void refresh() {
        performSearch();
    }

    private void performSearch() {
        String query = searchField.getText();
        if (query.contains("Search")) query = "";
        
        List<Event> results = DataManager.getInstance().searchEventsUnified(query);
        renderEvents(results);
    }

    private void renderEvents(List<Event> events) {
        cardsContainer.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(0, 0, 20, 20); gbc.anchor = GridBagConstraints.NORTHWEST; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;

        int col = 0;
        for (Event e : events) {
            cardsContainer.add(createCard(e), gbc);
            col++; gbc.gridx++;
            if (col >= 2) { col = 0; gbc.gridx = 0; gbc.gridy++; }
        }
        
        GridBagConstraints s = new GridBagConstraints(); s.gridx = 0; s.gridy = gbc.gridy + 1; s.weighty = 1.0;
        cardsContainer.add(Box.createGlue(), s);
        cardsContainer.revalidate();
        cardsContainer.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        setBackground(SettingsPanel.isDarkMode ? SettingsPanel.BG_DARK : SettingsPanel.BG_LIGHT);
        super.paintComponent(g);
    }

    private JPanel createCard(Event data) {
        RoundedPanel card = new RoundedPanel(15);
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(300, 160));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        
        MouseAdapter clickListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { showJoinDialog(data); }
            @Override
            public void mouseEntered(MouseEvent e) { 
                card.setShadowOpacity(SettingsPanel.isDarkMode ? 0.5f : 0.2f); 
                card.repaint(); 
            }
            @Override
            public void mouseExited(MouseEvent e) { 
                card.setShadowOpacity(SettingsPanel.isDarkMode ? 0.2f : 0.1f); 
                card.repaint(); 
            }
        };
        card.addMouseListener(clickListener);
        
        JPanel header = new JPanel(new BorderLayout()); header.setOpaque(false); header.setBorder(new EmptyBorder(20, 20, 10, 20));
        JLabel name = new JLabel(data.getName()) { 
            @Override
            public Color getForeground() { return SettingsPanel.isDarkMode ? SettingsPanel.GREEN_DARK : SettingsPanel.TEXT_LIGHT_MODE; } 
        };
        name.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JLabel status = new JLabel(data.getStatus()) { 
            @Override
            public Color getForeground() { return SettingsPanel.isDarkMode ? SettingsPanel.GREEN_DARK : SettingsPanel.GREEN_LIGHT; } 
        };
        status.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.add(name, BorderLayout.CENTER); header.add(status, BorderLayout.EAST);
        
        JPanel body = new JPanel(); body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS)); body.setOpaque(false); body.setBorder(new EmptyBorder(0, 20, 20, 20));
        
        body.add(createIconRow("image_a3c97a.png", data.getDate())); 
        body.add(Box.createVerticalStrut(5));
        body.add(createIconRow("image_a3c976.png", (data.getLocation() != null ? data.getLocation() : "Unknown"))); 
        body.add(Box.createVerticalStrut(5));
        body.add(createIconRow("image_a3c978.png", data.getAttendees() + " Attendees")); 
        
        card.add(header, BorderLayout.NORTH); card.add(body, BorderLayout.CENTER);
        
        for (Component c : header.getComponents()) c.addMouseListener(clickListener);
        for (Component c : body.getComponents()) c.addMouseListener(clickListener);
        
        return card;
    }
    
    private JPanel createIconRow(String iconName, String text) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row.setOpaque(false);
        
        JLabel iconLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(iconName);
            if (icon.getIconWidth() > 0) {
                Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                iconLabel.setIcon(new ImageIcon(img));
            } else {
                iconLabel.setText("•"); 
                iconLabel.setForeground(SettingsPanel.isDarkMode ? SettingsPanel.GREEN_DARK : Color.GRAY);
            }
        } catch (Exception e) {
            iconLabel.setText("•");
        }
        
        JLabel textLabel = new JLabel(text) {
             @Override
             public Color getForeground() { return SettingsPanel.isDarkMode ? SettingsPanel.GREEN_DARK : Color.GRAY; }
        };
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        row.add(iconLabel); row.add(textLabel);
        return row;
    }
    
    private void showJoinDialog(Event event) {
        int choice = JOptionPane.showConfirmDialog(this, "Do you want to join '" + event.getName() + "'?", "Join Event", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            DataManager.getInstance().joinEvent(event);
            refresh(); 
            JOptionPane.showMessageDialog(this, "You have successfully joined the event!");
        }
    }

    private class RoundedPanel extends JPanel {
        private final int radius; private float shadowOpacity = 0.1f;
        public RoundedPanel(int r) { this.radius = r; setOpaque(false); }
        public void setShadowOpacity(float o) { this.shadowOpacity = o; }
        
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
    
    private static class RoundedTextField extends JTextField {
        private int radius;
        public RoundedTextField(int radius) { this.radius = radius; setOpaque(false); setBorder(new EmptyBorder(5, 10, 5, 10)); }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(SettingsPanel.isDarkMode ? new Color(60, 60, 60) : Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
            
            if (isFocusOwner()) {
                g2.setColor(SettingsPanel.GREEN_DARK); 
                g2.setStroke(new BasicStroke(2));
            } else {
                g2.setColor(SettingsPanel.isDarkMode ? new Color(80, 80, 80) : Color.LIGHT_GRAY);
            }
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
            super.paintComponent(g);
        }
    }
}