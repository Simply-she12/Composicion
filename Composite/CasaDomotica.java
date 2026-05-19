import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// ── Interfaz Componente ──────────────────────────────────────
interface IDispositivo {
    void encender();
    void apagar();
    boolean estaEncendido();
    String getNombre();
}

class LuzD implements IDispositivo {
    private String nombre;
    private boolean encendido = true;
    public LuzD(String nombre) { 
        this.nombre = nombre; 
    }
    public void encender() { 
        encendido = true; 
    }
    public void apagar()   { 
        encendido = false; 
    }
    public boolean estaEncendido() { 
        return encendido; 
    }
    public String getNombre() { 
        return "Luz (" + nombre + ")";
     }
}


class VentiladorD implements IDispositivo {
    private String nombre;
    private boolean encendido = true;
    public VentiladorD(String nombre) {
         this.nombre = nombre;
         }
    public void encender() { 
        encendido = true;
     }
    public void apagar()   { 
        encendido = false;
     }
    public boolean estaEncendido() { 
        return encendido; 
    }
    public String getNombre() {
         return "Ventilador (" + nombre + ")"; }
}

// ── Composite: Habitacion ────────────────────────────────────
class HabitacionD implements IDispositivo {
    private String nombre;
    private List<IDispositivo> dispositivos = new ArrayList<>();

    public HabitacionD(String nombre) { 
        this.nombre = nombre;
     }

    public void agregar(IDispositivo d) { 
        dispositivos.add(d); 
    }

    public List<IDispositivo> getDispositivos() { 
        return dispositivos; 
    }

    public void encender() { 
        for (IDispositivo d : dispositivos) d.encender(); 
    }

    public void apagar()   {
         for (IDispositivo d : dispositivos) d.apagar(); 
        }

    public boolean estaEncendido() {
        for (IDispositivo d : dispositivos) if (d.estaEncendido()) return true;
        return false;
    }
    public String getNombre() { 
        return nombre; 
    }
}

public class CasaDomotica extends JFrame {

    static final Color BG        = new Color(15, 17, 23);
    static final Color PANEL_BG  = new Color(22, 27, 39);
    static final Color CARD_BG   = new Color(30, 37, 55);
    static final Color BORDER_C  = new Color(45, 55, 72);
    static final Color ON_COLOR  = new Color(251, 191, 36);
    static final Color OFF_COLOR = new Color(100, 116, 139);
    static final Color TEXT_PRI  = new Color(226, 232, 240);
    static final Color TEXT_SEC  = new Color(113, 128, 150);
    static final Color COMP_CLR  = new Color(99, 179, 237);
    static final Color BTN_RED   = new Color(252, 129, 129);
    static final Color BTN_GRN   = new Color(104, 211, 145);
    static final Color BTN_PURP  = new Color(183, 148, 244);

    // Iconos de estado (texto)
    static final String ICONO_ON_LUZ  = "ENCENDIDA";
    static final String ICONO_OFF_LUZ = "APAGADA";
    static final String ICONO_ON_VEN  = "ENCENDIDO";
    static final String ICONO_OFF_VEN = "APAGADO";

    private JTextArea logArea;

    private LuzD        luz1;
    private VentiladorD vent1;
    private HabitacionD sala;
    private HabitacionD casa;

    private JLabel  estadoLuz,  estadoVent;
    private JButton btnLuz,     btnVent;
    private JPanel  cardLuz,    cardVent;

    public CasaDomotica() {
        super("Casa Domotica - Patron Composite");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(980, 660);
        setMinimumSize(new Dimension(880, 580));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);

        // Arbol Composite
        luz1  = new LuzD("Sala");
        vent1 = new VentiladorD("Sala");
        sala  = new HabitacionD("Sala");
        sala.agregar(luz1);
        sala.agregar(vent1);
        casa  = new HabitacionD("Casa Completa");
        casa.agregar(sala);

        JPanel root = new JPanel(new BorderLayout(0, 14));
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        header.setBackground(BG);
        JLabel titulo = new JLabel("Casa Domotica  ");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        JLabel subtit = new JLabel("- Sin patron  vs  Con patron Composite");
        subtit.setForeground(TEXT_SEC);
        subtit.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        header.add(titulo);
        header.add(subtit);
        root.add(header, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(1, 2, 14, 0));
        centro.setBackground(BG);
        centro.add(panelIndividual());
        centro.add(panelComposite());
        root.add(centro, BorderLayout.CENTER);

