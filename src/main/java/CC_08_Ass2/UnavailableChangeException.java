package CC_08_Ass2;

public class UnavailableChangeException extends Exception{
    public UnavailableChangeException(){
        super("No available change to dispense");
    }
}
