package com.example.kanplan;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

/**
 * SignalGenerator is a utility class that provides methods for generating signals such as
 * displaying toast messages, vibrating the device, and playing sounds.
 */
public class SignalGenerator {
    private static SignalGenerator instance = null;
    private Context context;
    private Vibrator vibrator;
    private MediaPlayer mediaPlayer;

    /**
     * Private constructor to enforce singleton pattern.
     *
     * @param context The application context.
     */
    private SignalGenerator(Context context) {
        this.context = context;
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Initialize mediaPlayer if required
        // mediaPlayer = MediaPlayer.create(context, DataManager.getSoundCrashID());
    }

    /**
     * Initializes the SignalGenerator instance.
     *
     * @param context The application context.
     */
    public static void init(Context context) {
        if (instance == null) {
            instance = new SignalGenerator(context);
        }
    }

    /**
     * Retrieves the SignalGenerator instance.
     *
     * @return The SignalGenerator instance.
     */
    public static SignalGenerator getInstance() {
        if (instance == null) {
            throw new IllegalStateException("SignalGenerator is not initialized. Call init() first.");
        }
        return instance;
    }

    /**
     * Displays a toast message with the given text and duration.
     *
     * @param text   The text to display in the toast.
     * @param length The duration of the toast. Either Toast.LENGTH_SHORT or Toast.LENGTH_LONG.
     */
    public void toast(String text, int length) {
        Toast.makeText(context, text, length).show();
    }

    /**
     * Vibrates the device for the specified length of time.
     *
     * @param length The duration of the vibration in milliseconds.
     */
    public void vibrate(long length) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(length, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            // Deprecated in API 26
            vibrator.vibrate(length);
        }
    }

    // Add additional methods for playing sounds or generating other types of signals
}
