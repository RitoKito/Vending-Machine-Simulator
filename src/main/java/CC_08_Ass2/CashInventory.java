package CC_08_Ass2;


import com.opencsv.CSVWriter;
import com.sun.jdi.IntegerValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CashInventory {
    private ArrayList<Currency> cashList;
    public ArrayList<Currency> getCashInventory() {
        return cashList;
    }
    private ArrayList<Currency> customerInsertedCash;
    public ArrayList<Currency> getCustomerInsertedCash() { return customerInsertedCash; }
    public void insertCash(Currency c) { customerInsertedCash.add(c); }

    private int[] changeReturned = new int[11];
    public int[] getChangeReturned() { return changeReturned; }
    private void resetChangeReturned(){
        changeReturned[0] = 0;
        changeReturned[1] = 0;
        changeReturned[2] = 0;
        changeReturned[3] = 0;
        changeReturned[4] = 0;
        changeReturned[5] = 0;
        changeReturned[6] = 0;
        changeReturned[7] = 0;
        changeReturned[8] = 0;
        changeReturned[9] = 0;
        changeReturned[10] = 0;
    }

    public void returnInsertedCash() {
        for(Currency c: customerInsertedCash){
            if(c.getValue() == 0.05){ cashList.get(0).decreaseQuantity(1); }
            if(c.getValue() == 0.1){ cashList.get(1).decreaseQuantity(1); }
            if(c.getValue() == 0.2){ cashList.get(2).decreaseQuantity(1); }
            if(c.getValue() == 0.5){ cashList.get(3).decreaseQuantity(1); }
            if(c.getValue() == 1){ cashList.get(4).decreaseQuantity(1); }
            if(c.getValue() == 2){ cashList.get(5).decreaseQuantity(1); }
            if(c.getValue() == 5) { cashList.get(6).decreaseQuantity(1); }
            if(c.getValue() == 10) { cashList.get(7).decreaseQuantity(1); }
            if(c.getValue() == 20) { cashList.get(8).decreaseQuantity(1); }
            if(c.getValue() == 50) { cashList.get(9).decreaseQuantity(1); }
            if(c.getValue() == 100) { cashList.get(10).decreaseQuantity(1); }
        }
        customerInsertedCash.clear();
    }
    public void acceptInsertedCash(Currency c) {
        double amount = Double.valueOf(String.format("%.2f",c.getValue()));
        System.out.println("Amount is " + amount);
        System.out.println("getvalue is " + c.getValue());
        if(c.getValue() == 0.05){ System.out.println(cashList.get(0).getQuantity()); cashList.get(0).increaseQuantity(1); System.out.println(cashList.get(0).getQuantity()); }
        if(c.getValue() == 0.1){ cashList.get(1).increaseQuantity(1); }
        if(c.getValue() == 0.2){ cashList.get(2).increaseQuantity(1); }
        if(c.getValue() == 0.5){ cashList.get(3).increaseQuantity(1); }
        if(c.getValue() == 1){ cashList.get(4).increaseQuantity(1); }
        if(c.getValue() == 2){ cashList.get(5).increaseQuantity(1); }
        if(c.getValue() == 5) { cashList.get(6).increaseQuantity(1); }
        if(c.getValue() == 10) { cashList.get(7).increaseQuantity(1); }
        if(c.getValue() == 20) { cashList.get(8).increaseQuantity(1); }
        if(c.getValue() == 50) { cashList.get(9).increaseQuantity(1); }
        if(c.getValue() == 100) { cashList.get(10).increaseQuantity(1); }
        customerInsertedCash.add(c);
//        customerInsertedCash.clear();
    }

    private static String productpath = "./src/main/resources/Cash.csv";

    public CashInventory() {
        cashList = loadCashFile();
        customerInsertedCash = new ArrayList<>();
    }



    public void returnCash(double change, ArrayList<Currency> cashList) {
        resetChangeReturned();

        // loop while there is change remaining
        while(change > 0) {
            // Priority of cash order
            change = Double.valueOf(String.format("%.2f", change));
            System.out.println(change);
            if(change >= 100 && cashList.get(10).getQuantity() > 0)
            {
                change -= 100;
                System.out.println("$100 note returned");
                cashList.get(10).decreaseQuantity(1);

                changeReturned[10] += 1;
            }
            else if (change >= 50 && cashList.get(9).getQuantity() > 0) {
                change -= 50;
                System.out.println("$50 note returned");
                cashList.get(9).decreaseQuantity(1);

                changeReturned[9] += 1;
            } else if (change >= 20 && cashList.get(8).getQuantity() > 0) {
                change -= 20;
                System.out.println("$20 note returned");
                cashList.get(8).decreaseQuantity(1);

                changeReturned[8] += 1;
            } else if (change >= 10 && cashList.get(7).getQuantity() > 0) {
                change -= 10;
                System.out.println("$10 note returned");
                cashList.get(7).decreaseQuantity(1);

                changeReturned[7] += 1;
            } else if (change >= 5 && cashList.get(6).getQuantity() > 0) {
                change -= 5;
                System.out.println("$5 note returned");
                cashList.get(6).decreaseQuantity(1);

                changeReturned[6] += 1;
            } else if (change >= 2 && cashList.get(5).getQuantity() > 0) {
                change -= 2;
                System.out.println("$2 note returned");
                cashList.get(5).decreaseQuantity(1);

                changeReturned[5] += 1;
            } else if (change >= 1 && cashList.get(4).getQuantity() > 0) {
                change -= 1;
                System.out.println("$1 note returned");
                cashList.get(4).decreaseQuantity(1);

                changeReturned[4] += 1;
            } else if (change >= 0.5 && cashList.get(3).getQuantity() > 0) {
                change -= 0.5;
                System.out.println("50 cents returned");
                cashList.get(3).decreaseQuantity(1);

                changeReturned[3] += 1;
            } else if (change >= 0.2 && cashList.get(2).getQuantity() > 0) {
                change -= 0.2;
                System.out.println("20 cents returned");
                cashList.get(2).decreaseQuantity(1);

                changeReturned[2] += 1;
            } else if (change >= 0.1 && cashList.get(1).getQuantity() > 0) {
                change -= 0.1;
                System.out.println("$10 cents returned");
                cashList.get(1).decreaseQuantity(1);

                changeReturned[1] += 1;
            } else if (change >= 0.04 && cashList.get(0).getQuantity() > 0) {
                change -= 0.05;
                System.out.println("5 cents returned");
                cashList.get(0).decreaseQuantity(1);

                changeReturned[0] += 1;
            }
            System.out.printf("Remaining change: %.2f\n", change);
            if (change > 0 && change < 0.04 ) {
                System.out.println("That's all valid remaining change");
                break;
            }
        }

        for(Currency c: cashList) {
            System.out.printf("%d %s cash remaining\n", c.getQuantity(), c.getName());
        }

        customerInsertedCash.clear();
    }

    public ArrayList<Currency> loadCashFile(){
        ArrayList<Currency> cash = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(productpath));
            String line = reader.readLine();
            while (line != null) {
                String[] item = line.split(",");
                cash.add(new Currency(item[0].replaceAll("\"",""),Integer.parseInt(item[1].replaceAll("\"",""))));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }// go back

        for(Currency c: cash) {
            System.out.printf("%d %s cash remaining\n", c.getQuantity(), c.getName());
        }

        return cash;
    }

    public void saveToCashFile(ArrayList<Currency> cashList){
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("./src/main/resources/Cash.csv"));
            List<String[]> cashDetails = new ArrayList<>();
            for(Currency c: cashList){
                String[] cashDeet = new String[2];
                cashDeet[0] = c.getName();
                cashDeet[1] = String.valueOf(c.getQuantity());
                cashDetails.add(cashDeet);
            }

            for(String[] d: cashDetails){
                writer.writeNext(d);
            }


            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkAvailableChange(double change){
        if(change > getTotalCash())
        {
            System.out.println(change + ":" + getTotalCash());
            return false;
        }


        while(change > 0) {
            change = Double.valueOf(String.format("%.1f", change));
            if (change >= 100 && cashList.get(10).getQuantity() > 0) {
                change -= 100;
            }else if (change >= 50 && cashList.get(9).getQuantity() > 0) {
                change -= 50;
            }else if (change >= 20 && cashList.get(8).getQuantity() > 0) {
                change -= 20;
            }else if (change >= 10 && cashList.get(7).getQuantity() > 0) {
                change -= 10;
            }else if (change >= 5 && cashList.get(6).getQuantity() > 0) {
                change -= 5;
            }else if (change >= 2 && cashList.get(5).getQuantity() > 0) {
                change -= 2;
            }else if (change >= 1 && cashList.get(4).getQuantity() > 0) {
                change -= 1;
            }else if (change >= 0.5 && cashList.get(3).getQuantity() > 0) {
                change -= 0.5;
            }else if (change >= 0.2 && cashList.get(2).getQuantity() > 0) {
                change -= 0.2;
            }else if (change >= 0.1 && cashList.get(1).getQuantity() > 0) {
                change -= 0.1;
            } else if (change >= 0.05 && cashList.get(0).getQuantity() > 0) {
                change -= 0.05;
            } else if (change > 0) {
                return false;
            } else {
                return false;
            }
        }

        return true;
    }

    private double getTotalCash(){
        double sum = 0;
        for(Currency c: cashList){
            System.out.println(c.getQuantity());
            sum += c.getValue() * c.getQuantity();
        }

        return sum;
    }

    public void produceCashReport(){
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("./reports/CashReport.csv"));
            List<String[]> cashDetails = new ArrayList<>();
            for(Currency c: cashList){
                String[] cashDeet = new String[2];
                cashDeet[0] = c.getName();
                cashDeet[1] = String.valueOf(c.getQuantity());
                cashDetails.add(cashDeet);
            }

            String[] headers = new String[3];
            headers[0] = "Cash Type";
            headers[1] = "Quantity";

            writer.writeNext(headers);

            for(String[] d: cashDetails){
                writer.writeNext(d);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
