package development.madcat.mogsptest.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import development.madcat.mogsptest.models.NewsModel;

public class DocParseUtils {
    public static final String ROOT_URL = "http://mogsp.by/";
    public static final String NEWS_CONTAINER = "news";
    public static final String NEWS_DATE_CONTAINER = "h5";
    public static final String NEWS_LINK_CONTAINER = "a[href]";
    public static final String NEWS_LINK_DATA_CONTAINER = "abs:href";

    public static final String FULL_NEWS_CONTAINER = "col-md-8 maincontent";
    public static final String NEWS_HEADER_CONTAINER = ".page-header";
    public static final String NEWS_BODY_CONTAINER = "p";

    public static final NewsModel getNewsByNumber(int index) throws IOException {
        Document doc = Jsoup.parse(loadDocument(ROOT_URL));

        if(doc == null)
            return null;

        Map<String, String> links = getNewsLinks(doc);

        NewsModel news = getNewsInHtml(links.keySet().iterator().next(), links.get(links.keySet().iterator().next()));

        return news;
    }

    public static final String loadDocument(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        return doc.toString();
    }

    public static final Map<String, String> getNewsLinks(Document document){
        Map<String, String> links = new LinkedHashMap<>();

        Elements elements = document.getElementsByClass(NEWS_CONTAINER);
        for(Element element : elements){
            Element date = element.select(NEWS_DATE_CONTAINER).first();
            Element link = element.select(NEWS_LINK_CONTAINER).first();
            links.put(link.attr(NEWS_LINK_DATA_CONTAINER), date.text());
        }

        return links;
    }

    public static final NewsModel getNewsInHtml(String url, String date) throws IOException {
        Document newsDoc = Jsoup.parse(loadDocument(url));

        NewsModel news = new NewsModel();

        Element element = newsDoc.getElementsByClass(FULL_NEWS_CONTAINER).first();
        news.setNewsLink(url);
        news.setNewsDateString(date);
        news.setNewsHeader(element.select(NEWS_HEADER_CONTAINER).first().text());
        news.setNewsHtmlBody(element.select(NEWS_BODY_CONTAINER).first().toString());

        return news;
    }
}
