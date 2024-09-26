import java.sql.*;
import java.util.ArrayList;

public class WorkSlot {
    private int slotId;
    private String date;
    private int hours;
    private String position;
    private Integer assignedTo;

    public WorkSlot(){}

    public WorkSlot(String date, int hours, String position, Integer assignedTo) {
        this.date = date;
        this.hours = hours;
        this.position = position;
        this.assignedTo = assignedTo;
    }

    // Assuming you have a constructor that includes the slotId
    public WorkSlot(int slotId, String date, int hours, String position, int assignedTo) {
        this.slotId = slotId;
        this.date = date;
        this.hours = hours;
        this.position = position;
        this.assignedTo = assignedTo;
    }

    // Getters and setters for the fields
    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    public boolean createWorkSlot(String date,int hours,String position, int assignedTo) {
        boolean success = false;

        // Prepare query
        String query = "INSERT INTO WORKSLOTS (SLOT_DATE, SLOT_HOURS, SLOT_POSITION) VALUES (?, ?, ?)";
        Connection connection = null;
        try {

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cafems",
                    "root", "Just@GroupProj3ctPW");

            // Prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameters for statement
            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, hours);
            preparedStatement.setString(3, position);


            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("WorkSlot created successfully.");
                success = true;
            } else {
                System.out.println("WorkSlot creation failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating WorkSlot: " + e.getMessage());
        }

        return success;
    }

    public WorkSlot getWorkSlotById(int slotId) {
        String selectQuery = "SELECT * FROM WORKSLOTS WHERE SLOT_ID = ?";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cafems",
                "root", "Just@GroupProj3ctPW")) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, slotId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve data from the result set and create a WorkSlot object
                        int retrievedSlotId = resultSet.getInt("SLOT_ID");
                        String date = resultSet.getString("SLOT_DATE");
                        int hour = resultSet.getInt("SLOT_HOURS");
                        String position = resultSet.getString("SLOT_POSITION");
                        int assignedTo = resultSet.getInt("SLOT_ASSIGNEDTO");

                        return new WorkSlot(retrievedSlotId, date, hour, position, assignedTo);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Handle the exception according to your application's needs
        }

        return null;
    }


    public ArrayList<WorkSlot> getAllWorkSlots() {
        ArrayList<WorkSlot> workSlots = new ArrayList<>();

        // Prepare query
        String myQuery = "SELECT * FROM WORKSLOTS";

        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cafems",
                    "root", "Just@GroupProj3ctPW");

            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(myQuery);

            while (resultSet.next()) {
                int slotID = resultSet.getInt("SLOT_ID");
                String date = resultSet.getString("SLOT_DATE").trim();
                int hours = resultSet.getInt("SLOT_HOURS");
                String position = resultSet.getString("SLOT_POSITION").trim();
                int assignedTo = resultSet.getInt("SLOT_ASSIGNEDTO");

                WorkSlot workSlot = new WorkSlot(slotID, date, hours, position, assignedTo);
                workSlots.add(workSlot);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }

        return workSlots;
    }

    public boolean editWorkSlot(WorkSlot ws) {
        boolean success = false;

        // Prepare query
        String query = "UPDATE WORKSLOTS SET SLOT_DATE = ?, SLOT_HOURS = ?, SLOT_POSITION = ? WHERE SLOT_ID = ?";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cafems",
                    "root", "Just@GroupProj3ctPW");

            // Prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameters for statement
            preparedStatement.setString(1, ws.getDate());
            preparedStatement.setInt(2, ws.getHours());
            preparedStatement.setString(3, ws.getPosition());
            preparedStatement.setInt(4, ws.slotId);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("WorkSlot updated successfully.");
                success = true;
            } else {
                System.out.println("WorkSlot update failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating WorkSlot: " + e.getMessage());
        }

        return success;
    }

    public boolean deleteWorkSlot() {
        boolean success = false;

        // Prepare query
        String query = "DELETE FROM WORKSLOTS WHERE SLOT_ID = ?";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cafems",
                    "root", "Just@GroupProj3ctPW");

            // Prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameters for statement
            preparedStatement.setInt(1, this.slotId);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("WorkSlot deleted successfully.");
                success = true;
            } else {
                System.out.println("WorkSlot deletion failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting WorkSlot: " + e.getMessage());
        }

        return success;
    }

    public ArrayList<WorkSlot> searchWorkSlots(String userInput) {
        ArrayList<WorkSlot> searchResults = new ArrayList<>();

        // Implement your database query to search for work slots based on date or position
        // You can modify the query accordingly based on your database schema

        String query = "SELECT * FROM WORKSLOTS WHERE SLOT_DATE LIKE ? OR SLOT_POSITION LIKE ?";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cafems",
                "root", "Just@GroupProj3ctPW");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + userInput + "%"); // Search for date
            preparedStatement.setString(2, "%" + userInput + "%"); // Search for position

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Populate the search results with the retrieved work slots
                int slotID = resultSet.getInt("SLOT_ID");
                String date = resultSet.getString("SLOT_DATE").trim();
                int hours = resultSet.getInt("SLOT_HOURS");
                String position = resultSet.getString("SLOT_POSITION").trim();
                int assignedTo = resultSet.getInt("SLOT_ASSIGNEDTO");

                WorkSlot workSlot = new WorkSlot(slotID, date, hours, position, assignedTo);
                searchResults.add(workSlot);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

    public ArrayList<WorkSlot> getUnassignedWorkSlots(int employeeId) {
        ArrayList<WorkSlot> unassignedWorkSlots = new ArrayList<>();

        // Your SQL query to fetch unassigned work slots
        String query = "SELECT * FROM WORKSLOTS WHERE SLOT_ASSIGNEDTO IS NULL AND SLOT_ID NOT IN (SELECT SLOT_ID FROM BIDS WHERE EMP_ID = ?)";


        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cafems",
                "root", "Just@GroupProj3ctPW")) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int slotID = resultSet.getInt("SLOT_ID");
                String date = resultSet.getString("SLOT_DATE").trim();
                int hours = resultSet.getInt("SLOT_HOURS");
                String position = resultSet.getString("SLOT_POSITION").trim();
                int assignedTo = resultSet.getInt("SLOT_ASSIGNEDTO");

                WorkSlot workSlot = new WorkSlot(slotID, date, hours, position, assignedTo);
                unassignedWorkSlots.add(workSlot);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching unassigned work slots: " + e.getMessage());
        }

        return unassignedWorkSlots;
    }

    public ArrayList<WorkSlot> searchUnassignedWorkSlots(String userInput) {
        ArrayList<WorkSlot> searchResults = new ArrayList<>();

        // Your SQL query to search for unassigned work slots based on userInput
        String query = "SELECT * FROM WORKSLOTS WHERE SLOT_ASSIGNEDTO IS NULL AND (SLOT_DATE LIKE ? OR SLOT_POSITION LIKE ?)";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cafems",
                "root", "Just@GroupProj3ctPW")) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + userInput + "%");
            preparedStatement.setString(2, "%" + userInput + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int slotID = resultSet.getInt("SLOT_ID");
                    String date = resultSet.getString("SLOT_DATE").trim();
                    int hours = resultSet.getInt("SLOT_HOURS");
                    String position = resultSet.getString("SLOT_POSITION").trim();
                    int assignedTo = resultSet.getInt("SLOT_ASSIGNEDTO");

                    WorkSlot workSlot = new WorkSlot(slotID, date, hours, position, assignedTo);
                    searchResults.add(workSlot);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching for unassigned work slots: " + e.getMessage());
        }

        return searchResults;
    }

    public ArrayList<WorkSlot> getWorkSlotsByAssignedEmployeeId(int employeeId) {
        ArrayList<WorkSlot> assignedWorkSlots = new ArrayList<>();

        String query = "SELECT * FROM WORKSLOTS WHERE SLOT_ASSIGNEDTO = ?";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cafems",
                "root", "Just@GroupProj3ctPW")) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, employeeId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int slotID = resultSet.getInt("SLOT_ID");
                        String date = resultSet.getString("SLOT_DATE").trim();
                        int hours = resultSet.getInt("SLOT_HOURS");
                        String position = resultSet.getString("SLOT_POSITION").trim();
                        int assignedTo = resultSet.getInt("SLOT_ASSIGNEDTO");

                        WorkSlot workSlot = new WorkSlot(slotID, date, hours, position, assignedTo);
                        assignedWorkSlots.add(workSlot);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

        return assignedWorkSlots;
    }

    public boolean assignWorkSlot(int slotId, int empId) {
        String updateQuery = "UPDATE WORKSLOTS SET SLOT_ASSIGNEDTO = ? WHERE SLOT_ID = ?";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cafems",
                "root", "Just@GroupProj3ctPW")) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, empId);
                preparedStatement.setInt(2, slotId);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
