package entity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WordsDBWordValidator implements WordValidator {
    public boolean wordIsValid(String word) throws WordValidationException {
        try {
            URL url = new URL("https://scrabble.us.wordsdb.ws/validateDict/" + word);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            JSONObject response = new JSONObject(content.toString());
            return response.getBoolean("v");
        } catch (MalformedURLException e) {
            throw new WordValidationException("Wrong URL", e);
        } catch (IOException e) {
            throw new WordValidationException("IOException", e);
        }
    }
}
