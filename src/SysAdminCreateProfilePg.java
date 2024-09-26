import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SysAdminCreateProfilePg extends JFrame implements ActionListener {

    private JTextField nameField;
    private JButton submitButton, clearButton;

    private static final Insets WEST_INSETS = new Insets(5, 0, 5, 5);
    private static final Insets EAST_INSETS = new Insets(5, 5, 5, 0);

    public SysAdminCreateProfilePg() {

        FlatDarkLaf.setup();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Create New Profile"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        GridBagConstraints c;
        
        // Set name Label
        JLabel nameLabel = new JLabel("Name: ");
        c = createGbc(0,1);
        panel.add(nameLabel, c);

        // Set name
        nameField = new JTextField();
        c = createGbc(1,1);
        panel.add(nameField, c);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        buttonPanel.add(submitButton);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);

        // Create JFrame
        final JFrame frame = new JFrame("Create New Profile");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setPreferredSize(new Dimension(200, 150));

        // Set frame to be visisble
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // Submit details to create employee record
        if (e.getSource() == submitButton) {
            // Retrieve details from form
            String name = nameField.getText();

            boolean validName = checkName(name);

            // Create profile
            String statusText;
            if (validName) {
                SysAdminCreateProfileController createPC = new SysAdminCreateProfileController();
                boolean createProfile = createPC.createProfileRecord(name);

                if (createProfile) {
                    statusText = "Profile added successfully!";
                }
                else {
                    statusText = "Profile creation failed. Account interfers with business rules.";
                }
            }
            else {
                statusText = "Profile creation failed. Please check the fields for invalid input.";
            }
            String titleText = "Profile Creation Status";
            JOptionPane.showMessageDialog(null, statusText, titleText, JOptionPane.PLAIN_MESSAGE);
        }

        // Clear fields if clear button is used
        if (e.getSource() == clearButton)
        {
            nameField.setText("");
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

    private boolean checkName(String name)
    {
        if (!InputCheck.isAlphaNumeric(name))
        {
            return false;
        }
        if (name.trim().equals(""))
        {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {       
        new SysAdminCreateProfilePg();
    }
}