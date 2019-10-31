package controlador;

import algoritmo.Algoritmo;
import static algoritmo.Algoritmo.*;
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
            System.out.println("El boton de encriptado ha sido pulsado. ");
            encriptarActionPerformed();
        } else if (comando == CommandName.DESENCRIPTAR_BTN) {
            System.out.println("El boton de desencriptado ha sido pulsado.");
            desencriptarActionPerformed();
        }
    }

    //ACCIONES DEL BOTON ENCRIPTAR
    private void encriptarActionPerformed() {

        String texto = userInterface.getEntradaTexto().getText();
        int tipoCifrado = userInterface.getSelectorTipo().getSelectedIndex();
        String resultado;

        //SELECTOR PARA CESAR O AES
        switch (tipoCifrado) {

            case 0: //CUANDO SE SELECCIONA ALGORITMO CESAR

                int clave = (int) userInterface.getClaveAlgoritmo().getValue();
                int valorAlfabeto = userInterface.getSelAlfabeto().getSelectedIndex();
                String alfabeto = "";

                switch (valorAlfabeto) { //SELECTOR PARA EL TIPO DE ALFABETO

                    case 0:
                        alfabeto = Alfabeto.ESP.toString();
                        break;

                    case 1:
                        alfabeto = Alfabeto.ENG.toString();
                        break;
                }
                resultado = Algoritmo.codificarCesar(texto, clave, alfabeto);
                userInterface.getResultadoEncriptacion().setText(resultado);
                break;

            case 1: //CUANDO SE SELECCIONA ALGORITMO AES

                byte[] textoEnBytes = texto.getBytes(StandardCharsets.UTF_8);
                int valorModoAES = userInterface.getSelectorModoAES().getSelectedIndex();
                int valorKey = userInterface.getSelectorKey().getSelectedIndex();
                int keySize = 0;

                switch (valorKey) { //SELECTOR DEL VALOR DEL CIFRADO SKEY

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

                switch (valorModoAES) { //SELECTOR DEL MODO DE CIFRADO AES : ECB O CBC

                    case 0: //CASO ECB

                        byte[] encriptadoECB = encryptDataECB(skey, textoEnBytes);
                        resultado = byteToHex(encriptadoECB);
                        userInterface.getResultadoEncriptacion().setText(resultado);

                    case 1: //CASO CBC

                        byte[] encriptadoCBC = encryptDataCBC(skey, textoEnBytes);
                        resultado = byteToHex(encriptadoCBC);
                        userInterface.getResultadoEncriptacion().setText(resultado);
                        break;
                }

        }
    }

    //ACCIONES DEL BOTON DESENCRIPTAR
    private void desencriptarActionPerformed() {

        String texto = userInterface.getEntradaTexto().getText();
        int tipoCifrado = userInterface.getSelectorTipo().getSelectedIndex();
        String resultado;

        //SELECTOR PARA CESAR O AES
        switch (tipoCifrado) {

            case 0: //ALGORITMO CESAR

                int clave = (int) userInterface.getClaveAlgoritmo().getValue();
                int valorAlfabeto = userInterface.getSelAlfabeto().getSelectedIndex();
                String alfabeto = "";

                switch (valorAlfabeto) { //SELECTOR PARA EL TIPO DE ALFABETO

                    case 0:
                        alfabeto = Alfabeto.ESP.toString();
                        break;

                    case 1:
                        alfabeto = Alfabeto.ENG.toString();
                        break;
                }
                resultado = Algoritmo.deCodificarCesar(texto, clave, alfabeto);
                userInterface.getResultadoDesencriptacion().setText(resultado);
                break;

            case 1: //ALGORITMO AES

                byte[] textoEnBytes = hexToByte(texto);
                int valorModoAES = userInterface.getSelectorModoAES().getSelectedIndex();
                int valorKey = userInterface.getSelectorKey().getSelectedIndex();
                int keySize = 0;

                switch (valorKey) { //VALOR SELECCIONADO EN LA SKEY

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

                switch (valorModoAES) { //SELECTOR DEL MODO AES

                    case 0: //CASO ECB

                        byte[] desencriptadoECB = decryptDataECB(skey, textoEnBytes);
                        resultado = new String(desencriptadoECB);
                        userInterface.getResultadoDesencriptacion().setText(resultado);
                        break;

                    case 1: //CASO CBC

                        byte[] desencriptadoCBC = decryptDataCBC(skey, textoEnBytes);
                        resultado = new String(desencriptadoCBC);
                        userInterface.getResultadoDesencriptacion().setText(resultado);
                        break;
                }

        }
    }
}
