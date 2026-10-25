package umg.UI;

import umg.DAO.CamioneroDAO;
import umg.modelo.Camionero;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmCamionero extends JFrame {
    private final CamioneroDAO datos = new CamioneroDAO();
    private final DefaultTableModel modelo = new DefaultTableModel(new String[] {"IdCamionero", "Nombre", "Telefono", "Direccion", "Salario", "Poblacion"}, 0) {
        @Override public boolean isCellEditable(int fila, int columna) { return false; }
    };
    private final JTable tabla = new JTable(modelo);
    private int id = 0;
    private final JTextField dato0 = new JTextField(18);
    private final JTextField dato1 = new JTextField(18);
    private final JTextField dato2 = new JTextField(18);
    private final JTextField dato3 = new JTextField(18);
    private final JTextField dato4 = new JTextField(18);

    public FrmCamionero() {
        setTitle("Camionero");
        setSize(850, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        agregarCampo(panel, "Nombre", dato0);
        agregarCampo(panel, "Telefono", dato1);
        agregarCampo(panel, "Direccion", dato2);
        agregarCampo(panel, "Salario", dato3);
        agregarCampo(panel, "Poblacion", dato4);
        JButton guardar = new JButton("Guardar");
        JButton editar = new JButton("Modificar");
        JButton borrar = new JButton("Eliminar");
        JButton limpiar = new JButton("Limpiar");
        JButton consultar = new JButton("Actualizar tabla");
        JPanel botones = new JPanel();
        for (JButton b : new JButton[] {guardar, editar, borrar, limpiar, consultar}) botones.add(b);
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
        guardar.addActionListener(e -> ejecutar("guardar"));
        editar.addActionListener(e -> ejecutar("editar"));
        borrar.addActionListener(e -> ejecutar("borrar"));
        limpiar.addActionListener(e -> limpiar());
        consultar.addActionListener(e -> listar());
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) seleccionar();
        });
        listar();
    }

    private void agregarCampo(JPanel p, String nombre, JTextField campo) {
        p.add(new JLabel(nombre + ":"));
        p.add(campo);
    }
    private Camionero leer() {
        Camionero x = new Camionero();
        x.setIdCamionero(id);
        x.setNombre(dato0.getText().trim());
        x.setTelefono(dato1.getText().trim());
        x.setDireccion(dato2.getText().trim());
        x.setSalario(Double.parseDouble(dato3.getText().trim()));
        x.setPoblacion(dato4.getText().trim());
        return x;
    }
    private void listar() {
        try {
            modelo.setRowCount(0);
            for (Camionero x : datos.listar()) {
                modelo.addRow(new Object[] {x.getIdCamionero(), x.getNombre(), x.getTelefono(), x.getDireccion(), x.getSalario(), x.getPoblacion()});
            }
        } catch (Exception ex) { mensaje(ex.getMessage()); }
    }
    private void seleccionar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
        dato0.setText(String.valueOf(tabla.getValueAt(fila, 1)));
        dato1.setText(String.valueOf(tabla.getValueAt(fila, 2)));
        dato2.setText(String.valueOf(tabla.getValueAt(fila, 3)));
        dato3.setText(String.valueOf(tabla.getValueAt(fila, 4)));
        dato4.setText(String.valueOf(tabla.getValueAt(fila, 5)));
    }
    private void limpiar() {
        id = 0;
        tabla.clearSelection();
        dato0.setText("");
        dato1.setText("");
        dato2.setText("");
        dato3.setText("");
        dato4.setText("");
    }
    private void ejecutar(String accion) {
        try {
            if (accion.equals("guardar")) {
                datos.insertar(leer());
            } else if (accion.equals("editar")) {
                if (id == 0) { mensaje("Selecciona un registro de la tabla."); return; }
                if (!datos.actualizar(leer())) { mensaje("No se encontro el registro."); return; }
            } else {
                if (id == 0) { mensaje("Selecciona un registro de la tabla."); return; }
                if (JOptionPane.showConfirmDialog(this, "¿Eliminar este registro?", "Confirmar", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
                if (!datos.eliminar(id)) { mensaje("No se encontro el registro."); return; }
            }
            limpiar();
            listar();
            mensaje("Operacion realizada correctamente.");
        } catch (NumberFormatException ex) {
            mensaje("Revisa los campos numericos.");
        } catch (Exception ex) { mensaje(ex.getMessage()); }
    }
    private void mensaje(String texto) { JOptionPane.showMessageDialog(this, texto); }
}
