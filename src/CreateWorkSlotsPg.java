import javax.swing.*;

import com.formdev.flatlaf.FlatDarkLaf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateWorkSlotsPg extends JFrame implements ActionListener {
    private JFrame frame;
    private JButton createButton, cancelButton;
    private JTextField dateField;
    private JTextField hoursField;
    private JComboBox<String> positionField;

    public CreateWorkSlotsPg() {
        // Setup for UI LAF
        FlatDarkLaf.setup();

        // Setup JFrame
        frame = new JFrame("Create Work Slot");
        frame.setLayout(new BorderLayout(5, 5));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create Panels
        JPanel inputPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Set date
        JLabel dateLabel = new JLabel("Date: ");
        inputPanel.add(dateLabel, createGbc(0, 0));
        dateField = new JTextField(15);
        inputPanel.add(dateField, createGbc(1, 0));

        // Set hours
        JLabel hoursLabel = new JLabel("Hours: ");
        inputPanel.add(hoursLabel, createGbc(0, 1));
        hoursField = new JTextField(15);
        inputPanel.add(hoursField, createGbc(1, 1));

        // Set position
        JLabel positionLabel = new JLabel("Position: ");
        inputPanel.add(positionLabel, createGbc(0, 2));
        String[] positionChoices = {" ", "Cashier","Chef", "Waiter"};
        positionField = new JComboBox<String>(positionChoices);
        positionField.setVisible(true);
        positionField.setSelectedIndex(0);
        inputPanel.add(positionField, createGbc(1, 2));

        // Create button
        createButton = new JButton("Create");
        createButton.addActionListener(this);
        buttonPanel.add(createButton);

        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);

        // Add panels to frame
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Set frame output
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            // Implement create work slot logic
            String date = dateField.getText();
            int hours = Integer.parseInt(hoursField.getText());
            String position = positionField.getSelectedItem().toString();

            // Perform validation and create work slot
            CafeOwnerCreateWorkSlotController c = new CafeOwnerCreateWorkSlotController();
            c.createWorkSlot(date, hours, position, hours);
            //WorkSlot newWorkSlot = new WorkSlot(date, hours, position, null);

            //newWorkSlot.createWorkSlot();

            JOptionPane.showMessageDialog(null, "Work Slot created successfully.", "Success", JOptionPane.PLAIN_MESSAGE);
            frame.dispose();
        }

        if (e.getSource() == cancelButton) {
            frame.dispose();
        }
    }

    private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        return gbc;
    }

    public static void main(String[] args) {
        new CreateWorkSlotsPg();
    }
}
