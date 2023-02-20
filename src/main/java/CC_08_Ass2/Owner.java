package CC_08_Ass2;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.util.ArrayList;
import java.io.*;
import java.util.List;
import java.util.ArrayList;



public class Owner extends User{
    public static final String userbasePath = "./src/main/resources/Userbase.csv";

    public Owner(int userID, String userName) {
        super(userID, userName);
        this.role = "Owner";
    }

    public String getUserName() { return userName; }
    public User findUserByName(String name){
        ArrayList<User> users = Owner.user_report();
        for (User x : users){
            if (x.getUserName().equals(name)){
                return x;
            }
        }
        return null;
    }

    public static ArrayList<User> user_report(){
        ArrayList<User> users = new ArrayList<>();

        BufferedReader reader;
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("./reports/UserReport.csv"));
            reader = new BufferedReader(new FileReader(userbasePath));
            String line = reader.readLine();

            String[] headers = new String[6];
            headers[0] = "Username";
            headers[1] = "Role";
            writer.writeNext(headers);

            System.out.println("testing");
            while (line != null) {
                String[] item = line.split(",");
                //System.out.println(line);
                for (String g : item){
                    System.out.println(g);
                }
                String[] rep = {item[1].replace("\"", ""), item[3].replace("\"", "")};

                User userr = null;
                if(item[3].replace("\"", "").equals("Seller"))
                {
                    userr = new Seller(Integer.parseInt(item[0].replace("\"", "")), item[1].replace("\"", ""));
                }
                else if(item[3].replace("\"", "").equals("Cashier")){
                    System.out.println("IUldjp:aOOON");
                    userr = new Cashier(Integer.parseInt(item[0].replace("\"", "")), item[1].replace("\"", ""));
                }
                else if(item[3].replace("\"", "").equals("Owner")){
                    System.out.println("SHJMTDJEURY");
                    userr = new Owner(Integer.parseInt(item[0].replace("\"", "")), item[1].replace("\"", ""));
                }
                else{
                    //System.out.println(item[3]);
                    userr = new User(Integer.parseInt(item[0].replace("\"", "")), item[1].replace("\"", ""));
                }

                users.add(userr);
                writer.writeNext(rep);
                line = reader.readLine();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }
    public static void remove_user(String id){
        ArrayList<Owner> users = new ArrayList<>();
        ArrayList<String[]> l = new ArrayList<>();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(userbasePath));
            String line = reader.readLine();

            while (line != null) {
                String[] item = line.split(",");
                l.add(item);
                line = reader.readLine();
            }
            reader.close();

            CSVWriter writer = new CSVWriter(new FileWriter(userbasePath));
            for(String[] x : l){

                ArrayList<String> p = new ArrayList<>();
                for (String kl : x){
                    p.add(kl.replace("\"", ""));
                }

                if(!(x[0].replace("\"", "")).equals(id)){
                    String[] stockArr = new String[p.size()];
                    stockArr = p.toArray(stockArr);
                    writer.writeNext(stockArr);
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void add_user(int userID, String username, String password){
        UserManager.registerUser(userID, username, password, "User");
    }

    public static void changeRole(String username, String role){ //user report when change roles // dropdown not text
        try {
            CSVReader reader = new CSVReader(new FileReader(userbasePath));
            List<String[]> csv = reader.readAll();

            System.out.println("TRYING TO SAV");

            int i = 0;
            for(String[] stringArray: csv){
                if(csv.get(i)[1].replace("\"", "").equals(username)){
                    csv.get(i)[3] = role;
                    System.out.println("OVERWROTE EXISTING ROLE");
                    break;
                }
                i++;
            }

            reader.close();

            CSVWriter writer = new CSVWriter(new FileWriter(userbasePath));
            writer.writeAll(csv);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}