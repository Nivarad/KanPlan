package com.example.kanplan;

import android.app.Application;

import com.example.kanplan.Utils.MySP;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MySP.init(this);
        SignalGenerator.init(this);

    }
}
