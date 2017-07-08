package pl.gbielanski.newsapp;


class News {
    private String mTitle;
    private String mSection;
    private String mAuthor;
    private String mDate;

    public News(String title, String section, String author, String date) {
        this.mTitle = title;
        this.mSection = section;
        this.mAuthor = author;
        this.mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDate() {
        return mDate;
    }
}
