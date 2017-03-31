package fhictcompanion_account;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import fhictcompanion_api_helper.ApiHelper;

import static android.content.ContentValues.TAG;

/**
 * Created by Daniel on 23-Mar-17.
 */
public class GetAccountTask extends AsyncTask<String, Void, Account> {
    IGetAccountDisplay context;
    public GetAccountTask(IGetAccountDisplay context){
        this.context = context;
    }
    @Override
    protected void onPostExecute(Account account) {
        super.onPostExecute(account);
        this.context.DisplayAccount(account);

    }

    @Override
    protected Account doInBackground(String... params) {

        if (!params[0].isEmpty()) {

            try {

                Log.d("TASK", "doInBackground: GETTING ACCOUNT");
                ApiHelper api = new ApiHelper(params[0]);
                JsonReader jsonReader = api.GetAccount();
                Account account = new Account();

                if (jsonReader.peek() == JsonToken.BEGIN_OBJECT) {

                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {

                        switch (jsonReader.nextName()) {
                            case "id":
                                if (jsonReader.peek() == JsonToken.STRING) {
                                    account.id = jsonReader.nextString();
                                }
                                break;
                            case "displayName":
                                if (jsonReader.peek() == JsonToken.STRING) {
                                    account.displayName = jsonReader.nextString();
                                }
                                break;
                            case "mail":
                                if (jsonReader.peek() == JsonToken.STRING) {
                                    account.mail = jsonReader.nextString();
                                }
                                break;
                            case "photo":
                                if (jsonReader.peek() == JsonToken.STRING) {
                                    account.photo = jsonReader.nextString();
                                }
                                break;
                            case "department":
                                if (jsonReader.peek() == JsonToken.STRING) {
                                    account.department = jsonReader.nextString();
                                }
                                break;
                            case "personalTitle":
                                if (jsonReader.peek() == JsonToken.STRING) {
                                    account.personalTitle = jsonReader.nextString();
                                }
                                break;
                            default:
                                jsonReader.skipValue();

                        }
                    }
                    jsonReader.endObject();
                    return account;
                }
                return null;
            } catch (Exception e) {

                return null;
            }
        }
        return null;
    }

    public interface IGetAccountDisplay{
        void DisplayAccount(Account account);
    }
}