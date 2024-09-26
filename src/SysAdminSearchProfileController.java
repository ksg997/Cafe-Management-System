// Controller class for searching user profile
public class SysAdminSearchProfileController {
    // Empty Constructor
    public SysAdminSearchProfileController() {}

    public Profile searchProfile(String name)
    {
        Profile myProfile = new Profile();
        myProfile = myProfile.searchProfile(name);

        return myProfile;
    }
}
