package com.example.kanplan.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.kanplan.Models.Project;

/**
 * MySP is a utility class for handling shared preferences.
 */
public class MySP {

    private static final String DB_FILE = "DB_FILE";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_NAME = "Name";
    private static final String PROJECT_ID = "Project";

    private static MySP instance = null;
    private SharedPreferences sharedPreferences;

    /**
     * Private constructor to enforce singleton pattern.
     *
     * @param context The application context.
     */
    private MySP(Context context) {
        sharedPreferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
    }

    /**
     * Initializes the MySP instance.
     *
     * @param context The application context.
     */
    public static void init(Context context) {
        if (instance == null) {
            instance = new MySP(context);
        }
    }

    /**
     * Retrieves the MySP instance.
     *
     * @return The MySP instance.
     */
    public static MySP getInstance() {
        if (instance == null) {
            throw new IllegalStateException("MySP is not initialized. Call init() first.");
        }
        return instance;
    }

    /**
     * Saves the email to shared preferences.
     *
     * @param email The email to save.
     */
    public void saveEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    /**
     * Retrieves the saved email from shared preferences.
     *
     * @return The saved email.
     */
    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    /**
     * Saves the name to shared preferences.
     *
     * @param name The name to save.
     */
    public void saveName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.apply();
    }

    /**
     * Retrieves the saved name from shared preferences.
     *
     * @return The saved name.
     */
    public String getName() {
        return sharedPreferences.getString(KEY_NAME, "");
    }

    /**
     * Saves the project ID to shared preferences.
     *
     * @param projectID The project ID to save.
     */
    public void saveProjectID(String projectID) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PROJECT_ID, projectID);
        editor.apply();
    }

    /**
     * Retrieves the saved project ID from shared preferences.
     *
     * @return The saved project ID.
     */
    public String getProjectID() {
        return sharedPreferences.getString(PROJECT_ID, "");
    }
}
