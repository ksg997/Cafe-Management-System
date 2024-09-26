import java.util.ArrayList;

public class CafeManagerViewWorkSlotsController {
    public CafeManagerViewWorkSlotsController() {}

    public ArrayList<WorkSlot> getAllWorkSlots()
    {
        WorkSlot ws = new WorkSlot();
        ArrayList<WorkSlot> workSlots = new ArrayList<WorkSlot>();
        workSlots = ws.getAllWorkSlots();

        return workSlots;
    }
    
}
