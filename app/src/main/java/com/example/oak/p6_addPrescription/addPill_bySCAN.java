package com.example.oak.p6_addPrescription;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.oak.myapplication.R;

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
import java.util.List;

/**
 * Created by suppasri on 19/4/2559.
 */
public class addPill_bySCAN extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pill);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        showInfo();

    }

    public void showInfo() {
        final TextView tTradeN = (TextView)findViewById(R.id.tvTradeN);
        final TextView tGenN = (TextView)findViewById(R.id.tvCommonN);
        final TextView tType = (TextView) findViewById(R.id.tvType1);
        String url = "http://medalertapp.comli.com/addPill_getByGenN.php";


        Intent intent = getIntent();
        final String Pill_GenN = intent.getStringExtra("SCAN_RESULT");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("sGenN", Pill_GenN));

        /** Get result from Server (Return the JSON Code) */
        String resultServer  = getHttpPost(url,params);

        String strPill_ID = "";
        String strTradeN = "";
        String strGenN = "";
        String strType = "";
//        String strUsedFor = "";
//        String strHowto = "";
//        String strSideE = "";

        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            strPill_ID = c.getString("pill_id");
            strTradeN = c.getString("trade_name");
            strGenN = c.getString("general_name");
            strType = c.getString("pill_type");
//            strUsedFor = c.getString("used_for");
//            strHowto = c.getString("how_to");
//            strSideE = c.getString("side_effect");

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

}
