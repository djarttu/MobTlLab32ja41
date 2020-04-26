package com.example.myfootballapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Area {
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    String id;
    String name;

    public Area(JSONObject object) throws JSONException {
        this.id=object.getString("id");
        this.name=object.getString("name");
        Log.d("gko", this.name);

    }
}
