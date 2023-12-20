package com.example.catdogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catdogapp.cat.pojo.CatFact;
import com.example.catdogapp.cat.api.CatFactApi;
import com.example.catdogapp.cat.pojo.CatImage;
import com.example.catdogapp.cat.api.CatImageApi;
import com.example.catdogapp.dog.pojo.Data;
import com.example.catdogapp.dog.pojo.DogFact;
import com.example.catdogapp.dog.api.DogFactApi;
import com.example.catdogapp.dog.pojo.DogImage;
import com.example.catdogapp.dog.api.DogImageApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Generator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generator);

        // Get IDs
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        BottomNavigationView bottomNavigationView= (BottomNavigationView)findViewById(R.id.bottomNavigationView);
        MenuItem menuItem = bottomNavigationView.getMenu().findItem(R.id.generator);
        menuItem.setChecked(true);

        // Get data from previous activity
        Bundle extras = getIntent().getExtras();
        String result = extras.getString("animal");

        // Check data from previous activity
        if(result.equals("cat")){
            generateCatImage(imageView);
            generateCatFact(textView);
        }else if(result.equals("dog")){
            generateDogImage(imageView);
            generateDogFact(textView);
        }

        // Button Next Fact
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(result.equals("cat")){
                    generateCatFact(textView);
                }else if(result.equals("dog")){
                    generateDogFact(textView);
                }
            }
        });

        // Navigation Bar
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                startActivity(intent);
            } else if (itemId == R.id.trivia) {
                Intent intent = new Intent(this, Trivia.class);
                intent.putExtra("animal", result);
                startActivity(intent);
            }
            return true;
        });
    }


    public void generateCatImage(ImageView imageView){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CatImageApi catImageApi = retrofit.create(CatImageApi.class);
        Call<List<CatImage>> call = catImageApi.getData();

        call.enqueue(new Callback<List<CatImage>>() {
            @Override
            public void onResponse(Call<List<CatImage>> call, Response<List<CatImage>> response) {
                if(response.isSuccessful()){
                    List<CatImage> data = response.body();
                    String imageUrl = data.get(0).getUrl();
                    Picasso.get().load(imageUrl).into(imageView);
                }
            }

            @Override
            public void onFailure(Call<List<CatImage>> call, Throwable t) {
                System.out.println("failed");
            }
        });
    }
    public void generateDogImage(ImageView imageView){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thedogapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DogImageApi dogImageApi = retrofit.create(DogImageApi.class);
        Call<List<DogImage>> call = dogImageApi.getData();

        call.enqueue(new Callback<List<DogImage>>() {
            @Override
            public void onResponse(Call<List<DogImage>> call, Response<List<DogImage>> response) {
                if(response.isSuccessful()){
                    List<DogImage> data = response.body();
                    String imageUrl = data.get(0).getUrl();
                    Picasso.get().load(imageUrl).into(imageView);
                }
            }

            @Override
            public void onFailure(Call<List<DogImage>> call, Throwable t) {
                System.out.println("failed");
            }
        });
    }
    public void generateCatFact(TextView textView){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://catfact.ninja") //https://cat-fact.herokuapp.com
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CatFactApi catFactApi = retrofit.create(CatFactApi.class);
        Call<CatFact> factCall = catFactApi.getCatFact();
        factCall.enqueue(new Callback<CatFact>() {
            @Override
            public void onResponse(Call<CatFact> call, Response<CatFact> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String text = response.body().getText();
                    textView.setText(text);
                }
            }
            @Override
            public void onFailure(Call<CatFact> call, Throwable t) {
                System.out.println("failed");
            }
        });
    }
    public void generateDogFact(TextView textView){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dogapi.dog")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DogFactApi dogFactApi = retrofit.create(DogFactApi.class);
        Call<DogFact> factCall = dogFactApi.getDogFact();

        factCall.enqueue(new Callback<DogFact>() {
            @Override
            public void onResponse(Call<DogFact> call, Response<DogFact> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DogFact jsonResponse = response.body();
                    List<Data> dogFactList =  new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    String text = dogFactList.get(0).getAttributes().getBody();
                    textView.setText(text);
                }
            }
            @Override
            public void onFailure(Call<DogFact> call, Throwable t) {
                System.out.println("failed");
            }
        });
    }

}