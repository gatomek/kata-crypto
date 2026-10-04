# Keys

## Links
* https://docs.oracle.com/javase/8/docs/api/java/security/SecureRandom.html
* https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html#SecureRandom

## Checking RSA private key integrity
```sh
openssl rsa -in rsa-priv.key -check -noout
```