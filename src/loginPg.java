import com.formdev.flatlaf.FlatDarkLaf;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class loginPg extends JFrame implements ActionListener 
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JFrame frame;
    
    private static final Insets WEST_INSETS = new Insets(5, 0, 5, 5);
    private static final Insets EAST_INSETS = new Insets(5, 5, 5, 0);

    // constructor
    public loginPg()
    {
        // Setup for UI LAF
        FlatDarkLaf.setup();

        // Create Panels
        JPanel imgPanel = new JPanel();
        imgPanel.setPreferredSize(new Dimension(300, 500));
        JPanel loginPanel = new JPanel(new GridBagLayout());

        // Set background image
        try { 
        BufferedImage myPicture = ImageIO.read(new File("loginbackground.jpg"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        imgPanel.add(picLabel);
        } catch (IOException ex) {
            ex.printStackTrace();
       }

        // Set login Panel
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Login to Cafe Management System"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        GridBagConstraints c;

        // Set title
        JLabel headerLabel = new JLabel("Cafe Management System");
        headerLabel.setFont(new Font("Serif", Font.BOLD, 20));
        c = createGbc(0,0);
        c.gridwidth = 2;
        loginPanel.add(headerLabel, c);

        // Set username label
        JLabel usernameLabel = new JLabel("Username: ");
        c = createGbc(0,1);
        loginPanel.add(usernameLabel, c);
        // Set username
        usernameField = new JTextField();
        c = createGbc(1,1);
        loginPanel.add(usernameField, c);

        // Set password label
        JLabel passwordLabel = new JLabel("Password: ");
        c = createGbc(0,2);
        loginPanel.add(passwordLabel, c);
        // Set password
        passwordField = new JPasswordField();
        c = createGbc(1,2);
        loginPanel.add(passwordField, c);

        // Set submit
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        c = createGbc(1, 3);
        loginPanel.add(submitButton, c);

        // Create Base Frame
        frame = new JFrame("Cafe Management System Login Page");
        frame.setLayout(new GridBagLayout());

        // Add Panels to frame
        c.gridx = 0;
        c.gridy = 1;
        frame.add(imgPanel, c);
        c.gridx = 1;
        c.gridy = 1;
        frame.add(loginPanel, c);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // Submit details to create employee record
        if (e.getSource() == submitButton) {
            // Retrieve details from form
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            User currentUser = new User();

            // Attempt login
            if ((InputCheck.isAlphaNumeric(username)) && (InputCheck.isAlphaNumeric(password)))
            {
                loginController loginController = new loginController();
                currentUser = loginController.loginUser(username, password);
            }

            // if current user is null display login failed
            if (currentUser.getEmpID() == 0)
            {
                String statusText = "Login Failed! Please try again.";
                String titleText = "Invalid Login";
                JOptionPane.showMessageDialog(null, statusText, titleText, JOptionPane.PLAIN_MESSAGE);
            }
            // else close current frame and display landing page
            else
            {
                JOptionPane.showMessageDialog(null, "Success!", "Login success", JOptionPane.PLAIN_MESSAGE);
                frame.dispose();
                // Call page based on profiles
                if (currentUser.getRoleID() == 2)
                {
                    new SystemAdminPg(currentUser);
                }
                else if(currentUser.getRoleID() == 3)
                {
                    new CafeOwnerPg(currentUser);
                }
                else if(currentUser.getRoleID() == 4)
                {
                    new CafeManagerPg(currentUser);
                }
                else if(currentUser.getRoleID() == 5)
                {
                    new CafeStaffPg(currentUser);
                }
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

    public static void main(String[] args) {        
        new loginPg();
    }
}
