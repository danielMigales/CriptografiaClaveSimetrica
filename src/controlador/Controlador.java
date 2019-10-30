package controlador;

import algoritmo.Cifrado;
import static algoritmo.Cifrado.*;
import interfazGrafica.UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import util.Alfabeto;
import util.CommandName;

/**
 * @Daniel Migales
 */
public class Controlador implements ActionListener {

    private final UI userInterface;

    private Controlador(UI userInterface) {
        this.userInterface = userInterface;
    }

    public static Controlador fromUserInterface(UI userInterface) {
        return new Controlador(userInterface);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CommandName comando = CommandName.valueOf(e.getActionCommand());

        if (comando == CommandName.ENCRIPTAR_BTN) {
            System.out.println("Boton Encriptado pulsado. ");
            encriptarActionPerformed();
        } else if (comando == CommandName.DESENCRIPTAR_BTN) {
            System.out.println("Boton Desencriptado pulsado.");
            desencriptarActionPerformed();
        }
    }

    private void encriptarActionPerformed() {

        String texto = userInterface.getEntradaTexto().getText();
        int tipoCifrado = userInterface.getSelectorTipo().getSelectedIndex();
        String resultado;

        switch (tipoCifrado) {
            case 0:
                int clave = (int) userInterface.getClaveAlgoritmo().getValue();
                int valorAlfabeto = userInterface.getSelAlfabeto().getSelectedIndex();
                String alfabeto = "";
                switch (valorAlfabeto) {
                    case 0:
                        alfabeto = Alfabeto.ESP.toString();
                        break;
                    case 1:
                        alfabeto = Alfabeto.ENG.toString();
                        break;
                }
                resultado = Cifrado.codificarCesar(texto, clave, alfabeto);
                userInterface.getResultadoEncriptacion().setText(resultado);
                break;

            case 1:

                byte[] contraseña = texto.getBytes(StandardCharsets.UTF_8);
                int valorKey = userInterface.getSelectorKey().getSelectedIndex();
                int keySize = 0;
                switch (valorKey) {
                    case 0:
                        keySize = 128;
                        break;
                    case 1:
                        keySize = 192;
                        break;
                    case 2:
                        keySize = 256;
                        break;
                }
                SecretKey skey = keygenKeyGeneration(keySize);
                byte[] encriptado = encryptData(skey, contraseña);
                resultado = byteToHex(encriptado);
                userInterface.getResultadoEncriptacion().setText(resultado);
                break;
        }
    }

    private void desencriptarActionPerformed() {

        String texto = userInterface.getEntradaTexto().getText();
        int tipoCifrado = userInterface.getSelectorTipo().getSelectedIndex();
        String resultado;

        switch (tipoCifrado) {
            case 0:
                int clave = (int) userInterface.getClaveAlgoritmo().getValue();
                int valorAlfabeto = userInterface.getSelAlfabeto().getSelectedIndex();
                String alfabeto = "";
                switch (valorAlfabeto) {
                    case 0:
                        alfabeto = Alfabeto.ESP.toString();
                        break;
                    case 1:
                        alfabeto = Alfabeto.ENG.toString();
                        break;
                }
                resultado = Cifrado.deCodificarCesar(texto, clave, alfabeto);
                userInterface.getResultadoDesencriptacion().setText(resultado);
                break;

            case 1:
                byte[] contraseña = texto.getBytes(StandardCharsets.UTF_8);
                int valorKey = userInterface.getSelectorKey().getSelectedIndex();
                int keySize = 0;
                switch (valorKey) {
                    case 0:
                        keySize = 128;
                        break;
                    case 1:
                        keySize = 192;
                        break;
                    case 2:
                        keySize = 256;
                        break;
                }
                SecretKey skey = keygenKeyGeneration(keySize);
                byte[] desencriptado = decryptData(skey, contraseña);
                resultado = byteToHex(desencriptado);
                userInterface.getResultadoDesencriptacion().setText(resultado);
        }

    }
}
