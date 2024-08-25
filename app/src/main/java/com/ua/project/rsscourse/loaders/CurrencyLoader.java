package com.ua.project.rsscourse.loaders;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ua.project.rsscourse.models.Currency;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CurrencyLoader extends AsyncTaskLoader<List<Currency>> {
    private final String API_URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5";

    public CurrencyLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public List<Currency> loadInBackground() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            if(connection.getResponseCode() == 200) {
                try(InputStream is = connection.getInputStream()) {
                    Gson gson = new Gson();

                    return gson.fromJson(
                            IOUtils.toString(is, StandardCharsets.UTF_8),
                            new TypeToken<ArrayList<Currency>>(){}
                    );
                }
            }
        }
        catch (Exception e) {
            Log.e("TAG", "loadInBackground: ", e);
        }

        return Collections.emptyList();
    }
}
