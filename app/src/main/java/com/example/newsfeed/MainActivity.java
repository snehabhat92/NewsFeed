package com.example.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskCompleteListener{
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private ProgressBar progressBar;
    private TextView txtNoInternet;
    private Spinner spinner;
    private LinearLayout linearSpinner;
    private ArrayList<NewsFeed.Articles> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseUI();
    }

    private void initialiseUI() {
        recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progress_bar);
        txtNoInternet = findViewById(R.id.txt_no_internet);
        spinner = findViewById(R.id.spinner);
        linearSpinner = findViewById(R.id.layout_spinner);
        setUpSpinner();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        String url = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json";
        new WebAsyncTask(url, this).execute();
    }

    private void setUpSpinner() {
        List<String> categories = new ArrayList<String>();
        categories.add("new-to-old");
        categories.add("old-to-new");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if ("old-to-new".equals(item)) {
                    sort(true);
                } else {
                    sort(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void sort(boolean ascending) {
        if(articles != null) {
            if (ascending) {
                Collections.sort(articles, new NewsFeed.ArticleComparatorAscending());

            } else {
                Collections.sort(articles, new NewsFeed.ArticleComparatorDescending());
            }
            itemAdapter = new ItemAdapter(articles, MainActivity.this);
            recyclerView.setAdapter(itemAdapter);

        }
    }

    @Override
    public void onTaskDone(String responseData) {
        showProgress(false, false);
        NewsFeed newsFeed = new Gson().fromJson(responseData, NewsFeed.class);
        articles = newsFeed.getArticles();
        sort(false);
    }

    @Override
    public void onError() {
        showProgress(false, true);
        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }

    private void showProgress(boolean show, boolean error) {
        if(show) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            txtNoInternet.setVisibility(View.GONE);
            linearSpinner.setVisibility(View.GONE);
        } else if(error) {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            txtNoInternet.setVisibility(View.VISIBLE);
            linearSpinner.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            linearSpinner.setVisibility(View.VISIBLE);
            txtNoInternet.setVisibility(View.GONE);
        }

    }
}

