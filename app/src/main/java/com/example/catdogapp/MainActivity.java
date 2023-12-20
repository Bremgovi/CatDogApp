package com.example.catdogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton catImage = (ImageButton) findViewById(R.id.catImage);
        ImageButton dogImage = (ImageButton) findViewById(R.id.dogImage);
    }

    public void catClicked(View v){
        System.out.println("Cat Clicked");
        Intent intent = new Intent(MainActivity.this, Generator.class);
        intent.putExtra("animal", "cat");
        startActivity(intent);
    }

    public void dogClicked(View v){
        System.out.println("Cat Clicked");
        Intent intent = new Intent(MainActivity.this, Generator.class);
        intent.putExtra("animal", "dog");
        startActivity(intent);
    }
}