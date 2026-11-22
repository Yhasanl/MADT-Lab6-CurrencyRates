package com.example.currencyratesapp;

import android.os.AsyncTask;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DataLoader extends AsyncTask<String, Void, List<String>> {

    @Override
    protected List<String> doInBackground(String... urls) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(urls[0]).openConnection();
            InputStream stream = conn.getInputStream();
            return Parser.parseCurrencyXml(stream);
        } catch (Exception e) {
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}