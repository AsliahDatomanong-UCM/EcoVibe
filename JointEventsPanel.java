import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class JointEventsPanel extends JPanel {

    private JPanel cardsContainer;

    public JointEventsPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel titleLabel = new JLabel("Joined Events") {
             @Override
             public Color getForeground() {
                 return SettingsPanel.isDarkMode ? SettingsPanel.TEXT_DARK_MODE : SettingsPanel.TEXT_LIGHT_MODE;
             }
        };
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        cardsContainer = new JPanel(new GridBagLayout()) {
            @Override
            public Color getBackground() {
                return SettingsPanel.isDarkMode ? SettingsPanel.BG_DARK : SettingsPanel.BG_LIGHT;
            }
        };
        
        JScrollPane scrollPane = new JScrollPane(cardsContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        
        add(scrollPane, BorderLayout.CENTER);
        
        refresh(); 
    }
    
    public void refresh() {
        cardsContainer.removeAll();
        // Fetch Real Joined Data
        List<Event> events = DataManager.getInstance().getJoinedEvents();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 20); 
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int colCount = 0;
        for (Event event : events) {
            cardsContainer.add(createEventCard(event), gbc);
            colCount++;
            gbc.gridx++;
            
            if (colCount >= 2) {
                colCount = 0;
                gbc.gridx = 0;
                gbc.gridy++;
            }
        }
        
        // Handle empty state
        if (events.isEmpty()) {
            JLabel emptyLabel = new JLabel("You haven't joined any events yet.");
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            emptyLabel.setForeground(Color.GRAY);
            cardsContainer.add(emptyLabel);
        } else {
            GridBagConstraints spacer = new GridBagConstraints();
            spacer.gridx = 0; spacer.gridy = gbc.gridy + 1; spacer.weighty = 1.0;
            cardsContainer.add(Box.createGlue(), spacer);
        }
        
        cardsContainer.revalidate();
        cardsContainer.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        setBackground(SettingsPanel.isDarkMode ? SettingsPanel.BG_DARK : SettingsPanel.BG_LIGHT);
        super.paintComponent(g);
    }

    private JPanel createEventCard(Event data) {
        RoundedPanel card = new RoundedPanel(15);
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(300, 160));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // --- Leave Event Listener ---
        MouseAdapter clickListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showLeaveDialog(data);
            }
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
        
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(20, 20, 10, 20));
        
        JLabel nameLabel = new JLabel(data.getName()) {
            @Override
            public Color getForeground() {
                return SettingsPanel.isDarkMode ? SettingsPanel.GREEN_DARK : SettingsPanel.TEXT_LIGHT_MODE;
            }
        };
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        JLabel statusLabel = new JLabel(data.getStatus()) {
            @Override
            public Color getForeground() {
                 return SettingsPanel.isDarkMode ? SettingsPanel.GREEN_DARK : SettingsPanel.GREEN_LIGHT;
            }
        };
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setOpaque(false);
        
        header.add(nameLabel, BorderLayout.CENTER);
        header.add(statusLabel, BorderLayout.EAST);
        
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(0, 20, 20, 20));
        
        // --- Icons for Joined Events ---
        body.add(createIconRow("image_a3c97a.png", data.getDate())); // Date
        body.add(Box.createVerticalStrut(5));
        body.add(createIconRow("image_a3c976.png", data.getType())); // Type acts as location/category here
        
        card.add(header, BorderLayout.NORTH);
        card.add(body, BorderLayout.CENTER);
        
        // Propagate clicks
        for(Component c : header.getComponents()) c.addMouseListener(clickListener);
        for(Component c : body.getComponents()) c.addMouseListener(clickListener);
        
        return card;
    }
    
    private JPanel createIconRow(String iconName, String text) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row.setOpaque(false);
        
        JLabel iconLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(iconName);
            Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            iconLabel.setText("•");
        }
        
        JLabel textLabel = new JLabel(text) {
             @Override
            public Color getForeground() {
                 return SettingsPanel.isDarkMode ? SettingsPanel.GREEN_DARK : Color.GRAY;
            }
        };
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        row.add(iconLabel);
        row.add(textLabel);
        return row;
    }
    
    private void showLeaveDialog(Event event) {
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to leave '" + event.getName() + "'?",
            "Leave Event",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            DataManager.getInstance().leaveEvent(event);
            refresh(); // Reload list to remove the card
            JOptionPane.showMessageDialog(this, "You have left the event.");
        }
    }

    private class RoundedPanel extends JPanel {
        private final int radius; 
        private float shadowOpacity = 0.1f;
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
}