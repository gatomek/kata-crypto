package pl.gatomek.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Chat {
    protected static Map<String, PublicKey> publicKeyRepo = new HashMap<>();
    protected static Map<String, User> userRepo = new HashMap<>();
    protected static Deque<Message> channel = new ArrayDeque<>();

    protected static void register(User user) {
        publicKeyRepo.put(user.getName(), user.getPublicKey());
        userRepo.put(user.getName(), user);
    }

    protected static void sendMessage(Message msg) {
        channel.add(msg);
    }

    protected static PublicKey findPublicKeyByUserName(String name) {
        return Optional.ofNullable(publicKeyRepo.get(name)).orElseThrow();
    }

    static void main() throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        IO.println("Welcome to secure chat!");

        User ala = new User("ala");
        User tomi = new User("tomi");

        register(ala);
        register(tomi);

        Message msg = ala.makeSignedMessage("tomi", "How are you?");
        IO.println(msg);

        sendMessage(msg);
    }
}