        logArea = new JTextArea();
        logArea.setBackground(new Color(8, 10, 16));
        logArea.setForeground(new Color(180, 210, 180));
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        logArea.setEditable(false);
        logArea.setMargin(new Insets(8, 12, 8, 12));
        logArea.setText(">> Sistema iniciado. Todos los dispositivos encendidos.\n");

        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_C));
        scroll.setPreferredSize(new Dimension(0, 130));

        JLabel lbl = new JLabel("  >> Consola de salida");
        lbl.setForeground(new Color(104, 211, 145));
        lbl.setFont(new Font("Consolas", Font.BOLD, 12));
        lbl.setBorder(new EmptyBorder(6, 0, 3, 0));

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(BG);
        bottom.add(lbl, BorderLayout.NORTH);
        bottom.add(scroll, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        add(root);
        setVisible(true);
    }

    private JPanel panelIndividual() {
        JPanel p = basePanel("Sin patron — control individual",
                             "Cada dispositivo se maneja por separado (Leaf)");

        JLabel aviso = new JLabel("<html><font color='#EF9F27'>⚠ Sin patron: hay que apagar uno por uno</font></html>");
        aviso.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        aviso.setAlignmentX(LEFT_ALIGNMENT);
        p.add(aviso);
        p.add(Box.createVerticalStrut(10));

        cardLuz = crearCard(true);
        JLabel icoLuz = lbl("Luz  [Leaf]", TEXT_PRI, 15, Font.BOLD);
        JLabel subLuz = lbl("Sala — sin patron, control directo", TEXT_SEC, 11, Font.PLAIN);
        estadoLuz = lbl(ICONO_ON_LUZ, ON_COLOR, 13, Font.BOLD);
        btnLuz = boton("Apagar", BTN_RED);
        btnLuz.addActionListener(e -> toggleIndividual(luz1, estadoLuz, btnLuz, cardLuz, true));
        cardLuz.add(panelInfo(icoLuz, subLuz), BorderLayout.CENTER);
        cardLuz.add(panelDerecha(estadoLuz, btnLuz), BorderLayout.EAST);
        p.add(cardLuz);
        p.add(Box.createVerticalStrut(10));

        cardVent = crearCard(true);
        JLabel icoVent = lbl(" Ventilador  [Leaf]", TEXT_PRI, 15, Font.BOLD);
        JLabel subVent = lbl("Sala — sin patron, control directo", TEXT_SEC, 11, Font.PLAIN);
        estadoVent = lbl(ICONO_ON_VEN, ON_COLOR, 13, Font.BOLD);
        btnVent = boton("Apagar", BTN_RED);
        btnVent.addActionListener(e -> toggleIndividual(vent1, estadoVent, btnVent, cardVent, false));
        cardVent.add(panelInfo(icoVent, subVent), BorderLayout.CENTER);
        cardVent.add(panelDerecha(estadoVent, btnVent), BorderLayout.EAST);
        p.add(cardVent);
        p.add(Box.createVerticalGlue());
        return p;
    }

    private JPanel panelComposite() {
        JPanel p = basePanel("Con patron Composite",
                             "Un solo comando propaga a todos los hijos del arbol");

        JLabel aviso = new JLabel("<html><font color='#68D391'>✔ Con patron: un boton controla todo el arbol</font></html>");
        aviso.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        aviso.setAlignmentX(LEFT_ALIGNMENT);
        p.add(aviso);
        p.add(Box.createVerticalStrut(10));

        // Bloque Sala
        JPanel bloqSala = bloque(" Habitacion: Sala  [Composite]", COMP_CLR,
                                  "Hijos: Luz (Sala), Ventilador (Sala)");
        JPanel btnsSala = filaBotones();
        JButton bEncSala = boton("Encender Sala", BTN_GRN);
        JButton bApaSala = boton("Apagar Sala",   BTN_RED);
        bEncSala.addActionListener(e -> compositeAccion(sala, true));
        bApaSala.addActionListener(e -> compositeAccion(sala, false));
        btnsSala.add(bEncSala);
        btnsSala.add(bApaSala);
        bloqSala.add(btnsSala);
        p.add(bloqSala);
        p.add(Box.createVerticalStrut(12));

        // Bloque Casa
        JPanel bloqCasa = bloque(" Casa Completa  [Composite Raiz]", BTN_PURP,
                                  "Hijos: Sala -> Luz, Ventilador");
        JPanel btnsCasa = filaBotones();
        JButton bEncCasa = boton("Encender Todo", BTN_GRN);
        JButton bApaCasa = boton("Apagar Todo",   BTN_RED);
        bEncCasa.addActionListener(e -> compositeAccion(casa, true));
        bApaCasa.addActionListener(e -> compositeAccion(casa, false));
        btnsCasa.add(bEncCasa);
        btnsCasa.add(bApaCasa);
        bloqCasa.add(btnsCasa);
        p.add(bloqCasa);
        p.add(Box.createVerticalGlue());

        JLabel nota = new JLabel(
            "<html><center><font color='#718096'>" +
            "encender()/apagar() se propaga automaticamente<br>" +
            "por todo el arbol sin llamar cada dispositivo</font></center></html>");
        nota.setAlignmentX(CENTER_ALIGNMENT);
        p.add(Box.createVerticalStrut(10));
        p.add(nota);
        return p;
    }

    private void toggleIndividual(IDispositivo d, JLabel estado,
                                   JButton btn, JPanel card, boolean esLuz) {
        if (d.estaEncendido()) {
            d.apagar();
            actualizarCard(estado, btn, card, false, esLuz);
            log("[Sin patron] " + d.getNombre() + " -> apagado manualmente");
        } else {
            d.encender();
            actualizarCard(estado, btn, card, true, esLuz);
            log("[Sin patron] " + d.getNombre() + " -> encendido manualmente");
        }
    }

    private void compositeAccion(HabitacionD hab, boolean encender) {
        if (encender) hab.encender(); else hab.apagar();

        log("── [Composite] " + hab.getNombre()
            + (encender ? ".encender()" : ".apagar()") + " ──");

        // Log del recorrido del arbol
        for (IDispositivo d : hab.getDispositivos()) {
            if (d instanceof HabitacionD) {
                log("  > Habitacion: " + d.getNombre());
                for (IDispositivo sub : ((HabitacionD) d).getDispositivos())
                    log("      -> " + sub.getNombre()
                        + (encender ? " encendido" : " apagado"));
            } else {
                log("  -> " + d.getNombre()
                    + (encender ? " encendido" : " apagado"));
            }
        }

        actualizarCard(estadoLuz,  btnLuz,  cardLuz,  luz1.estaEncendido(),  true);
        actualizarCard(estadoVent, btnVent, cardVent, vent1.estaEncendido(), false);

        log(encender
            ? "  [Panel izquierdo actualizado: todos encendidos]"
            : "  [Panel izquierdo actualizado: todos apagados]");
    }

    private void actualizarCard(JLabel estado, JButton btn,
                                 JPanel card, boolean on, boolean esLuz) {
        if (on) {
            estado.setText(esLuz ? ICONO_ON_LUZ : ICONO_ON_VEN);
            estado.setForeground(ON_COLOR);
            btn.setText("Apagar");
            btn.setBackground(BTN_RED);
            card.setBackground(CARD_BG);
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_C),
                new EmptyBorder(14, 14, 14, 14)));
        } else {
            estado.setText(esLuz ? ICONO_OFF_LUZ : ICONO_OFF_VEN);
            estado.setForeground(OFF_COLOR);
            btn.setText("Encender");
            btn.setBackground(BTN_GRN);
            // Borde rojo tenue para indicar apagado
            card.setBackground(new Color(40, 25, 25));
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(120, 50, 50)),
                new EmptyBorder(14, 14, 14, 14)));
        }
        estado.repaint();
        btn.repaint();
        card.repaint();
    }

    private JPanel basePanel(String titulo, String sub) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(PANEL_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_C),
            new EmptyBorder(16, 16, 16, 16)));
        JLabel lT = lbl(titulo, Color.WHITE, 15, Font.BOLD);
        lT.setAlignmentX(LEFT_ALIGNMENT);
        JLabel lS = lbl(sub, TEXT_SEC, 11, Font.PLAIN);
        lS.setAlignmentX(LEFT_ALIGNMENT);
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_C);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        p.add(lT); p.add(Box.createVerticalStrut(2));
        p.add(lS); p.add(Box.createVerticalStrut(10));
        p.add(sep); p.add(Box.createVerticalStrut(12));
        return p;
    }

    private JPanel crearCard(boolean alLeft) {
        JPanel c = new JPanel(new BorderLayout(10, 0));
        c.setBackground(CARD_BG);
        c.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_C),
            new EmptyBorder(14, 14, 14, 14)));
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        c.setAlignmentX(LEFT_ALIGNMENT);
        return c;
    }

    private JPanel bloque(String titulo, Color color, String sub) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(CARD_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_C),
            new EmptyBorder(12, 14, 14, 14)));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 115));
        p.setAlignmentX(LEFT_ALIGNMENT);
        JLabel lT = lbl(titulo, color, 14, Font.BOLD);
        lT.setAlignmentX(LEFT_ALIGNMENT);
        JLabel lS = lbl(sub, TEXT_SEC, 11, Font.PLAIN);
        lS.setAlignmentX(LEFT_ALIGNMENT);
        p.add(lT); p.add(Box.createVerticalStrut(2));
        p.add(lS); p.add(Box.createVerticalStrut(10));
        return p;
    }

    private JPanel filaBotones() {
        JPanel f = new JPanel(new GridLayout(1, 2, 8, 0));
        f.setBackground(CARD_BG);
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        f.setAlignmentX(LEFT_ALIGNMENT);
        return f;
    }

    private JPanel panelInfo(JLabel t, JLabel s) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(CARD_BG);
        p.add(t); p.add(Box.createVerticalStrut(4)); p.add(s);
        return p;
    }

    private JPanel panelDerecha(JLabel estado, JButton btn) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(CARD_BG);
        estado.setAlignmentX(RIGHT_ALIGNMENT);
        btn.setAlignmentX(RIGHT_ALIGNMENT);
        p.add(estado); p.add(Box.createVerticalStrut(6)); p.add(btn);
        return p;
    }

    private JLabel lbl(String texto, Color color, int size, int style) {
        JLabel l = new JLabel(texto);
        l.setForeground(color);
        l.setFont(new Font("Segoe UI", style, size));
        return l;
    }

    private JButton boton(String texto, Color bg) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setForeground(Color.BLACK);
        b.setBackground(bg);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(6, 14, 6, 14));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void log(String msg) {
        logArea.append(msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(CasaDomotica::new);
    }
}