package EDC;

import org.junit.Assert;
import org.junit.Test;

import java.security.KeyPair;

import static org.junit.Assert.*;

public class DigitalSignatureTest {

    @Test
    public void generateKeyPair() {
        DigitalSignature ds = new DigitalSignature();

        KeyPair keyPair = ds.generateKeyPair();
        System.out.println(keyPair.getPrivate().toString());
        System.out.println(keyPair.getPublic().toString());
        Assert.assertTrue(true);
    }
}