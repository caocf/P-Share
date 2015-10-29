package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.fragment.adapter.MonthlyRentHistoryListAdapter;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.FeeOrderInfo;
import com.azhuoinfo.pshare.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 * 月租历史缴费列表
 */
public class MonthlyRentHistoryListFragment extends BaseContentFragment{

    //月租历史缴费列表
    private ListView mMonthlyRentHistoryListView;
    private ArrayList<FeeOrderInfo> list=new ArrayList<>();
    private CustomerInfo customerInfo;
    private String customerId;

    private AccountVerify mAccountVerify;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
        customerInfo=(CustomerInfo)this.app.getSession().getSerializable("customerInfo");
        customerId=customerInfo.getCustomer_Id();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_monthlyrent_historylist,container,false);
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

    MonthlyRentHistoryListAdapter monthlyRentHistoryListAdapter;
    @Override
    protected void findViews(View view) {
        mMonthlyRentHistoryListView=(ListView) view.findViewById(R.id.lv_monthlyrnet_history);
        monthlyRentHistoryListAdapter = new MonthlyRentHistoryListAdapter(this.getActivity(), list);
        mMonthlyRentHistoryListView.setAdapter(monthlyRentHistoryListAdapter);
    }

    @Override
    protected void initViews(Bundle bundle) {
        mMonthlyRentHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                replaceFragment(MonthlyRentCarfinishPayFragment.class, "MonthlyRentCarFinishPayFragment", null);
            }
        });
    }

    @Override
    protected void initData(Bundle bundle) {
        postGetOrderInfo(customerId,"1");
    }

    public void postGetOrderInfo(String customer_id,String index) {
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_GETORDERINFO));
        apiTask.setParams(ApiContants.instance(getActivity()).getGetOrderInfo(customer_id, index));
        apiTask.setRoot("feeOrderInfoList");
        apiTask.execute(new OnDataLoader<List<FeeOrderInfo>>() {

            LoadingDialog loadingDialog;

            public void onStart() {
                if (isEnable()) {
                    loadingDialog = LoadingDialog.show(getActivity());
                }
            }

            @Override
            public void onSuccess(List<FeeOrderInfo> feeOrderInfoList) {
                if (!feeOrderInfoList.isEmpty()){
                    monthlyRentHistoryListAdapter.getItems().clear();
                    monthlyRentHistoryListAdapter.getItems().addAll(feeOrderInfoList);
                    monthlyRentHistoryListAdapter.notifyDataSetChanged();
                    //mMonthlyRentListView.deferNotifyDataSetChanged();
                }
                for (FeeOrderInfo feeOrderInfo : feeOrderInfoList) {
                    Log.i("fee", feeOrderInfo.toString());
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(String code, String message) {
                showToast(message);
                loadingDialog.dismiss();
            }
        });
    }

    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return false;
    }
}
