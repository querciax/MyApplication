package com.example.oak.p6_addPrescription;

/**
 * Created by GGG on 21/4/2559.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;



/**
 * Created by octoboy on 22/11/2557.
 */
public class AlarmReceiver_Pill extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent arg1) {
        Intent i = new Intent(context, ShowEventPill.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}