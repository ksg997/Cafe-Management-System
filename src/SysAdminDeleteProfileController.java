// controller class to delete profile
public class SysAdminDeleteProfileController {
    public boolean deleteProfileRecord(int id){
        Profile newProfile = new Profile();
        boolean deleteProfile = newProfile.deleteProfileRecord(id);

        return deleteProfile;
    }
}
