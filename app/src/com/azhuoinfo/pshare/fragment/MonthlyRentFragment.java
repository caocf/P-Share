package com.azhuoinfo.pshare.fragment;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.fragment.adapter.MyPagerAdapter;
import com.azhuoinfo.pshare.view.TabPageManager;

import java.util.ArrayList;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class MonthlyRentFragment extends BaseContentFragment{
    private TabPageManager mTabPageManager;
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private AccountVerify mAccountVerify;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getCustomActionBar().setCustomHomeAsUpIndicator(R.drawable.left_head, R.drawable.left_head);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_monthly_rent,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    protected void findViews(View view) {
        mTabHost = (TabHost)view.findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mViewPager = (ViewPager)view.findViewById(R.id.pager);
        mTabPageManager=new TabPageManager(this.getChildFragmentManager(),mTabHost,mViewPager);
        mTabPageManager.addTab(mTabHost.newTabSpec("ItemFragment1").setIndicator("产权/月租"), MonthlyRentListFragment.class, new Bundle());
        mTabPageManager.addTab(mTabHost.newTabSpec("ItemFragment2").setIndicator("历史缴费记录"), MonthlyRentHistoryListFragment.class, new Bundle());
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.rent);
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return true;
    }
}
