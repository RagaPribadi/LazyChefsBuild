package edu.pitt.cs.cs1635.alk157.lazychef;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Recipe extends AppCompatActivity {

    TextView recipeName;
    TextView cookTime;
    TextView calories;
    TextView nutrition;
    TextView recipeDetails;

    String rID;
    String rFull;
    String cTime;
    String cal;
    String nut;
    String rDetials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipeName = (TextView) findViewById(R.id.recipeName);
        cookTime = (TextView) findViewById(R.id.cookTime);
        calories = (TextView) findViewById(R.id.calories);
        nutrition = (TextView) findViewById(R.id.nutrition);
        recipeDetails = (TextView) findViewById(R.id.recipeDetails);

        rFull = getIntent().getExtras().getString("rFull","full_recipes");//name of recipe
        rID = getIntent().getExtras().getString("rID","selected_recipes");//recipe ID number

        recipeName.setText(rFull);
        cookTime.setText(cTime);
        calories.setText(cal);
        nutrition.setText(nut);
        recipeDetails.setText(rDetials);

        nutrition.getText();
    }

    public void toRecipeList(View view)
    {
        Intent i = new Intent(this, RecipeList.class);//Change to right name
        i.putExtra("recipe_package",rFull);
        startActivity(i);
    }

    public void toFavorites(View view)
    {
        Intent i = new Intent(this, FavoriteActivity.class);//change to right name
        startActivity(i);
    }

    public void addFavorites(View view)
    {
        Intent i = new Intent(this, FakeFavoriteActivity.class);//change to right name
        i.putExtra("rID",rID);
        startActivity(i);
    }
}