package com.figueroaluis.recipelistapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by luisfigueroa on 3/12/18.
 */

public class Recipe {

    // instance variables for recipe class
    // taken from the recipes.json file
    public String title;
    public String imageUrl;
    public String instructionUrl;
    public String description;
    public String dietLabel;
    public String servings;
    public String servings_label;
    public String prepTime;
    public String prepTime_label;
    public ArrayList<String> searchLabels;

    public static ArrayList<Recipe> getRecipesFromFile(String filename, Context context){
        ArrayList<Recipe> recipeList = new ArrayList<>();


        try{
            String jsonString = loadJsonFromAsset("recipes.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("recipes");


            for (int i = 0; i < recipes.length(); i++){
                Recipe recipe = new Recipe();
                recipe.title = recipes.getJSONObject(i).getString("title");
                recipe.description = recipes.getJSONObject(i).getString("description");
                recipe.imageUrl = recipes.getJSONObject(i).getString("image");
                recipe.instructionUrl =recipes.getJSONObject(i).getString("url");
                recipe.dietLabel =recipes.getJSONObject(i).getString("dietLabel");
                recipe.servings =recipes.getJSONObject(i).getString("servings");
                recipe.prepTime = recipes.getJSONObject(i).getString("prepTime");
                recipe.searchLabels = new ArrayList<>();

                int servingsNum = Integer.parseInt(recipe.servings);
                if (servingsNum < 4){
                    recipe.servings_label = "less than 4";
                }
                else if(servingsNum <= 6){
                    recipe.servings_label = "4-6";
                }
                else if(servingsNum <= 9){
                    recipe.servings_label = "7-9";
                }
                else{
                    recipe.servings_label = "more than 10";
                }

                if (recipe.prepTime.contains("hour")){
                    recipe.prepTime_label = "more than 1 hour";
                }
                else{
                    recipe.prepTime_label ="less than 1 hour";
                }
                recipe.searchLabels.add(recipe.dietLabel);
                recipe.searchLabels.add(recipe.prepTime_label);
                recipe.searchLabels.add(recipe.servings_label);

                // add the recipes to an arraylist
                recipeList.add(recipe);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeList;
    }


    // helper method that loads from any Json file
    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}
