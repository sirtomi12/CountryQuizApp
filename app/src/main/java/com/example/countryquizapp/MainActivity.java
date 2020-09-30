package com.example.countryquizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView countLabel;
    private TextView questionLabel;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 5;

    ArrayList<ArrayList> quizArray = new ArrayList<>();

    String quizData[][] = {
            // {"Country", "Rignt Answer", "Choice1", "Choice2","Choice3"}
            {"China", "Beijing", "Jakarta", "Manila", "Stockholm"},
            {"India", "New Delhi", "Beijing", "Bangkok", "Seoul"},
            {"Indonesia", "Jakarta", "Manila", "New Delhi", "Kuala Lumpur"},
            {"Japan", "Tokyo", "Bangkok", "Taipei", "Jakarta"},
            {"Thailand", "Bangkok", "Berlin", "Havana", "Kingston"},
            {"Brazil", "Brasilia", "Havana", "Bangkok", "Copenhagen"},
            {"Canada", "Ottawa", "Bern", "Copenhagen", "Jakarta"},
            {"Cuba", "Havana", "Bern", "London", "Mexico City"},
            {"Mexico", "Mexico City", "Ottawa", "Berlin", "Santiago"},
            {"United States", "Washington D.C", "San Jose", "Buenos Aires", "Kuala Lumpur"},
            {"France", "Paris", "Ottawa", "Copenhagen", "Toyko"},
            {"Germany", "Berlin", "Copenhagen", "Bangkok", "Santiago"},
            {"Italy", "Rome", "London", "Paris", "Athens"},
            {"Spain", "Madrid", "Mexico City", "Jakarta", "Havana"},
            {"United Kingdom", "London", "Rome", "Paris", "Singapore"}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countLabel = (TextView) findViewById(R.id.countLabel);
        questionLabel = (TextView) findViewById(R.id.questionLabel);
        answerBtn1 = (Button) findViewById(R.id.answerBtn1);
        answerBtn2 = (Button) findViewById(R.id.answerBtn2);
        answerBtn3 = (Button) findViewById(R.id.answerBtn3);
        answerBtn4 = (Button) findViewById(R.id.answerBtn4);

        //Receive quizCategory from StartActivity.
        int quizCategory = getIntent().getIntExtra("QUIZ_CATEGORY", 0 );

        Log.v("CATEGORY_TAG", quizCategory + "");

        //create quizArray from quizData
        for (int i = 0; i < quizData.length; i++) {
            //prepare Array
            ArrayList<String> tmpArray = new ArrayList<>();
            tmpArray.add(quizData[i][0]); //Country
            tmpArray.add(quizData[i][1]); //RightAnswer
            tmpArray.add(quizData[i][2]); //Choice1
            tmpArray.add(quizData[i][3]); //Choice2
            tmpArray.add(quizData[i][4]); //Choice3

            //Add tmpArray to QuizArray
            quizArray.add(tmpArray);
        }
        showNextQuiz();
    }

    public void showNextQuiz() {

        //Update quizCountLabel
        countLabel.setText("Q" + quizCount);

        //Generate random number between 0 and 14 (quizArray's size - 1).

        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        //pick one quiz set
        ArrayList<String> quiz = quizArray.get(randomNum);

        //set question and right answer
        //Array format
        questionLabel.setText(quiz.get(0));
        rightAnswer = quiz.get(1);

        //Remove "Country" from quiz and shuffle choice
        quiz.remove(0);
        Collections.shuffle(quiz);

        //Set Choice
        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));

        //Remove this quiz
        quizArray.remove(randomNum);

    }

    public void checkAnswer(View view) {
        //get pushed button
        Button answerBtn = (Button) findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;

        if (btnText.equals(rightAnswer)) {
            //correct!
            alertTitle = "Correct!";
            rightAnswerCount++;

        } else {
            //Wrong.....
            alertTitle = "Wrong...";
        }

        //create Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("Answer :" + rightAnswer);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                if (quizCount == QUIZ_COUNT) {
                    //Show result
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    startActivity(intent);
                } else {
                    quizCount++;
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }


}
