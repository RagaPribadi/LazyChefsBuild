package edu.pitt.cs.cs1635.alk157.lazychef;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;

public class RecipeFavorite extends AppCompatActivity {

    TextView recipeName;
    TextView cookTime;
    TextView calories;
    TextView nutrition;
    TextView recipeDetails;
    private ImageView mainImage = null;
    ArrayList<String> favorite_recipes = new ArrayList<String>();
    private boolean existed = true;
    String rSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_favorite);

        recipeName = (TextView) findViewById(R.id.recipeName);
        cookTime = (TextView) findViewById(R.id.cookTime);
        calories = (TextView) findViewById(R.id.calories);
        nutrition = (TextView) findViewById(R.id.nutrition);
        recipeDetails = (TextView) findViewById(R.id.recipeDetails);

        mainImage = (ImageView)findViewById(R.id.imageView);

        Bundle extras = getIntent().getExtras();

        rSelected = extras.getString("recipe_favorite");
        Log.i("passed", rSelected);
        String[] elements = rSelected.split("\\|");

        String attributes = elements[4];
        int startIndex1 = attributes.indexOf("Calories:")+("Calories:").length();
        int endIndex1 = attributes.indexOf("Fat")-2;
        String substring1 = attributes.substring(startIndex1, endIndex1);
        if(substring1.contains(" "))
            substring1 = substring1.substring(1, substring1.length());
        double cals = Double.parseDouble(substring1);
        int startIndex2 = attributes.indexOf("Protein:")+("Protein:").length();
        int endIndex2= attributes.indexOf("Cholesterol:")-2;
        String substring2 = attributes.substring(startIndex2, endIndex2);
        if(substring2.contains(" "))
            substring2 = substring2.substring(1, substring2.length());
        double protein = Double.parseDouble(substring2);
        String instruction="";
        String[] primitive = elements[6].split("\\}\\{");
        primitive[0] = primitive[0].substring(1, primitive[0].length());
        primitive[primitive.length-1] = primitive[primitive.length-1].substring(0, primitive[primitive.length-1].length()-1);
        for(int i = 0; i < primitive.length; i++)
        {
            instruction += ((i+1)+". "+primitive[i]+"\n\n");
        }
        recipeName.setText(elements[1]);
        int start_index = elements[5].indexOf("Cook: ") + ("Cook: ").length();
        int end_index = elements[5].indexOf("Ready in:") - 2;
        int cook_minutes = Integer.parseInt(elements[5].substring(start_index, end_index));

        cookTime.setText(cook_minutes+" Minutes");
        calories.setText(cals+"");
        nutrition.setText(protein+"g of Protein");
        recipeDetails.setMovementMethod(new ScrollingMovementMethod());
        recipeDetails.setText(instruction);

        new LoadImage().execute(elements[2]);
    }

    public void deleteThisEntry(View view)
    {
        readFromFile();
        String to_be_sent = "";
        if(favorite_recipes.size() == 1)
        {
            writeToFile("");
        }
        else {
            for (String recipe : favorite_recipes) {
                if (!recipe.equals(rSelected)) {
                    to_be_sent += (recipe + "\n");
                }
            }
            to_be_sent = to_be_sent.substring(0, to_be_sent.length() - 1);
            writeToFile(to_be_sent);
        }
        //go to favorite page automatically
        Intent i = new Intent(this, FavoriteActivity.class);//change to right name
        startActivity(i);
    }

    public void toMain(View view)
    {
        Intent i = new Intent(this, MainActivity.class);//change to right name
        startActivity(i);
    }

    public void toFavorites(View view)
    {
        Intent i = new Intent(this, FavoriteActivity.class);//change to right name
        startActivity(i);
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("favorite.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            Log.i("test", data);
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

    private class LoadImage extends AsyncTask<String, String, Bitmap[]> {

        protected Bitmap[] doInBackground(String... args) {
            Bitmap bitmap[] = new Bitmap[3];
            try {
                bitmap[0] = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap[] image) {
            if(image != null) {
                mainImage.setImageBitmap(image[0]);
            }
        }
    }
}
