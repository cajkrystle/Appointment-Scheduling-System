// GUI CLASS
// 4-305 [Castro, Rulida, Salcedo, Timbal]

package AppointmentSchedulingSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class AppointmentSchedulerGUI {

    // ── Color Palette / Theme ───────────────────────────────────────────────────────────────
    private static final Color BG_TOP         = new Color(8,   20,  70);
    private static final Color BG_BOT         = new Color(4,   10,  38);
    private static final Color SLOT_FREE      = new Color(30,  80, 180);   // lighter blue = available
    private static final Color SLOT_FREE_HOV  = new Color(50, 110, 220);
    private static final Color SLOT_BOOKED    = new Color(10,  35,  90);   // darker blue = booked
    private static final Color SLOT_BOOKED_HOV= new Color(20,  55, 120);
    private static final Color ACCENT         = new Color(59, 130, 246);
    private static final Color ACCENT_HOVER   = new Color(96, 165, 250);
    private static final Color ACCENT_DARK    = new Color(19,  90, 206);
    private static final Color HEADER_BG      = new Color(6,   16,  55);
    private static final Color SUCCESS_GRN    = new Color(34, 139, 60);
    private static final Color ERROR_RED      = new Color(180, 30,  30);
    private static final Color ERROR_HOVER    = new Color(220, 60,  60);
    private static final Color TEXT_WHITE     = Color.WHITE;
    private static final Color TEXT_SOFT      = new Color(160, 190, 255);
    private static final Color TEXT_MUTED     = new Color(100, 130, 200);

    // ── Core state ────────────────────────────────────────────────────────────
    private final JFrame               frame;
    private final AppointmentScheduler scheduler = new AppointmentScheduler();

    // ─────────────────────────────────────────────────────────────────────────
    public AppointmentSchedulerGUI() {
        frame = new JFrame("AppointMate");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(980, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        showMainMenu();
        frame.setVisible(true);
    }

    // =========================================================================
    //                                SCREENS
    // =========================================================================

    /* Main Menu */
    private void showMainMenu() {
        JPanel bg = makeBg();
        bg.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(36, 0, 20, 0));

        JLabel title = centeredLabel("AppointMate", new Font("Georgia", Font.BOLD, 36), TEXT_WHITE);
        JLabel sub   = centeredLabel("Appointment Scheduling System  |  Select an option below", new Font("Georgia", Font.ITALIC, 15), TEXT_SOFT);
        sub.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
        header.add(title);
        header.add(sub);

        String[] labels = {
            "1.  Add Appointment",    "2.  View Appointments",
            "3.  Check Conflicts",    "4.  Cancel Appointment",
            "5.  Sort Appointments",  "6.  Save to File",
            "7.  Exit",               ""
        };
        Runnable[] actions = {
            () -> showScheduleScreen(Mode.ADD),
            () -> showScheduleScreen(Mode.VIEW),
            this::showConflictScreen,
            () -> showScheduleScreen(Mode.CANCEL),
            this::showSortScreen,
            this::showSaveScreen,
            this::confirmExit,
            null
        };

        JPanel grid = new JPanel(new GridLayout(4, 2, 20, 14));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(0, 80, 50, 80));

        for (int i = 0; i < labels.length; i++) {
            if (labels[i].isEmpty()) {
                grid.add(new JLabel());
            } else {
                final Runnable act = actions[i];
                JButton btn = makeMenuBtn(labels[i]);
                btn.addActionListener(e -> act.run());
                grid.add(btn);
            }
        }

        bg.add(header, BorderLayout.NORTH);
        bg.add(grid,   BorderLayout.CENTER);
        setScreen(bg);
    }

    // ─────────────────────────────────────────────────────────────────────────

    /* Add / View / Cancel — all share the schedule table */
    private enum Mode { ADD, VIEW, CANCEL }

    /**
     * Shared schedule-table screen.
     * ADD    — click free slot → name dialog → tapos i save sa txt file
     *        — click booked slot → confirm double-book
     * VIEW   — read-only, shows names in cells
     * CANCEL — click booked slot → confirm remove
     */
    private void showScheduleScreen(Mode mode) {
        JPanel bg = makeBg();
        bg.setLayout(new BorderLayout(0, 0));

        String titleText = mode == Mode.ADD    ? "Add Appointment"
                         : mode == Mode.VIEW   ? "View Appointments"
                                               : "Cancel Appointment";
        bg.add(makeTopBar(titleText), BorderLayout.NORTH);

        // ── Schedule table ────────────────────────────────────────────────────
        JPanel tableWrap = new JPanel(new GridBagLayout());
        tableWrap.setOpaque(false);
        tableWrap.setBorder(BorderFactory.createEmptyBorder(10, 24, 20, 24));

        JPanel table = buildScheduleTable(mode);
        tableWrap.add(table);
        bg.add(tableWrap, BorderLayout.CENTER);

        // ── Legend (ADD/CANCEL only) ──────────────────────────────────────────
        if (mode != Mode.VIEW) {
            JPanel legend = new JPanel(new FlowLayout(FlowLayout.CENTER, 28, 6));
            legend.setOpaque(false);
            legend.add(legendDot(SLOT_FREE,   "Available"));
            legend.add(legendDot(SLOT_BOOKED, "Booked"));
            bg.add(legend, BorderLayout.SOUTH);
        }

        setScreen(bg);
    }

    /** Build the 4-row × 7-col interactive schedule grid */
    private JPanel buildScheduleTable(Mode mode) {
        int ROWS = 4, COLS = 7;

        JPanel grid = new JPanel(new GridLayout(ROWS + 1, COLS + 1, 3, 3));
        grid.setOpaque(false);

        // Corner cell
        grid.add(makeHeaderCell(""));

        // Day headers
        for (String day : Appointment.DAY_NAMES) {
            grid.add(makeHeaderCell(day));
        }

        // Slot rows
        for (int s = 0; s < ROWS; s++) {
            // Row label
            grid.add(makeSlotLabelCell(Appointment.SLOT_LABELS[s]));

            // Day cells
            for (int d = 0; d < COLS; d++) {
                grid.add(makeSlotCell(d, s, mode));
            }
        }

        // Size the whole grid nicely
        int cellW = 112, cellH = 82;
        grid.setPreferredSize(new Dimension(
            (COLS + 1) * cellW + COLS * 3,
            (ROWS + 1) * cellH + ROWS * 3
        ));
        return grid;
    }

    /** One interactive slot cell */
    private JPanel makeSlotCell(int dayIdx, int slotIdx, Mode mode) {
        boolean booked = scheduler.isBooked(dayIdx, slotIdx);
        String  names  = scheduler.getSlotNames(dayIdx, slotIdx);

        Color baseColor = booked ? SLOT_BOOKED : SLOT_FREE;
        Color hovColor  = booked ? SLOT_BOOKED_HOV : SLOT_FREE_HOV;

        JPanel cell = new JPanel(new BorderLayout()) {
            private boolean hov = false;
            {
                if (mode != Mode.VIEW) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    addMouseListener(new MouseAdapter() {
                        public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                        public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                        public void mouseClicked(MouseEvent e) { handleSlotClick(dayIdx, slotIdx, mode); }
                    });
                }
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? hovColor : baseColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                // subtle inner border
                g2.setColor(new Color(255, 255, 255, 25));
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 10, 10);
                g2.dispose();
            }
        };
        cell.setOpaque(false);
        cell.setPreferredSize(new Dimension(112, 82));

        if (mode == Mode.VIEW && names != null) {
            // Show name(s) inside cell
            JLabel nameLbl = new JLabel("<html><center>" + names.replace(", ", "<br>") + "</center></html>", JLabel.CENTER);
            nameLbl.setFont(new Font("Georgia", Font.BOLD, 11));
            nameLbl.setForeground(TEXT_WHITE);
            nameLbl.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
            cell.add(nameLbl, BorderLayout.CENTER);
        } else if (booked && mode != Mode.VIEW) {
            JLabel dot = new JLabel("●", JLabel.CENTER);
            dot.setFont(new Font("Georgia", Font.PLAIN, 18));
            dot.setForeground(new Color(255, 255, 255, 120));
            cell.add(dot, BorderLayout.CENTER);
        }

        return cell;
    }

    /** Handles a click on a slot cell */
    private void handleSlotClick(int dayIdx, int slotIdx, Mode mode) {
        boolean booked = scheduler.isBooked(dayIdx, slotIdx);
        String dayName  = Appointment.DAY_NAMES[dayIdx];
        String slotName = Appointment.SLOT_LABELS[slotIdx];

        if (mode == Mode.ADD) {
            if (booked) {
                // Already booked — confirm double booking
                String existing = scheduler.getSlotNames(dayIdx, slotIdx);
                int choice = JOptionPane.showConfirmDialog(
                    frame,
                    "<html><b>" + dayName + " | " + slotName + "</b><br><br>"
                    + "This slot is already booked by: <b>" + existing + "</b><br><br>"
                    + "Do you still want to add another appointment here?</html>",
                    "Slot Already Booked",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                if (choice != JOptionPane.YES_OPTION) return;
            }
            // Ask for name
            showNameDialog(dayIdx, slotIdx, dayName, slotName);

        } else if (mode == Mode.CANCEL) {
            if (!booked) {
                JOptionPane.showMessageDialog(frame,
                    "<html>This slot is already empty.</html>",
                    "Nothing to Cancel", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String existing = scheduler.getSlotNames(dayIdx, slotIdx);
            int choice = JOptionPane.showConfirmDialog(
                frame,
                "<html><b>" + dayName + " | " + slotName + "</b><br><br>"
                + "Booked by: <b>" + existing + "</b><br><br>"
                + "Remove this appointment?</html>",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (choice == JOptionPane.YES_OPTION) {
                scheduler.cancelSlot(dayIdx, slotIdx);
                showScheduleScreen(Mode.CANCEL); // refresh
            }
        }
    }

    /** Name-entry dialog for ADD mode */
    private void showNameDialog(int dayIdx, int slotIdx, String dayName, String slotName) {
        // Custom styled dialog panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(10, 25, 80));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        JLabel info = new JLabel("<html><b>" + dayName + " | " + slotName + "</b></html>");
        info.setFont(new Font("Georgia", Font.PLAIN, 14));
        info.setForeground(TEXT_SOFT);
        info.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel("Enter name:");
        lbl.setFont(new Font("Georgia", Font.BOLD, 15));
        lbl.setForeground(TEXT_WHITE);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(12, 0, 6, 0));

        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Georgia", Font.PLAIN, 15));
        nameField.setBackground(new Color(20, 50, 130));
        nameField.setForeground(TEXT_WHITE);
        nameField.setCaretColor(TEXT_WHITE);
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(59, 130, 246), 1, true),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(info);
        panel.add(lbl);
        panel.add(nameField);

        UIManager.put("OptionPane.background",          new Color(10, 25, 80));
        UIManager.put("Panel.background",               new Color(10, 25, 80));
        UIManager.put("OptionPane.messageForeground",   TEXT_WHITE);
        UIManager.put("Button.background",              ACCENT);
        UIManager.put("Button.foreground",              TEXT_WHITE);

        int result = JOptionPane.showOptionDialog(
            frame, panel,
            "Add Appointment",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            new Object[]{"Save", "Cancel"},
            "Save"
        );

        if (result == 0) {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            scheduler.addAppointment(name, dayIdx, slotIdx);
            showScheduleScreen(Mode.ADD); // refresh table
        }
    }

    // ─────────────────────────────────────────────────────────────────────────

    /** Check Conflicts — text only */
    private void showConflictScreen() {
        JPanel bg = makeBg();
        bg.setLayout(new BorderLayout());
        bg.add(makeTopBar("Check Conflicts"), BorderLayout.NORTH);

        JPanel card = makeFrostedCard(740, 400);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 28, 16, 28));

        JTextArea output = makeOutputArea();
        output.setText(scheduler.getConflictReport());
        card.add(styledScroll(output), BorderLayout.CENTER);

        centreCard(bg, card);
        setScreen(bg);
    }

    // ─────────────────────────────────────────────────────────────────────────

    /** Sort Appointments — chronological text */
    private void showSortScreen() {
        JPanel bg = makeBg();
        bg.setLayout(new BorderLayout());
        bg.add(makeTopBar("Sort Appointments"), BorderLayout.NORTH);

        JPanel card = makeFrostedCard(740, 400);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 28, 16, 28));

        JTextArea output = makeOutputArea();
        output.setText(scheduler.getCount() == 0
            ? "No appointments to sort."
            : scheduler.getSortedReport());
        card.add(styledScroll(output), BorderLayout.CENTER);

        centreCard(bg, card);
        setScreen(bg);
    }

    // ─────────────────────────────────────────────────────────────────────────

    /** Save to File */
    private void showSaveScreen() {
        JPanel bg = makeBg();
        bg.setLayout(new BorderLayout());
        bg.add(makeTopBar("Save to File"), BorderLayout.NORTH);

        JPanel card = makeFrostedCard(480, 280);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(32, 48, 32, 48));

        JLabel hint = new JLabel("Enter filename to save the schedule:");
        hint.setFont(new Font("Georgia", Font.PLAIN, 15));
        hint.setForeground(TEXT_SOFT);
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField fileField = makeField("e.g. schedule.txt");
        JLabel     status    = makeStatusLabel();

        JButton saveBtn = makeRoundBtn("Save",   ACCENT,      ACCENT_HOVER);
        JButton backBtn = makeRoundBtn("← Back", ACCENT_DARK, ACCENT);

        saveBtn.addActionListener(e -> {
            String filename = fileField.getText().trim();
            if (filename.isEmpty() || filename.equals("e.g. schedule.txt")) {
                setStatus(status, "Please enter a filename.", false);
                return;
            }
            try {
                FileManager fm = new FileManager(filename);
                fm.saveToFile(scheduler.getAppointments(), scheduler.getCount());
                setStatus(status,
                    "Saved " + scheduler.getCount() + " appointment(s) to \"" + filename + "\"",
                    true);
            } catch (Exception ex) {
                setStatus(status, "Error: " + ex.getMessage(), false);
            }
        });

        backBtn.addActionListener(e -> showMainMenu());

        card.add(hint);
        card.add(Box.createRigidArea(new Dimension(0, 14)));
        card.add(fileField);
        card.add(Box.createRigidArea(new Dimension(0, 24)));
        card.add(hBox(saveBtn, backBtn));
        card.add(Box.createRigidArea(new Dimension(0, 14)));
        card.add(status);

        centreCard(bg, card);
        setScreen(bg);
    }

    // ─────────────────────────────────────────────────────────────────────────

    /** Exit confirmation */
    private void confirmExit() {
        JPanel bg = makeBg();
        bg.setLayout(new GridBagLayout());

        JPanel card = makeFrostedCard(360, 220);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(36, 48, 36, 48));

        JLabel msg = new JLabel(
            "<html><center>Are you sure you<br>want to exit?</center></html>",
            JLabel.CENTER);
        msg.setFont(new Font("Georgia", Font.BOLD, 22));
        msg.setForeground(TEXT_WHITE);
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton yesBtn = makeRoundBtn("Exit",   ERROR_RED,   ERROR_HOVER);
        JButton noBtn  = makeRoundBtn("Cancel", ACCENT_DARK, ACCENT);
        yesBtn.addActionListener(e -> System.exit(0));
        noBtn .addActionListener(e -> showMainMenu());

        card.add(msg);
        card.add(Box.createRigidArea(new Dimension(0, 28)));
        card.add(hBox(yesBtn, noBtn));

        bg.add(card);
        setScreen(bg);
    }

    // =========================================================================
    // TOP BAR  (all inner screens)
    // =========================================================================

    /**
     * Top bar: "Title nato sa left corner banda,
     * screen title in the centre, "MENU" button on the right.
     */
    private JPanel makeTopBar(String screenTitle) {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);
        bar.setBorder(BorderFactory.createEmptyBorder(18, 22, 8, 22));

        JLabel appName = new JLabel("AppointMate");
        appName.setFont(new Font("Georgia", Font.BOLD, 14));
        appName.setForeground(TEXT_MUTED);

        JLabel titleLbl = new JLabel(screenTitle, JLabel.CENTER);
        titleLbl.setFont(new Font("Georgia", Font.BOLD, 26));
        titleLbl.setForeground(TEXT_WHITE);

        JButton menuBtn = makeRoundBtn("MENU", ACCENT_DARK, ACCENT);
        menuBtn.setPreferredSize(new Dimension(100, 36));
        menuBtn.setMaximumSize  (new Dimension(100, 36));
        menuBtn.addActionListener(e -> showMainMenu());

        bar.add(appName,  BorderLayout.WEST);
        bar.add(titleLbl, BorderLayout.CENTER);
        bar.add(menuBtn,  BorderLayout.EAST);
        return bar;
    }

    // =========================================================================
    // CELL FACTORIES
    // =========================================================================

    private JPanel makeHeaderCell(String text) {
        JPanel cell = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(HEADER_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
            }
        };
        cell.setOpaque(false);
        cell.setPreferredSize(new Dimension(text.isEmpty() ? 96 : 112, 36));
        if (!text.isEmpty()) {
            JLabel lbl = new JLabel(text, JLabel.CENTER);
            lbl.setFont(new Font("Georgia", Font.BOLD, 12));
            lbl.setForeground(TEXT_SOFT);
            cell.add(lbl, BorderLayout.CENTER);
        }
        return cell;
    }

    private JPanel makeSlotLabelCell(String text) {
        JPanel cell = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(HEADER_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
            }
        };
        cell.setOpaque(false);
        cell.setPreferredSize(new Dimension(96, 82));
        JLabel lbl = new JLabel("<html><center>" + text.replace(" - ", "<br>") + "</center></html>", JLabel.CENTER);
        lbl.setFont(new Font("Georgia", Font.PLAIN, 10));
        lbl.setForeground(TEXT_SOFT);
        lbl.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        cell.add(lbl, BorderLayout.CENTER);
        return cell;
    }

    private JPanel legendDot(Color color, String label) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        p.setOpaque(false);
        JPanel dot = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.dispose();
            }
        };
        dot.setPreferredSize(new Dimension(18, 18));
        dot.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Georgia", Font.PLAIN, 13));
        lbl.setForeground(TEXT_SOFT);
        p.add(dot);
        p.add(lbl);
        return p;
    }

    // =========================================================================
    // COMPONENT FACTORIES
    // =========================================================================

    private JPanel makeBg() {
        return new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, BG_TOP, getWidth(), getHeight(), BG_BOT));
                g2.fillRect(0, 0, getWidth(), getHeight());
                int cx = getWidth() / 2, cy = getHeight() / 2;
                float r = Math.max(getWidth(), getHeight()) * 0.6f;
                g2.setPaint(new RadialGradientPaint(cx, cy, r,
                    new float[]{0f, 0.5f, 1f},
                    new Color[]{
                        new Color(59, 130, 246, 45),
                        new Color(10,  25,  80, 20),
                        new Color(4,   10,  38,  0)
                    }));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
    }

    private JPanel makeFrostedCard(int w, int h) {
        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 20));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);
                g2.setColor(new Color(100, 160, 255, 50));
                g2.setStroke(new BasicStroke(1.3f));
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 22, 22);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(w, h));
        return card;
    }

    private JButton makeRoundBtn(String text, Color base, Color hover) {
        JButton btn = new JButton(text) {
            private boolean hov = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? hover : base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);
                g2.setColor(new Color(255,255,255,35));
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 22, 22);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Georgia", Font.BOLD, 14));
        btn.setForeground(TEXT_WHITE);
        btn.setPreferredSize(new Dimension(170, 42));
        btn.setMaximumSize  (new Dimension(170, 42));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton makeMenuBtn(String text) {
        JButton btn = new JButton(text) {
            private boolean hov = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? new Color(59,130,246,200) : new Color(255,255,255,20));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(new Color(100,160,255,65));
                g2.setStroke(new BasicStroke(1.3f));
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Georgia", Font.BOLD, 16));
        btn.setForeground(TEXT_WHITE);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 22, 0, 0));
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JTextField makeField(String placeholder) {
        JTextField tf = new JTextField(placeholder) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255,255,255,16));
                g2.fillRoundRect(0,0,getWidth(),getHeight(),12,12);
                g2.setColor(new Color(100,160,255,65));
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,12,12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        tf.setForeground(TEXT_SOFT);
        tf.setFont(new Font("Georgia", Font.PLAIN, 15));
        tf.setOpaque(false);
        tf.setBorder(BorderFactory.createEmptyBorder(8,14,8,14));
        tf.setCaretColor(TEXT_WHITE);
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
        tf.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (tf.getText().equals(placeholder)) { tf.setText(""); tf.setForeground(TEXT_WHITE); }
            }
            public void focusLost(FocusEvent e) {
                if (tf.getText().isEmpty()) { tf.setText(placeholder); tf.setForeground(TEXT_SOFT); }
            }
        });
        return tf;
    }

    private JTextArea makeOutputArea() {
        JTextArea ta = new JTextArea();
        ta.setFont(new Font("Monospaced", Font.PLAIN, 14));
        ta.setForeground(TEXT_WHITE);
        ta.setBackground(new Color(4, 10, 38, 210));
        ta.setOpaque(true);
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        return ta;
    }

    private JScrollPane styledScroll(java.awt.Component view) {
        JScrollPane sp = new JScrollPane(view);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setBorder(BorderFactory.createEmptyBorder());
        return sp;
    }

    private JLabel makeStatusLabel() {
        JLabel lbl = new JLabel(" ");
        lbl.setFont(new Font("Georgia", Font.ITALIC, 14));
        lbl.setForeground(TEXT_SOFT);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private void setStatus(JLabel lbl, String text, boolean ok) {
        lbl.setText(text);
        lbl.setForeground(ok ? SUCCESS_GRN : ERROR_RED);
    }

    private JLabel centeredLabel(String text, Font font, Color color) {
        JLabel lbl = new JLabel(text, JLabel.CENTER);
        lbl.setFont(font);
        lbl.setForeground(color);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    private JPanel hBox(JButton... btns) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        for (JButton b : btns) p.add(b);
        return p;
    }

    private void centreCard(JPanel bg, JPanel card) {
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.add(card);
        bg.add(center, BorderLayout.CENTER);
    }

    private void setScreen(JPanel bg) {
        frame.setContentPane(bg);
        frame.revalidate();
        frame.repaint();
    }
}
