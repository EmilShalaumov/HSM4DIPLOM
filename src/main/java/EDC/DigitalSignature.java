package EDC;

import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class DigitalSignature {
    String encryptionAlgorithm = "RSA";
    String signatureAlgorithm = "MD5withRSA";

    public KeyPair generateKeyPair() {
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(encryptionAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(encryptionAlgorithm + " encryption algorithm is not supported.");
            e.printStackTrace();
            return null;
        }
        return keyPairGenerator.generateKeyPair();
    }

    public byte[] signBytes(byte[] source, byte[] privKeyBytes) {
        Signature signature;
        SecureRandom secureRandom = new SecureRandom();
        try {
            KeyFactory kf = KeyFactory.getInstance(encryptionAlgorithm);
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privKeyBytes);
            PrivateKey privKey = kf.generatePrivate(privateKeySpec);
            signature = Signature.getInstance(signatureAlgorithm);
            signature.initSign(privKey, secureRandom);
            signature.update(source);
            return signature.sign();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(signatureAlgorithm + " encryption algorithm is not supported.");
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException | InvalidKeySpecException e) {
            System.out.println("Private Key is invalid.");
            e.printStackTrace();
            return null;
        } catch (SignatureException e) {
            System.out.println("Unable to sign file.");
            e.printStackTrace();
            return null;
        }
    }


}
