package CC_08_Ass2;

public class Currency {
    private String name;
    public String getName() { return name; }

    private double value;
    public double getValue() { return  value; }

    private int quantity;
    public int getQuantity() { return quantity; }
    public void increaseQuantity(int val) { quantity += val; }
    public void decreaseQuantity(int val) { quantity -= val; }

    public Currency(String name, int quantity){
        this.name = name;
        this.value = Double.valueOf(name.replaceAll("[$, c]", ""));
        this.quantity = quantity;
    }
}
