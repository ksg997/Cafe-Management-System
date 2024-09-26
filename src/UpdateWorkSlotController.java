public class UpdateWorkSlotController {
    public UpdateWorkSlotController() {}

    public boolean editWorkSlot(WorkSlot ws)
    {
        WorkSlot workSlot = new WorkSlot();
        return workSlot.editWorkSlot(ws);
    }
    
}
