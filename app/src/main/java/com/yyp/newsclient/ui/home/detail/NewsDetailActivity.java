package com.yyp.newsclient.ui.home.detail;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yyp.newsclient.R;
import com.yyp.newsclient.base.BaseActivity;
import com.yyp.newsclient.interfaces.OnCommentItemClickListener;
import com.yyp.newsclient.model.Comment;
import com.yyp.newsclient.model.News;
import com.yyp.newsclient.ui.adapter.CommentAdapter;
import com.yyp.newsclient.util.ImageLoaderUtils;
import com.yyp.newsclient.widget.NewsDetailHeaderView;
import com.yyp.newsclient.widget.NewsDetailWebView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_btn)
    ImageView back_btn;

    @BindView(R.id.toolbar_center_ll)
    LinearLayout toolbar_center_ll;
    @BindView(R.id.ivAvatar)
    ImageView avatar;
    @BindView(R.id.tvUserName)
    TextView username;
    @BindView(R.id.tvFansCount)
    TextView fansCount;

    @BindView(R.id.news_detail_sv)
    ScrollView sv;
    @BindView(R.id.news_detail_header_view)
    NewsDetailHeaderView headerView;

    @BindView(R.id.news_detail_content)
    NewsDetailWebView news_detail_content;

    RecyclerView recyclerView;
    CommentAdapter mAdapter;
    protected List<Comment> mDatas = new ArrayList<>();

    private News news;
    Animation animFadeIn;
    Animation animFadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        if(getIntent() != null){
            news = (News) getIntent().getSerializableExtra("news");
        }
    }

    @Override
    protected void bindViews() {
        recyclerView = (RecyclerView) findViewById(R.id.news_detail_recyclerview);
        // 动画
        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(createAdapter());
        initToolbar();
        initHeaderView();
        loadContent();
        loadCommData();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void setListener() {
        back_btn.setOnClickListener(this);
        sv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int x, int y, int oldX, int oldY) {
                int[] location = new int[2];
                headerView.getLocation(location);

                if(toolbar_center_ll.getVisibility() == View.GONE &&
                        y >= location[1]+headerView.getHeight()){  // 当头部滑动到不可视，toolbar显示对应布局
                    toolbar_center_ll.clearAnimation();
                    toolbar_center_ll.setAnimation(animFadeIn);
                    animFadeIn.start();
                }else if(toolbar_center_ll.getVisibility() == View.VISIBLE
                        && y < location[1]+headerView.getHeight()){  // 当头部滑动到可视，toolbar隐藏对应布局
                    toolbar_center_ll.clearAnimation();
                    toolbar_center_ll.setAnimation(animFadeOut);
                    animFadeOut.start();
                }
            }

        });
        animFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                toolbar_center_ll.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                toolbar_center_ll.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mAdapter.setOnCommentItemClickListener(new OnCommentItemClickListener() {
            @Override
            public void onCommentItemClick(int position) {
                // 弹出回复详情页
                ReplyDialogFragment dialogFragment = ReplyDialogFragment.newInstance(mDatas.get(position));
                dialogFragment.show(getSupportFragmentManager(), "REPLY");
            }
        });
    }

    protected BaseQuickAdapter createAdapter() {
        return mAdapter = new CommentAdapter(this, mDatas);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_btn:
                finish();
                break;
        }
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar(){
        ImageLoaderUtils.loadCircleAvatar(this, news.avatar, avatar);
        username.setText(news.source);
        fansCount.setText(news.fans_count+"粉丝");
    }

    /**
     * 初始化内容头布局
     */
    private void initHeaderView(){
        headerView.setData(news);
    }

    /**
     * 初始化新闻内容
     */
    private void loadContent(){
        news_detail_content.loadUrl(news.content_url);
        news_detail_content.setWebViewClient(new WebViewClient());
    }

    /**
     * 加载评论内容
     */
    private void loadCommData(){
        List<Comment> list = new ArrayList<>();
        for(int i=0;i<8;i++){
            Comment comment = new Comment();
            comment.avatar = "http://img3.imgtn.bdimg.com/it/u=1530678619,3275431284&fm=27&gp=0.jpg";
            comment.name = "阳光"+i;
            comment.thumbsUpCount = i*10;
            comment.content = "不然还是会超出边界";
            comment.date = "2017-10-31T09:37:04";
            comment.commentList = new ArrayList<>();
            for(int j=0;j<10-i;j++){
                Comment reply = new Comment();
                reply.avatar = "http://up.qqjia.com/z/19/tu21125_15.jpg";
                reply.name = "小龙哥"+i;
                reply.thumbsUpCount = i*10;
                reply.content = "如果不添加item的话，直接在使用涟漪效果的外部套一层layout也可以达到不超出边界的效果。关于这个方法，还有一个条件是外部的layout要添加background，不然还是会超出边界";
                reply.date = "2017-10-31T09:37:04";
                reply.commentList = new ArrayList<>();

                comment.commentList.add(reply);
            }

            list.add(comment);
        }
        // 刷新数据
        mDatas.clear();
        mDatas.addAll(0, list);
        mAdapter.notifyItemRangeChanged(0, list.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(animFadeIn.hasStarted()){
            animFadeIn.cancel();
        }
        if(animFadeOut.hasStarted()){
            animFadeOut.cancel();
        }
    }
}
