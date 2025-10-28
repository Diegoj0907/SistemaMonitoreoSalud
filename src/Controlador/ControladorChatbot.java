package Controlador;

import Modelo.*;
import Vista.*;
import java.util.Date;
import java.util.concurrent.*;
import javax.swing.*;

public class ControladorChatbot {
    private final FrmChatbot view;
    private final RegistroEmocionalDAO registroDAO = new RegistroEmocionalDAO();
    private final AnalizadorTexto analizador = new AnalizadorTexto();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean activo = true;
    private final int usuarioIdEnSesion;

    public ControladorChatbot(FrmChatbot v, int usuarioId) {
        this.view = v;
        this.usuarioIdEnSesion = usuarioId;
        init();
    }

    private void init() {
        view.btnEnviar.addActionListener(e -> onEnviar());
        view.btnFinalizar.addActionListener(e -> onFinalizar());
        view.txtMensaje.addActionListener(e -> onEnviar());
    }

    private void onEnviar() {
        final String texto = view.txtMensaje.getText().trim();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(view, "⚠️ Escriba un mensaje antes de enviar.");
            return;
        }

        appendChat("🧑‍💬 Tú: " + texto);
        view.txtMensaje.setText("");

        if (!activo) {
            appendChat("💬 Chat finalizado, no se puede enviar más mensajes.");
            return;
        }

        executor.submit(() -> {
            try {
                AnalizadorTexto.ResultadoAnalisis res = analizador.analizar(texto);

                RegistroEmocional registro = new RegistroEmocional(
                        usuarioIdEnSesion, new Date(), texto, res.emocion, res.puntaje);
                registroDAO.insertarRegistro(registro);

                SwingUtilities.invokeLater(() -> {
                    appendChat("🤖 Chatbot: He detectado una emoción "
                            + res.emocion + " (puntaje: " + res.puntaje + ")");
                    view.lblEmocion.setText("Emoción detectada: " + res.emocion);
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(view, "❌ Error al procesar mensaje: " + ex.getMessage())
                );
            }
        });
    }

    private void onFinalizar() {

        activo = false;
        view.btnEnviar.setEnabled(false);
        view.btnFinalizar.setEnabled(false);
        executor.shutdownNow();

        appendChat("💬 Chatbot finalizado. Gracias por comunicarte.");

        Timer t = new Timer(1200, e -> {
            view.dispose();

            Usuario usuario = Session.getInstance().getUsuario();

            if (usuario != null) {

                FrmPrincipal frm = new FrmPrincipal();
                new ControladorPrincipal(frm);
                frm.setLocationRelativeTo(null);
                frm.setVisible(true);
            } else {

                FrmLogin login = new FrmLogin();
                new ControladorLogin(login);
                login.setLocationRelativeTo(null);
                login.setVisible(true);
            }
        });
        t.setRepeats(false);
        t.start();
    }

    private void appendChat(String line) {
        view.txtChat.append(line + System.lineSeparator());
    }
}
