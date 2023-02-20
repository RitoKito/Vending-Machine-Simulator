package CC_08_Ass2;

public class CartProduct {

    public CartProduct(Product product,
                       int quantity){

        this.product = product;
        this.productID = product.getProductID();
        this.productType = product.getProductType();
        this.name = product.getName();
        this.quantity = quantity;
        this.singlePrice = product.getPrice();
        this.totalPrice = singlePrice * quantity;
    }

    private int productID;
    public int getProductID() { return productID; }

    private Product product;

    private String productType;
    public String getProductType() { return productType; }

    private String name;
    public String getName() { return name; }

    private int quantity;

    public int getQuantity() { return quantity; }

    private double singlePrice;
    private double totalPrice;
    public double getTotalPrice() { return totalPrice; }
    public void calculateTotalPrice() { totalPrice = singlePrice * quantity; }


    public void reduceProductStock(int quantity){
        product.reduceStock(quantity);
    }

    public void reduceQuantity(int quantity){
        this.quantity -= quantity;
    }

    public void increaseQuantity(int quantity){
        this.quantity += quantity;
    }

    public int getProductStock(){
        return product.getStock();
    }

    public void raiseProductSales(int amount){
        product.setTotalSales(amount);
        product.getTotalSold();
    }

}
