package pl.gatomek.crypto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Message {
    String content;
    String hashAlgorithm;
    String signature;
    String from;
    String to;
}
