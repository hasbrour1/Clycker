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

        Context context = getApplicationContext();
        CharSequence text = studentName + " " + testCode + " " + getTest;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    public void displayValues(String str){
        TextView text = (TextView) findViewById(R.id.inputText);
        text.setText(str);
    }

    public void startTest(String str){
        setContentView(R.layout.activity_test);
        TextView questNum = (TextView) findViewById(R.id.questNumber);
        RadioButton buttonA = (RadioButton) findViewById(R.id.radioButton);
        RadioButton buttonB = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton buttonC = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton buttonD = (RadioButton) findViewById(R.id.radioButton4);
        Button submint = (Button) findViewById(R.id.submit_button);
        String studentAnswer = "Student: " + studentName + "Answered: ";
        int numbOfQuestions = -1;

        //get test
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            String currentToken = st.nextToken();
            if(testCode.equals(currentToken)){
                numbOfQuestions = Integer.parseInt(st.nextToken());
            }else{
                st.nextToken();
            }
        }

        if(numbOfQuestions == -1){
            questNum.setText("No test data");
        }else{
            questNum.setText("#1");
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
                URL url = new URL("http://192.168.107.27/clycker-web/getTests.txt");

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
            TestActivity.getTest = str;
        }
    }

}
