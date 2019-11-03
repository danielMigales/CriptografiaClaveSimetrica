package algoritmo;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Daniel Migales
 */
public class Algoritmo {

    //CODIFICACION CESAR
    public static String codificarCesar(String texto, int clave, String alfabeto) {

        char inputChar;
        int charValue;
        int newCharValue;
        texto = texto.toUpperCase();
        StringBuilder encriptado = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {

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

    //DECODIFICACION CESAR
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

    //CODIFICACION AES
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

    public static SecretKey passwordKeyGeneration(String text, int keySize) {

        SecretKey sKey = null;

        if ((keySize == 128) || (keySize == 192) || (keySize == 256)) {
            try {

                byte[] data = text.getBytes("UTF-8");
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(data);
                byte[] key = Arrays.copyOf(hash, keySize / 8);
                sKey = new SecretKeySpec(key, "AES");
            } catch (Exception ex) {
                System.err.println("Error generant la clau:" + ex);
            }
        }
        return sKey;
    }

    //CODIFICACION ECB
    public static byte[] encryptDataECB(SecretKey sKey, byte[] data) {

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

    //DECODIFICACION ECB
    public static byte[] decryptDataECB(SecretKey sKey, byte[] data) {

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

    //CODIFICACION CBC
    public static final byte[] IV_PARAM = {0x00, 0x01, 0x02, 0x03,
        0x04, 0x05, 0x06, 0x07,
        0x08, 0x09, 0x0A, 0x0B,
        0x0C, 0x0D, 0x0E, 0x0F};

    public static byte[] encryptDataCBC(SecretKey sKey, byte[] data) {

        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV_PARAM);
            cipher.init(Cipher.ENCRYPT_MODE, sKey, iv);
            encryptedData = cipher.doFinal(data);
        } catch (Exception ex) {
            System.err.println("Error cifrando los datos: " + ex);
        }
        return encryptedData;
    }

    //DECODIFICACION CBC
    public static byte[] decryptDataCBC(SecretKey sKey, byte[] data) {

        byte[] decryptedData = null;
        try {
            Cipher decipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV_PARAM);
            decipher.init(Cipher.DECRYPT_MODE, sKey, iv);
            decryptedData = decipher.doFinal(data);
        } catch (Exception ex) {
            System.err.println("Error descrifrando los datos: " + ex);
        }
        return decryptedData;
    }

    //METODO DE BYTE A HEXADECIMAL
    public static String byteToHex(byte[] bytes) {

        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }

    //METODO DE STRING A HEXADECIMAL
    public static String toHex(String arg) {

        return String.format("%040x", new BigInteger(1, arg.getBytes(StandardCharsets.UTF_8)));
    }

    //METODO DE HEXADECIMAL A BYTE
    public static byte[] hexToByte(String hexadecimal) {

        byte[] byteConvertido = new byte[hexadecimal.length() / 2];
        for (int i = 0; i < byteConvertido.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(hexadecimal.substring(index, index + 2), 16);
            byteConvertido[i] = (byte) v;
        }
        return byteConvertido;
    }
}
