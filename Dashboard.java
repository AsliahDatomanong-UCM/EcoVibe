import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Dashboard extends JFrame {

    private static final Color DARK_GREEN = Color.decode("#2D5A27");
    private static final Color LIGHT_GRAY = Color.decode("#E9E9E9");
    private static final Color BORDER_GRAY = Color.decode("#DDDDDD");

    public Dashboard() {
        setTitle("EcoVibe Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainContent(), BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.WHITE);
        sidebar.setPreferredSize(new Dimension(240, 0)); // ~20%
        sidebar.setLayout(new BorderLayout());
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER_GRAY));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(Color.WHITE);
        top.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel logo = new JLabel("EcoVibe");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logo.setForeground(DARK_GREEN);
        logo.setIcon(new ImageIcon("EcoVibe.jpg"));

        top.add(logo);
        sidebar.add(top, BorderLayout.NORTH);

        JPanel nav = new JPanel();
        nav.setBackground(Color.WHITE);
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBorder(new EmptyBorder(10, 20, 10, 20));

        nav.add(createNavItem("Dashboard"));
        nav.add(Box.createVerticalStrut(10));
        nav.add(createNavItem("Joined Events"));
        nav.add(Box.createVerticalStrut(10));
        nav.add(createNavItem("Posted Events"));

        sidebar.add(nav, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel settings = new JLabel("Settings");
        settings.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bottom.add(settings);

        sidebar.add(bottom, BorderLayout.SOUTH);

        return sidebar;
    }

    private JButton createNavItem(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return btn;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);
        main.setBorder(new EmptyBorder(20, 30, 20, 30));

        main.add(createHeader(), BorderLayout.NORTH);
        main.add(createBody(), BorderLayout.CENTER);

        return main;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);

        RoundedTextField search =
                new RoundedTextField("Search by date or location", LIGHT_GRAY);
        search.setPreferredSize(new Dimension(280, 38));

        header.add(search, BorderLayout.EAST);
        return header;
    }

    private JPanel createBody() {
        JPanel body = new JPanel();
        body.setBackground(Color.WHITE);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(new EmptyBorder(20, 0, 0, 0));

        RoundedPanel greeting = new RoundedPanel(30, DARK_GREEN);
        greeting.setLayout(new FlowLayout(FlowLayout.LEFT));
        greeting.setBorder(new EmptyBorder(18, 25, 18, 25));
        greeting.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JLabel hi = new JLabel("Hi, user123!");
        hi.setForeground(Color.WHITE);
        hi.setFont(new Font("Segoe UI", Font.BOLD, 16));
        greeting.add(hi);

        body.add(greeting);
        body.add(Box.createVerticalStrut(30));

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

        JLabel uploadLabel = new JLabel("📷  Upload Photo");
        uploadLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        upload.add(uploadLabel);

        form.add(upload);
        body.add(form);

        return body;
    }

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
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
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
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
    }
}
