package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.amap.api.location.AMapLocation;
import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.fragment.adapter.HistoryOrderAdapter;
import com.azhuoinfo.pshare.fragment.adapter.MonthlyRentListAdapter;
import com.azhuoinfo.pshare.fragment.adapter.ParkingDetailsAdapter;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.OrderList;
import com.azhuoinfo.pshare.model.Parking;
import com.azhuoinfo.pshare.view.PromptView;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 * 历史订单列表
 */
public class HistoryOrderFragment extends BaseContentFragment{

    //历史订单列表
    private ListView mHistoryOrderListView;
    private PromptView mPromptView;
    private AMapLocation mAMapLocation;
    private AccountVerify mAccountVerify;
    private CustomerInfo customerInfo;
    private String customerId;
    private HistoryOrderAdapter mDataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
        customerInfo=(CustomerInfo)this.app.getSession().get("customerInfo");
        customerId=customerInfo.getCustomer_Id();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_history_order,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        postHistoryOrder(customerId);
        initViews(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    protected void findViews(View view) {
        mPromptView=(PromptView) findViewById(R.id.promptView);
        mHistoryOrderListView=(ListView) findViewById(R.id.lv_history_order);

    }

    @Override
    protected void initViews(Bundle bundle) {
        mDataAdapter=new HistoryOrderAdapter(this.getActivity());
        mHistoryOrderListView.setAdapter(mDataAdapter);
        mHistoryOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderList item = (OrderList) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("orderList",item);
                replaceParentFragment(HistoryOrderDetailsFragment.class, "HistoryOrderDetailsFragment", bundle);
            }
        });
    }

    @Override
    protected void initData(Bundle bundle) {

    }
    protected void updateViews(List<OrderList> list) {
        if(list!=null&&list.size()>0){
            Log.e("updateViews",list+"");
            mDataAdapter.clear();
            mDataAdapter.addAll(list);
            if(mDataAdapter.getCount()>0){
                mPromptView.showContent();
            }else{
                mPromptView.showPrompt(R.string.common_empty);
            }
        }else{
            mPromptView.showPrompt(R.string.common_empty);
        }
    }
    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return true;
    }
    public void postHistoryOrder(String customerId){
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_HISTORYORDER));
        apiTask.setParams(ApiContants.instance(getActivity()).historyOrder(customerId));
        apiTask.setRoot("orderInfo");
        apiTask.execute(new OnDataLoader<List<OrderList>>() {
            @Override
            public void onStart() {
                /*if (isEnable())
                    mPromptView.showLoading();*/
            }
            @Override
            public void onSuccess(List<OrderList> orderLists) {
                if (isEnable()) {
                    updateViews(orderLists);
                    Log.e(TAG, orderLists + "");
                }

            }
            @Override
            public void onFailure(String code, String message) {
                showToast(message);
                if (isEnable())
                    mPromptView.showEmpty();
            }
        });

    }
}