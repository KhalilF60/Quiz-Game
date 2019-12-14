package com.example.quizgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
boolean correctAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.textView_question);
        Button trueButton = findViewById(R.id.true_button);
        Button falseButton = findViewById(R.id.false_button);


        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer == false) {
                    showDialogBox("You won", "You picked the right answer");
                }else{
                    showDialogBox("You lost", "You picked the wrong answer");
                }

            }
        });
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer == true) {
                    showDialogBox("You won", "You picked the right answer");
                }else{
                    showDialogBox("You lost", "You picked the wrong answer");
                }
            }
        });
// ...

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://opentdb.com/api.php?amount=1&type=boolean";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        TriviaResponse newTrivia = gson.fromJson(response, TriviaResponse.class);

                        String question = newTrivia.getResults().get(0).getQuestion();
                        correctAnswer = Boolean.parseBoolean(newTrivia.getResults().get(0).getCorrect_answer());
                        // Display the first 500 characters of the response string.
                        Log.d("test",response);
                        textView.setText("Question: "+ question);
                        setTimer(10);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
                error.printStackTrace();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);





    }
    private void setTimer(int timeDesired) {
        //turn passed in number into milliseconds by multiplying by 1000
        timeDesired = timeDesired * 1000;

        //find the progress bar
        final ProgressBar timeRemainingView = findViewById(R.id.progressBar);

        //set the max value
        timeRemainingView.setMax(timeDesired);
        timeRemainingView.setProgress(timeDesired);

        //Create a Countdown timer
        CountDownTimer time = new CountDownTimer(timeDesired, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                //Get the current time and subtract interval
                int currentTime = timeRemainingView.getProgress();
                currentTime -= 55;

                //Re display time
                timeRemainingView.setProgress(currentTime);
            }

            @Override
            public void onFinish() {
                //if the user clicked a button then we do not show the showDialog Box
                //  if(userAnswered == false){
                showDialogBox("You Lost!", "You let the timer reach to zero");
                // }
            }
        };

        time.start();
    }

    //This method shows a simple dialog box with whatever title and message is passed in
    private void showDialogBox(String title, String message) {
        new AlertDialog.Builder(MainActivity.this).setTitle(title).setMessage(message).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }








}
