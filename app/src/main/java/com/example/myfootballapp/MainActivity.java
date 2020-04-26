package com.example.myfootballapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter mAdapter;
    RequestQueue requestQueue;
    ArrayList<Area> areaList=new ArrayList<>();
    String url= "http://api.football-data.org/v2/areas";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestQueue= Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.areaListView);
        mAdapter = new ArrayAdapter(this, R.layout.listviewareas, areaList){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                View v = convertView;
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.listviewareas, null);
                TextView textView = v.findViewById(R.id.countryNameTextField);
                textView.setText(areaList.get(position).getName());

                return v;

            }

        };
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openArea(position);
            }
        });
        requestJson();
    }
    private void openArea(int position){
        Intent intent=new Intent(this, AreaActivity.class);
        Log.d("idMAin",areaList.get(position).getId());
        intent.putExtra("id", areaList.get(position).getId());
        startActivity(intent);
    }
    private void requestJson(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray array = new JSONArray();
                        try {
                            array = response.getJSONArray("areas");
                            for(int i=0; i<array.length();i++){
                                JSONObject area=  array.getJSONObject(i);
                                Log.d("g", area.getString("name"));
                                mAdapter.add(new Area(area));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }






                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.toString());
                        // TODO: Handle error

                    }


                }
                );
        requestQueue.add(jsonObjectRequest);
    }
}
