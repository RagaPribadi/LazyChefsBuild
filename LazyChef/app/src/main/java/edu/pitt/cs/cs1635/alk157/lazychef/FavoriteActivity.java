package edu.pitt.cs.cs1635.alk157.lazychef;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity  {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private ArrayList<String> favorite_recipes = new ArrayList<String>();
    private boolean existed = true;
    // private RelativeLayout myRelativeLayout;
    private RelativeLayout myRelativeLayout;
    private LinearLayout myLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        myRelativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        myLinearLayout = (LinearLayout)findViewById(R.id.linearLayout_f);
        readFromFile();
        if(!existed)
        {
            //add a text view for no recipes.
            Log.i("NoRecipe", "Display nothing");
            TextView nothing_display = new TextView(this);
            nothing_display.setText("Uh oh! Looks like you haven't favorited any recipes yet!");
            nothing_display.setTextColor(Color.BLACK);
            nothing_display.setTextSize(36);
            Log.i("NoRecipe", nothing_display.getText().toString());
            myRelativeLayout.addView(nothing_display);
        }
        else
        {
            for(final String recipe : favorite_recipes)
            {
                TextView recipe_display = new TextView(this);
                final String[] elements = recipe.split("\\|");
                recipe_display.setText(elements[1] + "\n");
                recipe_display.setTextColor(Color.BLACK);
                recipe_display.setTextSize(18);
                recipe_display.setClickable(true);
                recipe_display.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //start a new activity called favorite_recipe_display.
                        Intent intent = new Intent(FavoriteActivity.this, RecipeFavorite.class);
                        intent.putExtra("recipe_favorite", recipe);
                        startActivity(intent);
                    }
                }
                );
                myLinearLayout.addView(recipe_display);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void backToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("favorite.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void readFromFile() {
        try {
            InputStream inputStream = openFileInput("favorite.txt");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    favorite_recipes.add(receiveString);
                }
                inputStream.close();
                if(favorite_recipes.size() == 0) {
                    //add a text view for no recipes.
                    Log.i("NoRecipe", "Display nothing");
                    existed = false;
                }
            }
        }
        catch (FileNotFoundException e) {
            //the file does not exist.
            //add a text view for no recipes.
            Log.e("NoRecipe", "Display nothing");
            writeToFile(""); //write an empty string to the file
            existed = false;
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }
}
