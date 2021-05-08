package com.example.newsfeed;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebAsyncTask extends AsyncTask<String, Void, String> {

    private final TaskCompleteListener taskCompleteListener;
    private String urlStr = "";

    public WebAsyncTask(String url, TaskCompleteListener onTaskDoneListener) {
        this.urlStr = url;
        this.taskCompleteListener = onTaskDoneListener;
    }

    @Override
    protected String doInBackground(String... params) {
        try {

            URL mUrl = new URL(urlStr);
            HttpURLConnection httpConnection = (HttpURLConnection) mUrl.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (taskCompleteListener != null && s != null) {
            taskCompleteListener.onTaskDone(s);
        } else
            taskCompleteListener.onError();
    }
}
