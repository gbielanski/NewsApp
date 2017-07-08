package pl.gbielanski.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<News>>, NewsAdapter.OnClickNewsHandler {

    private static final int LOADER_ID = 1234;
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private TextView mEmptyNewsList;
    private ProgressBar mPregressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rc_news_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new NewsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mEmptyNewsList = (TextView) findViewById(R.id.empty_news_list_text_view);

        mPregressBar = (ProgressBar)findViewById(R.id.progress_bar);
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            mEmptyNewsList.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            mPregressBar.setVisibility(View.VISIBLE);
            //if (!savedSearchString.isEmpty()) {
            getLoaderManager().restartLoader(LOADER_ID, null, this).forceLoad();
        } else {
            mEmptyNewsList.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mPregressBar.setVisibility(View.GONE);
            mEmptyNewsList.setText(getString(R.string.no_connection));
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
        mPregressBar.setVisibility(View.GONE);

        if(!newsList.isEmpty()) {
            mEmptyNewsList.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.addNewsData(newsList);
        } else {
            mEmptyNewsList.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mEmptyNewsList.setText(getString(R.string.no_news_data));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.addNewsData(new ArrayList<News>());
    }

    @Override
    public void newsOnClick(int position) {

        News news = mAdapter.getNewsData().get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(news.getArticleUrl()));

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
        else
            Toast.makeText(MainActivity.this, getString(R.string.article_cannot_be_shown), Toast.LENGTH_LONG).show();
    }
}
