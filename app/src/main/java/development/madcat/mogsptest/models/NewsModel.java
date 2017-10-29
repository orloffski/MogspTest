package development.madcat.mogsptest.models;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsModel implements Parcelable{

    private String newsLink;
    private String newsDateString;
    private String newsHeader;
    private String newsHtmlBody;

    public NewsModel() {
    }

    public NewsModel(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);
        newsLink = data[0];
        newsDateString = data[1];
        newsHeader = data[2];
        newsHtmlBody = data[3];
    }

    public NewsModel(String newsLink, String newsDateString, String newsHeader, String newsHtmlBody) {
        this.newsLink = newsLink;
        this.newsDateString = newsDateString;
        this.newsHeader = newsHeader;
        this.newsHtmlBody = newsHtmlBody;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    public String getNewsDateString() {
        return newsDateString;
    }

    public void setNewsDateString(String newsDateString) {
        this.newsDateString = newsDateString;
    }

    public String getNewsHeader() {
        return newsHeader;
    }

    public void setNewsHeader(String newsHeader) {
        this.newsHeader = newsHeader;
    }

    public String getNewsHtmlBody() {
        return newsHtmlBody;
    }

    public void setNewsHtmlBody(String newsHtmlBody) {
        this.newsHtmlBody = newsHtmlBody;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] { newsLink, newsDateString, newsHeader, newsHtmlBody });
    }

    public static final Parcelable.Creator<NewsModel> CREATOR = new Parcelable.Creator<NewsModel>() {

        @Override
        public NewsModel createFromParcel(Parcel source) {
            return new NewsModel(source);
        }

        @Override
        public NewsModel[] newArray(int size) {
            return new NewsModel[size];
        }
    };
}
