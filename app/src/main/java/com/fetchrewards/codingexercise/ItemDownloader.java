package com.fetchrewards.codingexercise;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDownloader {
    private static final String TAG = "ItemDownloader";
    private static MainActivity mainActivity;
    private static RequestQueue queue;
    private static Item itemObj;
    private static List<Item> itemList = new ArrayList<>();
    private static final String fetchUrl = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

    public static void downloadItems(MainActivity mainActivityIn) {
        mainActivity = mainActivityIn;
        queue = Volley.newRequestQueue(mainActivity);

        Response.Listener<JSONArray> listener =
                response -> parseJSON(response.toString());
        Response.ErrorListener error =
                error1 -> mainActivity.updateData(null);
        JsonArrayRequest jsonArrayRequestRequest =
                new JsonArrayRequest(Request.Method.GET, fetchUrl,
                        null, listener, error);
        queue.add(jsonArrayRequestRequest);
    }

    private static void parseJSON(String s) {
        try {
            JSONArray jObjMain = new JSONArray(s);

            for (int i = 0; i < jObjMain.length()-900; i++) {
                JSONObject jItem = jObjMain.getJSONObject(i);
                Integer jId = jItem.getInt("id");
                Integer jListId = jItem.getInt("listId");
                String jName = "";
                if (jItem.get("name") != JSONObject.NULL) {
                    jName = jItem.getString("name");
                }
                itemObj = new Item(jId, jListId, jName);
                itemList.add(itemObj);
            }
            mainActivity.updateData(itemList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
