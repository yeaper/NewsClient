package com.yyp.newsclient.view;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.yyp.newsclient.R;
import com.yyp.newsclient.base.BaseActivity;
import com.yyp.newsclient.interfaces.OnBottomBarClickListener;
import com.yyp.newsclient.util.FragmentController;
import com.yyp.newsclient.widget.MainBottomBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.main_bottom_bar)
    MainBottomBar main_bottom_bar;
    private FragmentController mController;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void bindViews() {
        main_bottom_bar.addItem(R.drawable.skin_tab_icon_conversation_normal
                , R.drawable.skin_tab_icon_conversation_selected
                , R.drawable.rvq, R.drawable.rvr, "首页");
        main_bottom_bar.addItem(R.drawable.skin_tab_icon_conversation_normal
                , R.drawable.skin_tab_icon_conversation_selected
                , R.drawable.rvq, R.drawable.rvr, "视频");
        main_bottom_bar.addItem(R.drawable.skin_tab_icon_conversation_normal
                , R.drawable.skin_tab_icon_conversation_selected
                , R.drawable.rvq, R.drawable.rvr, "个人");
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mController = FragmentController.getInstance(this, R.id.fl_content, true);
        setEnableSwipe(false);
        mController.showFragment(0);
    }

    @Override
    protected void setListener() {
        main_bottom_bar.setOnBottomBarClickListener(new OnBottomBarClickListener() {
            @Override
            public void onBottomBarClick(int position) {
                mController.showFragment(position);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentController.onDestroy();
    }
}
