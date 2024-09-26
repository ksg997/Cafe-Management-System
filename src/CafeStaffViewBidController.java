import java.util.ArrayList;

public class CafeStaffViewBidController {
    public CafeStaffViewBidController() {}

    public ArrayList<Bid> getBidsForEmployee(int empID)
    {
        ArrayList<Bid> myBids = new ArrayList<Bid>();
        Bid myBid = new Bid();
        myBids = myBid.getBidsForEmployee(empID);
        return myBids;
    }

}
