package com.example.oak.p3_addappointment;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.oak.myapplication.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;

public class P3_AddAppointment extends AppCompatActivity {

    //Bind Widget

    private TextView showDate,showTime;
    private TimePickerDialog timePickerDialog;
    int yearMain,monthMain,dateMain,hourMain,muniteMain;
    int value;
    private ListView listAlarm;
    public static ArrayList<String> listValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b3_addappointment);

        listAlarm = (ListView)findViewById(R.id.listView);
        listValue = new ArrayList<String>();
        showDate = (TextView)findViewById(R.id.textView3);
        showTime = (TextView)findViewById(R.id.textView4);


    } //Main Method

    public void clickDate (View view) {

        Calendar calendar = Calendar.getInstance();
        int intDate = calendar.get(Calendar.DAY_OF_MONTH);
        int intMonth = calendar.get(Calendar.MONTH);
        int intYear = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog =  new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                showDate.setText(Integer.toString(dayOfMonth) + "/" +
                        Integer.toString(monthOfYear + 1) + "/ " +
                        Integer.toString(year));

                yearMain = year;
                monthMain = monthOfYear;
                dateMain = dayOfMonth;

            }

        }, intYear,intMonth,intDate );

        datePickerDialog.show();

    } // Click setDate


    public void clickTime (View view) {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(P3_AddAppointment.this, onTimeSetListener,
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
            showTime.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));

            hourMain = hourOfDay;
            muniteMain = minute;



            Log.d("18April", "cloneCalendar1 ==> " + cloneCalendar1.getTime());


        } // OnTimeSet
    };


    public void setValue(View view){

        Calendar calendar = Calendar.getInstance();
        Calendar cloneCalendar = (Calendar)calendar.clone();

        cloneCalendar.set(Calendar.YEAR,yearMain);
        cloneCalendar.set(Calendar.MONTH,monthMain);
        cloneCalendar.set(Calendar.DAY_OF_MONTH,dateMain);
        cloneCalendar.set(Calendar.HOUR_OF_DAY,hourMain);
        cloneCalendar.set(Calendar.MINUTE,muniteMain);
        cloneCalendar.set(Calendar.SECOND,0);

        mySetToAlarm(cloneCalendar);




    }

    private void mySetToAlarm(Calendar mySetCalendar1) {

        listValue.add(mySetCalendar1.getTime() + "");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listValue);
        listAlarm.setAdapter(adapter);

        final int _id = (int) System.currentTimeMillis();

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), _id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, mySetCalendar1.getTimeInMillis(), pendingIntent);

    } // mySetToAlarm mothod

}
