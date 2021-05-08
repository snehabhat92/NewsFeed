package com.example.newsfeed;

public interface TaskCompleteListener {
    void onTaskDone(String responseData);

    void onError();
}
