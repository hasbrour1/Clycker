package edu.newpaltz.cs.hasbrour1.clycker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;


public class SendTestData extends ActionBarActivity {

    private String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_test_data);

        Intent intent = getIntent();
        answer = intent.getStringExtra(StartTest.NAME_MESSAGE2);

        displayValues("Sending Answers");

        SendFeedTask setWeb = new SendFeedTask();
        setWeb.execute();


    }

    public void displayValues(String str){
        TextView text = (TextView) findViewById(R.id.sentTest);
        text.setText(str);
    }

    public void onBackPressed(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_test_data, menu);
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


    /*
    Send Test Data Back to server
     */
    private class SendFeedTask extends AsyncTask<Long, String, String> {

        @Override
        protected String doInBackground(Long... params) {

            /**  Connects to the SimpleServer on port 8189 and sends a few demo lines
             *  to the server, and reads, displays the server reply,
             *  then issues a Bye command signaling the server to quit.
             */
            onProgressUpdate("Submitting Answers");
            String serverurl = "192.168.107.10";
            int serverport = 9999;
            Socket socket = null;
            try {
                socket = new Socket(serverurl, serverport);
                //Set socket timeout for 10000 milliseconds or 10 seconds just
                //in case the host can't be reached
                socket.setSoTimeout(10000);

                //InputStreamReader inputstreamreader = new InputStreamReader(socket.getInputStream());
                //BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
                //establish an printwriter using the output streamof the socket object
                //and set auto flush on
                PrintWriter printwriter = new PrintWriter(socket.getOutputStream(), true);
                printwriter.println(answer);
                printwriter.println("BYE");
                String lineread = "";
                //bufferedreader.close();
                //inputstreamreader.close();
                printwriter.close();
                socket.close();
                System.exit(0);

            } catch (UnknownHostException unhe) {

            } catch (InterruptedIOException intioe) {

            } catch (IOException ioe) {

            } finally {
                try {
                    socket.close();
                } catch (IOException ioe) {

                }
            }
            return("Sent Answers");
        }


        protected void onProgressUpdate(String... values){
            super.onProgressUpdate(values);

            displayValues(values[0]);

        }

        protected void onPostExecute(String str) {
            displayValues(str);
        }
    }

}
