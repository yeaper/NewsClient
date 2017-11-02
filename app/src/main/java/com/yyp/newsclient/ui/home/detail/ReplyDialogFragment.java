package com.yyp.newsclient.ui.home.detail;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyp.newsclient.R;
import com.yyp.newsclient.model.Comment;
import com.yyp.newsclient.ui.adapter.CommentAdapter;
import com.yyp.newsclient.util.DateUtils;
import com.yyp.newsclient.util.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReplyDialogFragment extends DialogFragment {

    @BindView(R.id.reply_title)
    TextView reply_title;
    private Comment comment;
    private List<Comment> mDatas = new ArrayList<>();
    private CommentAdapter mAdapter;
    @BindView(R.id.reply_recyclerview)
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 正常、无titleBar风格
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            //添加动画
            dialog.getWindow().setWindowAnimations(R.style.dialogSlideAnim);
        }
        return inflater.inflate(R.layout.activity_reply, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        processLogic();
    }

    public static ReplyDialogFragment newInstance(Comment comment) {
        ReplyDialogFragment dialogFragment = new ReplyDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("comment", comment);
        // 传递频道参数
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    private void processLogic() {
        // 获取数据
        if(getArguments().getSerializable("comment") != null){
            comment = (Comment) getArguments().getSerializable("comment");
            mDatas.addAll(0, comment.commentList);
            reply_title.setText(comment.commentList.size()+"条回复");
        }

        // 进行数据适配
        mAdapter = new CommentAdapter(getActivity(), mDatas);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        initHeader();
    }

    /**
     * 初始化头部数据
     */
    public void initHeader(){
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.news_detail_reply_header, null);
        ImageView avatar = headerView.findViewById(R.id.reply_header_avatar);
        TextView name = headerView.findViewById(R.id.reply_header_username);
        TextView content = headerView.findViewById(R.id.reply_header_content);
        TextView thumbsUpCount = headerView.findViewById(R.id.thumbs_up_count);
        TextView date = headerView.findViewById(R.id.reply_header_date);

        ImageView thumbs_up_people1 = headerView.findViewById(R.id.thumbs_up_people1);
        ImageView thumbs_up_people2 = headerView.findViewById(R.id.thumbs_up_people2);

        ImageLoaderUtils.loadCircleAvatar(getContext(), comment.avatar, avatar);
        ImageLoaderUtils.loadCircleAvatar(getContext(), comment.commentList.get(0).avatar, thumbs_up_people1);
        ImageLoaderUtils.loadCircleAvatar(getContext(), comment.commentList.get(1).avatar, thumbs_up_people2);
        name.setText(comment.name);
        content.setText(comment.content);
        thumbsUpCount.setText(comment.thumbsUpCount+"");
        date.setText(DateUtils.getTimeDown(comment.date));

        mAdapter.addHeaderView(headerView);
        mAdapter.notifyDataSetChanged();
    }

    /*-------------fragment退出时回调-------------*/

    @OnClick(R.id.reply_collapse)
    public void onClick(View v) {
        dismiss();
    }
}
