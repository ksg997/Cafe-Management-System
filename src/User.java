import java.sql.*;
import java.util.ArrayList;

/* User Entity Class

    Information in Employee database:
    empID, Name, Salary, DateJoined, RoleID, PositionID, Username, Password
*/

class User
{
    // Declaration of variables in Employee
    private int empID, roleID, maxSlots;
    private String name, username, password, dateJoined, position;
    private double salary;

    // Default Constructor for User class - Should not be added to database at anytime!
    public User()
    {
        this.empID = 0;
        this.name = "Default";
        this.salary = 0;
        this.dateJoined = "00/00/0000";
        this.roleID = 0;
        this.position = "Default";
        this.username = "Default";
        this.password = "Default";
        this.maxSlots = 0;
    }

    // Constructor for User Class
    public User (int empID, String name, double salary, String dateJoined, 
                        int roleID, String position, String username, String password)
    {
        this.empID = empID;
        this.name = name;
        this.salary = salary;
        this.dateJoined = dateJoined;
        this.roleID = roleID;
        this.position = position;
        this.username = username;
        this.password = password;
    }

    public User (int empID, String name, double salary, String dateJoined,
                 int roleID, String position, String username, String password, int maxSlots)
    {
        this.empID = empID;
        this.name = name;
        this.salary = salary;
        this.dateJoined = dateJoined;
        this.roleID = roleID;
        this.position = position;
        this.username = username;
        this.password = password;
        this.maxSlots = maxSlots;
    }

    /* ================================
     *  Get Set Methods
     * ================================ */

    public int getEmpID()
    { return this.empID; }

    public int getRoleID()
    { return this.roleID; }

    public String getName()
    { return this.name; }

    public String getUsername()
    { return this.username; }

    public String getPassword() // is it a good idea to have this
    { return this.password; }
    // need it for authenticating so maybe leave it in for now

    public String getDateJoined()
    { return this.dateJoined; }

    public String getPosition()
    { return this.position; }

    public double getSalary()
    { return this.salary; }

    public void setEmpID(int empID)
    { this.empID = empID; }

    public void setRoleID(int roleID)
    { this.roleID = roleID; }

    public void setName(String name)
    { this.name = name; }

    public void setUsername(String username)
    { this.username = username; }

    public void setPassword(String password) // is it a good idea to have this
    { this.password = password; }

    public void setDateJoined(String dateJoined)
    { this.dateJoined = dateJoined; }

    public void setPosition(String position)
    { this.position = position; }

    public void getSalary(double salary)
    { this.salary = salary; }

    public int getMaxSlots() 
    { return this.maxSlots; }

    public void setMaxSlots(int maxSlots) 
    { this.maxSlots = maxSlots; }

    /* ================================
     *  End Get Set Methods
     * ================================ */

    /* Method to create new user record in database
     * EmpID is automatically set by database
     */
    public boolean createUserRecord(User myUser)
    {
        boolean success = false; 
        
        // Prepare query
        String myQuery = "INSERT INTO EMPLOYEE (EMP_NAME, EMP_SALARY, EMP_DATEJOINED, "
                            + "EMP_ROLEID, EMP_POSITION, EMP_USERNAME, EMP_PASSWORD) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        try 
        {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cafems",
                "root", "Just@GroupProj3ctPW");
 
            // Prepare statement
            PreparedStatement myStatement = connection.prepareStatement(myQuery);

            // Set parameters for statement
            myStatement.setString(1, myUser.name);
            myStatement.setDouble(2, myUser.salary);
            myStatement.setString(3, myUser.dateJoined); // Possible swap to SQL Date?
            myStatement.setInt(4, myUser.roleID);
            if (myUser.position == "NULL")
            {
                myStatement.setNull(5, Types.VARCHAR);
            }
            else
            {
                myStatement.setString(5, myUser.position);
            }
            myStatement.setString(6, myUser.username);
            myStatement.setString(7, myUser.password);

            int i = myStatement.executeUpdate();
            if (i > 0) {
                System.out.println("EMPLOYEE RECORD INSERTED");
                success = true;
            } else {
                System.out.println("EMPLOYEE RECORD NOT INSERTED");
            }
            myStatement.close();
            connection.close();
        }
        catch (Exception exception) 
        {
            System.out.println(exception);
        }

