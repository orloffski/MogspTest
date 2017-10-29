package development.madcat.mogsptest;

import android.os.AsyncTask;

import java.io.IOException;

import development.madcat.mogsptest.models.NewsModel;
import development.madcat.mogsptest.utils.DocParseUtils;
import development.madcat.mogsptest.utils.SystemUtils;

public class NewsLoadTask extends AsyncTask<NewsLoader, Void, NewsModel>{

    private NewsLoader loader;
    private NewsModel lastNews;
    private String message;


    @Override
    protected NewsModel doInBackground(NewsLoader... params) {
        this.loader = params[0];

        try {
            if(SystemUtils.isNetworkAvailableAndConnected(loader.getContext()))
                this.lastNews = DocParseUtils.getNewsByNumber(1);
            else{
                this.message = loader.getContext().getResources().getString(R.string.internet_disconnected);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.lastNews;
    }

    @Override
    protected void onPostExecute(NewsModel news) {
        if(this.lastNews == null) {
            if(this.message.isEmpty())
                this.message = loader.getContext().getResources().getString(R.string.load_news_error);;
            loader.onNewsLoad(null, this.message);
        }else
            loader.onNewsLoad(this.lastNews, "");
    }

    public void setLoader(NewsLoader loader){
        this.loader = loader;
    }

    public void clearLoader(){
        this.loader = null;
    }
}
