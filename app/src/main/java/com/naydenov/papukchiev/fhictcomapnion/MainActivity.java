package com.naydenov.papukchiev.fhictcomapnion;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.MenuItem;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements TokenFragment.OnFragmentInteractionListener {

    private String token;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_account:
                    getAccount();
                    return true;
                case R.id.navigation_schedule:
                    getSchedule();
                    return true;
                case R.id.navigation_grades:
                    getGrades();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(String token) {

        this.token = token;
        getAccount();
    }

    private void showMessage(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void getAccount() {

        new GetAccountTask().execute(this.token);
    }

    private void getGrades() {

        new GetGradesTask().execute(this.token);
    }

    private void getSchedule() {
        new GetScheduleTask().execute(this.token);
    }

    public class GetAccountTask extends AsyncTask<String, Void, Account> {
        @Override
        protected void onPostExecute(Account account) {
            super.onPostExecute(account);

            FragmentManager fragMgr = getFragmentManager();
            FragmentTransaction fragTrans = fragMgr.beginTransaction();
            AccountFragment hello = AccountFragment.newInstance(account);

            fragTrans.replace(R.id.fragmentPlaceholder, hello, "HELLO");
            fragTrans.commit();
        }

        @Override
        protected Account doInBackground(String... params) {

            if (!params[0].isEmpty()) {

                try {
                    URL url = new URL("https://api.fhict.nl/people/me");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestProperty("Authorization", "Bearer " + params[0]);
                    connection.connect();
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    JsonReader jsonReader = new JsonReader(isr);
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
    }

    public class GetGradesTask extends AsyncTask<String, Void, ArrayList<Grade>> {
        @Override
        protected void onPostExecute(ArrayList<Grade> grades) {
            super.onPostExecute(grades);

            FragmentManager fragMgr = getFragmentManager();
            FragmentTransaction fragTrans = fragMgr.beginTransaction();
            GradesFragment hello = GradesFragment.newInstance(grades);

            fragTrans.replace(R.id.fragmentPlaceholder, hello, "HELLO");
            fragTrans.commit();
        }

        @Override
        protected ArrayList<Grade> doInBackground(String... params) {

            if (params.length > 0 && !params[0].isEmpty()) {

                try {
                    URL url = new URL("https://api.fhict.nl/grades/me");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestProperty("Authorization", "Bearer " + params[0]);
                    connection.connect();
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    JsonReader jsonReader = new JsonReader(isr);
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
    }

    public class GetScheduleTask extends AsyncTask<String, Void, ArrayList<ScheduleItem>> {

        @Override
        protected void onPostExecute(ArrayList<ScheduleItem> schedule) {
            super.onPostExecute(schedule);

            FragmentManager fragMgr = getFragmentManager();
            FragmentTransaction fragTrans = fragMgr.beginTransaction();
            ScheduleFragment hello = ScheduleFragment.newInstance(schedule);

            fragTrans.replace(R.id.fragmentPlaceholder, hello, "HELLO");
            fragTrans.commit();
        }

        @Override
        protected ArrayList<ScheduleItem> doInBackground(String... params) {

            if (params.length > 0 && !params[0].isEmpty()) {

                try {
                    URL url = new URL("https://api.fhict.nl/schedule/me");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestProperty("Authorization", "Bearer " + params[0]);
                    connection.connect();
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    JsonReader jsonReader = new JsonReader(isr);
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
    }
}
