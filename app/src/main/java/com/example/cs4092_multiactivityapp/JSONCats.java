package com.example.cs4092_multiactivityapp;
import android.content.Context;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;



public class JSONCats {

    private final Context context;

    public JSONCats(Context context) {
        this.context = context;
    }

    // Analyze Json file's data and return List<CatData>
    public List<Cat> getCatDataList() {
        String json = loadJSONFromAsset();
        if (json == null) {
            return null;
        }

        // Using Gson to analyze JSON
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Cat>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    // Load JSON file from assets folder
    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream inputStream = context.getAssets().open("cat.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
