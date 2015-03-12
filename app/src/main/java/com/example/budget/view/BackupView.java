package com.example.budget.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.budget.R;
import com.example.budget.service.BackupService;

public class BackupView extends Activity {
    public static final String TAG = "myBudget";
    
    private BackupService backupService;
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backup);
        backupService = new BackupService();
    }
    
    public void onBackupClick(View view){
        backupService.execute();
    }
}