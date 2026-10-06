package pl.gatomek.crypto;

public record Message(String from, String to, String content, String signature, String hashAlgorithm) {
}
