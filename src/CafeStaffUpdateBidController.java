public class CafeStaffUpdateBidController {
    public CafeStaffUpdateBidController() {}

    boolean updateBid(Bid aBid)
    {
        Bid someBid = new Bid();
        boolean result = someBid.updateBid(aBid);
        return result;
    }
}
