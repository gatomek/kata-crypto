https://www.baeldung.com/java-aes-encryption-decryption
https://www.baeldung.com/java-rsa


## Issue
> Ręczne szyfrowanie skrótu kluczem prywatnym zamiast użycia dedykowanego API (java.security.Signature)
> Podpis cyfrowy RSA to nie jest po prostu „zaszyfrowanie skrótu kluczem prywatnym”. Prawidłowy podpis wymaga zastosowania odpowiedniego schematu dopełnienia (paddingu), np. PKCS#1 v1.5 z tzw. DigestInfo (struktura ASN.1) lub nowocześniejszego PSS (RSASSA-PSS).
> Użycie Cipher.getInstance("RSA") szyfruje surowe bajty bez prawidłowej struktury podpisu, co jest uznawane za podatność kryptograficzną i niezgodne ze standardem PKCS#1.
