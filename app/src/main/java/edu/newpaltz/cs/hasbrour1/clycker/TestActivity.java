package edu.newpaltz.cs.hasbrour1.clycker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;


public class TestActivity extends ActionBarActivity {

    public final static String NUM_OF_QUEST_MESSAGE = "com.mycompany.myfirstapp.TestActivity.NUMBOFQUESTIONS";
    public final static String NAME_OF_STUDENT = "edu.newpaltz.cs.hasbrour1.clycker.TestActivity.STUDENTNAME";
    public final static String TEST_CODE = "edu.newpaltz.cs.hasbrour1.clycker.TestActivity.TESTCODE";


    public String studentName;
    public String testCode;
    public static String getTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent = getIntent();
        studentName = intent.getStringExtra(MainActivity.NAME_MESSAGE);
        testCode = intent.getStringExtra(MainActivity.CODE_MESSAGE);
        RetrieveFeedTask getWeb = new RetrieveFeedTask();
        getWeb.execute();

    }

    public void displayValues(String str){
        TextView text = (TextView) findViewById(R.id.inputText);
        text.setText(str);
    }

    public void startTest(String str){

        int numbOfQuestions = -1;

        //get test
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            String currentToken = st.nextToken();

            if(st.hasMoreTokens() && currentToken != null) {
                if (testCode.equals(currentToken))
                    numbOfQuestions = Integer.parseInt(st.nextToken());
            }
        }

        Context context = getApplicationContext();
        CharSequence text = studentName + " " + testCode;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        if(numbOfQuestions == -1){
            displayValues("No Test Data, Enter Different Test Code");
        }else{
            displayValues("Got Test Data, number of questions =" + numbOfQuestions);
            Intent intent1 = new Intent(TestActivity.this, StartTest.class);
            intent1.putExtra(NUM_OF_QUEST_MESSAGE, "" + numbOfQuestions);
            intent1.putExtra(NAME_OF_STUDENT, studentName);
            intent1.putExtra(TEST_CODE, testCode);
            startActivity(intent1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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

    private class RetrieveFeedTask extends AsyncTask<Long, String, String> {

        private String getTest = "";

        private Exception exception;

        @Override
        protected String doInBackground(Long... params) {
            try {

                onProgressUpdate("Proccessing");
                // Create a URL for the desired page
                URL url = new URL("http://192.168.107.10/clycker-web/getTests.txt");

                // Read all the text returned by the server
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                while ((str = in.readLine()) != null) {
                    // str is one line of text; readLine() strips the newline character(s)
                    getTest = getTest + str + " ";
                }
                in.close();

                return getTest;

            } catch (MalformedURLException e) {
            } catch (IOException e) {
            }
            return "No Test Data";
        }

        protected void onProgressUpdate(String... values){
            super.onProgressUpdate(values);

            displayValues(values[0]);

        }

        protected void onPostExecute(String str) {
            displayValues(str);
            startTest(str);
        }
    }

}
