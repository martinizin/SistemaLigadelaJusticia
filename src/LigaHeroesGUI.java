import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LigaHeroesGUI {
    private JButton REGISTRARHEROEButton;
    private JButton CALCULARDETALLESButton;
    private JButton ACTUALIZARDATOSDEHEROEButton;
    private JButton MOSTRARLISTADEHEROESButton;
    private JButton LIMPIARFORMULARIOButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField6;
    private JTextArea textArea1;
    private JPanel pGeneral;
    private JComboBox<Integer>  comboBox1;
    private GestionHeroes gestion = new GestionHeroes();

    public LigaHeroesGUI() {
        // Agregar los elementos al comboBox1
        for (int i = 1; i <= 5; i++) {
            comboBox1.addItem(i);
        }

        REGISTRARHEROEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = textField1.getText();
                    String nombre = textField2.getText();
                    String superpoder = textField3.getText();
                    String mision = textField4.getText();
                    int nivelDificultad = (Integer) comboBox1.getSelectedItem();
                    double pagoMensual = Double.parseDouble(textField6.getText());

                    Heroes heroe = new Heroes(id, nombre, superpoder, mision, nivelDificultad, pagoMensual);
                    gestion.registrarHeroe(heroe);

                    textArea1.setText("Héroe registrado exitosamente.\n");
                    LIMPIARFORMULARIOButton.doClick();
                } catch (Exception ex) {
                    textArea1.setText("Error al registrar el héroe: " + ex.getMessage());
                }

            }
        });
        CALCULARDETALLESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = textField1.getText();
                if (id.isEmpty()) {
                    textArea1.setText("Por favor, ingrese el ID del héroe para calcular detalles.\n");
                    return;
                }

                try {
                    String detalles = gestion.calcularAporteImpuestos(id);
                    textArea1.setText(detalles);
                } catch (Exception ex) {
                    textArea1.setText("Error al calcular los detalles: " + ex.getMessage());
                }

            }
        });
        ACTUALIZARDATOSDEHEROEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = textField1.getText();
                if (id.isEmpty()) {
                    textArea1.setText("Por favor, ingrese el ID del héroe a actualizar.\n");
                    return;
                }

                try {
                    double nuevoPago = Double.parseDouble(textField6.getText());
                    gestion.modificarHeroe(id, nuevoPago);
                    textArea1.setText("Datos del heroe actualizados con éxito!\n");
                } catch (Exception ex) {
                    textArea1.setText("Error al actualizar el héroe: " + ex.getMessage());
                }
                limpiarFormulario();
            }
        });
        MOSTRARLISTADEHEROESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder("Héroes Registrados:\n");
                gestion.listarHeroes(sb); // Recorre el árbol y agrega los héroes al StringBuilder
                textArea1.setText(sb.toString());

            }

        });
        LIMPIARFORMULARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                limpiarFormulario();
                }
        });

    }
    private void limpiarFormulario() {
        textField1.setText(""); // ID
        textField2.setText(""); // Nombre
        textField3.setText(""); // Superpoder
        textField4.setText(""); // Misión
        comboBox1.setSelectedIndex(0); // Nivel de dificultad
        textField6.setText(""); // Pago mensual
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LigaHeroesGUI");
        frame.setContentPane(new LigaHeroesGUI().pGeneral);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
