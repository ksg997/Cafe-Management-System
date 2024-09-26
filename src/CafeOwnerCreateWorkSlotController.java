public class CafeOwnerCreateWorkSlotController {

    public CafeOwnerCreateWorkSlotController() {
        
    }

    public boolean createWorkSlot(String date,int hours,String position, int assignedTo){
        WorkSlot newWorkSlot = new WorkSlot(date, hours, position, assignedTo);
        return newWorkSlot.createWorkSlot(date, hours, position, assignedTo);
    }
    
}
