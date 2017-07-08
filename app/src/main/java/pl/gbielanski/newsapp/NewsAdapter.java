package pl.gbielanski.newsapp;

import android.content.Context;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder>{
    ArrayList<News> mNewsData = new ArrayList<>();

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
        holder.mAuthor.setText(news.getAuthor());
        holder.mSection.setText(news.getSection());
        holder.mTitle.setText(news.getTitle());
        holder.mDate.setText(news.getDate());
    }

    public void addNewsData(ArrayList<News> newsList){
        mNewsData = newsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNewsData.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder{

        TextView mTitle;
        TextView mSection;
        TextView mAuthor;
        TextView mDate;

        public NewsHolder(View itemView) {
            super(itemView);
            mTitle  = (TextView)itemView.findViewById(R.id.article_title);
            mSection  = (TextView)itemView.findViewById(R.id.article_section);
            mAuthor  = (TextView)itemView.findViewById(R.id.article_author);
            mDate  = (TextView)itemView.findViewById(R.id.article_date);
        }
    }
}
