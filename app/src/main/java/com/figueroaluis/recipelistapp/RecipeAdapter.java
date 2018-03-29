package com.figueroaluis.recipelistapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by luisfigueroa on 3/12/18.
 */

public class RecipeAdapter extends BaseAdapter {

    // ------- most of this code is the same for any basic list app ------- //

    // Adapter: take the app itself, list of data to display
    // Other instance variables included
    private Context mContext;
    private ArrayList<Recipe> mRecipeList;
    private LayoutInflater mInflater;
    private TextView titleTextView;

    // Constructor
    public RecipeAdapter(Context mContext, ArrayList<Recipe> mRecipeList) {
        // Initialize instance variables
        this.mContext = mContext;
        this.mRecipeList = mRecipeList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // Methods that we need to override
    @Override
    public int getCount() {
        return mRecipeList.size();
    }

    // Returns item at given position
    @Override
    public Object getItem(int position) {
        return mRecipeList.get(position);
    }

    // Return the row ID of the associated list item
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Check if the view already exists
        // If so, no need to inflate/findViewbyId
        if(convertView == null){
            // Inflate
            convertView = mInflater.inflate(R.layout.list_item_recipe, parent, false);
            // Add the views to the holder
            holder = new ViewHolder();
            holder.titleTextView = convertView.findViewById(R.id.recipe_list_title);
            holder.servingTextView = convertView.findViewById(R.id.recipe_list_title);
            holder.servingTextView = convertView.findViewById(R.id.recipe_list_servings);
            holder.timeTextView = convertView.findViewById(R.id.recipe_list_prep_time);
            holder.thumbnailImageView = convertView.findViewById(R.id.recipe_list_thumbnail);
            holder.recipeInstructions = convertView.findViewById(R.id.select_recipe_button);
            // Add the holder to the view
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
        }

        // Set all the components to the views
        titleTextView = holder.titleTextView;
        TextView servingsTextView = holder.servingTextView;
        TextView timeTextView = holder.timeTextView;
        ImageView thumbnailView = holder.thumbnailImageView;
        ImageButton recipeInstructions = holder.recipeInstructions;

        // Get the recipe from the position
        final Recipe recipe = (Recipe)getItem(position);

        // Title
        titleTextView.setText(recipe.title);
        titleTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        titleTextView.setTextSize(20);

        // Servings
        servingsTextView.setText(recipe.servings + " servings");
        servingsTextView.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
        servingsTextView.setTextSize(12);

        // Preparation Time
        timeTextView.setText(recipe.prepTime);
        servingsTextView.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
        timeTextView.setTextSize(12);

        // Thumbnail (load using picasso)
        Picasso.with(mContext).load(recipe.imageUrl).into(thumbnailView);

        // onClickListener for the button
        recipeInstructions.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String instructions = "The instruction for " + recipe.title + " can be found here!";

                final Notification.Builder builder = new Notification.Builder(mContext);

                builder.setStyle(new Notification.BigTextStyle(builder)
                        .bigText(instructions)
                        .setBigContentTitle("Cooking Instructions")
                        .setSummaryText(recipe.description))
                        .setContentTitle("Title")
                        .setContentText("Summary")
                        .setSmallIcon(android.R.drawable.sym_def_app_icon);

                Uri instructionsWebpage = Uri.parse(recipe.instructionUrl);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, instructionsWebpage);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext,0, webIntent,0);
                builder.setContentIntent(pendingIntent);

                NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                nm.notify(1, builder.build());
            }
        });
        return convertView;
    }

    // View holder for specific view we want to work with
    private static class ViewHolder {
        public TextView titleTextView;
        public TextView servingTextView;
        public ImageView thumbnailImageView;
        public TextView timeTextView;
        public ImageButton recipeInstructions;

    }

}
