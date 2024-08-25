package com.ua.project.rsscourse;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.ua.project.rsscourse.databinding.ActivityMainBinding;
import com.ua.project.rsscourse.loaders.CurrencyListener;
import com.ua.project.rsscourse.models.Currency;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        final String[] CURRENCY_ABBR = { "UAH", "USD", "EUR" };

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_spinner_item,
                CURRENCY_ABBR);
        List<Currency> currencyList = updateCurrencyStatus();

        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Currency usd = currencyList.stream().filter(currency -> currency.getCcy().equals("USD")).findFirst().get();
        Currency eur = currencyList.stream().filter(currency -> currency.getCcy().equals("EUR")).findFirst().get();

        binding.usdTextView.setText(String.format("$ %s/%s", usd.getBuy(), usd.getSale()));
        binding.usdTextView.setText(String.format("$ %s/%s", eur.getBuy(), eur.getSale()));
        binding.leftCurrencySpinner.setAdapter(adapter);
        binding.rightCurrencySpinner.setAdapter(adapter);
        binding.updateCurrencyButton.setOnClickListener(v -> {
            updateCurrencyStatus();
        });
    }

    private List<Currency> updateCurrencyStatus(){
        try {
            final int LOADER_ID = 1;
            final List<Currency> currencyList = new ArrayList<>();
            LoaderManager loaderManager = LoaderManager.getInstance(MainActivity.this);
            CurrencyListener currencyListener = new CurrencyListener(MainActivity.this, loaderManager);
            Loader<List<Currency>> listLoader = loaderManager.initLoader(LOADER_ID, null, currencyListener);

            listLoader.registerOnLoadCanceledListener(currencyListener);
            listLoader.forceLoad();

            new Handler().postDelayed(() -> {
                if (currencyListener.getCurrencyList() != null) {
                    currencyList.addAll(currencyListener.getCurrencyList());
                    Log.d("TAG", "Currency data: " + currencyList);
                }
            }, 3000);

            return currencyList;
        }
        catch (Exception e) {
            Log.e("TAG", "onCreate: ", e);
        }
        return null;
    }
}