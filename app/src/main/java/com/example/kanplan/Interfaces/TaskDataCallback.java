package com.example.kanplan.Interfaces;


import com.example.kanplan.Models.Task;

import java.util.ArrayList;

public interface TaskDataCallback {
    void onCallback(ArrayList<Task> tasks);
}
