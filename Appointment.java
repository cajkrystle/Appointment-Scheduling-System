// APPOINTMENT CLASS
// 4-305 [Castro, Rulida, Salcedo, Timbal]

package AppointmentSchedulingSystem;

public class Appointment {
    private String name;
    private int    dayIndex;   // 0=Sunday ... 6=Saturday
    private int    slotIndex;  // 0=7-9AM, 1=9AM-12PM, 2=1-3PM, 3=3-5PM 

    public static final String[] DAY_NAMES = {
        "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };
    public static final String[] SLOT_LABELS = {
        "7:00 AM - 9:00 AM",
        "9:00 AM - 12:00 PM",
        "1:00 PM - 3:00 PM",
        "3:00 PM - 5:00 PM"
    };

    public Appointment(String name, int dayIndex, int slotIndex) {
        this.name      = name;
        this.dayIndex  = dayIndex;
        this.slotIndex = slotIndex;
    }

    public String getName()      { return name; }
    public int    getDayIndex()  { return dayIndex; }
    public int    getSlotIndex() { return slotIndex; }
    public String getDate()      { return DAY_NAMES[dayIndex]; }
    public String getTime()      { return SLOT_LABELS[slotIndex]; }

    @Override
    public String toString() {
        return String.format("%-20s | %-12s | %s", name, getDate(), getTime());
    }
}
