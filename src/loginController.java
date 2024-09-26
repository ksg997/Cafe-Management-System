/* Controller class for login
 * Method available for all users
 */

final class loginController {

    // Empty constructor
    public loginController() {}

    // Log in user by retrieving user details from database
    public User loginUser(String username, String password)
    {
        User currentUser = new User();

        currentUser = currentUser.loginUser(username, password);

        return currentUser;
    }
}
