package development.madcat.mogsptest.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import development.madcat.mogsptest.models.NewsModel;

public class SystemUtils {
    public static final boolean isNetworkAvailableAndConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworcConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();

        return isNetworcConnected;
    }

    public static final boolean checkNewsDate(NewsModel news){
        try {
            DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

            Date todayDate = formatter.parse(formatter.format(new Date()));
            Date newsDate = formatter.parse(news.getNewsDateString());

            if(todayDate.after(newsDate))
                return false;
            else
                return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }
}
