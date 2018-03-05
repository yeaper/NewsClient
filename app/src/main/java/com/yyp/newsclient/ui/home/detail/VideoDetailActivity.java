package com.yyp.newsclient.ui.home.detail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yyp.newsclient.R;
import com.yyp.newsclient.base.BaseActivity;
import com.yyp.newsclient.interfaces.OnCommentItemClickListener;
import com.yyp.newsclient.model.Comment;
import com.yyp.newsclient.model.News;
import com.yyp.newsclient.ui.adapter.CommentAdapter;
import com.yyp.newsclient.util.ImageLoaderUtils;
import com.yyp.newsclient.widget.VideoDetailHeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class VideoDetailActivity extends BaseActivity implements View.OnClickListener {

    ImageView back;

    private News news;
    JZVideoPlayerStandard jzVideoPlayerStandard;

    @BindView(R.id.ivAvatar)
    ImageView avatar;
    @BindView(R.id.tvUserName)
    TextView name;
    @BindView(R.id.tvFansCount)
    TextView fansCount;

    VideoDetailHeaderView headerView;

    @BindView(R.id.video_detail_recyclerview)
    RecyclerView recyclerView;
    CommentAdapter mAdapter;
    protected List<Comment> mDatas = new ArrayList<>();

    @BindView(R.id.action_comment_count)
    TextView action_comment_count;
    @BindView(R.id.action_view_comment)
    ImageView action_view_comment;
    @BindView(R.id.action_favor)
    ImageView action_favor;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this, this);
    }

    @Override
    protected void bindViews() {
        if(getIntent().getSerializableExtra("news") != null){
            news = (News) getIntent().getSerializableExtra("news");
        }

        back = (ImageView) findViewById(R.id.back_btn);
    }

    protected BaseQuickAdapter createAdapter() {
        return mAdapter = new CommentAdapter(this, mDatas);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(createAdapter());
        initVideo();
        initHeaderView();
        loadCommData();
    }

    @Override
    protected void setListener() {
        back.setOnClickListener(this);
        mAdapter.setOnCommentItemClickListener(new OnCommentItemClickListener() {
            @Override
            public void onCommentItemClick(int position) {
                // 弹出回复详情页
                ReplyDialogFragment dialogFragment = ReplyDialogFragment.newInstance(mDatas.get(position));
                dialogFragment.show(getSupportFragmentManager(), "REPLY");
            }
        });
        action_view_comment.setOnClickListener(this);
        action_favor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.action_view_comment:

                break;
            case R.id.action_favor:
                if(action_favor.isSelected()){
                    action_favor.setSelected(false);
                }else{
                    action_favor.setSelected(true);
                }
                break;
        }
    }

    private void initVideo(){
        jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        jzVideoPlayerStandard.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        ImageLoaderUtils.loadCommonImage(this, news.image_url, jzVideoPlayerStandard.thumbImageView);
    }

    private void initHeaderView(){
        ImageLoaderUtils.loadCircleAvatar(this, news.avatar, avatar);
        name.setText(news.source);
        fansCount.setText(news.fans_count+"粉丝");

        headerView = new VideoDetailHeaderView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        headerView.setLayoutParams(layoutParams);
        headerView.setData(news);
        mAdapter.addHeaderView(headerView);
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

        if(mDatas.size() > 0){
            action_comment_count.setVisibility(View.VISIBLE);
            action_comment_count.setText(mDatas.size()+"");
        }
    }


    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (jzVideoPlayerStandard != null) {
            jzVideoPlayerStandard.release();
            jzVideoPlayerStandard = null;
        }
    }
}