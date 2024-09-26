import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.formdev.flatlaf.FlatDarkLaf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class CafeStaffBrowseWorkSlotsPg extends JFrame implements ActionListener {
    private JFrame frame;
    private JTextField searchField;
    private JButton searchButton;
    private JTable workSlotTable;
    public int employeeId;
    private ArrayList<WorkSlot> workSlots;

    public CafeStaffBrowseWorkSlotsPg(int employeeId) {

        // Setup for UI LAF
        FlatDarkLaf.setup();

        // Fetch all work slots without assignedTo
        CafeStaffBrowseWorkSlotsController bsc = new CafeStaffBrowseWorkSlotsController();
        workSlots = bsc.getUnassignedWorkSlots(employeeId);
        this.employeeId = employeeId;

        // Setup JFrame
        frame = new JFrame("Browse Work Slots");
        frame.setLayout(new BorderLayout(5, 5));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create Panel
        JPanel panel = new JPanel(new GridBagLayout());

        // Create search components
        JLabel searchLabel = new JLabel("Search:");
        searchField = new JTextField(25);
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);

        // Add search components to the panel
        GridBagConstraints searchConstraints = new GridBagConstraints();
        searchConstraints.gridx = 0;
        searchConstraints.gridy = 0;
        searchConstraints.gridwidth = 2;
        searchConstraints.insets = new Insets(5, 5, 5, 5);
        panel.add(searchLabel, searchConstraints);

        searchConstraints.gridx = 2;
        searchConstraints.gridwidth = 3; // Increase the gridwidth to make the search field wider
        searchConstraints.fill = GridBagConstraints.HORIZONTAL; // Make the search field fill the horizontal space
        panel.add(searchField, searchConstraints);

        searchConstraints.gridx = 5; // Adjust the column index according to your layout
        searchConstraints.gridwidth = 1;
        searchConstraints.fill = GridBagConstraints.NONE; // Reset fill property
        panel.add(searchButton, searchConstraints);

        String[] columnNames = {"Date", "Hours", "Position", "Assigned To", "Action"};
        Object[][] data = new Object[workSlots.size()][5];

        for (int i = 0; i < workSlots.size(); i++) {
            WorkSlot workSlot = workSlots.get(i);
            data[i][0] = workSlot.getDate();
            data[i][1] = workSlot.getHours();
            data[i][2] = workSlot.getPosition();
            data[i][3] = workSlot.getAssignedTo();
            data[i][4] = "Bid";
        }

        TableModel tableModel = new DefaultTableModel(data, columnNames);
        workSlotTable = new JTable(tableModel);

        Action bidAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

                JTable table = (JTable) e.getSource();
                int modelRow = Integer.valueOf(e.getActionCommand());
                handleBidButtonClick(modelRow);
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);

            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(workSlotTable, bidAction, 4);
        buttonColumn.setMnemonic(KeyEvent.VK_D);
        JScrollPane scrollPane = new JScrollPane(workSlotTable);


        // Add the table to the panel
        GridBagConstraints tableConstraints = new GridBagConstraints();
        tableConstraints.gridx = 0;
        tableConstraints.gridy = 1;
        tableConstraints.gridwidth = 5;
        tableConstraints.fill = GridBagConstraints.BOTH;
        tableConstraints.weightx = 1.0;
        tableConstraints.weighty = 1.0;
        panel.add(scrollPane, tableConstraints);

        // Set dimensions
        panel.setPreferredSize(new Dimension(600, 400));

        // Set location
        frame.add(panel, BorderLayout.CENTER);

        // Set frame output
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void handleBidButtonClick(int rowIndex) {

        // Get the WorkSlot corresponding to the clicked row
        WorkSlot workSlot = workSlots.get(rowIndex);

        // Assuming you have the employee ID, replace 123 with the actual employee ID


        // Create a bid based on the employee ID and slot ID
        Bid newBid = new Bid(workSlot.getSlotId(), this.employeeId);

        // Now, you can perform any necessary actions with the new bid, e.g., save it to the database
        boolean bidCreated = newBid.createBid();

        if (bidCreated) {
            JOptionPane.showMessageDialog(
                    this,
                    "Bid created successfully!",
                    "Bid Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to create bid.",
                    "Bid Failure",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            // Implement search functionality
            String userInput = searchField.getText();
            CafeStaffSearchWorkSlotController swsc = new CafeStaffSearchWorkSlotController();
            ArrayList<WorkSlot> searchResults = swsc.searchUnassignedWorkSlots(userInput);

            // Update the table with search results
            DefaultTableModel model = (DefaultTableModel) workSlotTable.getModel();
            model.setRowCount(0); // Clear existing rows

            for (WorkSlot workSlot : searchResults) {
                model.addRow(new Object[]{workSlot.getDate(), workSlot.getHours(), workSlot.getPosition(), workSlot.getAssignedTo(), "Bid"});
            }
        }
    }

    public static void main(String[] args) {
        new CafeStaffBrowseWorkSlotsPg(10002);
    }
}
