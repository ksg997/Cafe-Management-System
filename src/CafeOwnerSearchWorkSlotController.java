import java.util.ArrayList;

public class CafeOwnerSearchWorkSlotController {

    public CafeOwnerSearchWorkSlotController() {
        
    }

    public ArrayList <WorkSlot> searchWorkSlots(String userinput){
        WorkSlot ws = new WorkSlot();
        return ws.searchWorkSlots(userinput);
    }
    
}
