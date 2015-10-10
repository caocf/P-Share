package com.azhuoinfo.pshare.fragment;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.fragment.adapter.MyPagerAdapter;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.UnfinishedOrderInfo;
import com.azhuoinfo.pshare.view.TabManager;
import com.azhuoinfo.pshare.view.TabPageManager;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
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
        this.getCustomActionBar().setCustomHomeAsUpIndicator(R.drawable.left_head, R.drawable.left_head);
        mAccountVerify = AccountVerify.getInstance(getActivity());
        customerInfo=(CustomerInfo) this.app.getSession().get("customerInfo");
        customer_Id=customerInfo.getCustomer_Id();
        postUnfinishedOrder(customer_Id);
        //unfinishedOrderInfos=(List<UnfinishedOrderInfo>)this.app.getSession().get("unfinishedOrderInfos");
        // listSize=unfinishedOrderInfos.size();
       /* for(int i=0;i<unfinishedOrderInfos.size();i++){
            UnfinishedOrderInfo unfinishedOrderInfo=unfinishedOrderInfos.get(i);
            order_state=unfinishedOrderInfo.getOrder_state();
        }*/
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
        Log.e(TAG,order_state+"order_state");
        mTabManager=new TabManager(this.getChildFragmentManager(),mTabHost,R.id.realtabcontent);
        //mTabManager.addTab(mTabHost.newTabSpec("ItemFragment1").setIndicator("预/订"), Order4Fragment.class, new Bundle());

    }
    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.my_reserve);
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
    public void initTable(){
        if(listSize==0){
            mTabManager.addTab(mTabHost.newTabSpec("ItemFragment1").setIndicator("预/订"), Order1Fragment.class, new Bundle());
        }else if(listSize!=0&&order_state.equals("1")){
            mTabManager.addTab(mTabHost.newTabSpec("ItemFragment1").setIndicator("预/订"), Order2Fragment.class, new Bundle());
        }else if(listSize!=0&&order_state.equals("2")){
            mTabManager.addTab(mTabHost.newTabSpec("ItemFragment1").setIndicator("预/订"), Order3Fragment.class, new Bundle());
        }
        mTabManager.addTab(mTabHost.newTabSpec("ItemFragment2").setIndicator("历史订单"), HistoryOrderFragment.class, new Bundle());
    }
    public void postUnfinishedOrder(String customerId){
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_UNFINISHEDORDER));
        apiTask.setParams(ApiContants.instance(getActivity()).unFinishedOrder(customerId));
        apiTask.setRoot("orderInfo");
        apiTask.execute(new OnDataLoader<List<UnfinishedOrderInfo>>() {
            @Override
            public void onStart(){
            }
            @Override
            public void onSuccess(boolean page, List<UnfinishedOrderInfo> unfinishedOrderInfos) {
                Log.e(TAG, unfinishedOrderInfos.size() + "");
                listSize=unfinishedOrderInfos.size();
                if(listSize!=0){
                    for(int i=0;i<unfinishedOrderInfos.size();i++){
                        UnfinishedOrderInfo unfinishedOrderInfo=unfinishedOrderInfos.get(i);
                        order_state=unfinishedOrderInfo.getOrder_state();
                        Log.e(TAG, order_state);
                    }
                }
                initTable();
            }
            @Override
            public void onFailure(String code, String message) {

            }
        });
    }
}
