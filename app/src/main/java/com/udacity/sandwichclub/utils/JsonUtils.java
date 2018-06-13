package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        /*
        *  I have referenced the work in -Sunshine-student\S02.03-Solution-Polish to parse the json String
        *  I have also referenced the documentation on the Android developers website related to Json API
        * */
        //json key names
        final String NAME = "name";
        final String MAIN_NAME = "mainName";
        final String PLACE_OF_ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE_URL = "image";
        final String ALSO_KNOWN_AS = "alsoKnownAs";
        final String INGREDIENTS = "ingredients";

        List<String> alsoKnownAs = null;
        List<String> ingredients = null;

        String mainName;
        String palceOfOrigin;
        String description;
        String imageUrl;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject nameJson = jsonObject.getJSONObject(NAME);
            //get the main name from Json object
            mainName = nameJson.getString(MAIN_NAME);
            // use alsoKnownAsJsonArray object to create the array of alsoKnownAs names
            JSONArray alsoKnownAsJsonArray = nameJson.getJSONArray(ALSO_KNOWN_AS);
            if(alsoKnownAsJsonArray.length() > 0){
                alsoKnownAs = new ArrayList<>();
                //loop through the JsonArray object to obtain the aka's
                for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                    alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
                }
            }
            palceOfOrigin = jsonObject.getString(PLACE_OF_ORIGIN);
            description = jsonObject.getString(DESCRIPTION);
            imageUrl = jsonObject.getString(IMAGE_URL);

            JSONArray ingredientsJsonArray = jsonObject.getJSONArray(INGREDIENTS);
            if(ingredientsJsonArray.length() > 0){
                ingredients = new ArrayList<>();
                //loop through the JsonArray object to obtain the aka's
                for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                    ingredients.add(ingredientsJsonArray.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        Sandwich sandwich = new Sandwich(mainName,alsoKnownAs,palceOfOrigin,description,imageUrl,ingredients);
        return sandwich;
    }
}
