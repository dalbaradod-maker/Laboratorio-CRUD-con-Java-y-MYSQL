package umg.UI;
import javax.swing.*;
import java.awt.*;

public class AppTransporteUmg extends JFrame {
    public AppTransporteUmg() {
        setTitle("Transporte - UMG");
        setSize(380, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 35, 20, 35));
        JLabel titulo = new JLabel("Sistema de Transporte", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        panel.add(titulo);
        boton(panel, "Camiones", () -> new FrmCamion().setVisible(true));
        boton(panel, "Camioneros", () -> new FrmCamionero().setVisible(true));
        boton(panel, "Paquetes", () -> new FrmPaquete().setVisible(true));
        boton(panel, "Camionero - Camion", () -> new FrmCamioneroCamion().setVisible(true));
        boton(panel, "Ciudades", () -> new FrmCiudad().setVisible(true));
        add(panel);
    }
    private void boton(JPanel panel, String texto, Runnable abrir) {
        JButton b = new JButton(texto);
        b.addActionListener(e -> abrir.run());
        panel.add(b);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppTransporteUmg().setVisible(true));
    }
}
