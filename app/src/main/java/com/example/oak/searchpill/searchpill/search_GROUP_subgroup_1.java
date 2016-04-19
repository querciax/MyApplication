package com.example.oak.searchpill.searchpill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.oak.myapplication.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by suppasri on 18/4/2559.
 */
public class search_GROUP_subgroup_1 extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_group_1);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ShowData();

    }

    public void ShowData() {

        final ListView lisView1 = (ListView)findViewById(R.id.searchGroup_listView1);

        String url = "http://medalertapp.comli.com/searchGroup_showAllPill_1.php";

        // Paste Parameters
        //List<NameValuePair> params = new ArrayList<>();
        //params.add(new BasicNameValuePair("txtKeyword", strKeySearch.getText().toString()));

        try {
            JSONArray data = new JSONArray(getJSONUrl( url ));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
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
                map.put("storage_methods", c.getString("storage_methods"));
                MyArrList.add(map);

            }

            lisView1.setAdapter(new ImageAdapter(this, MyArrList));

            // OnClick Item
            lisView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView,
                                        int position, long mylng) {

                    String sPillID = MyArrList.get(position).get("pill_id");
                    //String sTradeName = MyArrList.get(position).get("trade_name");

                    Intent newActivity = new Intent(search_GROUP_subgroup_1.this,search_GROUP_detail_1.class);
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
        private ArrayList<HashMap<String, String>> MyArr = new ArrayList<>();

        public ImageAdapter(Context c, ArrayList<HashMap<String, String>> list) {
            context = c;
            MyArr = list;
        }

        public int getCount() {
            return MyArr.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.search_column, null);
            }

            // R.id.ColTName
            TextView txtTradeN = (TextView) convertView.findViewById(R.id.ColTName);
            txtTradeN.setPadding(5, 0, 0, 0);
            txtTradeN.setText(MyArr.get(position).get("trade_name"));

            return convertView;
        }
    }

    public String getJSONUrl(String url) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            //httpPost.setEntity(new UrlEncodedFormEntity(params));
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
