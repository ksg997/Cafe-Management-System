import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputFilter.Config;
import java.util.ArrayList;

public class CafeOwnerPg extends JFrame implements ActionListener
{
    private JFrame frame;
    private JButton createWorkSlotButton, viewWorkSlotsButton,
            searchWorkSlotsButton, logoutButton;
    private JTextField searchWorkSlotsField;

    private static final Insets WEST_INSETS = new Insets(5, 0, 5, 5);
    private static final Insets EAST_INSETS = new Insets(5, 5, 5, 0);

    public CafeOwnerPg(User myUser)
    {
        GridBagConstraints c;
        // Setup for UI LAF
        FlatDarkLaf.setup();

        // Setup JFrame
        frame = new JFrame("Cafe Owner Homepage");
        frame.setLayout(new BorderLayout(5, 5));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create Panels
        JPanel InfoPanel = new JPanel(new GridBagLayout());
        JPanel HomePanel = new JPanel(new GridBagLayout());
        JPanel ButtonPanel = new JPanel(new GridBagLayout());

        // ================ Set Home Panel ======================
        HomePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Cafe Owner | Home"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

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
        // Set createWorkSlot
        createWorkSlotButton = new JButton("Create Work Slot");
        createWorkSlotButton.addActionListener(this);
        c = createGbc(0, 0);
        ButtonPanel.add(createWorkSlotButton, c);

        // Set viewWorkSlots
        String viewWorkSlotButtonText = "<html><div text-align:center>View & Update" + "<br/>Work Slots</div></html>";
        viewWorkSlotsButton = new JButton(viewWorkSlotButtonText);
        viewWorkSlotsButton.addActionListener(this);
        c = createGbc(0, 1);
        ButtonPanel.add(viewWorkSlotsButton, c);

        // Set logout
        logoutButton = new JButton("Log out");
        logoutButton.addActionListener(this);
        c = createGbc(0, 4);
        ButtonPanel.add(logoutButton, c);

        // ================ Set Info Panel ======================
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
        if(e.getSource() == logoutButton){
            JOptionPane.showMessageDialog(null, "Logging out. You will now be redirected back to the login page.", "Logout success", JOptionPane.PLAIN_MESSAGE);
            frame.dispose();
            new loginPg();
        }
        if (e.getSource() == createWorkSlotButton) {
            // Implement create work slot functionality
            new CreateWorkSlotsPg();
        }
        if (e.getSource() == viewWorkSlotsButton) {
            // Implement view work slots functionality included edit and delete
            // Fetch all work slots
            //ArrayList<WorkSlot> workSlots = WorkSlot.getAllWorkSlots();
            CafeOwnerViewWorkSlotController c = new CafeOwnerViewWorkSlotController();
            ArrayList <WorkSlot> ws = c.getWorkSlots();
            new ViewWorkSlotsPg(ws);
        }

        if (e.getSource() == searchWorkSlotsButton) {
            // Retrieve user input
            String userInput = searchWorkSlotsField.getText().trim();

            // Search for work slots based on date or position
            CafeOwnerSearchWorkSlotController c = new CafeOwnerSearchWorkSlotController();
            ArrayList <WorkSlot> results = c.searchWorkSlots(userInput);

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
        User owner = new User();
        owner = owner.loginUser("owner1", "owner1");
        new CafeOwnerPg(owner);
    }
}