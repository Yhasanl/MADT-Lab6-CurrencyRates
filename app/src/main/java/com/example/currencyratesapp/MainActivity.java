package com.example.currencyratesapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String CURRENCY_API_URL = "http://www.floatrates.com/daily/usd.xml";

    private EditText etCurrencyFilter;
    private ListView lvCurrencyRates;
    private ArrayAdapter<String> currencyAdapter;
    private final List<String> displayedCurrencies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCurrencyFilter = findViewById(R.id.etCurrencyFilter);
        lvCurrencyRates = findViewById(R.id.lvCurrencyRates);

        currencyAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                displayedCurrencies
        );
        lvCurrencyRates.setAdapter(currencyAdapter);

        setupFilterListener();
        loadCurrencyRates();
    }

    private void setupFilterListener() {
        etCurrencyFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currencyAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadCurrencyRates() {
        new DataLoader() {
            @Override
            protected void onPostExecute(List<String> result) {
                if (result != null) {
                    displayedCurrencies.clear();
                    displayedCurrencies.addAll(result);
                    currencyAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load currency rates", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(CURRENCY_API_URL);
    }
}