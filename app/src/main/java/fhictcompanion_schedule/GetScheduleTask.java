package fhictcompanion_schedule;

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

public class GetScheduleTask extends AsyncTask<String, Void, ArrayList<ScheduleItem>> {

    private IGetScheduleDisplay context;
    public GetScheduleTask(IGetScheduleDisplay context){
        this.context = context;
    }
    @Override
    protected void onPostExecute(ArrayList<ScheduleItem> schedule) {
        super.onPostExecute(schedule);
        this.context.DisplaySchedule(schedule);
    }

    @Override
    protected ArrayList<ScheduleItem> doInBackground(String... params) {

        if (params.length > 0 && !params[0].isEmpty()) {

            try {

                Log.d("TASK", "doInBackground: GETTING SCHEDULE");
                ApiHelper api = new ApiHelper(params[0]);
                JsonReader jsonReader = api.GetSchedule();
                ArrayList<ScheduleItem> schedule = new ArrayList<ScheduleItem>();

                if (jsonReader.peek() == JsonToken.BEGIN_OBJECT) {

                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) { //begin response

                        if (jsonReader.nextName().equals("data") && jsonReader.peek() == JsonToken.BEGIN_ARRAY) {

                            jsonReader.beginArray(); //begin data

                            while (jsonReader.hasNext()) { //for each schedule item
                                if (jsonReader.peek() == JsonToken.BEGIN_OBJECT) {

                                    jsonReader.beginObject();
                                    ScheduleItem scheduleItem = new ScheduleItem();

                                    while (jsonReader.hasNext()) {
                                        switch (jsonReader.nextName()) {
                                            case "room":
                                                if (jsonReader.peek() == JsonToken.STRING) {
                                                    scheduleItem.room = jsonReader.nextString();
                                                }
                                                break;
                                            case "start":
                                                if (jsonReader.peek() == JsonToken.STRING) {
                                                    String data = jsonReader.nextString();
                                                    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                                    scheduleItem.start = parser.parse(data);
                                                }
                                                break;
                                            case "end":
                                                if (jsonReader.peek() == JsonToken.STRING) {
                                                    String data = jsonReader.nextString();
                                                    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                                    scheduleItem.end = parser.parse(data);
                                                }
                                                break;
                                            case "subject":
                                                if (jsonReader.peek() == JsonToken.STRING) {
                                                    scheduleItem.subject = jsonReader.nextString();
                                                }
                                                break;
                                            case "teacherAbbreviation":
                                                if (jsonReader.peek() == JsonToken.STRING) {
                                                    scheduleItem.teacherAbbreviation = jsonReader.nextString();
                                                }
                                                break;
                                            default:
                                                jsonReader.skipValue();

                                        }
                                    }

                                    if (scheduleItem.end.compareTo(new Date()) <= 1)
                                        schedule.add(scheduleItem);
                                    jsonReader.endObject();
                                }
                            }
                        } else {
                            jsonReader.skipValue();
                        }
                    }
                    jsonReader.endArray();
                    Arrays.sort(schedule.toArray());
                    return schedule;
                }
                jsonReader.endObject();
                return null;
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        return null;
    }
    public interface IGetScheduleDisplay{
        void DisplaySchedule(ArrayList<ScheduleItem> schedule);
    }
}