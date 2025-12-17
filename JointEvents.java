import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class JointEvents extends JFrame {

    // Color palette (matched to your screenshot)
    private static final Color GREEN = new Color(76, 175, 80);
    private static final Color DARK_GREEN = new Color(56, 142, 60);
    private static final Color LIGHT_BG = new Color(245, 247, 246);
    private static final Color SIDEBAR_BG = Color.WHITE;
    private static final Color TEXT_GRAY = new Color(90, 90, 90);

    public JointEvents() {
        setTitle("EcoVibe | Joined Events");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainContent(), BorderLayout.CENTER);
    }

    /** SIDEBAR (static – Joined Events only) */
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(230, getHeight()));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)));

        JLabel logo = new JLabel("♻ EcoVibe");
        logo.setBorder(new EmptyBorder(20, 20, 20, 20));
        logo.setFont(new Font("SansSerif", Font.BOLD, 20));
        logo.setForeground(GREEN);
        sidebar.add(logo, BorderLayout.NORTH);

        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(SIDEBAR_BG);

        JButton joinedEvents = new JButton("Joined Events");
        joinedEvents.setEnabled(false); // this page ONLY
        styleMenuButton(joinedEvents, true);
        menu.add(joinedEvents);

        sidebar.add(menu, BorderLayout.CENTER);

        JButton settings = new JButton("⚙ Settings");
        styleMenuButton(settings, false);
        settings.setBorder(new EmptyBorder(15, 20, 15, 20));
        sidebar.add(settings, BorderLayout.SOUTH);

        return sidebar;
    }

    private void styleMenuButton(JButton button, boolean active) {
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setFont(new Font("SansSerif", Font.PLAIN, 15));
        button.setBorder(new EmptyBorder(15, 20, 15, 20));

        if (active) {
            button.setBackground(new Color(232, 245, 233));
            button.setForeground(DARK_GREEN);
        } else {
            button.setBackground(Color.WHITE);
            button.setForeground(TEXT_GRAY);
        }
    }

    /** MAIN CONTENT */
    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(LIGHT_BG);
        main.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel title = new JLabel("Joined Events");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        main.add(title, BorderLayout.NORTH);

        main.add(createJoinedEventsTable(), BorderLayout.CENTER);
        return main;
    }

    /** JOINED EVENTS TABLE */
    private JPanel createJoinedEventsTable() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        String[] columns = {"Event Name", "Date", "Event Type"};

        Object[][] data = {
                {"Beat Plastic Pollution", "June 5, 2025", EventType.CAMPAIGN},
                {"Barangay Coastal Cleanup", "July 12, 2025", EventType.CLEAN_DRIVE},
                {"Green Tomorrow", "August 20, 2025", EventType.TREE_PLANTING},
                {"Climate Awareness Talk", "September 3, 2025", EventType.SEMINAR}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(32);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setForeground(DARK_GREEN);
        table.getTableHeader().setBackground(Color.WHITE);
        table.setGridColor(new Color(230, 230, 230));
        table.setShowVerticalLines(false);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        card.add(scroll, BorderLayout.CENTER);
        return card;
    }

    /** EVENT TYPES (STRICTLY DEFINED) */
    enum EventType {
        CAMPAIGN("Campaign"),
        CLEAN_DRIVE("Clean Drive"),
        TREE_PLANTING("Tree Planting"),
        SEMINAR("Seminar");

        private final String label;

        EventType(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EcoVibeJoinedEventsUI().setVisible(true));
    }
}
