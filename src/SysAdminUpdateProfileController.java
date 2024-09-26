// Controller class to update a profile object
public class SysAdminUpdateProfileController {
    // Empty constructor
    public SysAdminUpdateProfileController() {}

    public boolean updateProfileRecord(Profile newProfile){
        boolean updateProfile = newProfile.updateProfileRecord(newProfile);

        return updateProfile;
    }
}
