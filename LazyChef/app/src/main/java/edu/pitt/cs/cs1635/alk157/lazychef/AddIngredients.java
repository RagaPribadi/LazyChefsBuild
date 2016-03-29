package edu.pitt.cs.cs1635.alk157.lazychef;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.content.Intent;

public class AddIngredients extends AppCompatActivity {


    // Parent view for all ingred rows and the add button.
    private LinearLayout mContainerView;
   // private ImageButton plusIc; //Add button that takes the string from addIngred to entN, and creates new row
    // There always should be only one empty row, other empty rows will
    // be removed.
    private View mExclusiveEmptyView;

    //private EditText addIngred; // add ingredients box
    private TextView ent1; //the first recipe box
   // private int ent1N = 1;//this correlates to the position of the box that will be updated when clear
    private Button doneB; //send to next activity
    private int entN = 1; //where plusIc places the string
   // private int clearN = 0; //index addition to textEdit
   // private int maxN = 7;//expands with clearN
   private Button mAddButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContainerView = (LinearLayout) findViewById(R.id.parentView);

        //connect Image button to code
        //plusIc = (ImageButton) findViewById(R.id.imageButton);
         mAddButton = (Button) findViewById(R.id.btnAddNewItem); //og for reference


        //connect edittext to code
        //addIngred = (EditText) findViewById(R.id.addIngred);


        //connect textViews so they can display later
        ent1 = (TextView) findViewById(R.id.res1);

        //connect final done button to code
        doneB = (Button) findViewById(R.id.button);
        // while (ent1 == null)
        // doneB.setEnabled(false);

        inflateEditRow("ex: chicken");



        //enable plus image button when there is a string of at least 3 in text edit


        //on the click of the button, send the string to entN


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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // TODO: Handle screen rotation:
        // encapsulate information in a parcelable object, and save it
        // into the state bundle.

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // TODO: Handle screen rotation:
        // restore the saved items and inflate each one with inflateEditRow;

    }


    //onClick handler for search button
    public void getValues(View v){

        String send = null;
       int c =  mContainerView.getChildCount();
        for (int i = 1; i <= c; i++){
            Editable r =  (Editable) mContainerView.getChildAt(i); //get these into a string
            String val = r.toString();//this is coming out null
            send = send + ", " + val;//join to the string send and ", "
            System.out.println(send);

            //send an intent to the next activity - for demo just pass chicken
/**
            Intent i = new Intent(getApplicationContext(), NewActivity.class);
            i.putExtra("commaDelimitedList", send);
            startActivity(i);    **/


        }

    }



    // onClick handler for the "Add new"/plus button;
    public void onAddNewClicked(View v) {
        // Inflate a new row and hide the button self.
        inflateEditRow(null);
        v.setVisibility(View.GONE);
    }

    // onClick handler for the "X" button of each row
    public void onDeleteClicked(View v) {
        // remove the row by calling the getParent on button
        mContainerView.removeView((View) v.getParent());
    }

    // Helper for inflating a row
    private void inflateEditRow(String name) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.row, null);
        final ImageButton deleteButton = (ImageButton) rowView
                .findViewById(R.id.buttonDelete);
        final EditText editText = (EditText) rowView
                .findViewById(R.id.editText);

        if (name != null && !name.isEmpty()) {
            editText.setText(name);
        } else {
            mExclusiveEmptyView = rowView;
            deleteButton.setVisibility(View.INVISIBLE);
        }

// A TextWatcher to control the visibility of the "Add new" button and
        // handle the exclusive empty view.
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()) {
                    mAddButton.setVisibility(View.GONE);
                    deleteButton.setVisibility(View.INVISIBLE);

                    if (mExclusiveEmptyView != null
                            && mExclusiveEmptyView != rowView) {
                        mContainerView.removeView(mExclusiveEmptyView);
                    }
                    mExclusiveEmptyView = rowView;
                } else {

                    if (mExclusiveEmptyView == rowView) {
                        mExclusiveEmptyView = null;
                    }

                    mAddButton.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }


        });

// Inflate at the end of all rows but before the "Add new" button
        mContainerView.addView(rowView, mContainerView.getChildCount() - 1);
    }
}
