import java.util.ArrayList;

public class CafeManagerViewCSController {
    public CafeManagerViewCSController() {}

    public ArrayList<User> getEmployeesByRoleId(int id)
    {
        ArrayList<User> users = new ArrayList<User>();
        User myUser = new User();
        users = myUser.getEmployeesByRoleId(id);
        return users;
    }
}
