import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemAdminPg extends JFrame implements ActionListener
{
    private JFrame frame;
    private JButton createAccButton, createProfileButton, logoutButton, viewAccButton, viewProfileButton, searchAccButton, searchProfileButton;
    private JTextField searchAccField, searchProfileField;

    private static final Insets WEST_INSETS = new Insets(5, 0, 5, 5);
    private static final Insets EAST_INSETS = new Insets(5, 5, 5, 0);

    public SystemAdminPg(User myUser)
    {
        GridBagConstraints c;

        // Setup for UI LAF
        FlatDarkLaf.setup();

        // Setup JFrame
        frame = new JFrame("System Admin Homepage");
        frame.setLayout(new BorderLayout(5, 5));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create Panels
        JPanel InfoPanel = new JPanel(new GridBagLayout());
        JPanel HomePanel = new JPanel(new GridBagLayout());
        JPanel ButtonPanel = new JPanel(new GridBagLayout());

        // ================ Set Home Panel ======================
        HomePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("System Admin | Home"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Set title
        JLabel headerLabel = new JLabel("What do you want to do today?");
        headerLabel.setFont(new Font("Serif", Font.BOLD, 15));
        c = createGbc(0,0);
        c.gridwidth = 2;
        HomePanel.add(headerLabel, c);

        // Set search User Account
        JLabel searchAccLabel = new JLabel("Search Account (UID): ");
        c = createGbc(0,1);
        HomePanel.add(searchAccLabel, c);
        searchAccField = new JTextField();
        c = createGbc(1,1);
        HomePanel.add(searchAccField, c);
        searchAccButton = new JButton("Search");
        searchAccButton.addActionListener(this);
        c = createGbc(2,1);
        HomePanel.add(searchAccButton, c);

        // Set search Profile
        JLabel searchProfileLabel = new JLabel("Search Profile (NAME): ");
        c = createGbc(0,2);
        HomePanel.add(searchProfileLabel, c);
        searchProfileField = new JTextField();
        c = createGbc(1,2);
        HomePanel.add(searchProfileField, c);
        searchProfileButton = new JButton("Search");
        searchProfileButton.addActionListener(this);
        c = createGbc(2,2);
        HomePanel.add(searchProfileButton, c);

        // ================ Set Button Panel ======================
        // Set createAccount
        createAccButton = new JButton("Create new Account");
        createAccButton.addActionListener(this);
        c = createGbc(0,0);
        ButtonPanel.add(createAccButton, c);

        // Set createProfile
        createProfileButton = new JButton("Create new Profile");
        createProfileButton.addActionListener(this);
        c = createGbc(0,1);
        ButtonPanel.add(createProfileButton, c);

        // Set viewAccount
        viewAccButton = new JButton("View Accounts");
        viewAccButton.addActionListener(this);
        c = createGbc(0,2);
        ButtonPanel.add(viewAccButton, c);

        // Set viewPProfile
        viewProfileButton = new JButton("View Profiles");
        viewProfileButton.addActionListener(this);
        c = createGbc(0,3);
        ButtonPanel.add(viewProfileButton, c);

        // Set logout
        logoutButton = new JButton("Log out");
        logoutButton.addActionListener(this);
        c = createGbc(0,4);
        ButtonPanel.add(logoutButton, c);

        // ================ Set Info Panel ======================
        JLabel id = new JLabel("Employee ID: " + String.valueOf(myUser.getEmpID()));
        JLabel name = new JLabel("Name: " + myUser.getName());
        JLabel salary = new JLabel("Salary: " + String.valueOf(myUser.getSalary()));
        JLabel dateJoined = new JLabel("Date Joined: " + myUser.getDateJoined());
        c = createGbc(0,0);
        c.insets = new Insets(0,10,0,10);
        InfoPanel.add(id,c);
        c = createGbc(1,0);
        c.insets = new Insets(0,10,0,10);
        InfoPanel.add(name,c);
        c = createGbc(0,1);
        c.insets = new Insets(10,10,10,10);
        InfoPanel.add(salary,c);
        c = createGbc(1,1);
        c.insets = new Insets(0,10,0,10);
        InfoPanel.add(dateJoined,c);

        // Set Dimensions
        InfoPanel.setPreferredSize(new Dimension(500, 50));
        ButtonPanel.setPreferredSize(new Dimension(150, 250));
        HomePanel.setPreferredSize(new Dimension(350, 250));
        
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
        if (e.getSource() == createAccButton)
        {
            new SysAdminCreateAccountPg();
        }

        if(e.getSource() == logoutButton){
            JOptionPane.showMessageDialog(null, "Logging out. You will now be redirected back to the login page.", "Logout success", JOptionPane.PLAIN_MESSAGE);
            frame.dispose();
            new loginPg();
        }

        if(e.getSource() == viewAccButton){
            new SysAdminViewAccountsPg();
        }

        if(e.getSource() == viewProfileButton){
            new SysAdminViewProfilesPg();
        }

        if(e.getSource() == createProfileButton){
            new SysAdminCreateProfilePg();
        }

        if (e.getSource() == searchAccButton){
            String userInput = searchAccField.getText();
            if (InputCheck.isNumeric(userInput))
            {
                int userID = Integer.parseInt(userInput);
                new SysAdminSearchResultPg(userID);
            }
            else
            {
                String statusText = "Invalid ID entered.";
                String titleText = "Error";
                JOptionPane.showMessageDialog(null, statusText, titleText, JOptionPane.PLAIN_MESSAGE);
            }
        }

        if (e.getSource() == searchProfileButton){
            String userInput = searchProfileField.getText();
            new SysAdminSearchProfilePg(userInput);
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
        myUser = myUser.loginUser("sysadmin1", "sysadmin1");
        new SystemAdminPg(myUser);
    }
}
