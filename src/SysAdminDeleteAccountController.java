public class SysAdminDeleteAccountController {
    // Empty constructor
    public SysAdminDeleteAccountController() {}

    public boolean deleteUserRecord(int id)
    {
        User u = new User();
        boolean deleteAccount = u.deleteUserRecord(id);

        return deleteAccount;
    }
}
