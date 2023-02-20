package CC_08_Ass2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CardReaderManager {

    public Boolean checkCard(String number, String name) {
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader("./src/main/resources/credit_cards.json")){
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            for (Object o : jsonArray) {
                JSONObject person = (JSONObject) o;

                String name1 = (String) person.get("name");
                String number1 = (String) person.get("number");


                if (name1.equals(name) && number1.equals(number)) {
                    return true;
                }
            }

        }
        catch (IOException e){
            System.out.println("JSON Reading error");
            System.out.println(e);
        }
        catch (ParseException e){
            System.out.println("JSON Parsing error");
            System.out.println(e);
        }

        return false;
    }
}
