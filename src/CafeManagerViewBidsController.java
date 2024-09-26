import java.util.ArrayList;

public class CafeManagerViewBidsController {
    public CafeManagerViewBidsController() {}

    public ArrayList<Bid> getAllBids()
    {
        Bid aBid = new Bid();
        ArrayList<Bid> bids = new ArrayList<Bid>();

        bids = aBid.getAllBids();
        return bids;
    }
}
