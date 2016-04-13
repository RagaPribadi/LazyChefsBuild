package edu.pitt.cs.cs1635.alk157.lazychef;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
    private String searched_result;
    private String keywords;


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
       // ent1 = (TextView) findViewById(R.id.res1);

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

      /*  String send = null;
       int c =  mContainerView.getChildCount();
        for (int i = 1; i <= c; i++){
            Editable r =  (Editable) mContainerView.getChildAt(i); //get these into a string
            String val = r.toString();//this is coming out null
            send = send + ", " + val;//join to the string send and ", "
            System.out.println(send);
            */
            //send an intent to the next activity - for demo just pass chicken

            /**Intent i = new Intent(getApplicationContext(), NewActivity.class);
            i.putExtra("commaDelimitedList", send);
            startActivity(i);    **/

            //ip address will be changed to adjust to different ip.
            //client myClient = new client("192.168.43.30", 9000);
            //myClient.execute();
            //this will be moved later.
            String recipes = "0|Easy Spinach Souffle|http://images.media-allrecipes.com/userphotos/250x250/681190.jpg|{1 egg}{1/3 cup 1% milk}{1/3 cup grated Parmesan cheese}{1 teaspoon crushed garlic}{salt and pepper to taste}{2 (10 ounce) packages frozen chopped spinach, thawed and drained}|{Servings: 5}{Calories:89}{Fat: 4.1}{Carbs: 6.8}{Protein: 9}{Cholesterol: 44}{Sodium: 489}|{Prep: 5}{Cook: 20}{Ready in: 25}|{Preheat oven to 350 degrees F (175 degrees C).}{In a medium bowl whisk together egg, milk, cheese, garlic, salt and pepper.  Fold in spinach.  Place in a small casserole dish.}{Bake in preheated oven for 20 minutes, or until lightly set.}{NOTE:  If you are in a hurry, use a microwave safe casserole dish, cover with plastic wrap, and cook on high for 3 minutes.  Release the steam, recover, and cook on high for another 3 minutes.  Enjoy!}\n";
            recipes += "1|Chili Cheese Dip V|http://images.media-allrecipes.com/userphotos/250x250/475037.jpg|{1 (8 ounce) package cream cheese, softened}{1 (15 ounce) can chili}{1 cup shredded Cheddar cheese}|{Servings: 32}{Calories:53}{Fat: 4.3}{Carbs: 1.8}{Protein: 2.2}{Cholesterol: 14}{Sodium: 111}|{Prep: 10}{Cook: 5}{Ready in: 15}|{In the bottom of a 9 inch, microwave safe round baking dish, spread the cream cheese. Top cream cheese with an even layer of chili. Sprinkle Cheddar cheese over the chili.}{Heat in the microwave on high heat 5 minutes, or until the cheese has melted.}\n";
            recipes += "2|Hasty Chocolate Pudding|http://images.media-allrecipes.com/userphotos/250x250/39891.jpg|{1/2 cup white sugar}{1/3 cup unsweetened cocoa powder}{3 tablespoons cornstarch}{2 cups milk}{2 teaspoons vanilla extract}|{Servings: 4}{Calories:203}{Fat: 3.4}{Carbs: 40.3}{Protein: 5.4}{Cholesterol: 10}{Sodium: 52}|{Prep: 5}{Cook: 10}{Ready in: 15}|{In a microwave-safe bowl, whisk together the sugar, cocoa and cornstarch. Whisk in milk a little at a time so the mixture does not have any dry lumps.}{Place in the microwave, and cook for 3 minutes on high. Stir, then cook at 1 minute intervals, stirring between cooking times for 2 to 4 minutes, or until shiny and thick. Stir in vanilla.}{Place a piece of plastic wrap directly on the surface of the pudding to prevent a skin from forming, and chill in the refrigerator. Serve cold.\n";
            Intent intent = new Intent(this, RecipeList.class);
            intent.putExtra("recipe_package", recipes);
            startActivity(intent);

        //}

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
    /*
    public class client extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        int dstPort;
        int mystate;

        client(String addr, int port) {
            dstAddress = addr;
            dstPort = port;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Socket socket = null;
            Log.i("socket","before connect");
            try {
                socket = new Socket(dstAddress, dstPort);
                Log.i("socket","connected");
                // Create the input & output streams to the server
                ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
                Log.i("socket","test");
                   */
       			 /* Send Keywords to the server */
    /*
                Log.i("socket","Before Data sent");
                outToServer.writeObject(keywords);
                Log.i("socket","data Sent");

                ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());

                searched_result = (String)inFromServer.readObject(); //received the search list
                Log.i("socket","data Receive");

            } catch (UnknownHostException e) {
                Log.i("socket", "unknownHostException");
                e.printStackTrace();
            } catch (Exception e) {
                Log.i("socket", "Exception");
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                        Log.i("socket", "close");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }*/
}
