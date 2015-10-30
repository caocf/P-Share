package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.UnfinishedOrderInfo;
import com.azhuoinfo.pshare.view.TabManager;

import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 * 订单模块
 */
public class MineOrderFragment extends BaseContentFragment{
    private TabManager mTabManager;
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private AccountVerify mAccountVerify;
    private CustomerInfo customerInfo;
    private String order_state;
    private int listSize;
    private String customer_Id;
    private List<UnfinishedOrderInfo> unfinishedOrderInfos;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
        customerInfo=(CustomerInfo) this.getSession().getSerializable("customerInfo");
        customer_Id=customerInfo.getCustomer_Id();
        //AlarmManager am = (AlarmManager)("http://139.196.12.103/1/1.0.0/customer/unfinishedOrder?customer_id="+customer_Id.toString());
       // am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,0,5*1000,);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_mine_order,container,false);
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
        Log.e(TAG, order_state + "order_state");
        mTabManager=new TabManager(this.getChildFragmentManager(),mTabHost,R.id.realtabcontent);
        //postUnfinishedOrder(customer_Id);
        mTabManager.addTab(mTabHost.newTabSpec("ItemFragment1").setIndicator(tabView("预/订")), Order1Fragment.class, new Bundle());
        mTabManager.addTab(mTabHost.newTabSpec("ItemFragment2").setIndicator(tabView("历史订单")), HistoryOrderFragment.class, new Bundle());
    }
    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.my_reserve);
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
