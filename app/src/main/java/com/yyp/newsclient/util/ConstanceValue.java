package com.yyp.newsclient.util;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public interface ConstanceValue {
    String DATA = "data";
    String DATA_SELECTED = "dataSelected";
    String DATA_UNSELECTED = "dataUnselected";
    String ARTICLE_GENRE_GALLERY = "gallery";
    String ARTICLE_GENRE_VIDEO = "video";
    String  ARTICLE_GENRE_ARTICLE = "article";
    String URL = "url";
    String GROUP_ID = "groupId";
    String ITEM_ID = "itemId";
    String SP_THEME = "theme";
    int THEME_LIGHT = 1;
    int THEME_NIGHT = 2;
    /**
     * 修改主题
     */
    int MSG_TYPE_CHANGE_THEME = 100;

    String TITLE_SELECTED = "explore_title_selected";
    String TITLE_UNSELECTED = "explore_title_unselected";

    /**
     * 倒计时
     */
    class RxCountDown {
        public static Observable<Integer> countDown(int time) {
            if(time<0) time=0;
            final int countTime = time;
            // 间隔1s执行一次
            return Observable.interval(0, 1, TimeUnit.SECONDS)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<Long, Integer>() {
                        @Override
                        public Integer call(Long aLong) {
                            return countTime-aLong.intValue();
                        }
                    }).take(time+1);
        }
    }
}
