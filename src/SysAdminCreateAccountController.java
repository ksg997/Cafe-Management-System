/* Controller class for createUserRecord
 * Method only available for System Admin role
 * Used to check business rules for user database
 */

final class SysAdminCreateAccountController {

    // Empty constructor
    public SysAdminCreateAccountController() {}

    /* Method to check variables to create User account
     * True = Account created || False = Account not created
    */
    public boolean createUserRecord(User newUser)
    {
        boolean createAccount = newUser.createUserRecord(newUser);

        return createAccount;
    }
}

