// Class to display Account Information of search when SA enters ID in SystemAdminPg
// Option to update or delete user account here

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SysAdminSearchResultPg extends JFrame implements ActionListener {

    private JFrame frame;
    private JTextField nameField, salaryField, dateJoinedField, usernameField, passwordField;
    private JButton updateButton, deleteButton;
    private JComboBox<String> profileField, positionField;
    private User searchUser;
    private int empID = 0;

    private static final Insets WEST_INSETS = new Insets(5, 0, 5, 5);
    private static final Insets EAST_INSETS = new Insets(5, 5, 5, 0);

    public SysAdminSearchResultPg(int id) {
        empID = id;

        GridBagConstraints c;

        // UI Setup
        FlatDarkLaf.setup();

        // Retrieve User based on id
        SysAdminSearchAccountController sac = new SysAdminSearchAccountController();
        searchUser = new User();
        searchUser = sac.searchUserAccount(id);

        // Create Main Frame
        frame = new JFrame("Search Account");
        frame.setLayout(new BorderLayout(5, 5));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set up Panels
        JPanel MainPanel = new JPanel(new GridBagLayout());
        JPanel ButtonPanel = new JPanel(new FlowLayout());

        // ================ Set Main Panel ======================
        MainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Search Results"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        // Set Name
        JLabel nameLabel = new JLabel("Name: ");
        c = createGbc(0,1);
        MainPanel.add(nameLabel, c);
        nameField = new JTextField(searchUser.getName());
        c = createGbc(1,1);
        MainPanel.add(nameField, c);

        // Set Salary
        JLabel salaryLabel = new JLabel("Salary: ");
        c = createGbc(0,2);
        MainPanel.add(salaryLabel, c);
        salaryField = new JTextField(Double.toString(searchUser.getSalary()));
        c = createGbc(1,2);
        MainPanel.add(salaryField, c);

        // Set Date Joined
        JLabel dateJoinedLabel = new JLabel("Date Joined: ");
        c = createGbc(0,3);
        MainPanel.add(dateJoinedLabel, c);
        dateJoinedField = new JTextField(searchUser.getDateJoined());
        c = createGbc(1,3);
        MainPanel.add(dateJoinedField, c);

        // Set Profile
        JLabel profileLabel = new JLabel("Role: ");
        c = createGbc(0,4);
        MainPanel.add(profileLabel, c);
        String[] roleChoices = getProfileList();
        profileField = new JComboBox<String>(roleChoices);
        profileField.setVisible(true);
        profileField.setSelectedIndex(searchUser.getRoleID()-1);
        profileField.addActionListener(this);
        c = createGbc(1,4);
        MainPanel.add(profileField,c);

        // Set Position
        JLabel positionLabel = new JLabel("Position: ");
        c = createGbc(0,5);
        MainPanel.add(positionLabel, c);
        // Set Position if Role = Cafe Staff
        String[] positionChoices = {" ", "Cashier","Chef", "Waiter"};
        positionField = new JComboBox<String>(positionChoices);
        if (searchUser.getRoleID() != 5) {
            positionField.setVisible(false); // default set
            positionField.setSelectedIndex(0);
        }
        else {
            positionField.setVisible(true);
            positionField.setSelectedItem(searchUser.getPosition());
        }
        c = createGbc(1,5);
        MainPanel.add(positionField, c);

        // Set username
        JLabel usernameLabel = new JLabel("Username: ");
        c = createGbc(0,6);
        MainPanel.add(usernameLabel, c);
        usernameField = new JTextField(searchUser.getUsername());
        c = createGbc(1,6);
        MainPanel.add(usernameField, c);

        // Set password
        JLabel passwordLabel = new JLabel("Password: ");
        c = createGbc(0,7);
        MainPanel.add(passwordLabel, c);
        passwordField = new JTextField(searchUser.getPassword());
        c = createGbc(1,7);
        MainPanel.add(passwordField, c);

        // Set all elements to uneditable
        nameField.setEditable(false);
        salaryField.setEditable(false);
        dateJoinedField.setEditable(false);
        profileField.setEnabled(false);
        positionField.setEnabled(false);
        usernameField.setEditable(false);
        passwordField.setEditable(false);
        

        // ================ Set Button Panel ======================
        updateButton = new JButton("Update Account");
        updateButton.addActionListener(this);
        ButtonPanel.add(updateButton);

        deleteButton = new JButton("Delete Account");
        deleteButton.addActionListener(this);
        ButtonPanel.add(deleteButton);


        // Add elements to frame and pack
        frame.add(MainPanel, BorderLayout.CENTER);
        frame.add(ButtonPanel, BorderLayout.SOUTH);

        // Set frame output
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // If no user is found
        if (searchUser.getEmpID() == 0)
        {
            JOptionPane.showMessageDialog(null, "No user record was found!", "Search Result", JOptionPane.PLAIN_MESSAGE);
            frame.dispose();
        }
    }

    public void actionPerformed(ActionEvent e) {
        // Allow user to update details
        if (e.getSource() == updateButton) {
            if (!nameField.isEditable())
            {
                // Set all elements to editable
                nameField.setEditable(true);
                salaryField.setEditable(true);
                dateJoinedField.setEditable(true);
                profileField.setEnabled(true);
                positionField.setEnabled(true);
                usernameField.setEditable(true);
                passwordField.setEditable(true);

                updateButton.setText("Confirm");
            }
            else
            {
                // Save the data into database
                SysAdminUpdateAccountController uac = new SysAdminUpdateAccountController();

                // Retrieve data
                String name = nameField.getText();
                String salaryStr = salaryField.getText();
                String dateJoined = dateJoinedField.getText();
                int profileID = profileField.getSelectedIndex() + 1;
                String position = positionField.getSelectedItem().toString();
                String username = usernameField.getText();
                String password = passwordField.getText();

                // set null field if applicable
                if (position == " ")
                {
                    position = "NULL";
                }

                // Check fields
                boolean validAccount = checkFields(name, salaryStr, dateJoined, profileID, position, username, password);

                // Create employee object
                String statusText;
                if (validAccount) {
                    double salary = Double.parseDouble(salaryStr);
                    User newUser = new User(empID, name, salary, dateJoined, profileID, position, username, password);
                    boolean updateUser = uac.updateUserRecord(newUser);

                    if (updateUser) {
                        statusText = "Account updated successfully!";
                    }
                    else {
                        statusText = "Account update failed.";
                    }
                }
                else {
                    statusText = "Account update failed. Please check the fields for invalid input.";

                    // Reset values if invalid input
                    // Set all elements to editable
                    nameField.setEditable(true);
                    salaryField.setEditable(true);
                    dateJoinedField.setEditable(true);
                    profileField.setEnabled(true);
                    positionField.setEnabled(true);
                    usernameField.setEditable(true);
                    passwordField.setEditable(true);
                }
                String titleText = "Account Update Status";
                JOptionPane.showMessageDialog(null, statusText, titleText, JOptionPane.PLAIN_MESSAGE);

                // Set all elements to uneditable
                nameField.setText(searchUser.getName());
                salaryField.setText(Double.toString(searchUser.getSalary()));
                dateJoinedField.setText(searchUser.getDateJoined());
                profileField.setSelectedIndex(searchUser.getRoleID()-1);
                positionField.setSelectedItem(searchUser.getPosition());
                usernameField.setText(searchUser.getUsername());
                passwordField.setText(searchUser.getPassword());

                updateButton.setText("Update Account");
            }
        }

        // Delete current account
        if (e.getSource() == deleteButton)
        {
            int reply = JOptionPane.showConfirmDialog(null, "Are you sure you wish to delete this account?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                String statusText;
                SysAdminDeleteAccountController dac = new SysAdminDeleteAccountController();

                boolean deleted = dac.deleteUserRecord(empID);
                if (deleted) {
                    statusText = "Account deleted successfully!";
                    frame.dispose();
                }
                else {
                    statusText = "Invalid ID, please try again.";
                }

                String titleText = "Account Deletion Status";
                JOptionPane.showMessageDialog(null, statusText, titleText, JOptionPane.PLAIN_MESSAGE);

            } 
            // Do nothing if no is selected
        }

        /* Only show position field if Cafe Staff is selected
           If other roles selected, position is automatically set to null */
        if (e.getSource() == profileField)
        {
            if ((profileField.getSelectedItem().toString().equals("Cafe Staff")))
            {
                positionField.setVisible(true);
                positionField.setSelectedIndex(0);
            }
            else
            {
                positionField.setVisible(false);
                positionField.setSelectedIndex(0);
            }
        }
    }

    private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
  
        gbc.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
        gbc.fill = (x == 0) ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;
  
        gbc.insets = (x == 0) ? WEST_INSETS : EAST_INSETS;
        gbc.weightx = (x == 0) ? 0.1 : 1.0;
        gbc.weighty = 1.0;
        return gbc;
    }

    private boolean checkFields(String name, String salaryStr, String dateJoined, 
    int roleID, String position, String username, String password) {
        boolean validAccount = false;
        double salary;

        // Check if name is alpha-numeric
        if (!(InputCheck.isAlphaNumeric(name)) || name == "")
        {return validAccount;}

        // Check if salary is numeric
        if (InputCheck.isNumeric(salaryStr))
        {
            salary = Double.parseDouble(salaryStr);
        }
        else {
            return validAccount;
        }
        
        // Check if salary is within 0 to 100000
        if (salary < 0 || salary > 100000)
        {return validAccount;}

        // Check if roleID is within 2 to 100 - profile id starts from 2
        if (roleID < 2 || roleID > 100)
        {return validAccount;}

        if (!(InputCheck.isAlphaNumeric(position)))
        {return validAccount;}

        // Check if username is alphanumeric
        if (!(InputCheck.isAlphaNumeric(username)))
        {return validAccount;}

        // Check if password is alphanumeric
        if (!(InputCheck.isAlphaNumeric(password)))
        {return validAccount;}

        // If no issues
        validAccount = true;
        return validAccount;
    }

    // Retrieve list of profile in database
    private String[] getProfileList()
    {
        Profile pf = new Profile();

        ArrayList<String> profileArrayList = pf.profileRecordArray();
        // Set the empty option for default
        profileArrayList.set(0, " ");
        String[] profileArray = profileArrayList.toArray(new String[profileArrayList.size()]);
        return profileArray;
    }

    public static void main(String[] args) {       
        new SysAdminSearchResultPg(10003);
    }
}

