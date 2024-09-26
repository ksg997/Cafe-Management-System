// Class to display Profile Information of search
// Option to update or delete user profile here

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SysAdminSearchProfilePg extends JFrame implements ActionListener {

    private JFrame frame;
    private JTextField nameField, idField;
    private JButton updateButton, deleteButton;

    private Profile searchProfile;
    private int profileID;

    private static final Insets WEST_INSETS = new Insets(5, 0, 5, 5);
    private static final Insets EAST_INSETS = new Insets(5, 5, 5, 0);

    public SysAdminSearchProfilePg(String name) {
        GridBagConstraints c;

        // UI Setup
        FlatDarkLaf.setup();

        // Retrieve profile based on name
        SysAdminSearchProfileController spc = new SysAdminSearchProfileController();
        searchProfile = new Profile();
        searchProfile = spc.searchProfile(name);
        profileID = searchProfile.getID();

        // Create Main Frame
        frame = new JFrame("Search Profile");
        frame.setLayout(new BorderLayout(5, 5));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set up Panels
        JPanel MainPanel = new JPanel(new GridBagLayout());
        JPanel ButtonPanel = new JPanel(new FlowLayout());

        // ================ Set Main Panel ======================
        MainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Search Results"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        // Set ID
        JLabel idLabel = new JLabel("Profile ID: ");
        c = createGbc(0,1);
        MainPanel.add(idLabel, c);
        idField = new JTextField(Integer.toString(searchProfile.getID()));
        c = createGbc(1,1);
        MainPanel.add(idField, c);
        
        // Set Name
        JLabel nameLabel = new JLabel("Name: ");
        c = createGbc(0,2);
        MainPanel.add(nameLabel, c);
        nameField = new JTextField(searchProfile.getName());
        c = createGbc(1,2);
        MainPanel.add(nameField, c);

        // Set all elements to uneditable
        idField.setEditable(false);
        nameField.setEditable(false);
        

        // ================ Set Button Panel ======================
        updateButton = new JButton("Update Profile");
        updateButton.addActionListener(this);
        ButtonPanel.add(updateButton);

        deleteButton = new JButton("Delete Profile");
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
        if (searchProfile.getID() == 1)
        {
            JOptionPane.showMessageDialog(null, "No profile was found!", "Search Result", JOptionPane.PLAIN_MESSAGE);
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

                updateButton.setText("Confirm");
            }
            else
            {
                // Save the data into database
                SysAdminUpdateProfileController upc = new SysAdminUpdateProfileController();

                // Retrieve data
                String name = nameField.getText();

                // Create profile object
                Profile newProfile = new Profile(profileID, name);
                boolean updateProfile = upc.updateProfileRecord(newProfile);
                String statusText;

                if (updateProfile) {
                    statusText = "Profile updated successfully!";
                }
                else {
                    statusText = "Profile update failed.";
                    // Reset values if invalid input
                    // Set all elements to editable
                    nameField.setEditable(true);
                }
                String titleText = "Profile Update Status";
                JOptionPane.showMessageDialog(null, statusText, titleText, JOptionPane.PLAIN_MESSAGE);

                // Set all elements to uneditable
                nameField.setText(searchProfile.getName());

                updateButton.setText("Update Profile");
            }
        }

        // Delete current account
        if (e.getSource() == deleteButton)
        {
            int reply = JOptionPane.showConfirmDialog(null, "Are you sure you wish to delete this account?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                String statusText;
                SysAdminDeleteProfileController dpc = new SysAdminDeleteProfileController();

                boolean deleted = dpc.deleteProfileRecord(profileID);
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

    public static void main(String[] args) {       
        new SysAdminSearchProfilePg("System Admin");
    }
}
