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
import com.azhuoinfo.pshare.view.TabManager;
import com.azhuoinfo.pshare.view.TabPageManager;

import java.util.ArrayList;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 * 缴费模块
 */
public class MonthlyRentFragment extends BaseContentFragment{
   // private TabPageManager mTabPageManager;
    private TabManager mTabManager;
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private AccountVerify mAccountVerify;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mTabManager=new TabManager(this.getChildFragmentManager(),mTabHost,R.id.realtabcontent);
        mTabManager.addTab(mTabHost.newTabSpec("ItemFragment1").setIndicator(tabView("产权/月租")), MonthlyRentListFragment.class, new Bundle());
        mTabManager.addTab(mTabHost.newTabSpec("ItemFragment2").setIndicator(tabView("历史缴费记录")), MonthlyRentHistoryListFragment.class, new Bundle());
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle("我的社区生活");
    }

    @Override
    protected void initData(Bundle bundle) {

    }
    private View tabView(String title) {
        View indicatorview = android.view.LayoutInflater.from(this.getActivity()).inflate(R.layout.common_tab_view, null);
        TextView text = (TextView) indicatorview.findViewById(R.id.tabsText);
        text.setText(title);
        return indicatorview;
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
