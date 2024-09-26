public class SysAdminUpdateAccountController {
      // Empty constructor
    public SysAdminUpdateAccountController() {}

    public boolean updateUserRecord(User newUser)
    {
        boolean updateAccount = newUser.updateUserRecord(newUser);

        return updateAccount;
    }
}
