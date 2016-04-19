package com.example.oak.searchpill.searchpill;

/**
 * Created by Oak on 6/4/2559.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.oak.myapplication.R;

public class search_group_or_type extends  Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_group_or_type);

        final Button gototype = (Button) findViewById(R.id.searchByTradeName);
        gototype.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent1 = new Intent(getApplicationContext(), search_TYPE.class);
                startActivity(intent1);
            }
        });


        final Button gotogroup = (Button) findViewById(R.id.searchByRkarn);
        gotogroup.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent2 = new Intent(getApplicationContext(), search_GROUP.class);
                startActivity(intent2);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
