package pl.gbielanski.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

import java.util.List;

class NewsLoader extends AsyncTaskLoader<List<News>> {
    public NewsLoader(Context context) {
        super(context);
    }

    @Override
    public List<News> loadInBackground() {
        return null;
    }
}
