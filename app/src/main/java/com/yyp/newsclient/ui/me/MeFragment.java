package com.yyp.newsclient.ui.me;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yyp.newsclient.R;
import com.yyp.newsclient.base.BaseFragment;
import com.yyp.newsclient.model.Notice;
import com.yyp.newsclient.theme.util.SharedPreferencesMgr;
import com.yyp.newsclient.util.ConstanceValue;

public class MeFragment extends BaseFragment {
    private LinearLayout ll_night_mode;
    private TextView ll_mode_txt;
    private ImageView ivBg;

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_layout_me, null);
    }

    @Override
    protected void bindViews(View view) {
        ll_night_mode = get(R.id.ll_night_mode);
        ll_mode_txt = get(R.id.ll_mode_txt);
    }

    @Override
    protected void processLogic() {
    }

    @Override
    protected void setListener() {
        ll_night_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 日夜间模式切换
                if (SharedPreferencesMgr.getInt(ConstanceValue.SP_THEME, ConstanceValue.THEME_LIGHT) == ConstanceValue.THEME_LIGHT) {
                    SharedPreferencesMgr.setInt(ConstanceValue.SP_THEME, ConstanceValue.THEME_NIGHT);
                    getActivity().setTheme(R.style.Theme_Night);
                    ll_mode_txt.setText(getActivity().getResources().getString(R.string.mine_item_day_mode));
                } else {
                    SharedPreferencesMgr.setInt(ConstanceValue.SP_THEME, ConstanceValue.THEME_LIGHT);
                    getActivity().setTheme(R.style.Theme_Light);
                    ll_mode_txt.setText(getActivity().getResources().getString(R.string.mine_item_night_mode));
                }

                // 增加切换的过渡效果
                final View rootView = getActivity().getWindow().getDecorView();
                if (Build.VERSION.SDK_INT >= 16) {
                    // cache开启
                    rootView.setDrawingCacheEnabled(true);
                    // 创建位图
                    rootView.buildDrawingCache(true);
                    // 获取cache中的位图
                    final Bitmap localBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
                    // 把原来的cache回收
                    rootView.setDrawingCacheEnabled(false);
                    if (null != localBitmap && rootView instanceof ViewGroup) {
                        // 添加一层改变模式之前的影像View
                        final View localView2 = new View(getActivity());
                        localView2.setBackgroundDrawable(new BitmapDrawable(getResources(), localBitmap));
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        ((ViewGroup) rootView).addView(localView2, params);
                        // 影像淡出后，移除这层View
                        localView2.animate().alpha(0).setDuration(400).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                post(new Notice(ConstanceValue.MSG_TYPE_CHANGE_THEME));
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                ((ViewGroup) rootView).removeView(localView2);
                                localBitmap.recycle();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).start();
                    }
                } else {
                    post(new Notice(ConstanceValue.MSG_TYPE_CHANGE_THEME));
                }
            }
        });
    }
}