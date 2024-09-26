public class CafeManagerDeleteBidController {
    public CafeManagerDeleteBidController() {}

    public boolean deleteBid(Bid aBid)
    {
        Bid bid = new Bid();
        return bid.deleteBid(aBid);
    }
}
