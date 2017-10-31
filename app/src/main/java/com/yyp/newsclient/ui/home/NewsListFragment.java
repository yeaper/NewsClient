package com.yyp.newsclient.ui.home;

import android.content.Intent;
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
import com.yyp.newsclient.ui.home.detail.GalleryDetailActivity;
import com.yyp.newsclient.ui.home.detail.NewsDetailActivity;
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

    Intent goNewsDetail;
    Bundle bundle;

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
        goNewsDetail = new Intent(mContext, NewsDetailActivity.class);
        bundle = new Bundle();
        mTitleCode = getArguments().getString(ConstanceValue.DATA);
    }

    protected BaseQuickAdapter createAdapter() {
        return mAdapter = new NewsAdapter(mContext, mDatas);
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
                    news.title = "头条app内容详情页实现分析";
                    news.avatar = "http://up.qqjia.com/z/19/tu21125_15.jpg";
                    news.fans_count = 234;
                    news.source = "头条新闻";
                    news.comments_count = 45;
                    news.behot_time = "2017-10-06T09:37:04";
                    news.content_url = "http://www.chinanews.com/gn/2017/10-28/8362496.shtml";
                    news.image_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508409724371&di=d2c8fd49042985c95213c0deef7b3f25&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F279759ee3d6d55fb5cb87de664224f4a21a4ddf0.jpg";
                    list.add(news);
                    break;
                case 1:
                    News news1 = new News();
                    news1.id = i;
                    news1.article_genre = ConstanceValue.ARTICLE_GENRE_GALLERY;
                    news1.title = "头条app内容详情页实现分析";
                    news1.source = "酷我娱乐";
                    news1.comments_count = 11;
                    news1.behot_time = "2017-10-21T09:37:04";

                    news1.image_list = new ArrayList<>();
                    news1.image_list.add(new ImageUrl("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3743124979,3234956668&fm=27&gp=0.jpg",
                            "内容内容内容内容"));
                    news1.image_list.add(new ImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508409724372&di=82a9f619430da683c6826fa2f321eb29&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F0824ab18972bd40704fe413d72899e510fb30930.jpg",
                            "新闻，也叫消息，是通过报纸、电台、广播、电视台等媒体途径所传播信息的一种称谓。是记录社会、传播信息、反映时代的一种文体。新闻概念有广义与狭义之分。"));
                    news1.image_list.add(new ImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508409724372&di=82a9f619430da683c6826fa2f321eb29&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F0824ab18972bd40704fe413d72899e510fb30930.jpg",
                            "内容内容内容内容"));
                    news1.image_list.add(new ImageUrl("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2544206649,1936708125&fm=27&gp=0.jpg",
                            "内容内容内容内容内容内容"));
                    list.add(news1);
                    break;
                case 2:
                    News news2 = new News();
                    news2.id = i;
                    news2.article_genre = ConstanceValue.ARTICLE_GENRE_VIDEO;
                    news2.title = "头条app内容详情页实现分析";
                    news2.avatar = "http://up.qqjia.com/z/19/tu21125_15.jpg";
                    news2.fans_count = 234;
                    news2.source = "今日晚报";
                    news2.comments_count = 120;
                    news2.behot_time = "2017-10-19T09:37:04";
                    news2.content_url = "http://www.chinanews.com/gn/2017/10-28/8362496.shtml";
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
                switch (news.article_genre){
                    case ConstanceValue.ARTICLE_GENRE_ARTICLE:
                        bundle.clear();
                        bundle.putSerializable("news", mDatas.get(i));
                        goNewsDetail.putExtras(bundle);
                        mContext.startActivity(goNewsDetail);
                        break;
                    case ConstanceValue.ARTICLE_GENRE_GALLERY:
                        Intent goGalleryDetail = new Intent(mContext, GalleryDetailActivity.class);
                        bundle.clear();
                        bundle.putSerializable("news", mDatas.get(i));
                        goGalleryDetail.putExtras(bundle);
                        mContext.startActivity(goGalleryDetail);
                        break;
                    case ConstanceValue.ARTICLE_GENRE_VIDEO:
                        //视频
                        bundle.clear();
                        bundle.putSerializable("news", mDatas.get(i));
                        goNewsDetail.putExtras(bundle);
                        mContext.startActivity(goNewsDetail);
                        break;
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
    public void onError(String error) {
        // 加载数据失败，结束刷新
        srl.setRefreshing(false);
        showToast("数据加载失败："+error);
    }
}
