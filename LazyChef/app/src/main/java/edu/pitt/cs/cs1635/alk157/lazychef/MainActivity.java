package edu.pitt.cs.cs1635.alk157.lazychef;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**

    public void searchRecipe(View view) {
        Intent intent = new Intent(this, StageActivity.class);
        startActivity(intent);
    }
     */

    public void faveRecipes(View view) {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }


}
