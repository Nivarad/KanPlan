package com.example.kanplan;

import android.app.Application;

import com.example.kanplan.Utils.MySP;

/**
 * Custom Application class that initializes the application.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponents();
    }

    /**
     * Initializes the components required for the application.
     * This includes initializing MySP (Shared Preferences) and SignalGenerator.
     */
    private void initializeComponents() {
        MySP.init(this); // Initialize MySP (Shared Preferences) utility
        SignalGenerator.init(this); // Initialize SignalGenerator utility
    }
}
