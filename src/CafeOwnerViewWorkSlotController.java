import java.util.ArrayList;

public class CafeOwnerViewWorkSlotController {
    
    public CafeOwnerViewWorkSlotController(){

    }
    public ArrayList <WorkSlot> getWorkSlots() {
        WorkSlot w = new WorkSlot(null, 0, null, null);
        ArrayList<WorkSlot> workSlots = w.getAllWorkSlots();
        return workSlots;
    }
}
