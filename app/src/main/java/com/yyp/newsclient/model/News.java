package com.yyp.newsclient.model;

import java.io.Serializable;
import java.util.List;

public class News implements Serializable {
    public long id;
    public String article_genre; // 新闻类型

    public String title;
    public String source;
    public String avatar;
    public int fans_count;
    public int comments_count;
    public String behot_time;

    public String video_duration_str;

    public String image_url;
    public List<ImageUrl> image_list;

    public String content_url;
}
