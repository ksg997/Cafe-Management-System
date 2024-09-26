public class CafeManagerApproveBidController {
    public CafeManagerApproveBidController() {}

    public boolean approveBid(Bid bid)
    {
        Bid aBid = new Bid();
        return aBid.approveBid(bid);
    }
}
