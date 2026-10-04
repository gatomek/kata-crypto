package pl.gatomek.crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class Actor {
    protected KeyPair keyPair;

    public void init() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(4096);
        keyPair = keyGen.generateKeyPair();
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }
}
