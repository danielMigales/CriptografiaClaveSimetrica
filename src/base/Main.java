package base;

import interfazGrafica.UI;
import javax.swing.SwingUtilities;

/**
 * @Daniel Migales
 */

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new UI().setVisible(true);
            }
        });

    }
}
