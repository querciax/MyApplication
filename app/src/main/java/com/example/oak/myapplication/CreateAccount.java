package com.example.oak.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class CreateAccount extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);


        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // btnSave
        final Button btnSave = (Button) findViewById(R.id.btnCreateAcc);
        // Perform action on click
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (SaveData()) {
                    // When Save Complete
                    Intent logActivity = new Intent(CreateAccount.this, Login.class);
                    startActivity(logActivity);
                }
            }
        });

    }


    public boolean SaveData() {
        final EditText txtEmail = (EditText)findViewById(R.id.login_edtEmail);
        final EditText txtPassword = (EditText)findViewById(R.id.edtPass);
        final EditText txtFirstname = (EditText)findViewById(R.id.edtFirstname);
        final EditText txtLastname = (EditText)findViewById(R.id.edtLastname);
        final EditText txtAge = (EditText)findViewById(R.id.edtAge);
        final EditText txtGender= (EditText)findViewById(R.id.edtGender);
        final EditText txtWeight = (EditText)findViewById(R.id.edtWeight);
        final EditText txtHeight = (EditText)findViewById(R.id.edtHeight);
        final EditText txtTel = (EditText)findViewById(R.id.edtPhone);


        // Dialog
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        ad.setTitle("Error! ");
        ad.setIcon(android.R.drawable.btn_star_big_on);
        ad.setPositiveButton("Close", null);

        // Check Email
        if(txtEmail.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอก 'อีเมล์' ");
            ad.show();
            txtEmail.requestFocus();
            return false;
        }
        // Check Password
        if(txtPassword.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอก'รหัสผ่าน' ");
            ad.show();
            txtPassword.requestFocus();
            return false;
        }
        // Check Password and Confirm Password (Match)
//        if(!txtPassword.getText().toString().equals(txtConPassword.getText().toString()))
//        {
//            ad.setMessage("Password and Confirm Password Not Match! ");
//            ad.show();
//            txtConPassword.requestFocus();
//            return false;
//        }
        // Check FirstName
        if(txtFirstname.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอก'ชื่อจริง' ");
            ad.show();
            txtFirstname.requestFocus();
            return false;
        }
        // Check LastName
        if(txtLastname.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอก'นามสกุล' ");
            ad.show();
            txtLastname.requestFocus();
            return false;
        }
        // Check Age
        if(txtAge.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอก'อายุ' ");
            ad.show();
            txtAge.requestFocus();
            return false;
        }
        // Check Gender
        if(txtGender.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอก'เพศ' ");
            ad.show();
            txtGender.requestFocus();
            return false;
        }
        // Check Weight
        if(txtWeight.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอก'น้ำหนัก' ");
            ad.show();
            txtWeight.requestFocus();
            return false;
        }
        // Check Height
        if(txtHeight.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอก'ส่วนสูง' ");
            ad.show();
            txtHeight.requestFocus();
            return false;
        }
        // Check Tel
        if(txtTel.getText().length() == 0)
        {
            ad.setMessage("กรุณากรอก'เบอร์โทรศัพท์' ");
            ad.show();
            txtTel.requestFocus();
            return false;
        }


        String url = "http://medalertapp.comli.com/user/CreateAccount.php";


        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("sEmail", txtEmail.getText().toString()));
        params.add(new BasicNameValuePair("sPassword", txtPassword.getText().toString()));
        params.add(new BasicNameValuePair("sFirstName", txtFirstname.getText().toString()));
        params.add(new BasicNameValuePair("sLastName", txtLastname.getText().toString()));
        params.add(new BasicNameValuePair("sAge", txtAge.getText().toString()));
        params.add(new BasicNameValuePair("sGender", txtGender.getText().toString()));
        params.add(new BasicNameValuePair("sWeight", txtWeight.getText().toString()));
        params.add(new BasicNameValuePair("sHeight", txtHeight.getText().toString()));
        params.add(new BasicNameValuePair("sTel", txtTel.getText().toString()));

        /** Get result from Server (Return the JSON Code)
         * StatusID = ? [0=Failed,1=Complete]
         * Error	= ?	[On case error return custom error message]
         *
         * Eg Save Failed = {"StatusID":"0","Error":"Email Exists!"}
         * Eg Save Complete = {"StatusID":"1","Error":""}
         */

        String resultServer  = getHttpPost(url,params);

        /*** Default Value ***/
        String strStatusID = "0";
        String strError = "Unknow Status!";

        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            strStatusID = c.getString("StatusID");
            strError = c.getString("Error");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Prepare Save Data
        if(strStatusID.equals("0"))
        {
            ad.setMessage(strError);
            ad.show();
        }
        else
        {
            Toast.makeText(CreateAccount.this, "Create Account Successfully", Toast.LENGTH_SHORT).show();
            txtEmail.setText("");
            txtPassword.setText("");
            txtFirstname.setText("");
            txtLastname.setText("");
            txtAge.setText("");
            txtGender.setText("");
            txtWeight.setText("");
            txtHeight.setText("");
            txtTel.setText("");
        }


        return true;
    }

    public String getHttpPost(String url,List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));

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
