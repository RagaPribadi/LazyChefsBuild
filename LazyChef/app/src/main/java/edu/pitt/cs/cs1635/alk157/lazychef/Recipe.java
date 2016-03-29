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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipeName = (TextView) findViewById(R.id.recipeName);
        cookTime = (TextView) findViewById(R.id.cookTime);
        calories = (TextView) findViewById(R.id.calories);
        nutrition = (TextView) findViewById(R.id.nutrition);
        recipeDetails = (TextView) findViewById(R.id.recipeDetails);

        recipeName.setText("Chicken Parm");
        cookTime.setText("25 Minutes");
        calories.setText("450");
        nutrition.setText("Good");
        recipeDetails.setText("Put the cheese on the chicken");

        nutrition.getText();
    }

    public void toRecipeList(View view)
    {
        Intent i = new Intent(this, Recipe.class);//Change to right name
        startActivity(i);
    }

    public void toFavorites(View view)
    {
        Intent i = new Intent(this, Recipe.class);//change to right name
        startActivity(i);
    }

    public void addFavorites(View view)
    {
        Intent i = new Intent(this, Recipe.class);//change to right name
        i.putExtra("favoriteRecipe",recipeName.getText());
        startActivity(i);
    }
}