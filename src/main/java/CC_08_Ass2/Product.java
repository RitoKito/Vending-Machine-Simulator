package CC_08_Ass2;

public class Product {
    private int productID;
    public int getProductID() { return productID; }
    public void setProductID(int val) { productID = val; }

    private String productType;
    public String getProductType() { return productType; }
    public void setProductType(String val) { productType = val; }

    private String productArt;
    public String getProductArt() { return productArt; }

    private String name;
    public String getName() { return name; }
    public void setProductName(String val) { name = val; }

    private double price;
    public double getPrice() { return price; }
    public void setPrice(double val) { price = val; }

    private int stock;
    public int getStock() { return stock; }

    private int totalSold;
    public int getTotalSold() { return totalSold; }

    public Product(int productID, String productType ,String name, double price, int stock, int totalSold){
        this.productID = productID;
        this.productType = productType;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.totalSold = totalSold;

        setProductArt(productType);
    }

    public void setProductArt(String productType) {
        switch (productType){
            case "Drink":
                productArt =
                          "  _\n" +
                        " | |\n" +
                        ".' `.\n" +
                        "|   | Drink\n" +
                        "|   |\n" +
                        "|__|\n";
                break;
            case "Chocolate":
                productArt =
                        "[][][]\n" +
                        "[][][]\n" +
                        "[][][] Chocolate\n" +
                        "[][][]\n" +
                        "[][][]";
                break;
            case "Chips":
                productArt =
                        "+---\\/--+\n" +
                        "|             |\n" +
                        "|             | Chips\n" +
                        "|             | \n" +
                        "+--------+";
                break;
            case "Candies":
                productArt =
                        " /\\.--./\\\n" +
                        " \\/'--'\\/ Candy";
                break;

        }
    }

    public void reduceStock(Integer amount){
        if(stock > amount) {
            this.stock -= amount;
        } else {
            System.out.println("No products left");
            this.stock -= amount;
        }

    }

    public void restock(Integer amount){
        stock += amount;
    }

    public void setTotalSales(int amount){
        totalSold += amount;
    }
}