        return success;
    }

    // Check against database if username is unique
    public static boolean isUniqueUsername(String username)
    {
        boolean uniqueUsername = false;

        // Prepare query
        String myQuery = "SELECT * FROM EMPLOYEE WHERE EMP_USERNAME = (?)";

        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cafems",
                "root", "Just@GroupProj3ctPW");
 
            // Prepare statement
            PreparedStatement myStatement = connection.prepareStatement(myQuery);

            // Set parameters for statement
            myStatement.setString(1, username);

            ResultSet resultSet;
            resultSet = myStatement.executeQuery();

            // if resultSet gets a record, username is not unique
            if (resultSet.next()) {
                uniqueUsername = false;
            } else {
                uniqueUsername = true;
            }

            myStatement.close();
            connection.close();
        }
        catch (Exception exception) 
        {
            System.out.println(exception);
        }

        return uniqueUsername;
    }

    // retrieve users' information from database and returns as array
    public ArrayList<User> empRecordArray()
    {
        ArrayList<User> arr = new ArrayList<User>();
        String myQuery = "SELECT * FROM EMPLOYEE";

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

            // Declare values for output
            int empID, roleID;
            String empName, dateJoined, position, username, password;
            double salary;

            while (resultSet.next()) {
                empID = resultSet.getInt("EMP_ID");
                empName = resultSet.getString("EMP_NAME").trim();
                salary = resultSet.getDouble("EMP_SALARY");
                dateJoined = resultSet.getString("EMP_DATEJOINED").trim();
                roleID = resultSet.getInt("EMP_ROLEID");
                position = resultSet.getString("EMP_POSITION");
                username = resultSet.getString("EMP_USERNAME").trim();
                password = resultSet.getString("EMP_PASSWORD").trim();
                User user = new User(empID, empName, salary, dateJoined, roleID, position, username, password);
                arr.add(user);
            }

            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }

        return arr;
    }

    // Login to user account
    public User loginUser(String username, String password)
    {
        // Prepare query
        User currentUser = new User();
        String myQuery = "SELECT * FROM EMPLOYEE WHERE EMP_USERNAME = (?) AND EMP_PASSWORD = (?)";

        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cafems",
                "root", "Just@GroupProj3ctPW");
 
            // Prepare statement
            PreparedStatement myStatement = connection.prepareStatement(myQuery);

            // Set parameters for statement
            myStatement.setString(1, username);
            myStatement.setString(2, password);

            ResultSet resultSet;
            resultSet = myStatement.executeQuery();

            // Set variables to current user
            if (resultSet.next()) {
                currentUser.empID = resultSet.getInt("EMP_ID");
                currentUser.name = resultSet.getString("EMP_NAME").trim();
                currentUser.salary = resultSet.getDouble("EMP_SALARY");
                currentUser.dateJoined = resultSet.getString("EMP_DATEJOINED").trim();
                currentUser.roleID = resultSet.getInt("EMP_ROLEID");
                currentUser.position = resultSet.getString("EMP_POSITION");
                currentUser.username = resultSet.getString("EMP_USERNAME").trim();
                currentUser.password = resultSet.getString("EMP_PASSWORD").trim();
            }

            resultSet.close();
            myStatement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }

        return (currentUser);
    }

    // Search and return a User's information based on id
    public User searchUserAccount(int id)
    {
        // Prepare query
        User currentUser = new User();
        String myQuery = "SELECT * FROM EMPLOYEE WHERE EMP_ID = (?)";

        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cafems",
                "root", "Just@GroupProj3ctPW");
 
            // Prepare statement
            PreparedStatement myStatement = connection.prepareStatement(myQuery);

            // Set parameters for statement
            myStatement.setInt(1, id);

            ResultSet resultSet;
            resultSet = myStatement.executeQuery();

            // Set variables to current user
            if (resultSet.next()) {
                currentUser.empID = resultSet.getInt("EMP_ID");
                currentUser.name = resultSet.getString("EMP_NAME").trim();
                currentUser.salary = resultSet.getDouble("EMP_SALARY");
                currentUser.dateJoined = resultSet.getString("EMP_DATEJOINED").trim();
                currentUser.roleID = resultSet.getInt("EMP_ROLEID");
                currentUser.position = resultSet.getString("EMP_POSITION");
                currentUser.username = resultSet.getString("EMP_USERNAME").trim();
                currentUser.password = resultSet.getString("EMP_PASSWORD").trim();
            }

            resultSet.close();
            myStatement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }

        return (currentUser);
    }

    public boolean updateUserRecord(User myUser)
    {
        boolean success = false; 
        
        // Prepare query
        String myQuery = "UPDATE EMPLOYEE SET EMP_NAME = (?), EMP_SALARY = (?), EMP_DATEJOINED = (?),"
                            + "EMP_ROLEID = (?), EMP_POSITION = (?), EMP_USERNAME = (?), EMP_PASSWORD = (?)"
                            + " WHERE EMP_ID = (?)";

        Connection connection = null;
        try 
        {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cafems",
                "root", "Just@GroupProj3ctPW");
 
            // Prepare statement
            PreparedStatement myStatement = connection.prepareStatement(myQuery);

            // Set parameters for statement
            myStatement.setString(1, myUser.name);
            myStatement.setDouble(2, myUser.salary);
            myStatement.setString(3, myUser.dateJoined); // Possible swap to SQL Date?
            myStatement.setInt(4, myUser.roleID);
            if (myUser.position == "NULL")
            {
                myStatement.setNull(5, Types.VARCHAR);
            }
            else
            {
                myStatement.setString(5, myUser.position);
            }
            myStatement.setString(6, myUser.username);
            myStatement.setString(7, myUser.password);
            myStatement.setInt(8, myUser.empID);

            int i = myStatement.executeUpdate();
            if (i >= 0) { // as no rows are added/deleted, i = 0
                System.out.println("EMPLOYEE RECORD UPDATED");
                success = true;
            } else {
                System.out.println("EMPLOYEE RECORD NOT UPDATED");
            }
            myStatement.close();
            connection.close();
        }
        catch (Exception exception) 
        {
            System.out.println(exception);
        }

        return success;
    }

    public boolean deleteUserRecord(int id) {
        boolean success = false;

        // Prepare query
        String myQuery = "DELETE FROM EMPLOYEE WHERE EMP_ID = (?)";

        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cafems",
                    "root", "Just@GroupProj3ctPW");

            // Prepare statement
            PreparedStatement myStatement = connection.prepareStatement(myQuery);

            // Set parameters for statement
            myStatement.setInt(1, id);

            int i = myStatement.executeUpdate();
            if (i > 0) {
                System.out.println("EMPLOYEE RECORD DELETED");
                success = true;
            } else {
                System.out.println("EMPLOYEE RECORD NOT DELETED");
            }
            myStatement.close();
            connection.close();
        }
        catch (Exception exception)
        {
            System.out.println(exception);
        }

        return success;
    }

    public User getUserById(int userId) {
        User user = new User();

        // Your SQL query to fetch the user by ID
        String query = "SELECT * FROM Employee WHERE EMP_ID = ?";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cafems",
                "root", "Just@GroupProj3ctPW")) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        user.empID = resultSet.getInt("EMP_ID");
                        user.name = resultSet.getString("EMP_NAME");
                        user.salary = resultSet.getInt("EMP_SALARY");
                        user.dateJoined = resultSet.getString("EMP_DATEJOINED");
                        user.roleID = resultSet.getInt("EMP_ROLEID");
                        user.position = resultSet.getString("EMP_POSITION");
                        user.username = resultSet.getString("EMP_USERNAME");
                        user.password = resultSet.getString("EMP_PASSWORD");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

        return user;
    }

    public boolean updateMaxSlots(int max_slots) {
        boolean success = false;
        this.maxSlots = max_slots;

        // Prepare query
        String myQuery = "UPDATE EMPLOYEE SET EMP_MAX_SLOTS = ? WHERE EMP_ID = ?";

        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cafems",
                    "root", "Just@GroupProj3ctPW");

            // Prepare statement
            PreparedStatement myStatement = connection.prepareStatement(myQuery);

            // Set parameters for statement
            myStatement.setInt(1, max_slots);
            myStatement.setInt(2, this.empID);

            int i = myStatement.executeUpdate();
            if (i > 0) {
                System.out.println("EMPLOYEE MAX SLOTS UPDATED");
                success = true;
            } else {
                System.out.println("EMPLOYEE MAX SLOTS NOT UPDATED");
            }

            myStatement.close();
            connection.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }

        return success;
    }

    public ArrayList<User> getEmployeesByRoleId(int roleId) {
        ArrayList<User> employees = new ArrayList<>();

        String query = "SELECT * FROM Employee WHERE EMP_ROLEID = ?";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cafems",
                "root", "Just@GroupProj3ctPW")) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, roleId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int employeeId = resultSet.getInt("EMP_ID");
                        String name = resultSet.getString("EMP_NAME");
                        int salary = resultSet.getInt("EMP_SALARY");
                        String dateJoined = resultSet.getString("EMP_DATEJOINED");
                        int roleID = resultSet.getInt("EMP_ROLEID");
                        String position = resultSet.getString("EMP_POSITION");
                        String password = resultSet.getString("EMP_PASSWORD");
                        String username = resultSet.getString("EMP_USERNAME");
                        // Add other properties as needed

                        User employee = new User(employeeId, name, salary, dateJoined, roleID, position, username, password);
                        employees.add(employee);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

        return employees;
    }
    // Basic toString method to display unique userID
    @Override
    public String toString()
    {
        return (String.valueOf(this.empID));
    }
}
