package development.madcat.mogsptest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import development.madcat.mogsptest.models.NewsModel;
import development.madcat.mogsptest.utils.SystemUtils;

public class MainActivity extends AppCompatActivity implements NewsLoader{

    public static final String NEWS_KEY = "saved_news";
    private NewsLoadTask loadTask;
    private NewsModel news;

    @BindView(R.id.top_image) ImageView topImage;
    @BindView(R.id.date_text) TextView date;
    @BindView(R.id.news_header_text) TextView newsHeader;
    @BindView(R.id.news_body) WebView newsBody;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        loadTask = (NewsLoadTask) getLastCustomNonConfigurationInstance();
        if (loadTask == null) {
            loadTask = new NewsLoadTask();
            loadTask.execute(this);
        }else{
            loadTask.setLoader(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(NEWS_KEY, news);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        news = savedInstanceState.getParcelable(NEWS_KEY);
        updateViews(news);
    }

    public Object onRetainCustomNonConfigurationInstance() {
        loadTask.clearLoader();
        return loadTask;
    }

    @Override
    public void onNewsLoad(NewsModel news, String message) {
        if(news == null && !message.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            return;
        }

        this.news = news;

        if(!SystemUtils.checkNewsDate(news))
            Toast.makeText(this, getResources().getString(R.string.today_news_not_found), Toast.LENGTH_SHORT).show();

        updateViews(news);
    }

    private void updateViews(NewsModel news){
        if(news != null) {
            Glide.with(this).load(R.drawable.header).into(topImage);
            date.setText(news.getNewsDateString());
            newsHeader.setText(news.getNewsHeader());
            newsBody.loadData(news.getNewsHtmlBody(), "text/html", "UTF-8");
        }
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }
}
