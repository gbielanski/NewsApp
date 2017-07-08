package pl.gbielanski.newsapp;


class News {
    final private String mTitle;
    final private String mSection;
    final private String mDate;
    final private String mArticleUrl;

    public News(String title, String section, String date, String articleUrl) {
        this.mTitle = title;
        this.mSection = section;
        this.mDate = date;
        this.mArticleUrl = articleUrl;
    }

    String getTitle() {
        return mTitle;
    }

    String getSection() {
        return mSection;
    }

    String getDate() {
        return mDate;
    }

    public String getArticleUrl() {
        return mArticleUrl;
    }
}
