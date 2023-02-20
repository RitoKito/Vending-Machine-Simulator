package CC_08_Ass2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class ShoppingCartTest {
    @Test
    public void testShoppingCart() {
        ShoppingCart cart = new ShoppingCart();
        Product product = new Product(5001,"Drink","Fanta",2.5,10,1);
        CartProduct cartProd = new CartProduct(product,5);

        cart.addProduct(cartProd);
        assertEquals(cart.getCartProduct(5001),cartProd);
        assertNotEquals(cart.getCartProduct(9999),cartProd);

        cart.updateTotal();
        assertEquals((2.5*5),cart.getTotalToPay());
        assertEquals((2.5*5),cart.getTotal());

        int num = cartProd.getQuantity();
        cart.increaseQuantity(cartProd);
        assertEquals(num + 1,cartProd.getQuantity());

        num = cartProd.getQuantity();
        cart.subtractQuantity(cartProd);
        assertEquals(num - 1,cartProd.getQuantity());

        double desTotal = cart.getTotalToPay() - 5;
        cart.subtractFromTotal(5);
        assertEquals(desTotal,cart.getTotalToPay());

        HashSet<CartProduct> desired = new HashSet<CartProduct>();
        desired.add(cartProd);
        assertIterableEquals(desired,cart.getCartProducts());

        desired.remove(cartProd);
        cart.removeProduct(cartProd);
        assertIterableEquals(desired,cart.getCartProducts());

        desired.clear();
        cart.clearCart();
        assertIterableEquals(desired,cart.getCartProducts());
    }
}
