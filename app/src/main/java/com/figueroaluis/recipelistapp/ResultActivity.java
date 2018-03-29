package com.figueroaluis.recipelistapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by luisfigueroa on 3/12/18.
 */

public class ResultActivity extends AppCompatActivity {
    private ListView mListView;
    private Context mContext;
    private TextView resultTextView;
    private RecipeAdapter adapter;
    private ArrayList<Recipe> recipeList;
    private ArrayList<Recipe> foundList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_recipe_item);

        mContext = this;
        resultTextView = findViewById(R.id.result_text);
        recipeList = Recipe.getRecipesFromFile("recipes.json",this);
        foundList = new ArrayList<>();

        String diet = this.getIntent().getExtras().getString("diet");
        String serving = this. getIntent().getExtras().getString("serving");
        String time = this.getIntent().getExtras().getString("time");

        for (int i = 0; i < recipeList.size(); i++){
            if (labelCheck(diet, serving, time, recipeList.get(i))){
                foundList.add(recipeList.get(i));
            }
        }

        resultTextView.setText("Found " + foundList.size() + " recipes");

        adapter = new RecipeAdapter(this, foundList);
        mListView = findViewById(R.id.recipe_list_view);
        mListView.setAdapter(adapter);

    }



    public boolean dietCheck(String diet, Recipe recipe){
        if (diet.equals("No restriction") || recipe.searchLabels.contains(diet)){
            return true;
        } else{
            return false;
        }
    }

    public boolean servingCheck(String serving, Recipe recipe){
        if (serving.equals("No restriction") || recipe.searchLabels.contains(serving)){
            return true;
        } else{
            return false;
        }
    }
    public boolean timeCheck(String time, Recipe recipe){
        if(time.equals("No restriction") || recipe.searchLabels.contains(time)){
            return true;
        } else if(time.equals("30 minutes or less") && recipe.prepTime_label.equals("less than 1 hour")){
            String min = recipe.prepTime.substring(0, 2);
            int mins = Integer.parseInt(min);

            if(mins <= 30){
                return true;
            } else{
                return false;
            }
        } else{
            return false;
        }
    }
    public boolean labelCheck(String diet, String serving, String time, Recipe recipe){
        boolean matchDiet = dietCheck(diet, recipe);
        boolean matchServing = servingCheck(serving,recipe);
        boolean matchTime = timeCheck(time,recipe);

        if(matchDiet && matchServing && matchTime){
            return true;
        } else{
            return false;
        }
    }
}
