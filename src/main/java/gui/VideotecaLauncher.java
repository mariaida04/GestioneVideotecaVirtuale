package gui;

import javax.swing.*;

public class VideotecaLauncher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Frame frame = new Frame();
            VideotecaController controller = new VideotecaController(frame);
            frame.setController(controller);
            controller.mostraVideoteca();
            frame.setVisible(true);
        });
    }
}
