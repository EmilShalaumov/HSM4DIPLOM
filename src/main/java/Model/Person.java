package Model;

import com.mongodb.BasicDBObject;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Timestamp;
import java.util.UUID;
import org.bson.Document;
import org.bson.types.ObjectId;

import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;
import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64String;

public class Person {
    private ObjectId _id = null;
    private final String _name;
    private byte[] _privatekey = null;
    private byte[] _publicKey = null;
    private String _onetimepassword = null;
    private Timestamp _otptimestamp = null;

    public Person(String name) {
        _name = name;
    }

    public Person(ObjectId id, String name, PrivateKey privatekey, PublicKey publickey, String otp, Timestamp otptimestamp) {
        _id = id;
        _name = name;
        _privatekey = privatekey.getEncoded();
        _publicKey = publickey.getEncoded();
        _onetimepassword = otp;
        _otptimestamp = otptimestamp;
    }

    public Person(Document doc) {
        _id = (ObjectId) doc.get("_id");
        _name = (String) doc.get("name");
        _privatekey = decodeBase64((String) doc.get("privatekey"));
        _publicKey = decodeBase64((String) doc.get("publickey"));
        _onetimepassword = (String) doc.get("onetimepassword");
        _otptimestamp = (Timestamp) doc.get("otptimestamp");
    }

    public boolean appendKeyPair(KeyPair keyPair) {
        _privatekey = keyPair.getPrivate().getEncoded();
        _publicKey = keyPair.getPublic().getEncoded();
        return true;
    }

    public ObjectId getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public byte[] getPrivatekey() {
        return _privatekey;
    }

    public byte[] getPublicKey() {
        return _publicKey;
    }

    public String getOnetimepassword() {
        return _onetimepassword;
    }

    public Timestamp getOtptimestamp() {
        return _otptimestamp;
    }

    public Document PersonToBasicDB() {
        Document basicDB = new Document();
        basicDB.put("name", _name);
        basicDB.put("privatekey", encodeBase64String(_privatekey));
        basicDB.put("publickey", encodeBase64String(_publicKey));
        basicDB.put("onetimepassword", _onetimepassword);
        basicDB.put("otptimestamp", _otptimestamp);
        return basicDB;
    }

}
