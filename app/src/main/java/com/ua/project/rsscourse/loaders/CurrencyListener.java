package com.ua.project.rsscourse.loaders;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.ua.project.rsscourse.models.Currency;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CurrencyListener implements
        LoaderManager.LoaderCallbacks<List<Currency>>,
        Loader.OnLoadCanceledListener<List<Currency>> {
    private final int TARGET_LOADER_ID = 1;
    private final Activity activity;
    private final LoaderManager loaderManager;

    @Getter
    private List<Currency> currencyList;

    @NonNull
    @Override
    public Loader<List<Currency>> onCreateLoader(int id, @Nullable Bundle args) {
        if(id == TARGET_LOADER_ID) {
            return new CurrencyLoader(activity);
        }

        throw new RuntimeException("onCreateLoader");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Currency>> loader, List<Currency> data) {
        if(loader.getId() == TARGET_LOADER_ID) {
            loaderManager.destroyLoader(loader.getId());
            currencyList = data;

            Log.d("TAG", "onLoadFinished: " + currencyList);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Currency>> loader) {

    }

    @Override
    public void onLoadCanceled(@NonNull Loader<List<Currency>> loader) {

    }
}
