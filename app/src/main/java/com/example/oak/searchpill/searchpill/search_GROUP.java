package com.example.oak.searchpill.searchpill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.oak.myapplication.R;

/**
 * Created by Oak on 6/4/2559.
 */
public class search_GROUP extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_group_main);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final Button btn1 = (Button)findViewById(R.id.searchGroup_btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_subgroup1 = new Intent(getApplicationContext(),search_GROUP_subgroup_1.class);
                startActivity(intent_subgroup1);
            }
        });

//        final Button btn2 = (Button)findViewById(R.id.searchGroup_btn2);
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_subgroup2 = new Intent(getApplicationContext(),search_GROUP_subgroup_2.class);
//                startActivity(intent_subgroup2);
//            }
//        });
//
//        final Button btn3 = (Button)findViewById(R.id.searchGroup_btn3);
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_subgroup3 = new Intent(getApplicationContext(),search_GROUP_subgroup_3.class);
//                startActivity(intent_subgroup3);
//            }
//        });
//
//        final Button btn4 = (Button)findViewById(R.id.searchGroup_btn4);
//        btn4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_subgroup4 = new Intent(getApplicationContext(),search_GROUP_subgroup_4.class);
//                startActivity(intent_subgroup4);
//            }
//        });
//
//        final Button btn5 = (Button)findViewById(R.id.searchGroup_btn5);
//        btn5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_subgroup5 = new Intent(getApplicationContext(),search_GROUP_subgroup_5.class);
//                startActivity(intent_subgroup5);
//            }
//        });
//
//        final Button btn6 = (Button)findViewById(R.id.searchGroup_btn6);
//        btn6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_subgroup6 = new Intent(getApplicationContext(),search_GROUP_subgroup_6.class);
//                startActivity(intent_subgroup6);
//            }
//        });
//
//        final Button btn7 = (Button)findViewById(R.id.searchGroup_btn7);
//        btn7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_subgroup7 = new Intent(getApplicationContext(),search_GROUP_subgroup_7.class);
//                startActivity(intent_subgroup7);
//            }
//        });
//
//        final Button btn8 = (Button)findViewById(R.id.searchGroup_btn8);
//        btn8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_subgroup8 = new Intent(getApplicationContext(),search_GROUP_subgroup_8.class);
//                startActivity(intent_subgroup8);
//            }
//        });
//
//        final Button btn9 = (Button)findViewById(R.id.searchGroup_btn9);
//        btn9.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_subgroup9 = new Intent(getApplicationContext(),search_GROUP_subgroup_9.class);
//                startActivity(intent_subgroup9);
//            }
//        });
//
//        final Button btn10 = (Button)findViewById(R.id.searchGroup_btn10);
//        btn10.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_subgroup10 = new Intent(getApplicationContext(),search_GROUP_subgroup_10.class);
//                startActivity(intent_subgroup10);
//            }
//        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
