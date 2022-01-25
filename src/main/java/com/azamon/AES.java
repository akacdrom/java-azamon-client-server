package com.azamon;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES {
    /*variables for key and IV*/
    private SecretKey key;
    private final int KEY_SIZE = 128;
    private final int T_LEN = 128;
    private byte[] IV;

    /*Key generator for keys*/
    public void init() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(KEY_SIZE);
        key = generator.generateKey();
    }

    /*initializing the IV and KEY */
    public void initFromStrings(String secretKey, String IV){
        key = new SecretKeySpec(decode(secretKey),"AES");
        this.IV = decode(IV);
    }

    /*Method for create data with not exist secret KEY and IV*/
    public String encryptSignUp(String message) throws Exception {

        /*convert message to byte array*/
        byte[] messageInBytes = message.getBytes();
        Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        IV = encryptionCipher.getIV();
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        return encode(encryptedBytes);
    }
    /*Method for create data with already exist secret KEY and IV*/
    public String encryptLogIn(String message) throws Exception {
        /*convert message to byte array*/
        byte[] messageInBytes = message.getBytes();
        Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN,IV);
        /*Initialize cipher and add the key parameter*/
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key,spec);
        /*Start Encryption*/
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        return encode(encryptedBytes);
    }

    public String decrypt(String encryptedMessage) throws Exception {
        /*convert message to byte array*/
        byte[] messageInBytes = decode(encryptedMessage);
        /*start decryption cipher*/
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        /*put IV parameter from the encrypt method. First parameter is key size*/
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, IV);
        /*initialize the decryption cipher*/
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
        return new String(decryptedBytes);
    }

    /*Export KEY and IV for future usages of same data*/
    public String exportKey(){
        return encode(key.getEncoded());
    }
    public String exportIV(){
        return encode(IV);
    }

    /*return the encoded data*/
    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    /*return the decoded data*/
    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

}