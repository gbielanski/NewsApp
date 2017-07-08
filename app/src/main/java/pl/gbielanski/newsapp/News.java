package pl.gbielanski.newsapp;


class News {
    private String mTitle;
    private String mSection;
    private String mDate;
    private String mArticleUrl;

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
