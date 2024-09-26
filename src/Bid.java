import java.sql.*;
import java.util.ArrayList;

public class Bid {
    private int bidId;
    private int slotId;
    private int empId;

    // default constructor
    public Bid()
    {
        this.bidId = 0;
        this.slotId = 0;
        this.empId = 0;
    }
    
    public Bid(int slotId, int empId) {
        this.slotId = slotId;
        this.empId = empId;
    }

    // Assuming you have a constructor that includes the bidId
    public Bid(int bidId, int slotId, int empId) {
        this.bidId = bidId;
        this.slotId = slotId;
        this.empId = empId;
    }

    // Getters and setters for the fields
    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public boolean createBid() {
        boolean success = false;

        // Prepare query
        String query = "INSERT INTO BIDS (SLOT_ID, EMP_ID) VALUES (?, ?)";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cafems",
                    "root", "Just@GroupProj3ctPW");

            // Prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameters for statement
            preparedStatement.setInt(1, this.slotId);
            preparedStatement.setInt(2, this.empId);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Bid created successfully.");
                success = true;
            } else {
                System.out.println("Bid creation failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating Bid: " + e.getMessage());
        }

        return success;
    }

    public ArrayList<Bid> getAllBids() {
        ArrayList<Bid> bids = new ArrayList<>();

        // Prepare query
        String myQuery = "SELECT * FROM BIDS";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cafems",
                    "root", "Just@GroupProj3ctPW");

            // Create statement
            PreparedStatement preparedStatement = connection.prepareStatement(myQuery);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int bidId = resultSet.getInt("BID_ID");
                int slotId = resultSet.getInt("SLOT_ID");
                int empId = resultSet.getInt("EMP_ID");

                Bid bid = new Bid(bidId, slotId, empId);
                bids.add(bid);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching Bids: " + e.getMessage());
        }

        return bids;
    }

    public boolean updateBid(Bid aBid) {
        boolean success = false;

        // Prepare query
        String query = "UPDATE BIDS SET SLOT_ID=?, EMP_ID=? WHERE BID_ID=?";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cafems",
                    "root", "Just@GroupProj3ctPW");

            // Prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameters for statement
            preparedStatement.setInt(1, aBid.slotId);
            preparedStatement.setInt(2, aBid.empId);
            preparedStatement.setInt(3, aBid.bidId);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Bid updated successfully.");
                success = true;
            } else {
                System.out.println("Bid update failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating Bid: " + e.getMessage());
        }
        return success;
    }

    public boolean deleteBid(Bid aBid) {
        boolean success = false;

        // Prepare query
        String query = "DELETE FROM BIDS WHERE BID_ID=?";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cafems",
                    "root", "Just@GroupProj3ctPW");

            // Prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameters for statement
            preparedStatement.setInt(1, aBid.getBidId());

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Bid deleted successfully.");
                success = true;
            } else {
                System.out.println("Bid deletion failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting Bid: " + e.getMessage());
        }

        return success;
    }

    public ArrayList<Bid> getBidsForEmployee(int employeeId) {
        ArrayList<Bid> bids = new ArrayList<>();

        // Prepare query
        String query = "SELECT * FROM BIDS WHERE EMP_ID = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafems", "root", "Just@GroupProj3ctPW");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameter for the employee ID
            preparedStatement.setInt(1, employeeId);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int bidId = resultSet.getInt("BID_ID");
                    int slotId = resultSet.getInt("SLOT_ID");
                    int empId = resultSet.getInt("EMP_ID");

                    Bid bid = new Bid(bidId, slotId, empId);
                    bids.add(bid);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return bids;
    }

    public boolean approveBid(Bid myBid) {
        // Assuming you have the necessary information in the Bid class
        int empId = myBid.getEmpId();
        int slotId = myBid.getSlotId();

        // Update the WorkSlot's assigned employee ID
        WorkSlot ws = new WorkSlot();
        boolean workSlotAssigned = ws.assignWorkSlot(slotId, empId);

        return workSlotAssigned;
    }
}
