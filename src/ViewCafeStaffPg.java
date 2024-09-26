import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewCafeStaffPg extends JFrame {
    private JFrame frame;
    private JTable staffTable;

    public ViewCafeStaffPg(ArrayList<User> cafeStaff) {
        // Setup for UI LAF
        FlatDarkLaf.setup();

        // Setup JFrame
        frame = new JFrame("View Cafe Staff");
        frame.setLayout(new BorderLayout(1, 2));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create Panel
        JPanel panel = new JPanel(new GridBagLayout());

        // Create a table to display cafe staff and their assigned work slots
        String[] columnNames = {"Employee ID", "Name", "Salary", "Date Joined", "Role ID", "Position", "Username",  "Assigned Work Slots"};
        Object[][] data = new Object[cafeStaff.size()][8];

        for (int i = 0; i < cafeStaff.size(); i++) {
            User employee = cafeStaff.get(i);
            data[i][0] = employee.getEmpID();
            data[i][1] = employee.getName();
            data[i][2] = employee.getSalary();
            data[i][3] = employee.getDateJoined();
            data[i][4] = employee.getRoleID();
            data[i][5] = employee.getPosition();
            data[i][6] = employee.getUsername();

            // Add other properties as needed

            // Fetch work slots assigned to the current cafe staff member
            CafeStaffViewAllocWSController vawc = new CafeStaffViewAllocWSController ();
            ArrayList<WorkSlot> assignedWorkSlots = vawc.getWorkSlotsByAssignedEmployeeId(employee.getEmpID());
            StringBuilder assignedWorkSlotsText = new StringBuilder();

            for (WorkSlot workSlot : assignedWorkSlots) {
                assignedWorkSlotsText.append(workSlot.getSlotId()).append(", ");
                // Add other work slot properties as needed
            }

            data[i][7] = assignedWorkSlotsText.toString();
        }

        staffTable = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table non-editable
            }
        };

        JScrollPane scrollPane = new JScrollPane(staffTable);
        scrollPane.setPreferredSize(new Dimension(900, 400));

        // Add the table to the panel
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        panel.add(scrollPane, c);

        // Set location
        frame.add(panel, BorderLayout.CENTER);

        // Set frame output
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
