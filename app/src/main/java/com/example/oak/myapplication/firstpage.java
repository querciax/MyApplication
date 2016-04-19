package com.example.oak.myapplication;

/**
 * Created by Oak on 6/4/2559.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.oak.searchpill.searchpill.search_group_or_type;

public class firstpage extends  Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage_main);

        final Button gotoSearch = (Button) findViewById(R.id.searchpill);
        gotoSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent1 = new Intent(getApplicationContext(), search_group_or_type.class);
                startActivity(intent1);
            }
        });

        final Button gotoLogin = (Button) findViewById(R.id.register);
        gotoLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent1 = new Intent(getApplicationContext(), Login.class);
                startActivity(intent1);
            }
        });

        final Button gotoCreateAccount = (Button) findViewById(R.id.createAccount);
        gotoCreateAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent1 = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(intent1);
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
