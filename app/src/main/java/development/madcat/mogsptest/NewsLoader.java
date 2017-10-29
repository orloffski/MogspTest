package development.madcat.mogsptest;

import android.content.Context;

import development.madcat.mogsptest.models.NewsModel;

public interface NewsLoader {
    void onNewsLoad(NewsModel news, String message);
    Context getContext();
}
