package CC_08_Ass2;

import java.util.HashSet;

public class ShoppingCart {
    private HashSet<CartProduct> cartProducts;
    public HashSet<CartProduct> getCartProducts() { return cartProducts; }

    private double totalToPay;
    public double getTotalToPay() { return Double.valueOf(String.format("%.2f", totalToPay)); }
    public void  subtractFromTotal(double val) { totalToPay -= val; }
    public void updateTotal() { totalToPay = getTotal(); }


    public ShoppingCart(){
        this.cartProducts = new HashSet<>();
    }

    public void addProduct(CartProduct cartProduct){
        cartProducts.add(cartProduct);
    }

    public void removeProduct(CartProduct cartProduct){
        cartProducts.remove(cartProduct);
    }

    public void subtractQuantity(CartProduct cartProduct){
        cartProduct.reduceQuantity(1);
    }

    public void increaseQuantity(CartProduct cartProduct){
        cartProduct.increaseQuantity(1);
    }

    public double getTotal(){
        double sum = 0;

        for(CartProduct product: cartProducts){
            sum += product.getTotalPrice();
        }

        return sum;
    }

    public CartProduct getCartProduct(int productID){
        for(CartProduct product: cartProducts){
//            System.out.println(productID);
            product.getProductID();
            if(product.getProductID() == productID){
                return product;
            }
        }

//        System.out.println("Product not found");
        return null;
    }

    public void clearCart(){
        cartProducts.clear();
        updateTotal();
    }
}
