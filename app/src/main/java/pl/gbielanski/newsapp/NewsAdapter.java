package pl.gbielanski.newsapp;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Keep
class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder>{
    private ArrayList<News> mNewsData = new ArrayList<>();
    final private OnClickNewsHandler mOnClickNewsHandler;

    NewsAdapter(OnClickNewsHandler onClickNewsHandler) {
        this.mOnClickNewsHandler = onClickNewsHandler;
    }

    interface OnClickNewsHandler{
        void newsOnClick(int position);
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        int newsLayoutItemLayout = R.layout.news_list_item;

        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(newsLayoutItemLayout, parent,false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        News news = mNewsData.get(position);
        holder.mSection.setText(news.getSection());
        holder.mTitle.setText(news.getTitle());
        holder.mDate.setText(news.getDate());
        holder.itemView.setTag(news.getArticleUrl());
    }

    public void addNewsData(List<News> newsList){
        mNewsData = (ArrayList<News>) newsList;
        notifyDataSetChanged();
    }

    public List<News> getNewsData(){
        return mNewsData;
    }

    @Override
    public int getItemCount() {
        return mNewsData.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.article_title) TextView mTitle;
        @BindView(R.id.article_section) TextView mSection;
        @BindView(R.id.article_date) TextView mDate;


        public NewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickNewsHandler.newsOnClick(getAdapterPosition());
        }
    }
}
