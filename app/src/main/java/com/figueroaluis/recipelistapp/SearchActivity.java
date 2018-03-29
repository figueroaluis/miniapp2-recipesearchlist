package com.figueroaluis.recipelistapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by luisfigueroa on 3/12/18.
 */

public class SearchActivity extends AppCompatActivity {
    private Button mSearchButton;
    private Context mContext;
    private Spinner diet_spinner;
    private Spinner servings_spinner;
    private Spinner prep_time_spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        mContext = this;
        mSearchButton = (Button)findViewById(R.id.search_button);
        diet_spinner = (Spinner)findViewById(R.id.diet_spinner);
        servings_spinner = (Spinner)findViewById(R.id.servings_spinner);
        prep_time_spinner = (Spinner)findViewById(R.id.prep_time_spinner);


        final ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", this);
        ArrayList<String> dietlabel = new ArrayList<>();
        ArrayList<String> servinglabel = new ArrayList<>();
        ArrayList<String> timelabel = new ArrayList<>();
        dietlabel.add("No restriction");
        servinglabel.add("No restriction");
        timelabel.add("No restriction");
        timelabel.add("30 minutes or less");

        for (int i = 0; i < recipeList.size(); i++){
            String diet = recipeList.get(i).dietLabel;
            String serving = recipeList.get(i).servings_label;
            String time = recipeList.get(i).prepTime_label;
            if(!dietlabel.contains(diet)){
                dietlabel.add(diet);
            }
            if(!servinglabel.contains(serving)){
                servinglabel.add(serving);
            }
            if(!timelabel.contains(time)){
                timelabel.add(time);
            }
        }
        servinglabel.add("less than 4");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dietlabel);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, servinglabel);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timelabel);

        diet_spinner.setAdapter(adapter1);
        servings_spinner.setAdapter(adapter2);
        prep_time_spinner.setAdapter(adapter3);

        diet_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        servings_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        prep_time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(mContext, ResultActivity.class);

                detailIntent.putExtra("diet", diet_spinner.getSelectedItem().toString());
                detailIntent.putExtra("serving", servings_spinner.getSelectedItem().toString() );
                detailIntent.putExtra("time", prep_time_spinner.getSelectedItem().toString() );

                startActivity(detailIntent);
            }

        });
    }


}
