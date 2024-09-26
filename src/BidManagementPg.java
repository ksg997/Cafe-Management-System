import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.formdev.flatlaf.FlatDarkLaf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class BidManagementPg extends JFrame implements ActionListener {
    private JFrame frame;
    private JTable bidTable;

    private ArrayList<Bid> bids;
    // Add other necessary components like buttons, labels, etc.

    public BidManagementPg(ArrayList<Bid> bids) {
        // Setup for UI LAF
        FlatDarkLaf.setup();

        // Setup JFrame
        frame = new JFrame("Bid Management");
        frame.setLayout(new BorderLayout(5, 5));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.bids = bids;
        // Create Panel
        JPanel panel = new JPanel(new BorderLayout());

        // Create bid table
        initBidTable(bids);
        JScrollPane scrollPane = new JScrollPane(bidTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add other components like buttons, labels, etc., as needed

        // Set dimensions
        panel.setPreferredSize(new Dimension(800, 600));

        // Set location
        frame.add(panel, BorderLayout.CENTER);

        // Set frame output
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initBidTable(ArrayList<Bid> bids) {
        String[] columnNames = {"Bid ID", "Employee Name", "Position", "Slot date", "Slot hour","", ""};
        Object[][] data = new Object[bids.size()][7];

        for (int i = 0; i < bids.size(); i++) {
            Bid bid = bids.get(i);
            User employee = new User();
            employee = employee.getUserById(bid.getEmpId());
            CafeManagerGetWSController vbc = new CafeManagerGetWSController();
            WorkSlot slot = vbc.getWorkSlotById(bid.getSlotId());
            data[i][0] = bid.getBidId();
            data[i][1] = employee.getName();
            data[i][2] = employee.getPosition();
            data[i][3] = slot.getDate();
            data[i][4] = slot.getHours();
            data[i][5] = "Approve";
            data[i][6] = "Reject";
        }


        TableModel tableModel = new DefaultTableModel(data, columnNames);
        bidTable = new JTable(tableModel);

        Action approve = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

                JTable table = (JTable) e.getSource();
                int modelRow = Integer.valueOf(e.getActionCommand());
                handleApproveClick(modelRow);

            }
        };

        Action reject = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

                JTable table = (JTable) e.getSource();
                int modelRow = Integer.valueOf(e.getActionCommand());
                handleRejectClick(modelRow);

                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(bidTable, approve, 5);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        ButtonColumn buttonColumn2 = new ButtonColumn(bidTable, reject, 6);
        buttonColumn2.setMnemonic(KeyEvent.VK_D);
    }

    private JButton createActionButton(int rowIndex) {
        JButton actionButton = new JButton("Approve/Reject");
        actionButton.setActionCommand(String.valueOf(rowIndex));
        actionButton.addActionListener(this);
        return actionButton;
    }

    private void handleApproveClick(int rowIndex) {
        Bid bid = bids.get(rowIndex);
        CafeManagerApproveBidController apc = new CafeManagerApproveBidController();
        boolean bidApproved = apc.approveBid(bid);

        if (bidApproved) {
            JOptionPane.showMessageDialog(
                    this,
                    "Bid approved successfully! Work slot assigned to the employee.",
                    "Bid Approved",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to approve bid.",
                    "Bid Approval Failure",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void handleRejectClick(int rowIndex) {
        Bid bid = bids.get(rowIndex);
        // Reject the bid and delete it from the database
        CafeManagerDeleteBidController dbc = new CafeManagerDeleteBidController();
        boolean bidRejected = dbc.deleteBid(bid);

        if (bidRejected) {
            JOptionPane.showMessageDialog(
                    this,
                    "Bid rejected successfully! The bid has been deleted.",
                    "Bid Rejected",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to reject bid.",
                    "Bid Rejection Failure",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void actionPerformed(ActionEvent e) {
        // Handle other actions if needed
    }

    public static void main(String[] args) {
        // Example usage:
        CafeManagerViewBidsController vbc = new CafeManagerViewBidsController();
        ArrayList<Bid> bids = vbc.getAllBids(); // Fetch all bids
        new BidManagementPg(bids);
    }
}
