package Database;

import EDC.DigitalSignature;
import Model.Person;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.security.KeyPair;

import static com.mongodb.client.model.Filters.eq;

public class DatabaseAdapter {
    private static MongoDatabase database;
    private static boolean dbConnectionIsOpen = false;

    public static boolean initializeDbConnection() {
        if (dbConnectionIsOpen)
            System.out.println("DB connection is already open");
        else
            try {
                MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://HSMADMIN:HSMADMIN@cluster0-9aklk.gcp.mongodb.net/test?retryWrites=true\n"));
                database = mongoClient.getDatabase("hsmdb");
                dbConnectionIsOpen = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        return dbConnectionIsOpen;
    }

    public Person addPerson(String name) {
        if(dbConnectionIsOpen) {
            MongoCollection<Document> hsmusers = database.getCollection("hsmusers");
            if (hsmusers.countDocuments(new Document("name", name)) > 0) {
                System.out.println("Person with name " + name + " already exists.");
                return null;
            }
            Person newPerson = new Person(name);
            DigitalSignature ds = new DigitalSignature();
            KeyPair keyPair = ds.generateKeyPair();
            newPerson.appendKeyPair(keyPair);
            hsmusers.insertOne(newPerson.PersonToBasicDB());
            return getPerson(name);
        }
        return null;
    }

    public Person getPerson(String name) {
        if(dbConnectionIsOpen) {
            MongoCollection<Document> hsmusers = database.getCollection("hsmusers");
            Document person = hsmusers.find(new Document("name", name)).first();
            if (person == null) {
                System.out.println("Person with name " + name + " doesn't exist.");
                return null;
            }
            return new Person(person);
        }
        return null;
    }




}
