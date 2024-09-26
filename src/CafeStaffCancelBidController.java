public class CafeStaffCancelBidController {
    public CafeStaffCancelBidController() {}

    boolean deleteBid(Bid aBid)
    {
        Bid someBid = new Bid();
        boolean result = someBid.deleteBid(aBid);
        return result;
    }
}
