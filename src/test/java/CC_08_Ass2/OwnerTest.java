package CC_08_Ass2;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OwnerTest {
    @Test public void UserNameTest(){
        Owner owner = new Owner(1000, "nathankalanski");
        assertEquals(owner.getUserName(), "nathankalanski");
    }

    @Test public void addUserTest(){
        Owner owner = new Owner(1000, "nathankalanski");
        User kjkj = owner.findUserByName("kjkj");
        assertNull(kjkj);

        owner.add_user(1200, "kjkj", "123");

        kjkj = owner.findUserByName("kjkj");
        assertNotNull(kjkj);
        owner.remove_user("1200");
    }
    @Test public void removeUserTest(){
        Owner owner = new Owner(1000, "nathankalanski");
        owner.add_user(1200, "kjkj", "123");

        User kjkj = owner.findUserByName("kjkj");
        assertNotNull(kjkj);
        owner.remove_user("1200");
        kjkj = owner.findUserByName("kjkj");
        assertNull(kjkj);
    }
    @Test public void ChangeRoleTest(){
        Owner owner = new Owner(1000, "nathankalanski");

        owner.add_user(1200, "kjkj", "123");

        User kjkj = owner.findUserByName("kjkj");
        assertNotNull(kjkj);

        String before = kjkj.getRole();
        //1System.out.println(before);
        owner.changeRole("kjkj", "Seller");
        kjkj = owner.findUserByName("kjkj");
        String after = kjkj.getRole();
        assertNotSame(before, after);
        assertEquals(after, "Seller");

        owner.remove_user("1200");
    }
}
