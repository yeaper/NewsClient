package com.yyp.newsclient.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yyp.newsclient.R;

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P mvpPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (mvpPresenter == null) mvpPresenter = createPresenter();
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    protected void lazyLoad() {
        if (mvpPresenter == null) mvpPresenter = createPresenter();
        super.lazyLoad();
    }

    /**
     * 创建一个p
     * @return
     */
    protected abstract P createPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
            mvpPresenter = null;
        }
    }

    /**
     * 初始化一个线性、纵向滑动的RecyclerView
     * @param adapter
     * @param decoration
     * @return
     */
    public RecyclerView initCommonRecyclerView(BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration) {
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    /**
     * 初始化一个线性、横向滑动的RecyclerView
     * @param adapter
     * @param decoration
     * @return
     */
    public RecyclerView initHorizontalRecyclerView(BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration) {
        return initHorizontalRecyclerView(null, adapter, decoration);
    }

    public RecyclerView initHorizontalRecyclerView(RecyclerView recyclerView, BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration) {
        return initHorizontalRecyclerView(recyclerView, adapter, decoration, false);
    }

    public RecyclerView initHorizontalRecyclerView(RecyclerView recyclerView, BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration, boolean reverseLayout) {
        if (recyclerView == null)
            recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, reverseLayout));
        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    /**
     * 初始化一个表格、纵向滑动的RecyclerView
     * @param adapter
     * @param decoration
     * @param spanCount
     * @return
     */
    public RecyclerView initGridRecyclerView(BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration, int spanCount) {
        return initGridRecyclerView((RecyclerView) rootView.findViewById(R.id.recyclerView), adapter, decoration, spanCount);
    }

    public RecyclerView initGridRecyclerView(RecyclerView recyclerView, BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration, int spanCount) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));
        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(mContext, tarActivity);
        startActivity(intent);
    }
}
