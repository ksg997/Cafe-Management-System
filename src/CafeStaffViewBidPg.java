import javax.swing.*;

import com.formdev.flatlaf.FlatDarkLaf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class CafeStaffViewBidPg extends JFrame implements ActionListener {
    private JFrame frame;
    private JTable bidTable;

    private ArrayList<Bid> bids;

    public CafeStaffViewBidPg(User myUser) {
        // Fetch all bids for the logged-in user
        CafeStaffViewBidController vbc = new CafeStaffViewBidController();
        bids = vbc.getBidsForEmployee(myUser.getEmpID());

        // Setup for UI LAF
        FlatDarkLaf.setup();

        // Setup JFrame
        frame = new JFrame("View Bids");
        frame.setLayout(new BorderLayout(5, 5));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create Panel
        JPanel panel = new JPanel(new GridBagLayout());

        // Create table components
        initTable(bids);

        // Add components to the panel
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(5, 5, 5, 5);
        JScrollPane scrollPane = new JScrollPane(bidTable);

        // Set Dimensions
        scrollPane.setPreferredSize(new Dimension(900, 500));
        panel.add(scrollPane, c);

        // Set location
        frame.add(panel, BorderLayout.CENTER);

        // Set frame output
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initTable(ArrayList<Bid> input) {
        String[] columnNames = {"Slot ID", "Employee ID", "Bid ID", "Cancel", "Update"};
        Object[][] data = new Object[input.size()][5];

        for (int i = 0; i < input.size(); i++) {
            Bid bid = input.get(i);
            data[i][0] = bid.getSlotId();
            data[i][1] = bid.getEmpId();
            data[i][2] = bid.getBidId();
            data[i][3] = "Cancel"; // Add a placeholder for the cancel button
            data[i][4] = "Update";
        }

        bidTable = new JTable(data, columnNames);

        Action cancelAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JTable bidTable = (JTable) e.getSource();
                int modelRow = Integer.valueOf(e.getActionCommand());
                handleCancelBidButtonClick(modelRow);
            }
        };

        Action updateAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JTable bidTable = (JTable) e.getSource();
                int modelRow = Integer.valueOf(e.getActionCommand());
                handleUpdateBidButtonClick(modelRow);
            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(bidTable, cancelAction, 3);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        ButtonColumn buttonColumn2 = new ButtonColumn(bidTable, updateAction, 4);
        buttonColumn2.setMnemonic(KeyEvent.VK_D);
    }

    private void handleCancelBidButtonClick(int rowIndex) {
        // Get the Bid corresponding to the clicked row
        Bid bid = bids.get(rowIndex);

        // Implement bid cancellation
        CafeStaffCancelBidController cbc = new CafeStaffCancelBidController();
        boolean bidCancelled = cbc.deleteBid(bid);

        if (bidCancelled) {
            JOptionPane.showMessageDialog(
                    this,
                    "Bid cancelled successfully!",
                    "Cancel Bid Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to cancel bid.",
                    "Cancel Bid Failure",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void handleUpdateBidButtonClick(int rowIndex) {
        // Get the Bid corresponding to the clicked row
        Bid bid = bids.get(rowIndex);

        // Implement bid update
        CafeStaffUpdateBidController ubc = new CafeStaffUpdateBidController();
        boolean bidUpdated = ubc.updateBid(bid); 

        if (bidUpdated) {
            JOptionPane.showMessageDialog(
                    this,
                    "Bid updated successfully!",
                    "Update Bid Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update bid.",
                    "Update Bid Failure",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        User myUser = new User();
        myUser = myUser.loginUser("staff1", "staff1");
        new CafeStaffViewBidPg(myUser);
    }
}
