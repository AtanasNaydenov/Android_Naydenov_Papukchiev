package fhictcompanion_api_helper;

import android.util.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Daniel on 23-Mar-17.
 */

public class ApiHelper {

    private String token;
    public ApiHelper(String token){

        this.token = token;
    }
    public JsonReader GetGrades(){

        return GetGenericReader("grades/me");
    }
    public JsonReader GetAccount(){

        return GetGenericReader("people/me");
    }
    public JsonReader GetSchedule(){

        return  GetGenericReader("schedule/me");
    }
    private JsonReader GetGenericReader(String endpoint){
        try {
            URL url = new URL("https://api.fhict.nl/" + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + this.token);
            connection.connect();
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            return new JsonReader(isr);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }
}
