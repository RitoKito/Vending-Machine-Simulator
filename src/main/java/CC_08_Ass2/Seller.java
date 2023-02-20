package CC_08_Ass2;

public class Seller extends User{
    public Seller(int userID, String userName) {
        super(userID, userName);
        this.role = "Seller";
    }
}
