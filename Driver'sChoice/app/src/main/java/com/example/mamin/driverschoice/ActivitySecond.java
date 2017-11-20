package com.example.mamin.driverschoice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.text1;

public class ActivitySecond extends AppCompatActivity {

    private String localjsonString1 = null;
    private String localjsonString="";
    //private String localjsonString="{ \"Car\" :[{\"id\":\"1\",\"Make\":\"Acura\"},{\"id\":\"2\",\"Make\":\"Honda\"}] }";
    //https://khj7tf5cu8.execute-api.us-west-2.amazonaws.com/Dev/make
   ListView listView;
    List<Map<String, String>> carList = new ArrayList<Map<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        final ListView listView = (ListView) findViewById(R.id.listView1);
        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
              //  android.R.layout.simple_list_item_1, Integer.parseInt(localjsonString));

        makeRequest(new VolleyCallback() {
            @Override
            public void onSuccess(String result){
                localjsonString =  result.toString();
                //Toast.makeText(getApplicationContext(), "DATA" + localjsonString, Toast.LENGTH_SHORT).show();
                initList();
                final SimpleAdapter simpleAdapter = new SimpleAdapter(ActivitySecond.this, carList, android.R.layout.simple_list_item_1, new String[]{"Car"}, new int[]{android.R.id.text1});

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent in = new Intent(ActivitySecond.this, ThirdActivity.class);
                        startActivity(in);
                      //  putExtraData();
                    }
                });
                listView.setAdapter(simpleAdapter);

            }

        });


        //listView.setAdapter(adapter);

//        String carName = makeRequest(new VolleyCallback() {
//            @Override
//            public void onSuccess(String result) {
//
//
//// Request a string response from the provided URL.
//                initList();
//
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent = new Intent(ActivitySecond.this, ThirdActivity.class);
//                        intent.putExtra("carName", localjsonString.toString());
//                        startActivity(intent);
//                    }
//                });
//            }
//        });
//        setContentView(R.layout.activity_second);
//
//        final ListView listView = (ListView) findViewById(R.id.listView1);
//        ListAdapter simpleAdapter = new Adapter(this, carList, android.R.layout.simple_list_item_1, new String[]{"Car"}, new int[]{android.R.id.text1});
//        listView.setAdapter(simpleAdapter);

    }

    public interface VolleyCallback{
        void onSuccess(String result);
    }
    private void makeRequest(final VolleyCallback callback){ //final VolleyCallback callback
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://khj7tf5cu8.execute-api.us-west-2.amazonaws.com/Dev/make";
        try{
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(stringRequest);
    } catch (Exception e) {
        Toast.makeText(getApplicationContext(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();
    }
    }
    public void initList() {
        try {
            JSONArray jsonArray = new JSONArray(localjsonString);
          //  Toast.makeText(getApplicationContext(), "jsonArray " + jsonArray, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < jsonArray.length(); i++) {
                String name = jsonArray.getJSONObject(i).getString("make");
                // String number = jsonChildNode.optString("capital");
                String outPut = name ;
              //  Toast.makeText(getApplicationContext(), "name" + name, Toast.LENGTH_SHORT).show();
                carList.add(createCar("Car", outPut));
            }

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    private HashMap<String, String> createCar(String car, String name) {
        HashMap<String, String> carNameNo = new HashMap<String, String>();
        carNameNo.put(car,name);
        return carNameNo;
    }

}