package Spring;

import Database.DatabaseAdapter;
import EDC.DigitalSignature;
import Model.Person;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64String;

@RestController
public class SignatureController {
    DatabaseAdapter db = new DatabaseAdapter();
    DigitalSignature ds = new DigitalSignature();

    @PostMapping(value="/signFile"/*, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE*/)
    public String sign(@RequestParam("file") MultipartFile file,
         @RequestParam("schemaname") String schemaname,
         @RequestParam("password") String password,
         @RequestParam("username") String username) throws IOException, NoSuchAlgorithmException {

        if(schemaname.equals("MTUCI-EDC") && password.equals("PASWWORD")) {
            db.initializeDbConnection();
            Person person = db.getPerson(username);
            if (person == null) {
                System.out.println(username + " person is not presented in db.");
                person = db.addPerson(username);
            }

            //ds.signBytes(file.getBytes(), person.getPrivatekey());

            return encodeBase64String(ds.signBytes(file.getBytes(), person.getPrivatekey()));
        } else return null;
    }
}
