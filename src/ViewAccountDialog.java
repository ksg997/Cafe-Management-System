import javax.swing.*;

import com.formdev.flatlaf.FlatDarkLaf;

import java.awt.*;
import java.util.ArrayList;

public class ViewAccountDialog extends JDialog {
    private User currentUser;

    public ViewAccountDialog(Frame owner, User user) {
        super(owner, "View Account", true);
        this.currentUser = user;

        // Setup for UI LAF
        FlatDarkLaf.setup();

        // Create a panel to hold user details
        JPanel panel = new JPanel(new GridLayout(6, 2));

        // Add user details to the panel
        panel.add(new JLabel("Employee ID: "));
        panel.add(new JLabel(String.valueOf(user.getEmpID())));
        panel.add(new JLabel("Name: "));
        panel.add(new JLabel(user.getName()));
        panel.add(new JLabel("Salary: "));
        panel.add(new JLabel(String.valueOf(user.getSalary())));
        panel.add(new JLabel("Date Joined: "));
        panel.add(new JLabel(user.getDateJoined()));
        panel.add(new JLabel("Role ID: "));
        panel.add(new JLabel(String.valueOf(user.getRoleID())));
        panel.add(new JLabel("Position: "));
        panel.add(new JLabel(user.getPosition()));

        // Add the panel to the content pane
        getContentPane().add(panel, BorderLayout.CENTER);

        // Set the dialog size
        setSize(300, 200);

        // Center the dialog on the owner frame
        setLocationRelativeTo(owner);

        // Set default close operation
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // Example usage:
    public static void main(String[] args) {
        // Fetch the current user (replace this with your logic)
        User currentUser = new User();
        currentUser = currentUser.getUserById(10002);

        // Create and display the dialog
        ViewAccountDialog dialog = new ViewAccountDialog(null, currentUser);
        dialog.setVisible(true);
    }
}
