import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatDarkLaf;

import java.awt.*;
import java.util.ArrayList;

public class AllocatedSlots extends JDialog {
    private JTable allocatedSlotsTable;

    public AllocatedSlots(Frame owner, ArrayList<WorkSlot> allocatedSlots) {
        super(owner, "Allocated Slots", true);

        // Setup for UI LAF
        FlatDarkLaf.setup();
        
        // Initialize the table
        initTable(allocatedSlots);

        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(allocatedSlotsTable);

        // Add the scroll pane to the content pane
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Set the dialog size
        setSize(400, 300);

        // Center the dialog on the owner frame
        setLocationRelativeTo(owner);

        // Set default close operation
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initTable(ArrayList<WorkSlot> allocatedSlots) {
        String[] columnNames = {"Date", "Hours", "Position"};

        // Create a data array
        Object[][] data = new Object[allocatedSlots.size()][3];

        // Populate the data array with allocated slots information
        for (int i = 0; i < allocatedSlots.size(); i++) {
            WorkSlot slot = allocatedSlots.get(i);
            data[i][0] = slot.getDate();
            data[i][1] = slot.getHours();
            data[i][2] = slot.getPosition();
        }

        // Create a table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Create the table using the model
        allocatedSlotsTable = new JTable(model);

        // Set the table to be non-editable
        allocatedSlotsTable.setEnabled(false);
    }

    // Example usage:
    public static void main(String[] args) {
        // Fetch allocated slots (replace this with your logic)
        CafeStaffViewAllocWSController vawc = new CafeStaffViewAllocWSController();
        ArrayList<WorkSlot> allocatedSlots = vawc.getWorkSlotsByAssignedEmployeeId(10002);

        // Create and display the dialog
        AllocatedSlots dialog = new AllocatedSlots(null, allocatedSlots);
        dialog.setVisible(true);
    }
}
