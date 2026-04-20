// FILE MANAGER CLASS
// 4-305 [Castro, Rulida, Salcedo, Timbal]

package AppointmentSchedulingSystem;

import java.io.*;

public class FileManager {
    private String filename;

    public FileManager(String filename) {
        this.filename = filename;
    }

    public void saveToFile(Appointment[] list, int count) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("===== APPOINTMENT SCHEDULE =====");
            writer.println();
            for (int d = 0; d < 7; d++) {
                writer.println(Appointment.DAY_NAMES[d] + ":");
                boolean hasAny = false;
                for (int i = 0; i < count; i++) {
                    if (list[i] != null && list[i].getDayIndex() == d) {
                        writer.println("  - " + list[i].getName()
                            + " | " + list[i].getTime());
                        hasAny = true;
                    }
                }
                if (!hasAny) writer.println("  (no appointments)");
                writer.println();
            }
            System.out.println("File saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
