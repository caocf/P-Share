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
import com.azhuoinfo.pshare.view.LoadingDialog;
import com.azhuoinfo.pshare.view.PromptView;
import com.azhuoinfo.pshare.view.listview.LoadMoreAdapter;
import com.azhuoinfo.pshare.view.listview.OnLoadMoreListener;
import com.azhuoinfo.pshare.view.listview.pull.PullRefreshListView;

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
    private PullRefreshListView mHistoryOrderListView;
    LoadMoreAdapter<OrderList> mListLoadMoreAdapter;
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
        customerInfo=(CustomerInfo)this.app.getSession().getSerializable("customerInfo");
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

    int mPageIndex = 1;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    protected void findViews(View view) {
        mPromptView=(PromptView) findViewById(R.id.promptView);
        mHistoryOrderListView=(PullRefreshListView) findViewById(R.id.lv_history_order);
    }

    @Override
    protected void initViews(Bundle bundle) {
        mDataAdapter=new HistoryOrderAdapter(this.getActivity());
        mListLoadMoreAdapter = new LoadMoreAdapter<>(mDataAdapter);
        mListLoadMoreAdapter.setIsPullMode(false);
        mListLoadMoreAdapter.setAbsListView(mHistoryOrderListView);
        mHistoryOrderListView.setAdapter(mListLoadMoreAdapter);
        mHistoryOrderListView.setPullRefreshEnable(false);

        mListLoadMoreAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public boolean hasMore() {
                return true;
            }

            @Override
            public void onLoadMore() {
                postHistoryOrder(customerId,mPageIndex);
            }
        });

        mHistoryOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderList item = (OrderList) parent.getItemAtPosition(position);
                Log.e("pos",""+position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("orderList", item);
                replaceParentFragment(HistoryOrderDetailsFragment.class, "HistoryOrderDetailsFragment", bundle);
            }
        });
    }

    @Override
    protected void initData(Bundle bundle) {
    }

    @Override
    public void onResume() {
        super.onResume();
        postHistoryOrder(customerId, mPageIndex);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPageIndex = 1;
    }

    protected void updateViews(List<OrderList> list) {
        if(list!=null&&list.size()>0){
            Log.e("updateViews",list+"");
            mListLoadMoreAdapter.addMoreData(list);
            if(mListLoadMoreAdapter.getCount()>0){
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
    public void postHistoryOrder(String customerId, final int pageindex){
        if (pageindex == -1) {
            mListLoadMoreAdapter.addMoreData(new ArrayList<OrderList>());
            return;
        }
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_HISTORYORDER));
        apiTask.setParams(ApiContants.instance(getActivity()).historyOrder(customerId, "" + pageindex));
        apiTask.setRoot("orderInfo");
        apiTask.execute(new OnDataLoader<List<OrderList>>() {
            LoadingDialog loadingDialog;
            @Override
            public void onStart() {
                if (isEnable())
                    loadingDialog = LoadingDialog.show(getActivity());
            }
            @Override
            public void onSuccess(List<OrderList> orderLists) {
                if (isEnable()) {
                    if (pageindex<=1) {
                        updateViews(orderLists);
                    }else {
                        if (orderLists!=null && !orderLists.isEmpty()) {
                            mListLoadMoreAdapter.addMoreData(orderLists);
                        }
                    }

                    if (orderLists != null && orderLists.size()==10){
                        mPageIndex++;
                    }else {
                        mPageIndex = -1;
                    }

                    Log.e(TAG, orderLists + "");

                    loadingDialog.dismiss();
                }
            }
            @Override
            public void onFailure(String code, String message) {

                if (isEnable()) {
                    mPageIndex = -1;
                    showToast(message);
                    mPromptView.showEmpty();
                    loadingDialog.dismiss();
                }

            }
        });
    }


}