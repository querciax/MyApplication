package com.example.oak.p6_addPrescription;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class searchPill extends AppCompatActivity {

    ArrayList<HashMap<String, String>> MyArrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pill);

//        Button gotoAddPill = (Button) findViewById(R.id.b6_btnSearch);
//        gotoAddPill.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(getApplicationContext(), addPill.class);
//                startActivity(intent1);
//            }
//        });

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        // btnSearch
        final Button btnSearch = (Button) findViewById(R.id.b6_btnSearch);
        // Perform action on click
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShowData();
            }
        });

    }

    public void ShowData() {
        // listView1
        final ListView lisView1 = (ListView)findViewById(R.id.b6_listView1);

        // keySearch
        EditText strKeySearch = (EditText)findViewById(R.id.b6_KeySearch);

        // Disbled Keyboard auto focus
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(strKeySearch.getWindowToken(), 0);

        String url = "http://medalertapp.comli.com/addPill_showAllPill.php";


        // Paste Parameters
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("txtKeyword", strKeySearch.getText().toString()));

        try {
            JSONArray data = new JSONArray(getJSONUrl(url,params));

            MyArrList = new ArrayList<>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<>();
                map.put("pill_id", c.getString("pill_id"));
                map.put("trade_name", c.getString("trade_name"));
                map.put("general_name", c.getString("general_name"));
                map.put("pill_type", c.getString("pill_type"));
                map.put("used_for", c.getString("used_for"));
                map.put("how_to", c.getString("how_to"));
                map.put("side_effect", c.getString("side_effect"));
                MyArrList.add(map);

            }

            lisView1.setAdapter(new ImageAdapter(this));

            // OnClick Item
            lisView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView,
                                        int position, long mylng) {

                    String sPillID = MyArrList.get(position).get("pill_id");
                    String sTradeName = MyArrList.get(position).get("trade_name");
                    String sPillType = MyArrList.get(position).get("pill_type");

                    Intent newActivity = new Intent(searchPill.this,addPill.class);
                    newActivity.putExtra("pill_id", sPillID);
                    startActivity(newActivity);

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public class ImageAdapter extends BaseAdapter {
        private Context context;

        public ImageAdapter(Context c) {
            context = c;
        }

        public int getCount() {
            return MyArrList.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
                        return position;
        }
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.b6_typing_column, null);
            }

            // ColMemberID
            /*TextView txtdID = (TextView) convertView.findViewById(R.id.ColdID);
            txtdID.setPadding(10, 0, 0, 0);
            txtdID.setText(MyArrList.get(position).get("dID") +"."); */

            // R.id.ColTName
            TextView txtTradeN = (TextView) convertView.findViewById(R.id.ColTName);
            txtTradeN.setPadding(5, 0, 0, 0);
            txtTradeN.setText(MyArrList.get(position).get("trade_name"));

            // R.id.ColGName
            TextView txtGenN = (TextView) convertView.findViewById(R.id.ColGName);
            txtGenN.setPadding(5, 0, 0, 0);
            txtGenN.setText(MyArrList.get(position).get("general_name"));

            // R.id.ColType
            TextView txtType = (TextView) convertView.findViewById(R.id.ColType);
            txtType.setPadding(5, 0, 0, 0);
            txtType.setText(MyArrList.get(position).get("pill_type"));

            return convertView;
        }
    }


    public String getJSONUrl(String url,List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
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
