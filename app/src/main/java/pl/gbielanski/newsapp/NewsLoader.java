package pl.gbielanski.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.net.URL;
import java.util.List;

class NewsLoader extends AsyncTaskLoader<List<News>> {
    private Context mContext;

    NewsLoader(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public List<News> loadInBackground() {
        URL url = NewsUtils.getApiURL(mContext.getString(R.string.guardian_api_key));

        return NewsUtils.fetchDataFromServer(url);
    }
}
