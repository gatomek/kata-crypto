package pl.gatomek.crypto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

// [ ] todo: format PKCS#8 vs. X.509
// [ ] todo: algorithm DSA - Digital Signature Algorithm - any other algos? DSA, RSA
// [ ] todo: how can i check private and public keys?
// [ ] todo: check with external tool correctness of generating the key pair

// [x] todo: saving keys to files

public class Main {
    static void main() throws NoSuchAlgorithmException, IOException {
        generateKeys("RSA", 4096);
    }

    private static void generateKeys(String algorithm, int keySize) throws NoSuchAlgorithmException, IOException {
        IO.println(algorithm + " Keys Generator (" + keySize + ")");

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
        keyGen.initialize(keySize);

        KeyPair keyPair = keyGen.generateKeyPair();

        File outFolder = new File("out");
        outFolder.mkdirs();

        PrivateKey priv = keyPair.getPrivate();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("out/" + algorithm.toLowerCase() + "-priv.key"))) {
            writer.write("-----BEGIN PRIVATE KEY-----\n");
            byte[] encoded = priv.getEncoded();
            String pemFormatted = Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(encoded);
            writer.write(pemFormatted);
            writer.write("\n-----END PRIVATE KEY-----\n");
        }

        PublicKey publ = keyPair.getPublic();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("out/" + algorithm.toLowerCase() + "-publ.key"))) {
            writer.write("-----BEGIN PUBLIC KEY-----\n");
            byte[] encoded = publ.getEncoded();
            String pemFormatted = Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(encoded);
            writer.write(pemFormatted);
            writer.write("\n-----END PUBLIC KEY-----\n");
        }
    }
}
