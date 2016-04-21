package com.example.oak.p6_addPrescription;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.oak.myapplication.R;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class addPill extends AppCompatActivity {

    //Explicit
    private String urlImageString;
    private ImageView pillImageView;
    private  String[] timStrings = {"1","2","3","4","5"};
    private TextView showStartDateTextView,showEndDateTime,showFirstTime;
    private TimePickerDialog timePickerDialog;
    int yearMain,monthMain,dateMain,hourMain,muniteMain;
    private ListView listAlarm;
    public static ArrayList<String> listValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pill);

        //Bind WidgetView
        pillImageView = (ImageView) findViewById(R.id.imageView6);
        showStartDateTextView = (TextView) findViewById(R.id.textView19);
     //   showEndDateTime = (TextView) findViewById(R.id.textView22);
        showFirstTime = (TextView) findViewById(R.id.textView24);
        listAlarm = (ListView)findViewById(R.id.listView3);
        listValue = new ArrayList<String>();

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //TextView commonName = (TextView) findViewById(R.id.tvCommonN);

        showInfo();

        //ShowImage
        showImage();




    } //Main Method

    public void clickSetTime (View view) {

        Calendar calendar = Calendar.getInstance();
        int intDate = calendar.get(Calendar.DAY_OF_MONTH);
        int intMonth = calendar.get(Calendar.MONTH);
        int intYear = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog =  new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                showStartDateTextView.setText(Integer.toString(dayOfMonth) + "/" +
                        Integer.toString(monthOfYear + 1) + "/ " +
                        Integer.toString(year));

                yearMain = year;
                monthMain = monthOfYear;
                dateMain = dayOfMonth;

            }

        }, intYear,intMonth,intDate );

        datePickerDialog.show();

    } // Click setTime


  /*  public void clickEndTime (View view) {

        Calendar calendar = Calendar.getInstance();
        int intDate = calendar.get(Calendar.DAY_OF_MONTH);
        int intMonth = calendar.get(Calendar.MONTH);
        int intYear = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog =  new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                showEndDateTime.setText(Integer.toString(dayOfMonth) + "/" +
                        Integer.toString(monthOfYear + 1) + "/ " +
                        Integer.toString(year));

            }

        }, intYear,intMonth,intDate );

        datePickerDialog.show();

    } // Click EndTime  */


    public void FirstTime (View view) {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(addPill.this, onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false);



        timePickerDialog.setTitle("โปรดเลือกเวลา");
        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calendar = Calendar.getInstance();
            Calendar cloneCalendar1 = (Calendar) calendar.clone();

            cloneCalendar1.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cloneCalendar1.set(Calendar.MINUTE, minute);
            showFirstTime.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));

            hourMain = hourOfDay;
            muniteMain = minute;

            Log.d("18April", "cloneCalendar1 ==> " + cloneCalendar1.getTime());

        } // OnTimeSet
    };


    private void showImage() {

        Log.d("19April", "url ==> " + urlImageString);

        Picasso.with(addPill.this).load(urlImageString).resize(150, 150).into(pillImageView);


    } //Show Image Method

    public void showInfo() {
        //final TextView tPill_ID = (TextView)findViewById(R.id.txtPillid);
        final TextView tTradeN = (TextView)findViewById(R.id.tvTradeN);
        final TextView tGenN = (TextView)findViewById(R.id.tvCommonN);
        final TextView tType = (TextView)findViewById(R.id.tvType1);
//        final TextView tUsedFor = (TextView)findViewById(R.id.txtUsedFor);
//        final TextView tHowto = (TextView)findViewById(R.id.txtHowto);
//        final TextView tSideE = (TextView)findViewById(R.id.txtSideE);



        String url = "http://medalertapp.comli.com/addPill_getByID.php";


        Intent intent= getIntent();
        final String Pill_ID = intent.getStringExtra("pill_id");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("sPillID", Pill_ID));

        /** Get result from Server (Return the JSON Code) */
        String resultServer  = getHttpPost(url,params);

        String strPill_ID = "";
        String strTradeN = "";
        String strGenN = "";
        String strType = "";
        String strUsedFor = "";
        String strHowto = "";
        String strSideE = "";
        urlImageString = "";

        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            strPill_ID = c.getString("pill_id");
            strTradeN = c.getString("trade_name");
            strGenN = c.getString("general_name");
            strType = c.getString("pill_type");
            strUsedFor = c.getString("used_for");
            strHowto = c.getString("how_to");
            strSideE = c.getString("side_effect");
            urlImageString = c.getString("storage_methods");
            //PIC
            // urlImageString = c.getString("source");
            //test




            Log.d("19April", "strJSON == > " + resultServer);



            if(!strPill_ID.equals(""))
            {
                //tPill_ID.setText(strPill_ID);
                tTradeN.setText(strTradeN);
                tGenN.setText(strGenN);
                tType.setText(strType);
//                tUsedFor.setText(strUsedFor);
//                tHowto.setText(strHowto);
//                tSideE.setText(strSideE);

            }
            else
            {
                //tPill_ID.setText("-");
                tTradeN.setText("-");
                tGenN.setText("-");
                tType.setText("-");
//                tUsedFor.setText("-");
//                tHowto.setText("-");
//                tSideE.setText("-");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getHttpPost(String url,List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Status OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    public void setValueTime(View view){

        Calendar calendar = Calendar.getInstance();
        Calendar cloneCalendar = (Calendar)calendar.clone();

        cloneCalendar.set(Calendar.YEAR,yearMain);
        cloneCalendar.set(Calendar.MONTH,monthMain);
        cloneCalendar.set(Calendar.DAY_OF_MONTH, dateMain);
        cloneCalendar.set(Calendar.HOUR_OF_DAY, hourMain);
        cloneCalendar.set(Calendar.MINUTE,muniteMain);
        cloneCalendar.set(Calendar.SECOND, 0);

        Log.d("21","month==>" + cloneCalendar.get(monthMain));
        mySetToAlarm(cloneCalendar);
    }

    private void mySetToAlarm(Calendar mySetCalendar1) {

        listValue.add(mySetCalendar1.getTime() + "");



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listValue);
        listAlarm.setAdapter(adapter);


        final int _id = (int) System.currentTimeMillis();

        Intent intent = new Intent(getBaseContext(), AlarmReceiver_Pill.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), _id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, mySetCalendar1.getTimeInMillis(), pendingIntent);

    } // mySetToAlarm mothod

}