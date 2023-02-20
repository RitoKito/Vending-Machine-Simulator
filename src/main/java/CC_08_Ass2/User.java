package CC_08_Ass2;

import com.opencsv.CSVWriter;
import com.sun.javafx.scene.shape.ArcHelper;
import com.sun.jdi.InvocationException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class User {
    protected String role = "User";
    public String getRole() { return role; }
    protected int userID;
    public int getUserID() { return  userID; }
    protected String userName;
    public String getUserName() { return userName; }
    protected ShoppingCart cart;
    public ShoppingCart getCart() { return cart; }

    protected ArrayList<String> lastBoughtItems = new ArrayList<>();

    public ArrayList<String> getLastBoughtItems() {
        return lastBoughtItems;
    }

    public User(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
        this.cart = new ShoppingCart();
        loadLastBoughItems();
    }

    public ArrayList<String> loadLastBoughItems(){
        String userPath = String.format("./src/main/resources/UserInfo/%s", userName);
        File f = new File(userPath);
        if(f.exists() && !f.isDirectory()) {
            ArrayList<Product> products = new ArrayList<>();
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(userPath));
                String line = reader.readLine();
                while (line != null) {
                    String[] item = line.split(",");
                    try {
                        if (item[0] != null) {
                            lastBoughtItems.add(item[0].replace("\"", ""));
                        }
                        if (item[1] != null) {
                            lastBoughtItems.add(item[1].replace("\"", ""));
                        }
                        if (item[2] != null) {
                            lastBoughtItems.add(item[2].replace("\"", ""));
                        }
                        if (item[3] != null) {
                            lastBoughtItems.add(item[3].replace("\"", ""));
                        }
                        if (item[4] != null) {
                            lastBoughtItems.add(item[4].replace("\"", ""));
                        }
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }

                    line = reader.readLine();
                }
                for(String string: lastBoughtItems){
                    System.out.println(string);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }// go back

            return lastBoughtItems;
        }

        return null;
    }

    public void saveLastLoadItems(){
        try {
            String userPath = String.format("./src/main/resources/UserInfo/%s", userName);
            CSVWriter writer = new CSVWriter(new FileWriter(userPath));
            List<String[]> productDetails = new ArrayList<>();
            String[] productDeet = new String[5];
            if(lastBoughtItems.size() >= 1) {
                productDeet[0] = lastBoughtItems.get(0);
            }
            if(lastBoughtItems.size() >= 2) {
                productDeet[1] = lastBoughtItems.get(1);
            }
            if(lastBoughtItems.size() >= 3) {
                productDeet[2] = lastBoughtItems.get(2);
            }
            if(lastBoughtItems.size() >= 4) {
                productDeet[3] = lastBoughtItems.get(3);
            }
            if(lastBoughtItems.size() >= 5) {
                productDeet[4] = lastBoughtItems.get(4);
            }
            productDetails.add(productDeet);

            for (String[] d : productDetails) {
                writer.writeNext(d);
            }


            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
