package CC_08_Ass2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CartProductTest {
    @Test
    public void testCartProduct() {
        Product product = new Product(5001,"Drink","Fanta",2.5,10,1);
        CartProduct cartProd = new CartProduct(product,5);
        cartProd.calculateTotalPrice();

        assertEquals((5*2.5),cartProd.getTotalPrice());
        assertEquals("Fanta",cartProd.getName());
        assertEquals(5001,cartProd.getProductID());
        assertEquals("Drink",cartProd.getProductType());
        assertEquals(5,cartProd.getQuantity());
        assertEquals(10,cartProd.getProductStock());
    }

    @Test
    public void testCartProductChanges() {
        Product product = new Product(5001,"Drink","Fanta",2.5,10,1);
        CartProduct cartProd = new CartProduct(product,5);

        cartProd.increaseQuantity(1);
        assertEquals(6,cartProd.getQuantity());

        cartProd.reduceQuantity(1);
        assertEquals(5,cartProd.getQuantity());

        cartProd.reduceProductStock(1);
        assertEquals(9,product.getStock());

        cartProd.raiseProductSales(1);
        assertEquals(2,product.getTotalSold());
    }
}
