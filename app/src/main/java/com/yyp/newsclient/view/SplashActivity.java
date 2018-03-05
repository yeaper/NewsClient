package com.yyp.newsclient.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyp.newsclient.R;
import com.yyp.newsclient.base.BaseActivity;
import com.yyp.newsclient.util.ConstanceValue;
import com.yyp.newsclient.util.ImageLoaderUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public class SplashActivity extends BaseActivity {

    final int COUNT_DOWN_TIME = 5; // 延时5s
    @BindView(R.id.splash_banner_view)
    ImageView mBannerView;
    @BindView(R.id.splash_view)
    ImageView mSplashView;
    @BindView(R.id.splash_skip_real)
    TextView mSkipReal;
    @BindView(R.id.splash_ad_click_small)
    ImageView mAdClickSmall;
    @BindView(R.id.splash_ad_ignore)
    FrameLayout mAdIgnore;
    private Subscription mSubscription;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void bindViews() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mSubscription = ConstanceValue.RxCountDown.countDown(COUNT_DOWN_TIME)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        ImageLoaderUtils.loadBigImage(mContext,"http://www.3vsheji.com/uploads/allimg/151222/1F92594D_0.jpg", mSplashView);
                        mAdClickSmall.setVisibility(View.VISIBLE);
                        mSplashView.setVisibility(View.VISIBLE);
                        mAdIgnore.setVisibility(View.VISIBLE);
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        goMain();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        mSkipReal.setText(TextUtils.concat(integer + "s", getResources().getString(R.string.splash_ad_ignore)));
                    }
                });

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }


    @OnClick(R.id.splash_skip_real)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.splash_skip_real:
                goMain();
                break;
        }
    }

    private void goMain() {
        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
        intent2Activity(MainActivity.class);
        finish();
    }
}
