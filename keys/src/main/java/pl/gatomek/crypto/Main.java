package pl.gatomek.crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

// todo: format PKCS#8 vs. X.509
// todo: algorithm DSA - Digital Signature Algorithm - any other algos? DSA, RSA
// todo: how can i check private and public keys?
// todo: saving keys to files
// todo: check with external tool correctness of generating the key pair

// pem - privacy-enhanced mail


public class Main {
    static void main() throws NoSuchAlgorithmException, NoSuchProviderException {
        IO.println("Private and Public Keys Generator");

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);

        KeyPair keyPair = keyGen.generateKeyPair();
        PrivateKey priv = keyPair.getPrivate();
        PublicKey publ = keyPair.getPublic();

        // priv
        String algorithm = priv.getAlgorithm();
        String format = priv.getFormat();
        AlgorithmParameterSpec params = priv.getParams();
        byte[] encoded = priv.getEncoded();
        String privStr = Base64.getEncoder().encodeToString(encoded);

        // publ
        String algorithm1 = publ.getAlgorithm();
        String format1 = publ.getFormat();
        byte[] encoded1 = publ.getEncoded();
        String publStr = Base64.getEncoder().encodeToString(encoded1);
    }
}
