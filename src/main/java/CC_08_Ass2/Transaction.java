package CC_08_Ass2;

import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import java.util.ArrayList;

public class Transaction {
    private boolean success;
    private String failureReason;
    private LocalDateTime dateTime;
    private String FormatDate;
    private String PaymentMethod;
    private String UserName = "Anonymous";
    private ArrayList<String> productList;
    public void addProduct(String name) { productList.add(name); }
    public ArrayList<String> getProductList() { return productList; }

    private ArrayList<Integer> productQuant;
    public void addProductQuant(int value) { productQuant.add(value); }

    private ArrayList<Integer> changeReturned;
    public void addChangeReturned(int quant) { changeReturned.add(quant); }

    private double amountPaid;
    public void setAmountPaid(double val) { amountPaid = val; }

    public Transaction(){
        productList = new ArrayList<>();
        changeReturned = new ArrayList<>();
        productQuant = new ArrayList<>();
    }


   public void setSuccess(boolean b){
    this.success = b;
    this.dateTime =  LocalDateTime.now(); //We know transaction has beeen completed when we have set whether its successsful
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
    this.FormatDate = this.dateTime.format(myFormatObj);
   }
   public void setfailureReason(String s){
    this.failureReason = s;
   }
   public void setDate(LocalDateTime date){
    this.dateTime = date;
   }
   public void setPaymentmethod(String PaymentMethod){
    this.PaymentMethod = PaymentMethod;
   }
   public void setUserName(String name){
    this.UserName = name;
   }   

   public boolean getSuccess(){
    return this.success;
   }
   public String getFailureReason(){
    return this.failureReason;
   }
   public LocalDateTime getDateTime(){
    return this.dateTime;
   }

   public String getPaymentmethod(){
    return this.PaymentMethod;
   }

   public String getCustomerName(){
    return this.UserName;
   }

   public String CancelledOutput(){
    String line;
    line =  this.FormatDate + "," + this.UserName+ "," + this.failureReason;
    return line;

   }

   public String successfulOutput(){
       String line;
       String products = "";

       int i = 0;
       for(String product: productList){
           products += product + " x" + productQuant.get(i) + "|";
       }
       line =  this.FormatDate + "," + this.UserName+ "," + this.PaymentMethod + "," +  products;
       return line;
   }

   public String cashReportOutput(){
       String line;
       String change = "";
       for(int i=0; i < 12; i++){
           if(i == 0){
               change += "5c: " + changeReturned.get(0) +"|";
           }
           if(i == 1){
               change += "10c: " + changeReturned.get(1) +"|";
           }
           if(i == 2){
               change += "20c: " + changeReturned.get(2) +"|";
           }
           if(i == 3){
               change += "50c: " + changeReturned.get(3) +"|";
           }
           if(i == 4){
               change += "$1: " + changeReturned.get(4) +"|";
           }
           if(i == 5){
               change += "$2: " + changeReturned.get(5) +"|";
           }
           if(i == 6){
               change += "$5: " + changeReturned.get(6) +"|";
           }
           if(i == 7){
               change += "$10: " + changeReturned.get(7) +"|";
           }
           if(i == 8){
               change += "$20: " + changeReturned.get(8) +"|";
           }
           if(i == 9){
               change += "$50: " + changeReturned.get(9) +"|";
           }
           if(i == 10){
               change += "$100c: " + changeReturned.get(10) +"|";
           }

           System.out.println(change);

           //i++;
       }

       String products = "";
       int i = 0;
       for(String product: productList){
           products += product + " x" + productQuant.get(i) + "|";
       }


       line =  this.FormatDate + "," + this.UserName+ "," + this.PaymentMethod + "," + "Total Paid: $" + amountPaid + "," + products + "," + change;
       return line;
   }
}
