package edu.pitt.cs.cs1635.alk157.lazychef;

import android.os.AsyncTask;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class RecipeList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ArrayList<String> recipe_calories = null;
    private ArrayList<String> recipe_protein = null;
    private ArrayList<String> full_recipes = new ArrayList<>();
    private TextView recipeName1 = null;
    private TextView recipeName2 = null;
    private TextView recipeName3 = null;
    private TextView content1 = null;
    private TextView content2 = null;
    private TextView content3 = null;
    private ImageView image1 = null;
    private ImageView image2 = null;
    private ImageView image3 = null;
    private String received = "";
    //TO-DO: add each elements in the array dynamically.
    //Pass the information to next screen.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recipeName1 = (TextView)findViewById(R.id.recipe1);
        recipeName2 = (TextView)findViewById(R.id.recipe2);
        recipeName3 = (TextView)findViewById(R.id.recipe3);
        content1 = (TextView)findViewById(R.id.content1);
        content2 = (TextView)findViewById(R.id.content2);
        content3 = (TextView)findViewById(R.id.content3);
        image1 = (ImageView)findViewById(R.id.recipeImage1);
        image2 = (ImageView)findViewById(R.id.recipeImage2);
        image3 = (ImageView)findViewById(R.id.recipeImage3);

        final Spinner spinner = (Spinner)findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.packageType_Lists, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        Bundle extras = getIntent().getExtras();

        received = extras.getString("recipe_package");

        //split each receipe by "\n"
        String receipe_list[] = received.split("\n");
        String simplified_list[] = new String[receipe_list.length];
        for(int i = 0; i < receipe_list.length; i++)
        {
            full_recipes.add(receipe_list[i]);
            String[] elements = receipe_list[i].split("\\|");

            String attributes = elements[4];
            int startIndex1 = attributes.indexOf("Calories:")+("Calories:").length();
            int endIndex1 = attributes.indexOf("Fat")-2;
            String substring1 = attributes.substring(startIndex1, endIndex1);
            if(substring1.contains(" "))
                substring1 = substring1.substring(1, substring1.length());
            double calories = Double.parseDouble(substring1);
            int startIndex2 = attributes.indexOf("Protein:")+("Protein:").length();
            int endIndex2= attributes.indexOf("Cholesterol:")-2;
            String substring2 = attributes.substring(startIndex2, endIndex2);
            if(substring2.contains(" "))
                substring2 = substring2.substring(1, substring2.length());
            double protein = Double.parseDouble(substring2);
            simplified_list[i] = elements[1]+"|"+elements[2]+"|"+calories+"|"+protein;
        }

        //generate two ordered arrayList for different ordered view
        for(int i = 0; i < simplified_list.length -1 ; i++)
        {
            String temp;
            int temp_compare;
            String[] elements = simplified_list[i].split("\\|");
            double cal1 = Double.parseDouble(elements[2]);
            elements = simplified_list[i+1].split("\\|");
            double cal2 = Double.parseDouble(elements[2]);
            if(cal1 > cal2)
            {
                temp = simplified_list[i];
                simplified_list[i] = simplified_list[i+1];
                simplified_list[i+1] = temp;
            }
        }
        recipe_calories = new ArrayList<String>(Arrays.asList(simplified_list));
        setList_Cal();

        for(int i = 0; i < simplified_list.length -1 ; i++)
        {
            String temp;
            int temp_compare;
            String[] elements = simplified_list[i].split("\\|");
            double pro1 = Double.parseDouble(elements[3]);
            elements = simplified_list[i+1].split("\\|");
            double pro2 = Double.parseDouble(elements[3]);
            if(pro1 > pro2)
            {
                temp = simplified_list[i];
                simplified_list[i] = simplified_list[i+1];
                simplified_list[i+1] = temp;
            }
        }
        recipe_protein = new ArrayList<String>(Arrays.asList(simplified_list));
    }

    public void startNewScreen(View view)
    {
       TextView current_textView = (TextView)findViewById(view.getId());
        String recipe_name = current_textView.getText().toString();
        Log.i("socket", recipe_name);
        String recipe_TobeSent = "";
        for(String r : full_recipes)
        {
            if(r.contains(recipe_name))
            {
                recipe_TobeSent = r;
                break;
            }
        }

        //start a new activity
        Intent intent = new Intent(this, Recipe.class);
        intent.putExtra("full_recipes", received);
        intent.putExtra("selected_recipes", recipe_TobeSent);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if(item.equals("Calories"))
        {
            setList_Cal();
        }
        else
        {
            setList_Protein();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setList_Cal()
    {
        //set default as recipe_calories
        String url ="";
        String recipe = recipe_calories.get(0);
        String[] elements = recipe.split("\\|");
        recipeName1.setText(elements[0]);
        content1.setText("Calories : " + elements[2]);
        url = elements[1];

        String recipe2 = recipe_calories.get(1);
        String[] elements2 = recipe2.split("\\|");
        recipeName2.setText(elements2[0]);
        content2.setText("Calories : " + elements2[2]);
        url += ("|"+elements2[1]);

        String recipe3 = recipe_calories.get(2);
        String[] elements3 = recipe3.split("\\|");
        recipeName3.setText(elements3[0]);
        content3.setText("Calories : " + elements3[2]);
        url += ("|"+elements3[1]);

        new LoadImage().execute(url);
    }

    public void setList_Protein()
    {
        //set default as recipe_calories
        String url ="";
        String recipe = recipe_protein.get(0);
        String[] elements = recipe.split("\\|");
        recipeName1.setText(elements[0]);
        content1.setText("Protein : " + elements[3]);
        url = elements[1];

        String recipe2 = recipe_protein.get(1);
        String[] elements2 = recipe2.split("\\|");
        recipeName2.setText(elements2[0]);
        content2.setText("Protein : " + elements2[3]);
        url += ("|"+elements2[1]);

        String recipe3 = recipe_protein.get(2);
        String[] elements3 = recipe3.split("\\|");
        recipeName3.setText(elements3[0]);
        content3.setText("Protein : " + elements3[3]);
        url += ("|"+elements3[1]);

        new LoadImage().execute(url);
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap[]> {

        protected Bitmap[] doInBackground(String... args) {
            Bitmap bitmap[] = new Bitmap[3];
            try {
                String[] urls = args[0].split("\\|");
                bitmap[0] = BitmapFactory.decodeStream((InputStream)new URL(urls[0]).getContent());
                bitmap[1] = BitmapFactory.decodeStream((InputStream)new URL(urls[1]).getContent());
                bitmap[2] = BitmapFactory.decodeStream((InputStream)new URL(urls[2]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap[] image) {
            if(image != null) {
                image1.setImageBitmap(image[0]);
                image2.setImageBitmap(image[1]);
                image3.setImageBitmap(image[2]);
            }
        }
    }
}
