package edu.newpaltz.cs.hasbrour1.clycker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public final static String NAME_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public final static String CODE_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    public static String studentEnteredName;
    public static String enteredTestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends Fragment {

        EditText testCodeBox;
        EditText studentName;
        Button submitButton;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            testCodeBox = (EditText) rootView.findViewById(R.id.testCodeBox);
            studentName = (EditText) rootView.findViewById(R.id.student_name_Box);
            submitButton = (Button) rootView.findViewById(R.id.submit_name_button);

            submitButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    studentEnteredName = studentName.getText().toString();
                    enteredTestCode = testCodeBox.getText().toString();

                    Intent intent = new Intent(MainActivity.this, TestActivity.class);
                    intent.putExtra(NAME_MESSAGE, studentEnteredName);
                    intent.putExtra(CODE_MESSAGE, enteredTestCode);
                    startActivity(intent);
            }});


            return rootView;
        }
    }
}
