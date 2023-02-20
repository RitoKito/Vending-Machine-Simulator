package CC_08_Ass2;

public class Cashier extends User{
    public Cashier(int userID, String userName) {
        super(userID, userName);
        this.role = "Cashier";
    }
}
