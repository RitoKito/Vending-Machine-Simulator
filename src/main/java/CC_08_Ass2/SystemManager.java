
package CC_08_Ass2;

public class SystemManager {
    private Inventory inventory;
    public Inventory getInventory() { return inventory; }

    private CashInventory cashInventory;
    public CashInventory getCashier() { return cashInventory; }
    private UserManager userManager;
    public UserManager getUserManager() { return userManager; }

    public SystemManager(){
        inventory = new Inventory();
        cashInventory = new CashInventory();
        userManager = new UserManager();
        System.out.println(userManager.checkExistingCard("RobertDaSeller", "TEST1", "TEST1"));

//        Owner owne = new Owner(28, "gub", "Owner");
//        owne.changeRole("RobertDaSeller", "User");
    }
}

