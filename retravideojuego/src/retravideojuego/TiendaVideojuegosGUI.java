import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class TiendaVideojuegosGUI {
    private static ArrayList<Videojuego> videojuegos = new ArrayList<>();
    private static DefaultTableModel tableModel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tienda de Videojuegos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Panel Principal
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Componentes de la interfaz
        JLabel labelTitulo = new JLabel("Gestión de Videojuegos");
        labelTitulo.setBounds(300, 10, 200, 30);
        panel.add(labelTitulo);

        JLabel labelID = new JLabel("ID:");
        labelID.setBounds(20, 50, 50, 25);
        panel.add(labelID);
        JTextField textID = new JTextField();
        textID.setBounds(70, 50, 150, 25);
        panel.add(textID);

        JLabel labelNombre = new JLabel("Nombre:");
        labelNombre.setBounds(20, 90, 70, 25);
        panel.add(labelNombre);
        JTextField textNombre = new JTextField();
        textNombre.setBounds(90, 90, 150, 25);
        panel.add(textNombre);

        JLabel labelPrecio = new JLabel("Precio:");
        labelPrecio.setBounds(20, 130, 70, 25);
        panel.add(labelPrecio);
        JTextField textPrecio = new JTextField();
        textPrecio.setBounds(90, 130, 150, 25);
        panel.add(textPrecio);

        JLabel labelGenero = new JLabel("Género:");
        labelGenero.setBounds(20, 170, 70, 25);
        panel.add(labelGenero);
        JTextField textGenero = new JTextField();
        textGenero.setBounds(90, 170, 150, 25);
        panel.add(textGenero);

        JLabel labelPlataforma = new JLabel("Plataforma:");
        labelPlataforma.setBounds(20, 210, 80, 25);
        panel.add(labelPlataforma);
        JTextField textPlataforma = new JTextField();
        textPlataforma.setBounds(110, 210, 150, 25);
        panel.add(textPlataforma);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(20, 250, 100, 30);
        panel.add(btnAgregar);

        JButton btnActualizar = new JButton("Actualizar Precio");
        btnActualizar.setBounds(130, 250, 150, 30);
        panel.add(btnActualizar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(290, 250, 100, 30);
        panel.add(btnEliminar);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(400, 250, 100, 30);
        panel.add(btnBuscar);

        // Tabla
        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Género", "Plataforma"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 300, 740, 200);
        panel.add(scrollPane);

        // Eventos de los botones
        btnAgregar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(textID.getText());
                String nombre = textNombre.getText();
                double precio = Double.parseDouble(textPrecio.getText());
                String genero = textGenero.getText();
                String plataforma = textPlataforma.getText();

                Videojuego videojuego = new Videojuego(id, nombre, precio, genero, plataforma);
                videojuegos.add(videojuego);
                tableModel.addRow(new Object[]{id, nombre, precio, genero, plataforma});
                JOptionPane.showMessageDialog(frame, "Videojuego agregado correctamente.");
                limpiarCampos(textID, textNombre, textPrecio, textGenero, textPlataforma);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error al agregar el videojuego. Verifica los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(textID.getText());
                double nuevoPrecio = Double.parseDouble(textPrecio.getText());

                boolean encontrado = false;
                for (Videojuego v : videojuegos) {
                    if (v.getId() == id) {
                        v.setPrecio(nuevoPrecio);
                        actualizarTabla();
                        encontrado = true;
                        JOptionPane.showMessageDialog(frame, "Precio actualizado correctamente.");
                        break;
                    }
                }
                if (!encontrado) {
                    JOptionPane.showMessageDialog(frame, "No se encontró un videojuego con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error al actualizar el precio.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(textID.getText());

                boolean eliminado = videojuegos.removeIf(v -> v.getId() == id);
                if (eliminado) {
                    actualizarTabla();
                    JOptionPane.showMessageDialog(frame, "Videojuego eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(frame, "No se encontró un videojuego con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error al eliminar el videojuego.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(textID.getText());

                for (Videojuego v : videojuegos) {
                    if (v.getId() == id) {
                        JOptionPane.showMessageDialog(frame, v.toString());
                        return;
                    }
                }
                JOptionPane.showMessageDialog(frame, "No se encontró un videojuego con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error al buscar el videojuego.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void limpiarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }

    private static void actualizarTabla() {
        tableModel.setRowCount(0);
        for (Videojuego v : videojuegos) {
            tableModel.addRow(new Object[]{v.getId(), v.getNombre(), v.getPrecio(), v.getGenero(), v.getPlataforma()});
        }
    }
}
