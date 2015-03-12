package com.example.budget.test.service;

import java.io.IOException;

import android.test.AndroidTestCase;

import com.example.budget.service.BackupService;

public class BackupServiceTest extends AndroidTestCase {

    private BackupService backupService;
    
    public void testSendNewEntriesToServer() throws IllegalStateException, IOException{
        backupService = new BackupService();
        backupService.sendNewEntriesToServer();
    }
}
