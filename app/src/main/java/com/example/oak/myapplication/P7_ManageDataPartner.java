package com.example.oak.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * Created by Oak on 7/4/2559.
 */
public class P7_ManageDataPartner extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b6_addprescription);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
