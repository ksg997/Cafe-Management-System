import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CafeManagerPg extends JFrame implements ActionListener {
    private JFrame frame;
    private JButton viewWorkSlotsButton, viewCafeStaffButton, viewBidsButton,
            searchWorkSlotsButton, logoutButton;
    private JTextField searchWorkSlotsField;
    private static final Insets WEST_INSETS = new Insets(5, 0, 5, 5);
    private static final Insets EAST_INSETS = new Insets(5, 5, 5, 0);

    public CafeManagerPg(User myUser) {
        // Setup for UI LAF
        FlatDarkLaf.setup();

        // Setup JFrame
        frame = new JFrame("Cafe Manager Homepage");
        frame.setLayout(new BorderLayout(5, 5));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create Panels
        JPanel InfoPanel = new JPanel(new GridBagLayout());
        JPanel HomePanel = new JPanel(new GridBagLayout());
        JPanel ButtonPanel = new JPanel(new GridBagLayout());

        // Set System Admin Panel
        HomePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Cafe Manager | Home"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        GridBagConstraints c;

        // Set title
        JLabel headerLabel = new JLabel("What do you want to do today?");
        headerLabel.setFont(new Font("Serif", Font.BOLD, 15));
        c = createGbc(0, 0);
        c.gridwidth = 2;
        HomePanel.add(headerLabel, c);

        // Set search Work Slots
        JLabel searchWorkSlotsLabel = new JLabel("Search Work Slots: ");
        c = createGbc(0, 1);
        HomePanel.add(searchWorkSlotsLabel, c);
        searchWorkSlotsField = new JTextField();
        c = createGbc(1, 1);
        HomePanel.add(searchWorkSlotsField, c);
        searchWorkSlotsButton = new JButton("Search");
        searchWorkSlotsButton.addActionListener(this);
        c = createGbc(2, 1);
        HomePanel.add(searchWorkSlotsButton, c);

        // ================ Set Button Panel ======================
        // Set viewWorkSlots
        viewWorkSlotsButton = new JButton("View Work Slots");
        viewWorkSlotsButton.addActionListener(this);
        c = createGbc(0, 1);
        ButtonPanel.add(viewWorkSlotsButton, c);

        // Set viewCafeStaff
        viewCafeStaffButton = new JButton("View Cafe Staff");
        viewCafeStaffButton.addActionListener(this);
        c = createGbc(0, 2);
        ButtonPanel.add(viewCafeStaffButton, c);

        // Set viewBids
        viewBidsButton = new JButton("View Bids");
        viewBidsButton.addActionListener(this);
        c = createGbc(0, 3);
        ButtonPanel.add(viewBidsButton, c);

        // Set logout
        logoutButton = new JButton("Log out");
        logoutButton.addActionListener(this);
        c = createGbc(0, 4);
        ButtonPanel.add(logoutButton, c);

        // ================ Set Info Panel ======================
        // Placeholder labels for employee information
        JLabel id = new JLabel("Employee ID: " + String.valueOf(myUser.getEmpID()));
        JLabel name = new JLabel("Name: " + myUser.getName());
        JLabel salary = new JLabel("Salary: " + String.valueOf(myUser.getSalary()));
        JLabel dateJoined = new JLabel("Date Joined: " + myUser.getDateJoined());

        c = createGbc(0, 0);
        c.insets = new Insets(0, 10, 0, 10);
        InfoPanel.add(id, c);
        c = createGbc(1, 0);
        c.insets = new Insets(0, 10, 0, 10);
        InfoPanel.add(name, c);
        c = createGbc(0, 1);
        c.insets = new Insets(10, 10, 10, 10);
        InfoPanel.add(salary, c);
        c = createGbc(1, 1);
        c.insets = new Insets(0, 10, 0, 10);
        InfoPanel.add(dateJoined, c);

        // Set Dimensions
        InfoPanel.setPreferredSize(new Dimension(500, 50));
        ButtonPanel.setPreferredSize(new Dimension(150, 250));
        HomePanel.setPreferredSize(new Dimension(350, 150));

        // Set locations
        frame.add(InfoPanel, BorderLayout.NORTH);
        frame.add(HomePanel, BorderLayout.CENTER);
        frame.add(ButtonPanel, BorderLayout.EAST);

        // Set frame output
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton) {
            JOptionPane.showMessageDialog(null, "Logging out. You will now be redirected back to the login page.", "Logout success", JOptionPane.PLAIN_MESSAGE);
            frame.dispose();
            new loginPg();
        }
        if (e.getSource() == viewWorkSlotsButton) {
            // Implement view work slots functionality included edit and delete
            // Fetch all work slots
            new ViewWorkSlotsPg();
        }
        if (e.getSource() == viewCafeStaffButton) {
            // Implement view cafe staff functionality
            // Fetch all cafe staff
            CafeManagerViewCSController vcsc = new CafeManagerViewCSController();
            ArrayList<User> cafeStaffList = vcsc.getEmployeesByRoleId(5);
            new ViewCafeStaffPg(cafeStaffList);
        }

        if (e.getSource() == viewBidsButton) {
            // Implement view bids functionality
            CafeManagerViewBidsController vbc = new CafeManagerViewBidsController();
            ArrayList<Bid> allBids = vbc.getAllBids();
            new BidManagementPg(allBids);
        }

        if (e.getSource() == searchWorkSlotsButton) {
            // Retrieve user input
            String userInput = searchWorkSlotsField.getText().trim();

            // Search for work slots based on date or position
            CafeManagerSearchWorkSlotController c = new CafeManagerSearchWorkSlotController();
            ArrayList <WorkSlot> results = c.searchWorkSlots(userInput);
            //ArrayList<WorkSlot> searchResults = WorkSlot.searchWorkSlots(userInput);

            new ViewWorkSlotsPg(results);
        }
    }

    private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.insets = (x == 0) ? WEST_INSETS : EAST_INSETS;
        gbc.weightx = (x == 0) ? 0.1 : 1.0;
        gbc.weighty = 1.0;
        return gbc;
    }

    public static void main(String[] args) {
        User myUser = new User();
        myUser = myUser.loginUser("manager1", "manager1");
        new CafeManagerPg(myUser);
    }
}
