package CC_08_Ass2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CashInventoryTest {
    @Test
    public void testReturnCash() {
        CashInventory ci = new CashInventory();
        ci.returnCash(100 + 50 + 20 + 10 + 5 + 2 + 1 + 0.5 + 0.2 + 0.1 + 0.05, ci.getCashInventory());
        System.out.println(ci.getChangeReturned());
        int[] checkArr = {1,1,1,1,1,1,1,1,1,1,1};
        assertArrayEquals(ci.getChangeReturned(),checkArr);
    }

    @Test
    public void testReturnCash2() {
        CashInventory ci = new CashInventory();
        ci.returnCash(0.02, ci.getCashInventory());
        System.out.println(ci.getChangeReturned());
        int[] checkArr = {0,0,0,0,0,0,0,0,0,0,0};
        assertArrayEquals(ci.getChangeReturned(),checkArr);
    }

//    @Test
//    public void testAcceptInsertedCash() {
//        CashInventory ci = new CashInventory();
//        ArrayList<Currency> cashInv = new ArrayList<Currency>(ci.getCashInventory()) ;
//
//        int desiredQuant = cashInv.get(10).getQuantity() + 1;
//        ci.acceptInsertedCash(cashInv.get(10));
//        int desiredQuant2 = cashInv.get(9).getQuantity() + 1;
//        ci.acceptInsertedCash(cashInv.get(9));
//        int desiredQuant3 = cashInv.get(8).getQuantity() + 1;
//        ci.acceptInsertedCash(cashInv.get(8));
//        int desiredQuant4 = cashInv.get(7).getQuantity() + 1;
//        ci.acceptInsertedCash(cashInv.get(7));
//        int desiredQuant5 = cashInv.get(6).getQuantity() + 1;
//        ci.acceptInsertedCash(cashInv.get(6));
//        int desiredQuant6 = cashInv.get(5).getQuantity() + 1;
//        ci.acceptInsertedCash(cashInv.get(5));
//        int desiredQuant7 = cashInv.get(4).getQuantity() + 1;
//        ci.acceptInsertedCash(cashInv.get(4));
//        ci.acceptInsertedCash(cashInv.get(3));
//
//        assertEquals(desiredQuant,ci.getCashInventory().get(10).getQuantity());
//        assertEquals(desiredQuant2,ci.getCashInventory().get(9).getQuantity());
//        assertEquals(desiredQuant3,ci.getCashInventory().get(8).getQuantity());
//        assertEquals(desiredQuant4,ci.getCashInventory().get(7).getQuantity());
//        assertEquals(desiredQuant5,ci.getCashInventory().get(6).getQuantity());
//        assertEquals(desiredQuant6,ci.getCashInventory().get(5).getQuantity());
//        assertEquals(desiredQuant7,ci.getCashInventory().get(4).getQuantity());
//    }
    @Test
    public void testAcceptInsertedCash() {
        CashInventory ci = new CashInventory();
        ArrayList<Currency> cashInv = new ArrayList<Currency>(ci.getCashInventory()) ;

       int desiredQuant = cashInv.get(10).getQuantity() + 1;
       ci.acceptInsertedCash(cashInv.get(10));
       int desiredQuant2 = cashInv.get(9).getQuantity() + 1;
       ci.acceptInsertedCash(cashInv.get(9));
       int desiredQuant3 = cashInv.get(8).getQuantity() + 1;
       ci.acceptInsertedCash(cashInv.get(8));
       int desiredQuant4 = cashInv.get(7).getQuantity() + 1;
       ci.acceptInsertedCash(cashInv.get(7));
       int desiredQuant5 = cashInv.get(6).getQuantity() + 1;
       ci.acceptInsertedCash(cashInv.get(6));
       int desiredQuant6 = cashInv.get(5).getQuantity() + 1;
       ci.acceptInsertedCash(cashInv.get(5));
       int desiredQuant7 = cashInv.get(4).getQuantity() + 1;
       ci.acceptInsertedCash(cashInv.get(4));

       assertEquals(desiredQuant,ci.getCashInventory().get(10).getQuantity());
       assertEquals(desiredQuant2,ci.getCashInventory().get(9).getQuantity());
       assertEquals(desiredQuant3,ci.getCashInventory().get(8).getQuantity());
       assertEquals(desiredQuant4,ci.getCashInventory().get(7).getQuantity());
       assertEquals(desiredQuant5,ci.getCashInventory().get(6).getQuantity());
       assertEquals(desiredQuant6,ci.getCashInventory().get(5).getQuantity());
       assertEquals(desiredQuant7,ci.getCashInventory().get(4).getQuantity());
    }



    @Test
    public void testReturnInsertedCash() {
        CashInventory ci = new CashInventory();
        ArrayList<Currency> cashInv = new ArrayList<Currency>(ci.getCashInventory()) ;

        int desiredQuant = cashInv.get(10).getQuantity();
        ci.acceptInsertedCash(cashInv.get(10));
        ci.returnInsertedCash();
        assertEquals(desiredQuant,ci.getCashInventory().get(10).getQuantity());
    }

    @Test
    public void testNotEnoughChange() {
        CashInventory ci = new CashInventory();
        assertFalse(ci.checkAvailableChange(99999999));
    }

    @Test
    public void testCheckAvailableChange() {
        CashInventory ci = new CashInventory();
        assertTrue(ci.checkAvailableChange(100 + 50 + 20 + 10 + 5 + 2 + 1 + 0.5 + 0.2 + 0.1));
    }

    @Test
    public void testCheckAvailableChange2() {
        CashInventory ci = new CashInventory();
        assertFalse(ci.checkAvailableChange(0.03));
    }
}
