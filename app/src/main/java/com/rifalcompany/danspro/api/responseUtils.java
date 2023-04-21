package com.rifalcompany.danspro.api;

import okhttp3.ResponseBody;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.IOException;

public class responseUtils {

    public static JSONObject getResponseJson(ResponseBody response) throws JSONException, IOException {
        String responseString = response.string();
        Object json = new JSONTokener(responseString).nextValue();

        if (json instanceof JSONObject) {
            return new JSONObject(responseString);
        } else {
            throw new JSONException("Response is not a JSON object");
        }
    }
}
