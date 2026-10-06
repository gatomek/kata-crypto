package pl.gatomek.crypto;

// JCA - Java Cryptography Architecture
// NIST - National Institute of Standards and Technology
// PKCS5 - a padding algorithm
// PBKDF2 - password-based key derivation function (generowanie klucza na podstawie hasła
// PBKDF2WithHmacSHA256

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Six modes of operations
 * ecb - electronic code block
 * cbc - cipher block chaining
 * cfb - cipher feedback
 * ofb - output feedback
 * ctr - counter
 * gcm - galois/counter mode
 */

// todo: AES -> AES/GCM/NoPadding ?

public class Main {

    private static SecretKey makeSecretKey() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(secureRandom);
        return keyGenerator.generateKey();
    }

    private static byte[] encrypt(String content, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
    }

    private static String decrypt(byte[] encrypted, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] bytes = cipher.doFinal(encrypted);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    static void main() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        IO.println("AES symetric encryption and decryption");

        String content = "How are you?";

        SecretKey secretKey = makeSecretKey();
        IO.println("Secret Key:");
        IO.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));

        byte[] encrypted = encrypt(content, secretKey);
        IO.println("Encrypted content:");
        IO.println(Base64.getEncoder().encodeToString(encrypted));

        String decoded = decrypt(encrypted, secretKey);
        IO.println("Decrypted content:");
        IO.println(decoded);
    }
}
