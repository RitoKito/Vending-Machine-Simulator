package CC_08_Ass2;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    public static User checkCredentials(String username, String password){
        String userbasePath = "./src/main/resources/Userbase.csv";

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(userbasePath));
            String line = reader.readLine();
            while (line != null) {
                String[] item = line.split(",");
                if(item[1].replace("\"", "").equalsIgnoreCase(username) && item[2].replace("\"", "").equals(password))
                {
                    if(item[3].replace("\"", "").equals("Seller")) {
                        return new Seller(Integer.parseInt(item[0].replace("\"", "")), item[1].replace("\"", ""));
                    }
                    if(item[3].replace("\"", "").equals("Cashier")){
                        return new Cashier(Integer.parseInt(item[0].replace("\"", "")), item[1].replace("\"", ""));
                    }
                    if(item[3].replace("\"", "").equals("Owner")){
                        return new Owner(Integer.parseInt(item[0].replace("\"", "")), item[1].replace("\"", ""));
                    }
                    if(item[3].replace("\"", "").equals("User")){
                        return new User(Integer.parseInt(item[0].replace("\"", "").replace("\"", "")), item[1].replace("\"", ""));
                    }
                    else {
                        return null;
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User registerUser(int userID, String username, String password, String role){
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("./src/main/resources/Userbase.csv", true));

            String[] userDetails = new String[4];
            userDetails[0] = String.valueOf(userID);
            userDetails[1] = username;
            userDetails[2] = password;
            userDetails[3] = "User";

            writer.writeNext(userDetails);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new User(userID, username);
    }

    public int getLatestUserID(){
        String userbasePath = "./src/main/resources/Userbase.csv";

        BufferedReader reader;

        int max = 0;
        try {
            reader = new BufferedReader(new FileReader(userbasePath));
            String line = reader.readLine();

            while (line != null) {
                String[] item = line.split(",");
                if(Integer.valueOf(item[0].replace("\"", "")) > max)
                {
                    max = Integer.valueOf(item[0].replace("\"", ""));
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return max;
    }

    public boolean isUsernameAvailable(String username){
        String userbasePath = "./src/main/resources/Userbase.csv";

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(userbasePath));
            String line = reader.readLine();

            while (line != null) {
                String[] item = line.split(",");
                if(item[1].replace("\"", "").equalsIgnoreCase(username))
                {
                    System.out.println("Username taken");
                    return false;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public void saveNewCreditCard(String username, String cardNumber, String cardName) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("./src/main/resources/SavedCards.csv", true));
            String[] details = new String[3];
            details[0] = username;
            details[1] = cardNumber;
            details[2] = cardName;

            writer.writeNext(details);


            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //rework in owner for userbase
    public void saveUserCard(String username, String cardNumber, String cardName){
        try {
            CSVReader reader = new CSVReader(new FileReader("./src/main/resources/SavedCards.csv"));
            List<String[]> csv = reader.readAll();

            System.out.println("TRYING TO SAVE");

            boolean existingCardChanged = false;
            int i = 0;
            for(String[] stringArray: csv){
                if(csv.get(i)[0].replace("\"", "").equals(username)){
                    csv.get(i)[1] = cardNumber;
                    csv.get(i)[2] = cardName;
                    existingCardChanged = true;
                    System.out.println("OVERWROTE EXISTING CARD");
                    break;
                }
                i++;
            }

            reader.close();

            if(!existingCardChanged){
                saveNewCreditCard(username, cardNumber, cardName);
                System.out.println("WROTE NEW CARD");
                return;
            }

            CSVWriter writer = new CSVWriter(new FileWriter("./src/main/resources/SavedCards.csv"));
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

    public boolean checkExistingCard(String username, String cardNumber, String cardName){
        try {
            CSVReader reader = new CSVReader(new FileReader("./src/main/resources/SavedCards.csv"));
            List<String[]> csv = reader.readAll();

            boolean existingCardChanged = false;
            int i = 0;
            for (String[] stringArray : csv) {
                if (csv.get(i)[0].replace("\"", "").equals(username)) {
                    if(csv.get(i)[1].replace("\"", "").equals(cardNumber)
                    && csv.get(i)[2].replace("\"", "").equals(cardName))
                    {
                        reader.close();
                        return true;
                    }
                    reader.close();
                    return false;
                }
                i++;
            }

            reader.close();

            return false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkIfCustomerHasExistingCard(String username){
        try {
            CSVReader reader = new CSVReader(new FileReader("./src/main/resources/SavedCards.csv"));
            List<String[]> csv = reader.readAll();

            boolean existingCardChanged = false;
            int i = 0;
            for (String[] stringArray : csv) {
                if (csv.get(i)[0].replace("\"", "").equals(username)) {
                    reader.close();
                    return true;
                }
                i++;
            }

            reader.close();
            return false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] getCustomerExistingCard(String username) {
        try {
            CSVReader reader = new CSVReader(new FileReader("./src/main/resources/SavedCards.csv"));
            List<String[]> csv = reader.readAll();

            boolean existingCardChanged = false;
            int i = 0;
            for (String[] stringArray : csv) {
                if (csv.get(i)[0].replace("\"", "").equals(username)) {
                    String[] userCard = new String[2];
                    userCard[0] = csv.get(i)[1].replace("\"", "");
                    userCard[1] = csv.get(i)[2].replace("\"", "");
                    reader.close();

                    return userCard;
                }
                i++;
            }

            reader.close();
            return null;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
