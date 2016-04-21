package com.example.oak.p4_history;

/**
 * Created by GGG on 21/4/2559.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oak.myapplication.R;
import com.example.oak.searchpill.searchpill.search_TYPE;
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
import java.util.List;

public class P4_History_detail extends  Activity {

    private String urlImageString;
    private ImageView pillImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b4_search_detail);

        pillImageView = (ImageView) findViewById(R.id.imageView7);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        showInfo();

        // btnBack
        final Button btnBack = (Button) findViewById(R.id.search_btnBack);
        // Perform action on click
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newActivity = new Intent(P4_History_detail.this,search_TYPE.class);
                startActivity(newActivity);
            }
        });

    } //Main Medthod

    private void showImage() {

        Log.d("19April", "url ==> " + urlImageString);

        Picasso.with(P4_History_detail.this).load(urlImageString).resize(150, 150).into(pillImageView);


    } //Show Image Method

    public void showInfo() {
        //final TextView tPill_ID = (TextView)findViewById(R.id.txtPillid);
        final TextView tTradeN = (TextView)findViewById(R.id.txtTradeN);
        final TextView tGenN = (TextView)findViewById(R.id.txtGenN);
        final TextView tType = (TextView)findViewById(R.id.txtType);
        final TextView tUsedFor = (TextView)findViewById(R.id.txtUsedFor);
        final TextView tHowto = (TextView)findViewById(R.id.txtHowto);
        final TextView tSideE = (TextView)findViewById(R.id.txtSideE);
        final TextView tStorageMet = (TextView)findViewById(R.id.txtStorageMet);


        String url = "http://medalertapp.comli.com/searchType_getByID.php";

        Intent intent = getIntent();
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
        String strStorageMet = "";
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

            if(!strPill_ID.equals(""))
            {
                //tPill_ID.setText(strPill_ID);
                tTradeN.setText(strTradeN);
                tGenN.setText(strGenN);
                tType.setText(strType);
                tUsedFor.setText(strUsedFor);
                tHowto.setText(strHowto);
                tSideE.setText(strSideE);
                tStorageMet.setText(strStorageMet);
            }
            else
            {
                //tPill_ID.setText("-");
                tTradeN.setText("-");
                tGenN.setText("-");
                tType.setText("-");
                tUsedFor.setText("-");
                tHowto.setText("-");
                tSideE.setText("-");
                tStorageMet.setText("-");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    } //SHOW INFO

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
    } //POST METHOD

} //Main Class



