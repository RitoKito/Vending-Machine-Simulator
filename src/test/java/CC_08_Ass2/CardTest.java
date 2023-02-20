package CC_08_Ass2;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class CardTest {
    @Test public void saveNewCreditCard(){
        Owner owner = new Owner(1100, "marty");
        owner.add_user(1300, "kjkj", "123");

        UserManager um = new UserManager();
        um.saveNewCreditCard("kjkj", "12345", "morgan");
        boolean exist = um.checkExistingCard("kjkj", "12345", "morgan");
        assertTrue(exist);

        owner.remove_user("1300");
    }

    @Test public void saveUserCard(){
        UserManager um = new UserManager();
        um.saveUserCard("RobertDaSeller", "34572", "Chad");

        CardReaderManager crm = new CardReaderManager();
        boolean exist = crm.checkCard("34572", "Chad");
        boolean saved = um.checkExistingCard("RobertDaSeller", "34572", "Chad");
        assertTrue(exist);
        assertTrue(saved);

        boolean notexist = um.checkExistingCard("Robert", "99999", "C");
        assertFalse(notexist);
    }

    @Test public void getCustomerExistingCard(){
        UserManager um = new UserManager();
        um.saveUserCard("RobertDaSeller", "34572", "Chad");
        String[] saved = um.getCustomerExistingCard("RobertDaSeller");
        assertNotNull(saved);

        String[] notexist = um.getCustomerExistingCard("usernamewithoutcard");
        assertNull(notexist);
    }
}
