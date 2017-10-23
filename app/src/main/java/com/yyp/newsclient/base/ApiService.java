package com.yyp.newsclient.base;

import com.yyp.newsclient.model.News;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 定义所有APi服务
 */
public interface ApiService {
    //baseUrl
    String HOST = "http://www.toutiao.com/";
    String API_SERVER_URL = HOST + "api/";

    String URL_ARTICLE_FEED = "article/recent/";
    String URL_COMMENT_LIST = "comment/list/";
    String HOST_VIDEO = "http://i.snssdk.com/";
    String URL_VIDEO = "video/urls/v/1/toutiao/mp4/%s?r=%s";


    /**
     * 获取新闻数据
     * url = API_SERVER_URL + URL_ARTICLE_FEED + "?source=2&as=A1C528E25E76FB8&cp=582EC64FEBD84E1"
     */
    @GET(URL_ARTICLE_FEED + "?source=2&as=A1C528E25E76FB8&cp=582EC64FEBD84E1")
    Observable<ResultResponse<List<News>>> getNews(@Query("category") String category);
}
