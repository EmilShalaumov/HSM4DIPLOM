package Database;

import Model.Person;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.ui.Model;

import static org.junit.Assert.*;

public class DatabaseAdapterTest {

    @Test
    public void initializeDbConnection() {
        boolean result = DatabaseAdapter.initializeDbConnection();
        System.out.println(result);
        Assert.assertTrue(result);
    }

    @Test
    public void addPerson() {
        DatabaseAdapter dbAdapter = new DatabaseAdapter();
        dbAdapter.initializeDbConnection();
        Person result = dbAdapter.addPerson("Emil");
        System.out.println(result.getId());
        //Assert.assertTrue(result);
    }

    @Test
    public void getPerson() {
        DatabaseAdapter dbAdapter = new DatabaseAdapter();
        dbAdapter.initializeDbConnection();
        Person person = dbAdapter.getPerson("Misha");
        System.out.println(person.getId());
    }
}