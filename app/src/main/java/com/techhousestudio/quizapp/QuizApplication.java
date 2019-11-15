package com.techhousestudio.quizapp;

import android.app.Application;

import timber.log.Timber;

public class QuizApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
