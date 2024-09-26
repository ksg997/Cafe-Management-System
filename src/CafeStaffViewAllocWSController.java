import java.util.ArrayList;

public class CafeStaffViewAllocWSController {
    public CafeStaffViewAllocWSController() {}

    public ArrayList<WorkSlot> getWorkSlotsByAssignedEmployeeId(int input)
    {
        WorkSlot ws = new WorkSlot();
        ArrayList<WorkSlot> aws = new ArrayList<WorkSlot>();
        aws = ws.getWorkSlotsByAssignedEmployeeId(input);

        return aws;
    }
}
