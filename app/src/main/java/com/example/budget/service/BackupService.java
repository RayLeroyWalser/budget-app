package com.example.budget.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class BackupService extends AsyncTask<Object, Integer, Integer> {

    private static final String BASE_URL = "http://10.0.2.2:8080/budget";
    
    public void sendNewEntriesToServer() throws IllegalStateException, IOException{
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = null;
        HttpPost request = new HttpPost(BASE_URL+"/update/entries");
        try {
            StringEntity params = new StringEntity("details={\"id\":\"5468168\"}");
            request.addHeader("content-type","application/x-www-form-urlencoded");
            request.setEntity(params);
            response = httpClient.execute(request);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            httpClient.getConnectionManager().shutdown();
        }
        
        if( response != null ){
            byte[] buffer = new byte[(int) response.getEntity().getContentLength()];
            response.getEntity().getContent().read(buffer);
            Log.i("BackupService", buffer.toString());
        } else{
            Log.e("BackupService", "response is null");
        }
    };

    public void sendModifiedEntriesToServer(){};

    public void getModifiedEntriesFromServer(){};

    public void sendNewCategoriesToServer(){};

    public void sendModifiedCategoriesToServer(){};

    public void getModifiedCategoriesFromServer(){};

    public void syncAllEntries(){};

    public void syncAllCategories(){};

    public void syncAll(){}

    @Override
    protected Integer doInBackground(Object... params) {
        // TODO Auto-generated method stub
        return null;
    };
}
