package com.example.catdogapp.trivia;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catdogapp.Generator;
import com.example.catdogapp.MainActivity;
import com.example.catdogapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Trivia extends AppCompatActivity {
    private TextView questionTextView;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;
    private Button answerButton4;
    private String correctAnswer; // Replace with your correct answer
    public static String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trivia);

        Generator generator = new Generator();

        // IDs
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);
        ImageView imageView =(ImageView) findViewById(R.id.imageView);
        questionTextView = findViewById(R.id.questionTextView);
        answerButton1 = findViewById(R.id.answerButton1);
        answerButton2 = findViewById(R.id.answerButton2);
        answerButton3 = findViewById(R.id.answerButton3);
        answerButton4 = findViewById(R.id.answerButton4);
        MenuItem menuItem = bottomNavigationView.getMenu().findItem(R.id.trivia);
        menuItem.setChecked(true);

        // Get data from previous activity
        Bundle extras = getIntent().getExtras();
        result = extras.getString("animal");

        // NavigationBar
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.generator) {
                Intent intent = new Intent(this, Generator.class);
                intent.putExtra("animal", result);
                startActivity(intent);
            }
            else if (itemId == R.id.home) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            return true;
        });

        // Check data from previous activity
        if(result.equals("cat")){
            generator.generateCatImage(imageView);
            readJSON("cat_questions.json");
        }else if(result.equals("dog")){
            generator.generateDogImage(imageView);
            readJSON("dog_questions.json");
        }

    }

    public void answer(View v) {
        if (v instanceof Button) {
            Button button = (Button) v;
            if (button.getText().toString().equals(correctAnswer)) {
                button.setBackgroundColor(Color.GREEN);
                Intent intent = new Intent(this, Trivia.class);
                intent.putExtra("animal", result);
                startActivity(intent);
            } else {
                button.setBackgroundColor(Color.RED);
            }
        }
    }

    public void readJSON(String jsonFileName){
        // Read JSON
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(jsonFileName));
            JSONArray arr = obj.getJSONArray("questions");

            // DECLARE Arrays
            ArrayList<String> questionsArrayList = new ArrayList();
            ArrayList<String []> answersArrayList = new ArrayList();
            ArrayList<String> correctAnswersArrayList = new ArrayList();

            // FILL Arrays
            for(int i = 0; i < arr.length(); i++){
                String question = arr.getJSONObject(i).getString("question");
                correctAnswer = arr.getJSONObject(i).getString("correct_answer");
                JSONArray jsonArray = arr.getJSONObject(i).getJSONArray("answers");
                String [] answersArray = new String[4];
                for (int j = 0; j < jsonArray.length(); j++) {
                    answersArray[j] = jsonArray.getString(j);
                }
                questionsArrayList.add(question);
                answersArrayList.add(answersArray);
                correctAnswersArrayList.add(correctAnswer);
            }

            // Find random question and answers
            Random rand = new Random();
            int randomIndex = rand.nextInt(questionsArrayList.size());
            String randomQuestion = questionsArrayList.get(randomIndex);
            String randomCorrectAnswer = correctAnswersArrayList.get(randomIndex);

            // Change UI
            questionTextView.setText(randomQuestion);
            correctAnswer = randomCorrectAnswer;
            answerButton1.setText(answersArrayList.get(randomIndex)[0]);
            answerButton2.setText(answersArrayList.get(randomIndex)[1]);
            answerButton3.setText(answersArrayList.get(randomIndex)[2]);
            answerButton4.setText(answersArrayList.get(randomIndex)[3]);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String loadJSONFromAsset(String jsonFileName) {
        String json = null;
        try {
            InputStream is = this.getAssets().open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}