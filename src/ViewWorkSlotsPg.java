import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.formdev.flatlaf.FlatDarkLaf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ViewWorkSlotsPg extends JFrame {
    private JFrame frame;

    public ViewWorkSlotsPg() {
        // Fetch all work slots
        CafeManagerViewWorkSlotsController vwsc = new CafeManagerViewWorkSlotsController();
        ArrayList<WorkSlot> workSlots = vwsc.getAllWorkSlots();
        
        // Setup for UI LAF
        FlatDarkLaf.setup();

        // Setup JFrame
        frame = new JFrame("View Work Slots");
        frame.setLayout(new BorderLayout(5, 5));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a table to display work slots
        String[] columnNames = {"Date", "Hours", "Position", "Assigned To", "", ""};
        Object[][] data = new Object[workSlots.size()][6]; // Added two extra columns for buttons

        for (int i = 0; i < workSlots.size(); i++) {
            WorkSlot workSlot = workSlots.get(i);
            data[i][0] = workSlot.getDate();
            data[i][1] = workSlot.getHours();
            data[i][2] = workSlot.getPosition();
            data[i][3] = workSlot.getAssignedTo();
            data[i][4] = "Edit";
            data[i][5] = "Delete";
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        JTable table = new JTable(model);

        Action editAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int modelRow = Integer.valueOf(e.getActionCommand());
                handleEditButtonClick(modelRow);
            }
        };

        Action deleteAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int modelRow = Integer.valueOf(e.getActionCommand());
                handleDeleteButtonClick(modelRow);
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(table, deleteAction, 5);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        ButtonColumn buttonColumn2 = new ButtonColumn(table, editAction, 4);
        buttonColumn2.setMnemonic(KeyEvent.VK_D);

        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("Work slots");
        frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        frame.add(scrollPane);
        frame.setPreferredSize(new Dimension(450, 160));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public ViewWorkSlotsPg(ArrayList<WorkSlot> workSlots) {
        // Setup for UI LAF
        FlatDarkLaf.setup();

        // Setup JFrame
        frame = new JFrame("View Work Slots");
        frame.setLayout(new BorderLayout(5, 5));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a table to display work slots
        String[] columnNames = {"Date", "Hours", "Position", "Assigned To", "", ""};
        Object[][] data = new Object[workSlots.size()][6]; // Added two extra columns for buttons

        for (int i = 0; i < workSlots.size(); i++) {
            WorkSlot workSlot = workSlots.get(i);
            data[i][0] = workSlot.getDate();
            data[i][1] = workSlot.getHours();
            data[i][2] = workSlot.getPosition();
            data[i][3] = workSlot.getAssignedTo();
            data[i][4] = "Edit";
            data[i][5] = "Delete";
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        JTable table = new JTable(model);

        Action editAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int modelRow = Integer.valueOf(e.getActionCommand());
                handleEditButtonClick(modelRow);
            }
        };

        Action deleteAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int modelRow = Integer.valueOf(e.getActionCommand());
                handleDeleteButtonClick(modelRow);
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(table, deleteAction, 5);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        ButtonColumn buttonColumn2 = new ButtonColumn(table, editAction, 4);
        buttonColumn2.setMnemonic(KeyEvent.VK_D);

        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("Work slots");
        frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        frame.add(scrollPane);
        frame.setPreferredSize(new Dimension(450, 160));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Handle edit button click
    private void handleEditButtonClick(int rowIndex) {

        WorkSlot selectedWorkSlot = getWorkSlotForRow(rowIndex);
        // Implement edit action based on rowIndex
        // You can open a new window for editing or perform some other action
        System.out.println("Edit button clicked for row " + rowIndex);
        if (selectedWorkSlot != null) {
            JDialog editDialog = createEditDialog(selectedWorkSlot);
            editDialog.setVisible(true);
        }
    }

    // Handle delete button click
    private void handleDeleteButtonClick(int rowIndex) {
        // Implement delete action based on rowIndex
        // You can show a confirmation dialog and delete the row if confirmed
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this row?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            // Delete the row
            WorkSlot selectedWorkSlot = getWorkSlotForRow(rowIndex);
            if (selectedWorkSlot != null) {
                selectedWorkSlot.deleteWorkSlot();
            }

            System.out.println("Delete button clicked for row " + rowIndex);
        }
    }

    // Helper method to get the WorkSlot object for the selected row
    private WorkSlot getWorkSlotForRow(int rowIndex) {
        // Fetch all work slots
        WorkSlot w = new WorkSlot();
        ArrayList<WorkSlot> workSlots = w.getAllWorkSlots();

        // Ensure the rowIndex is within bounds
        if (rowIndex >= 0 && rowIndex < workSlots.size()) {
            return workSlots.get(rowIndex);
        }

        return null;
    }

    // Helper method to create an edit dialog
    private JDialog createEditDialog(WorkSlot workSlot) {
        JDialog editDialog = new JDialog(frame, "Edit Work Slot", true);
        editDialog.setLayout(new BorderLayout());

        // Create and add components to the dialog
        JPanel editPanel = new JPanel(new GridLayout(4, 2));
        JTextField dateField = new JTextField(workSlot.getDate());
        JTextField hoursField = new JTextField(String.valueOf(workSlot.getHours()));
        JTextField positionField = new JTextField(workSlot.getPosition());
        JTextField assignedToField = new JTextField(String.valueOf(workSlot.getAssignedTo()));

        editPanel.add(new JLabel("Date:"));
        editPanel.add(dateField);
        editPanel.add(new JLabel("Hours:"));
        editPanel.add(hoursField);
        editPanel.add(new JLabel("Position:"));
        editPanel.add(positionField);
        editPanel.add(new JLabel("Assigned To:"));
        editPanel.add(assignedToField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            // Update the WorkSlot object with edited values
            workSlot.setDate(dateField.getText());
            workSlot.setHours(Integer.parseInt(hoursField.getText()));
            workSlot.setPosition(positionField.getText());
            workSlot.setAssignedTo(Integer.parseInt(assignedToField.getText()));

            UpdateWorkSlotController uwsc = new UpdateWorkSlotController();
            boolean updatedWS = uwsc.editWorkSlot(workSlot);
            if (updatedWS)
            {
                JOptionPane.showMessageDialog(null, 
                            "Work Slot updated successfully!", 
                            "Update Status", 
                            JOptionPane.PLAIN_MESSAGE);
            }
            // Close the dialog
            editDialog.dispose();
            // Implement any additional logic to save the changes to the database if needed
        });

        editDialog.add(editPanel, BorderLayout.CENTER);
        editDialog.add(saveButton, BorderLayout.SOUTH);

        // Set dialog properties
        editDialog.setSize(300, 200);
        editDialog.setLocationRelativeTo(frame);

        return editDialog;
    }

}
