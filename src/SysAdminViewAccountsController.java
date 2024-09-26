import java.util.ArrayList;

final class SysAdminViewAccountsController {
    public SysAdminViewAccountsController(){}

    public ArrayList<User> displayAll(){
        User aUser = new User();
        return aUser.empRecordArray();
    }
}
