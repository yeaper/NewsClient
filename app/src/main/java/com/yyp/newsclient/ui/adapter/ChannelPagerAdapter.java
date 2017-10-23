package com.yyp.newsclient.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yyp.newsclient.base.BaseFragment;
import com.yyp.newsclient.model.Channel;

import java.util.List;


public class ChannelPagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> fragments;
    private List<Channel> mChannels;

    public ChannelPagerAdapter(FragmentManager fm, List<BaseFragment> fragments, List<Channel> channels) {
        super(fm);
        this.fragments = fragments;
        this.mChannels = channels;
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return mChannels.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels == null ? "" : mChannels.get(position).Title;
    }

}
