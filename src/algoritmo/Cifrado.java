package algoritmo;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * @Daniel Migales
 */

public class Cifrado {
    
    //CODIFICACION CESAR
    
    public static String codificarCesar(String texto, int clave, String alfabeto) {

        char inputChar;
        int charValue;
        int newCharValue;
        texto = texto.toUpperCase(); //CONVIERTE EL STRING A MAYUSCULAS
        StringBuilder encriptado = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) { //Recorremos el string texto

            inputChar = texto.charAt(i);
            charValue = alfabeto.indexOf(inputChar);
            newCharValue = (charValue + clave) % alfabeto.length();

            if (newCharValue < 0) {
                newCharValue += alfabeto.length();
            }
            
            encriptado.append(alfabeto.charAt(newCharValue));
        }
        return encriptado.toString();
    }

    public static String deCodificarCesar(String texto, int clave, String alfabeto) {
        char inputChar;
        int charValue;
        int newCharValue;
        texto = texto.toUpperCase();
        StringBuilder encriptado = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {
            inputChar = texto.charAt(i);
            charValue = alfabeto.indexOf(inputChar);
            newCharValue = (charValue - clave) % alfabeto.length();

            if (newCharValue < 0) {
                newCharValue += alfabeto.length();
            }

            encriptado.append(alfabeto.charAt(newCharValue));
        }
        return encriptado.toString();
    } 
    
    //CODIFICACION CESAR

    public static SecretKey keygenKeyGeneration(int keySize) {
        SecretKey sKey = null;
        if ((keySize == 128) || (keySize == 192) || (keySize == 256)) {
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(keySize);
                sKey = kgen.generateKey();
            } catch (NoSuchAlgorithmException ex) {
                System.err.println("Generador no disponible.");
            }
        }
        return sKey;
    }

    public static byte[] encryptData(SecretKey sKey, byte[] data) {
        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sKey);
            encryptedData = cipher.doFinal(data);
        } catch (Exception ex) {
            System.err.println("Error cifrando los datos: " + ex);
        }
        return encryptedData;
    }

    public static byte[] decryptData(SecretKey sKey, byte[] data) {
        byte[] decryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, sKey, cipher.getParameters());
            decryptedData = cipher.doFinal(data);
        } catch (Exception ex) {
            System.err.println("Error descrifrando los datos: " + ex);
        }
        return decryptedData;
    }

    public static String byteToHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }

}
