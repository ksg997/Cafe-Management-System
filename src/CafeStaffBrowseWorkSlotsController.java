import java.util.ArrayList;

public class CafeStaffBrowseWorkSlotsController {
    public CafeStaffBrowseWorkSlotsController() {}

    public ArrayList<WorkSlot> getUnassignedWorkSlots(int staffID)
    {
        WorkSlot ws = new WorkSlot();
        ArrayList<WorkSlot> workSlots = new ArrayList<WorkSlot>();
        workSlots = ws.getUnassignedWorkSlots(staffID);
        return workSlots;
    }
}
