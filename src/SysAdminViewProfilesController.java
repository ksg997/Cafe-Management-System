// Controller class to view list of profiles
import java.util.ArrayList;

final class SysAdminViewProfilesController {
    public SysAdminViewProfilesController(){}

    public ArrayList<Profile> getProfileArrayList(){
        Profile myProfile = new Profile();
        return myProfile.getProfileArrayList();
    }
}