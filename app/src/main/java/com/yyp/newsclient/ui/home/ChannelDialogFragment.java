package com.yyp.newsclient.ui.home;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.yyp.newsclient.R;
import com.yyp.newsclient.interfaces.ItemDragHelperCallBack;
import com.yyp.newsclient.interfaces.OnChannelDragListener;
import com.yyp.newsclient.interfaces.OnChannelListener;
import com.yyp.newsclient.model.Channel;
import com.yyp.newsclient.ui.adapter.ChannelAdapter;
import com.yyp.newsclient.util.ConstanceValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yyp.newsclient.model.Channel.TYPE_MY_CHANNEL;

public class ChannelDialogFragment extends DialogFragment implements OnChannelDragListener {

    private List<Channel> mDatas = new ArrayList<>();
    private ChannelAdapter mAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ItemTouchHelper mHelper;

    private OnChannelListener mOnChannelListener;

    public void setOnChannelListener(OnChannelListener onChannelListener) {
        mOnChannelListener = onChannelListener;
    }

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
        if (dialog != null) {
            //添加动画
            dialog.getWindow().setWindowAnimations(R.style.dialogSlideAnim);
        }
        return inflater.inflate(R.layout.activity_channel, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        processLogic();
    }

    public static ChannelDialogFragment newInstance(List<Channel> selectedDatas, List<Channel> unselectedDatas) {
        ChannelDialogFragment dialogFragment = new ChannelDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConstanceValue.DATA_SELECTED, (Serializable) selectedDatas);
        bundle.putSerializable(ConstanceValue.DATA_UNSELECTED, (Serializable) unselectedDatas);
        // 传递频道参数
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    /**
     * 为同一类型集合的item设置类型
     * @param datas
     * @param type
     */
    private void setDataType(List<Channel> datas, int type) {
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setItemType(type);
        }
    }

    private void processLogic() {
        // 获取频道参数
        Bundle bundle = getArguments();
        List<Channel> selectedDatas = (List<Channel>) bundle.getSerializable(ConstanceValue.DATA_SELECTED);
        List<Channel> unselectedDatas = (List<Channel>) bundle.getSerializable(ConstanceValue.DATA_UNSELECTED);
        setDataType(selectedDatas, TYPE_MY_CHANNEL);
        setDataType(unselectedDatas, Channel.TYPE_OTHER_CHANNEL);
        // 所有频道数据集合
        mDatas.add(new Channel(Channel.TYPE_MY, "我的频道", ""));
        mDatas.addAll(selectedDatas);
        mDatas.add(new Channel(Channel.TYPE_OTHER, "频道推荐", ""));
        mDatas.addAll(unselectedDatas);

        // 进行数据适配
        mAdapter = new ChannelAdapter(mDatas);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // 根据item类型，返回item占比
                int itemViewType = mAdapter.getItemViewType(position);
                return itemViewType == TYPE_MY_CHANNEL || itemViewType == Channel.TYPE_OTHER_CHANNEL ? 1 : 4;
            }
        });

        // 实现拖拽效果
        ItemDragHelperCallBack callBack = new ItemDragHelperCallBack(this);
        mHelper = new ItemTouchHelper(callBack);
        mAdapter.setOnChannelDragListener(this);
        // 绑定RecyclerView
        mHelper.attachToRecyclerView(mRecyclerView);
    }

    /*-------------fragment退出时回调-------------*/

    @OnClick(R.id.icon_collapse)
    public void onClick(View v) {
        dismiss();
    }

    private DialogInterface.OnDismissListener mOnDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null)
            mOnDismissListener.onDismiss(dialog);
    }

    /*-----------编辑、拖拽核心代码-------------*/

    @Override
    public void onStarDrag(BaseViewHolder baseViewHolder) {
        //开始拖动
        Logger.i("开始拖动");
        mHelper.startDrag(baseViewHolder);
    }

    @Override
    public void onItemMove(int starPos, int endPos) {
        //我的频道之间移动
        onMove(starPos, endPos);
        if (mOnChannelListener != null)
            // 去除标题所占的一个index，方便TabLayout刷新
            mOnChannelListener.onItemMove(starPos - 1, endPos - 1);
    }

    @Override
    public void onMoveToMyChannel(int starPos, int endPos) {
        //移动到我的频道
        onMove(starPos, endPos);
        if (mOnChannelListener != null)
            mOnChannelListener.onMoveToMyChannel(starPos - 1 - mAdapter.getMyChannelSize(), endPos - 1);
    }

    @Override
    public void onMoveToOtherChannel(int starPos, int endPos) {
        //移动到推荐频道
        onMove(starPos, endPos);
        if (mOnChannelListener != null)
            mOnChannelListener.onMoveToOtherChannel(starPos - 1, endPos - 2 - mAdapter.getMyChannelSize());
    }

    /**
     * 移动的核心代码，动画效果由 notifyItemMoved 提供
     * @param starPos
     * @param endPos
     */
    private void onMove(int starPos, int endPos) {
        Channel startChannel = mDatas.get(starPos);
        //先删除之前的位置
        mDatas.remove(starPos);
        //添加到现在的位置
        mDatas.add(endPos, startChannel);
        mAdapter.notifyItemMoved(starPos, endPos);
    }
}
