package com.example.myfootballapp;

import org.json.JSONException;
import org.json.JSONObject;

class Competition {
    String name;

    public String getName() {
        return name;
    }

    public String getTier() {
        return tier;
    }

    String tier;
    public Competition(JSONObject object) throws JSONException {
        this.name=object.getString("name");
        this.tier=object.getString("plan");

    }

}
