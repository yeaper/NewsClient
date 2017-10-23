package com.yyp.newsclient.ui.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yyp.newsclient.R;
import com.yyp.newsclient.base.BaseMvpFragment;
import com.yyp.newsclient.model.ImageUrl;
import com.yyp.newsclient.model.News;
import com.yyp.newsclient.presenter.NewsListPresenter;
import com.yyp.newsclient.ui.adapter.NewsAdapter;
import com.yyp.newsclient.util.ConstanceValue;
import com.yyp.newsclient.view.INewsListView;
import com.yyp.newsclient.widget.LoadingFlashView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListFragment extends BaseMvpFragment<NewsListPresenter> implements INewsListView {

    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.loadingView)
    LoadingFlashView loadingView;


    private String mTitleCode = "";
    protected List<News> mDatas = new ArrayList<>();
    protected BaseQuickAdapter mAdapter;

    @Override
    protected NewsListPresenter createPresenter() {
        return new NewsListPresenter(this);
    }

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        View v = inflater.inflate(R.layout.layout_recyclerview, null);
        ButterKnife.bind(this, v);
        return v;
    }

    public static NewsListFragment newInstance(String code) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstanceValue.DATA, code);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void bindViews(View view) {
    }

    @Override
    protected void processLogic() {
        initCommonRecyclerView(createAdapter(), null);
        mTitleCode = getArguments().getString(ConstanceValue.DATA);
    }

    protected BaseQuickAdapter createAdapter() {
        return mAdapter = new NewsAdapter(mDatas);
    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (TextUtils.isEmpty(mTitleCode))
            mTitleCode = getArguments().getString(ConstanceValue.DATA);
        if (mvpPresenter.mvpView == null)
            mvpPresenter = createPresenter();
        getData();
    }

    private void getData() {
        if (mDatas.size() == 0) {
            //没加载过数据
            if (loadingView == null) loadingView = get(R.id.loadingView);
            loadingView.setVisibility(View.VISIBLE);
            loadingView.showLoading();
        }
        //mvpPresenter.getNewsList(mTitleCode);

        List<News> list = new ArrayList<>();
        for(int i=1;i<12;i++){
            switch (i%3){
                case 0:
                    News news = new News();
                    news.id = i;
                    news.article_genre = ConstanceValue.ARTICLE_GENRE_ARTICLE;
                    news.title = "title";
                    news.source = "头条新闻";
                    news.comments_count = 45;
                    news.behot_time = "2017-10-06T09:37:04";
                    news.image_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508409724371&di=d2c8fd49042985c95213c0deef7b3f25&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F279759ee3d6d55fb5cb87de664224f4a21a4ddf0.jpg";
                    list.add(news);
                    break;
                case 1:
                    News news1 = new News();
                    news1.id = i;
                    news1.article_genre = ConstanceValue.ARTICLE_GENRE_GALLERY;
                    news1.title = "title";
                    news1.source = "酷我娱乐";
                    news1.comments_count = 11;
                    news1.behot_time = "2017-10-21T09:37:04";

                    news1.image_list = new ArrayList<>();
                    news1.image_list.add(new ImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508409724372&di=82a9f619430da683c6826fa2f321eb29&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F0824ab18972bd40704fe413d72899e510fb30930.jpg"));
                    news1.image_list.add(new ImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508409724372&di=82a9f619430da683c6826fa2f321eb29&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F0824ab18972bd40704fe413d72899e510fb30930.jpg"));
                    news1.image_list.add(new ImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508409724372&di=82a9f619430da683c6826fa2f321eb29&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F0824ab18972bd40704fe413d72899e510fb30930.jpg"));
                    news1.image_list.add(new ImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508409724372&di=82a9f619430da683c6826fa2f321eb29&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F0824ab18972bd40704fe413d72899e510fb30930.jpg"));
                    list.add(news1);
                    break;
                case 2:
                    News news2 = new News();
                    news2.id = i;
                    news2.article_genre = ConstanceValue.ARTICLE_GENRE_VIDEO;
                    news2.title = "title";
                    news2.source = "今日晚报";
                    news2.comments_count = 120;
                    news2.behot_time = "2017-10-19T09:37:04";
                    news2.image_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508409724373&di=04f070e530799c4b53925b5e7529b7c7&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F50da81cb39dbb6fd493c67e70024ab18962b378f.jpg";
                    news2.video_duration_str = "03:43";
                    list.add(news2);
                    break;
            }

        }

        // 获取数据后，结束刷新
        if (list.size() > 0) {
            loadingView.setVisibility(View.GONE);
        }
        srl.setRefreshing(false);
        mDatas.clear();
        mDatas.addAll(0, list);
        mAdapter.notifyItemRangeChanged(0, list.size());
    }

    @Override
    protected void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                News news = mDatas.get(i);
                if (news.article_genre.equals(ConstanceValue.ARTICLE_GENRE_VIDEO)) {
                    //视频
                    intent2Activity(NewsDetailActivity.class);
                } else {
                    intent2Activity(NewsDetailActivity.class);
                }
            }
        });
    }


    @Override
    public void onGetNewsListSuccess(List<News> response) {
        //由于最后一条重复 ，删除掉
        if (response.size() > 0) {
            response.remove(response.size() - 1);
            loadingView.setVisibility(View.GONE);
        }
        srl.setRefreshing(false);
        mDatas.addAll(0, response);
        mAdapter.notifyItemRangeChanged(0, response.size());
    }

    @Override
    public void onError(Throwable e) {
        // 加载数据失败，结束刷新
        srl.setRefreshing(false);
        showToast("数据加载失败："+e.getMessage());
    }
}
