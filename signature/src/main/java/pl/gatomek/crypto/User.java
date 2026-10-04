package pl.gatomek.crypto;

import lombok.Getter;
import lombok.Setter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

        Cipher encryptCipher = Cipher.getInstance( "RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
        byte[] bytes = encryptCipher.doFinal(digest);
        String signature = Base64.getEncoder().encodeToString(bytes);

        return Message.builder()
                .from(name)
                .to(to)
                .content(content)
                .signature(signature)
                .hashAlgorithm("SHA-256")
                .build();
    }

    public void receiveMessage(Message msg) {

    }
}
