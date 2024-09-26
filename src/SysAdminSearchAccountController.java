// Controller class to handle account searching based on UID - Only for SA

public class SysAdminSearchAccountController {
    // Constructor
    public SysAdminSearchAccountController() {}

    public User searchUserAccount(int id)
    {
        User myUser = new User();
        myUser = myUser.searchUserAccount(id);

        return myUser;
    }

}
