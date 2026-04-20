// APPOINTMENT SCHEDULER CLASS
// 4-305 [Castro, Rulida, Salcedo, Timbal]

package AppointmentSchedulingSystem;

public class AppointmentScheduler {

    // 2D array: [dayIndex 0-6][slotIndex 0-3]
    // Each cell holds a comma-separated string of names (supports double booking)
    private String[][] schedule;

    // Flat list for iteration (sort, conflicts, save)
    private Appointment[] appointments;
    private int count;
    private static final int MAX_SIZE = 200;

    public AppointmentScheduler() {
        schedule     = new String[7][4];
        appointments = new Appointment[MAX_SIZE];
        count        = 0;
    }

    /** Add an appointment. Allows double booking (multiple names per slot) so that CHECK CONFLICTS can be used. 
     *  Double booking warns user before proceeding.. */
    public void addAppointment(String name, int dayIndex, int slotIndex) {
        if (count >= MAX_SIZE) return;
        if (schedule[dayIndex][slotIndex] == null) {
            schedule[dayIndex][slotIndex] = name;
        } else {
            schedule[dayIndex][slotIndex] += ", " + name;
        }
        appointments[count++] = new Appointment(name, dayIndex, slotIndex);
    }

    /** Returns true if the slot already has at least one booking. */
    public boolean isBooked(int dayIndex, int slotIndex) {
        return schedule[dayIndex][slotIndex] != null;
    }

    /** Returns the name(s) in a slot, or null if empty. */
    public String getSlotNames(int dayIndex, int slotIndex) {
        return schedule[dayIndex][slotIndex];
    }

    /**
     * Cancel ALL appointments in a slot (clears the cell and removes
     * matching entries from the flat list).
     */
    public void cancelSlot(int dayIndex, int slotIndex) {
        schedule[dayIndex][slotIndex] = null;
        // Remove all matching entries from flat array using array shift
        int i = 0;
        while (i < count) {
            if (appointments[i].getDayIndex()  == dayIndex &&
                appointments[i].getSlotIndex() == slotIndex) {
                for (int j = i; j < count - 1; j++) {
                    appointments[j] = appointments[j + 1];
                }
                appointments[count - 1] = null;
                count--;
            } else {
                i++;
            }
        }
    }

    /**
     * Check conflicts: find any slot that has more than one booking
     * (same day + same time slot = conflict by definition of double booking).
     */
    public String getConflictReport() {
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for (int d = 0; d < 7; d++) {
            for (int s = 0; s < 4; s++) {
                String cell = schedule[d][s];
                if (cell != null && cell.contains(",")) {
                    sb.append("CONFLICT on ")
                      .append(Appointment.DAY_NAMES[d])
                      .append(" [")
                      .append(Appointment.SLOT_LABELS[s])
                      .append("]:\n");
                    String[] names = cell.split(",");
                    for (String n : names) {
                        sb.append("    - ").append(n.trim()).append("\n");
                    }
                    sb.append("\n");
                    found = true;
                }
            }
        }
        return found ? sb.toString() : "No conflicts found.";
    }

    /**
     * Sort appointments chronologically (by dayIndex then slotIndex)
     * using bubble sort (nested loops — fulfills academic requirement).
     */
    public void sortAppointments() {
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - 1 - i; j++) {
                int key1 = appointments[j].getDayIndex()     * 10 + appointments[j].getSlotIndex();
                int key2 = appointments[j+1].getDayIndex()   * 10 + appointments[j+1].getSlotIndex();
                if (key1 > key2) {
                    Appointment tmp  = appointments[j];
                    appointments[j]  = appointments[j + 1];
                    appointments[j + 1] = tmp;
                }
            }
        }
    }

    /**
     * Returns a formatted sorted schedule string grouped by day.
     * Calls sortAppointments() internally.
     */
    public String getSortedReport() {
        sortAppointments();
        StringBuilder sb = new StringBuilder();
        for (int d = 0; d < 7; d++) {
            sb.append(Appointment.DAY_NAMES[d]).append(":\n");
            boolean hasAny = false;
            for (int i = 0; i < count; i++) {
                if (appointments[i].getDayIndex() == d) {
                    sb.append("  * ")
                      .append(appointments[i].getName())
                      .append(" - ")
                      .append(appointments[i].getTime())
                      .append("\n");
                    hasAny = true;
                }
            }
            if (!hasAny) sb.append("  (no appointments)\n");
            sb.append("\n");
        }
        return sb.toString();
    }

    public String[][]    getSchedule()     { return schedule; }
    public Appointment[] getAppointments() { return appointments; }
    public int           getCount()        { return count; }
}
