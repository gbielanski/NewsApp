package pl.gbielanski.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

        TextView mTitle;
        TextView mSection;
        TextView mDate;

        public NewsHolder(View itemView) {
            super(itemView);
            mTitle  = (TextView)itemView.findViewById(R.id.article_title);
            mSection  = (TextView)itemView.findViewById(R.id.article_section);
            mDate  = (TextView)itemView.findViewById(R.id.article_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickNewsHandler.newsOnClick(getAdapterPosition());
        }
    }
}
