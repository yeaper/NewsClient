package com.yyp.newsclient.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yyp on 2017/10/31.
 */

public class Comment implements Serializable {

    public String avatar;
    public String name;
    public String content;
    public int thumbsUpCount = 0;
    public String date;

    public List<Comment> commentList;
}
