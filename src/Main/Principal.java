package Main;

import javax.swing.SwingUtilities;
import Vista.FrmLogin;
import Controlador.ControladorLogin;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmLogin login = new FrmLogin();
            new ControladorLogin(login);
            login.setVisible(true);
        });
    }
}
