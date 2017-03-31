package fhictcompanion_grades;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import fhictcompanion_api_helper.ApiHelper;

/**
 * Created by Daniel on 23-Mar-17.
 */

public class GetGradesTask extends AsyncTask<String, Void, ArrayList<Grade>> {

    private IGetGradesDisplay context;
    public GetGradesTask(IGetGradesDisplay context){

        this.context = context;
    }
    @Override
    protected void onPostExecute(ArrayList<Grade> grades) {
        super.onPostExecute(grades);
        this.context.DisplayGrades(grades);
    }

    @Override
    protected ArrayList<Grade> doInBackground(String... params) {

        if (params.length > 0 && !params[0].isEmpty()) {

            try {

                Log.d("TASK", "doInBackground: GETTING GRADES");
                ApiHelper api = new ApiHelper(params[0]);
                JsonReader jsonReader = api.GetGrades();
                ArrayList<Grade> grades = new ArrayList<Grade>();

                if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {

                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {

                        if (jsonReader.peek() == JsonToken.BEGIN_OBJECT) {

                            jsonReader.beginObject();
                            Grade g = new Grade();

                            while (jsonReader.hasNext()) {
                                switch (jsonReader.nextName()) {
                                    case "item":
                                        if (jsonReader.peek() == JsonToken.STRING) {
                                            g.description = jsonReader.nextString();
                                        }
                                        break;
                                    case "date":
                                        if (jsonReader.peek() == JsonToken.STRING) {
                                            String data = jsonReader.nextString();
                                            Log.d("RESPONSE1", "doInBackground: " + data);
                                            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                            Date date = parser.parse(data);
                                            g.date = date;
                                        }
                                        break;
                                    case "itemCode":
                                        if (jsonReader.peek() == JsonToken.STRING) {
                                            g.itemCode = jsonReader.nextString();
                                        }
                                        break;
                                    case "passed":
                                        if (jsonReader.peek() == JsonToken.BOOLEAN) {
                                            g.passed = jsonReader.nextBoolean();
                                        }
                                        break;
                                    case "grade":
                                        if (jsonReader.peek() == JsonToken.NUMBER) {
                                            g.grade = jsonReader.nextDouble();
                                        }
                                        break;
                                    default:
                                        jsonReader.skipValue();

                                }
                            }
                            grades.add(g);
                            jsonReader.endObject();
                        }
                    }
                    jsonReader.endArray();
                    Arrays.sort(grades.toArray());
                    return grades;
                }
                return null;
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        return null;
    }

    public interface IGetGradesDisplay{

        void DisplayGrades(ArrayList<Grade> grades);
    }
}
