package pl.gatomek.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Chat {
    protected static Map<String, PublicKey> publicKeyRepo = new HashMap<>();
    protected static Map<String, User> onlineUserRepo = new HashMap<>();

    protected static void logIn(User user) {
        publicKeyRepo.put(user.getName(), user.getPublicKey());
        onlineUserRepo.put(user.getName(), user);
    }

    protected static void sendMessage(Message msg) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        User user = onlineUserRepo.get(msg.to());
        user.receiveMessage(msg);
    }

    protected static PublicKey getPublicKeyOfUserName(String name) {
        return Optional.ofNullable(publicKeyRepo.get(name)).orElseThrow();
    }

    static void main() throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        IO.println("Welcome to signed chats!");

        User ala = new User("ala");
        User bob = new User("bob");

        logIn(ala);
        logIn(bob);

        sendMessage(ala.makeSignedMessage(bob.getName(), "How are you?"));
    }
}
