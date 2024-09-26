public class CafeManagerGetWSController {
    public CafeManagerGetWSController() {}

    public WorkSlot getWorkSlotById(int slotID)
    {
        WorkSlot ws = new WorkSlot();
        ws = ws.getWorkSlotById(slotID);

        return ws;
    }
}
