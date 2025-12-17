import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

// --- BACKEND PART: DATA MODEL ---
class Event {
    String name;
    String date;
    String type;

    public Event(String name, String date, String type) {
        this.name = name;
        this.date = date;
        this.type = type;
    }
}

public class EcoVibeApp extends JFrame {

    private JPanel contentPanel;
    private JPanel sidebar;
    private JPanel dashboardItem, joinedItem, postedItem, settingsItem;

    // --- BACKEND PART: SIMULATED DATABASE ---
    private List<Event> eventDatabase = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EcoVibeApp().setVisible(true));
    }

    public EcoVibeApp() {
        // Initialize with some dummy data (Backend setup)
        seedData();

        setTitle("EcoVibe - Integrated App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);

        showDashboardPage();
        setActive(dashboardItem);
    }

    private void seedData() {
        eventDatabase.add(new Event("Community Clean Up", "Dec 20, 2025", "Clean Drive"));
        eventDatabase.add(new Event("Tree Planting", "Jan 15, 2026", "Greenery"));
        eventDatabase.add(new Event("Recycling Workshop", "Feb 10, 2026", "Education"));
    }

    // ================= SIDEBAR (Frontend) =================

    private JPanel createSidebar() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(240, getHeight()));
        panel.setBackground(new Color(0xF6F9F6));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 16));
        logoPanel.setBackground(new Color(0xF6F9F6));
        JLabel logoText = new JLabel("EcoVibe");
        logoText.setFont(new Font("SansSerif", Font.BOLD, 22));
        logoText.setForeground(new Color(0x2D7B31));
        logoPanel.add(logoText);
        panel.add(logoPanel);

        // Navigation Items
        dashboardItem = createNavItem("  Dashboard", () -> { showDashboardPage(); setActive(dashboardItem); });
        joinedItem = createNavItem("  Joined Events", () -> { showJoinedEventsPage(); setActive(joinedItem); });
        postedItem = createNavItem("  Posted Events", () -> { showPostedEventsPage(); setActive(postedItem); });
        settingsItem = createNavItem("  Settings", () -> { showSettingsPage(); setActive(settingsItem); });

        panel.add(dashboardItem);
        panel.add(joinedItem);
        panel.add(postedItem);
        panel.add(Box.createVerticalGlue());
        panel.add(settingsItem);

        return panel;
    }

    private JPanel createNavItem(String text, Runnable action) {
        JPanel item = new JPanel(new BorderLayout());
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        item.setBackground(new Color(0xF6F9F6));
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 15));
        label.setBorder(new EmptyBorder(10, 20, 10, 20));
        item.add(label, BorderLayout.CENTER);

        item.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { action.run(); }
            public void mouseEntered(MouseEvent e) { if(!item.getBackground().equals(new Color(0xEAF5EA))) item.setBackground(new Color(0xE4F2E4)); }
            public void mouseExited(MouseEvent e) { if(!item.getBackground().equals(new Color(0xEAF5EA))) item.setBackground(new Color(0xF6F9F6)); }
        });
        return item;
    }

    private void setActive(JPanel activeItem) {
        for (Component c : sidebar.getComponents()) { if (c instanceof JPanel) c.setBackground(new Color(0xF6F9F6)); }
        activeItem.setBackground(new Color(0xEAF5EA));
    }

    // ================= PAGES (Frontend + Backend Integration) =================

    private void showDashboardPage() {
        contentPanel.removeAll();
        contentPanel.add(createTitle("Dashboard"), BorderLayout.NORTH);
        
        // Dynamic stats (Backend logic)
        int totalEvents = eventDatabase.size();
        JLabel stats = centerLabel("Welcome! We currently have " + totalEvents + " active environmental events.");
        
        contentPanel.add(stats, BorderLayout.CENTER);
        refresh();
    }

    private void showPostedEventsPage() {
        contentPanel.removeAll();
        contentPanel.add(createTitle("Posted Events"), BorderLayout.NORTH);

        String[] columns = {"Event Name", "Date", "Type"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // --- BACKEND LOGIC: Fetching data from our "Database" ---
        for (Event ev : eventDatabase) {
            model.addRow(new Object[]{ev.name, ev.date, ev.type});
        }

        JTable table = new JTable(model);
        table.setRowHeight(32);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        
        DefaultTableCellRenderer greenText = new DefaultTableCellRenderer();
        greenText.setForeground(new Color(0x2D7B31));
        table.getColumnModel().getColumn(0).setCellRenderer(greenText);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new EmptyBorder(10, 40, 40, 40));
        scroll.getViewport().setBackground(Color.WHITE);

        contentPanel.add(scroll, BorderLayout.CENTER);
        refresh();
    }

    private void showJoinedEventsPage() {
        contentPanel.removeAll();
        contentPanel.add(createTitle("Joined Events"), BorderLayout.NORTH);
        contentPanel.add(centerLabel("Feature coming soon: Link to User Profile"), BorderLayout.CENTER);
        refresh();
    }

    private void showSettingsPage() {
        contentPanel.removeAll();
        contentPanel.add(createTitle("Settings"), BorderLayout.NORTH);
        contentPanel.add(centerLabel("App Version 1.0 - Connected to Local Memory"), BorderLayout.CENTER);
        refresh();
    }

    // ================= HELPERS =================

    private JPanel createTitle(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 40, 10, 40));
        JLabel title = new JLabel(text);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(0x2D7B31));
        panel.add(title, BorderLayout.WEST);
        return panel;
    }

    private JLabel centerLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        return label;
    }

    private void refresh() {
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
