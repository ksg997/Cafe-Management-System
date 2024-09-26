import java.util.ArrayList;

public class CafeStaffSearchWorkSlotController {
    public CafeStaffSearchWorkSlotController() {}

    ArrayList<WorkSlot> searchUnassignedWorkSlots(String userInput)
    {
        ArrayList<WorkSlot> ws = new ArrayList<WorkSlot>();
        WorkSlot myWorkSlot = new WorkSlot();
        ws = myWorkSlot.searchUnassignedWorkSlots(userInput);

        return ws;
    }
}
