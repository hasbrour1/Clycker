package edu.newpaltz.cs.hasbrour1.clycker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class StartTest extends ActionBarActivity {


    private String numQuest = "";
    private int questNumber = 1;
    private int currentNum = 1;



    TextView questNum;
    RadioGroup radioGroupId;
    RadioButton radioAnsButton;

    Button submit;
    String studentAnswer = "";


    public final static String NAME_MESSAGE2 = "com.mycompany.myfirstapp.StartTest.studentAnswer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        questNum = (TextView) findViewById(R.id.questNumber);


        Intent intent = getIntent();
        numQuest = intent.getStringExtra(TestActivity.NUM_OF_QUEST_MESSAGE);
        studentAnswer = "TEST: " + intent.getStringExtra(TestActivity.TEST_CODE) + " Student: " + intent.getStringExtra(TestActivity.NAME_OF_STUDENT) + " Answered: ";
        questNumber = Integer.parseInt(numQuest);


        Context context = getApplicationContext();
        CharSequence text = studentAnswer;
        int duration = Toast.LENGTH_SHORT;


        submitButtonListener();
    }

    public void submitButtonListener(){

        radioGroupId = (RadioGroup) findViewById(R.id.radioAnswerGroup);
        submit = (Button) findViewById(R.id.submit_button);

        questNum.setText("Question: #1");
        numQuest = "1";

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                // get the selected radio button from the group
                int selectedOption = radioGroupId.getCheckedRadioButtonId();
                // find the radiobutton by the previously returned id

                radioAnsButton = (RadioButton) findViewById(selectedOption);
                studentAnswer = studentAnswer + numQuest + radioAnsButton.getText();

                currentNum++;
                numQuest =  "" + currentNum;

                if(currentNum <= questNumber) {
                    radioGroupId.clearCheck();
                    questNum.setText("Question: #" + numQuest);
                }

                if(currentNum == questNumber + 1){
                    Intent intent2 = new Intent(StartTest.this, SendTestData.class);
                    intent2.putExtra(NAME_MESSAGE2, studentAnswer);
                    startActivity(intent2);
                }
            }});

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_test, menu);
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
}
