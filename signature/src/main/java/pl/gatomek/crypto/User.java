package pl.gatomek.crypto;

import lombok.Getter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;

@Getter
public class User extends Actor {
    private final String name;

    public User(String name) throws NoSuchAlgorithmException {
        init();
        this.name = name;
    }

    public Message makeSignedMessage(String to, String content) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        String payload = to + "|" + name + "|" + content;

        MessageDigest digester = MessageDigest.getInstance("SHA-256");
        byte[] digest = digester.digest(payload.getBytes(StandardCharsets.UTF_8));

        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
        byte[] bytes = encryptCipher.doFinal(digest);
        String signature = Base64.getEncoder().encodeToString(bytes);

        return new Message( name, to, content, signature, "SHA-256");
    }

    public void receiveMessage(Message msg) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] sign = Base64.getDecoder().decode(msg.signature());
        PublicKey publicKey = Chat.getPublicKeyOfUserName(msg.from());
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] digestCandidate = encryptCipher.doFinal(sign);

        String payload = msg.to() + "|" + msg.from() + "|" + msg.content();
        MessageDigest digester = MessageDigest.getInstance(msg.hashAlgorithm());
        byte[] digest = digester.digest(payload.getBytes(StandardCharsets.UTF_8));

        boolean verified = Arrays.equals(digestCandidate, digest);
        String result = "ok";
    }
}
