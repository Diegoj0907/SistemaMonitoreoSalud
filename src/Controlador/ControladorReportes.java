package Controlador;

import Vista.*;
import Modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Date;
import java.math.BigDecimal;

public class ControladorReportes {
    private final FrmReportes vista;
    private final ReporteDAO dao = new ReporteDAO();

    public ControladorReportes(FrmReportes v) {
        this.vista = v;
        inicializarSpinners();
        cargarUsuariosEnCombo();
        iniciarEventos();
        cargarTablaInicial();
    }

    private void iniciarEventos() {
        vista.btnVerGrafico.addActionListener(e -> mostrarGrafico());
        vista.btnGenerarPDF.addActionListener(e -> generarPDF());
        vista.btnVolver.addActionListener(e -> {
        vista.dispose();
        FrmPrincipal p = new FrmPrincipal();
        new ControladorPrincipal(p);
        p.setVisible(true);
    });

        vista.cmbUsuario.addActionListener(e -> cargarTablaInicial());
        vista.spInicio.addChangeListener(e -> cargarTablaInicial());
        vista.spFin.addChangeListener(e -> cargarTablaInicial());
    }


    private void inicializarSpinners() {
        try {
            vista.spInicio.setModel(new javax.swing.SpinnerDateModel());
            vista.spFin.setModel(new javax.swing.SpinnerDateModel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarUsuariosEnCombo() {
        vista.cmbUsuario.removeAllItems();
        vista.cmbUsuario.addItem("Todos");
        try (var con = ConexionDAO.conectar();
             var cs = con.prepareCall("{CALL ObtenerUsuarios_SP}");
             var rs = cs.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                vista.cmbUsuario.addItem(id + " - " + nombre);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void cargarTablaInicial() {
    try {
        DefaultTableModel modelo = obtenerDatos();
        vista.tabla.setModel(modelo);

        vista.tabla.revalidate();
        vista.tabla.repaint();

        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(vista,
                "⚠️ No hay registros en el rango seleccionado.",
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(vista,
            "❌ Error al cargar los datos: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private DefaultTableModel obtenerDatos() {
        Date inicioDate = (vista.spInicio.getValue() instanceof Date)
                ? (Date) vista.spInicio.getValue() : new Date();
        Date finDate = (vista.spFin.getValue() instanceof Date)
                ? (Date) vista.spFin.getValue() : new Date();

        String usuarioSel = (String) vista.cmbUsuario.getSelectedItem();
        if (usuarioSel == null) usuarioSel = "Todos";
        boolean todos = usuarioSel.equals("Todos");

        if (todos) {
            return dao.obtenerResumen(inicioDate, finDate);
        } else {
            int idUsuario = Integer.parseInt(usuarioSel.split(" - ")[0]);
            return dao.obtenerPorUsuarioYRango(idUsuario, inicioDate, finDate);
        }
    }

    private void generarPDF() {
        DefaultTableModel model = obtenerDatos();
        vista.tabla.setModel(model);

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(vista, "⚠️ No hay datos para generar el PDF.");
            return;
        }

        String ruta = "reporte_emocional.pdf";

org.apache.pdfbox.pdmodel.font.PDFont fontBold;
org.apache.pdfbox.pdmodel.font.PDFont fontNormal;

try {
    fontBold = new org.apache.pdfbox.pdmodel.font.PDType1Font(
            org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD);
    fontNormal = new org.apache.pdfbox.pdmodel.font.PDType1Font(
            org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA);
} catch (Exception e) {
    fontBold = null;
    fontNormal = null;
}

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.LETTER);
            doc.addPage(page);

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                cs.beginText();
                if (fontBold != null) cs.setFont(fontBold, 14);
                cs.newLineAtOffset(50, 750);
                cs.showText("Reporte Emocional");
                cs.newLineAtOffset(0, -20);
                if (fontNormal != null) cs.setFont(fontNormal, 10);

                for (int i = 0; i < model.getRowCount(); i++) {
                    StringBuilder linea = new StringBuilder();
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object val = model.getValueAt(i, j);
                        linea.append((val == null ? "" : val.toString())).append(" | ");
                    }
                    cs.showText(truncateForPdf(linea.toString(), 200));
                    cs.newLineAtOffset(0, -12);
                }

                cs.endText();
            }

            doc.save(ruta);
            JOptionPane.showMessageDialog(vista, "✅ PDF generado correctamente en: " + ruta);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "❌ Error al generar PDF: " + ex.getMessage());
        }
    }

    private void mostrarGrafico() {
        DefaultTableModel model = obtenerDatos();
        vista.tabla.setModel(model);

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(vista, "⚠️ No hay datos para mostrar en el gráfico.");
            return;
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < model.getRowCount(); i++) {
            String fecha = String.valueOf(model.getValueAt(i, 2));
            double puntaje = toDoubleSafe(model.getValueAt(i, 5));
            dataset.addValue(puntaje, "Puntaje", fecha);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Tendencia de Puntajes Emocionales",
                "Fecha", "Puntaje",
                dataset, PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel panel = new ChartPanel(chart);
        JFrame f = new JFrame("Gráfico Emocional");
        f.setContentPane(panel);
        f.setSize(800, 600);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private static double toDoubleSafe(Object obj) {
        if (obj == null) return 0.0;
        if (obj instanceof Number) return ((Number) obj).doubleValue();
        if (obj instanceof BigDecimal) return ((BigDecimal) obj).doubleValue();
        try {
            return Double.parseDouble(obj.toString());
        } catch (Exception e) {
            return 0.0;
        }
    }

    private static String truncateForPdf(String s, int max) {
        if (s == null) return "";
        if (s.length() <= max) return s;
        return s.substring(0, max - 3) + "...";
    }
}
