package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.fragment.adapter.CarListAdapter;
import com.azhuoinfo.pshare.fragment.adapter.MonthlyRentListAdapter;
import com.azhuoinfo.pshare.model.CarList;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.FeeOrderInfo;
import com.azhuoinfo.pshare.model.Success;
import com.azhuoinfo.pshare.view.AllListView;
import com.azhuoinfo.pshare.view.CommonDialog;
import com.azhuoinfo.pshare.view.LoadingDialog;
import com.azhuoinfo.pshare.view.listview.LoadMoreAdapter;
import com.azhuoinfo.pshare.view.listview.MyListView;
import com.azhuoinfo.pshare.view.listview.OnLoadMoreListener;
import com.azhuoinfo.pshare.view.listview.pull.PullRefreshListView;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 * 产权月租列表
 */
public class MonthlyRentListFragment extends BaseContentFragment{

    //月租车列表
    private PullRefreshListView mMonthlyRentListView;
    //添加月租车
    private RelativeLayout mAddMonthlyRentCarRelativeLayout;

    private List<FeeOrderInfo> list=new ArrayList<>();
    private AccountVerify mAccountVerify;

    private CustomerInfo customerInfo;
    private String customerId;

    //private PullRefreshListView mListView;
    LoadMoreAdapter<FeeOrderInfo> mListLoadMoreAdapter;
    int mPageIndex = 1;

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
        return inflater.inflate(R.layout.fragment_monthlyrent_list,container,false);
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

    }

    @Override
    public void onResume() {
        super.onResume();
        postGetOrderInfo(customerId, "0", mPageIndex);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPageIndex = 1;
        monthlyRentListAdapter.clear();
    }


    MonthlyRentListAdapter monthlyRentListAdapter;
    @Override
    protected void initViews(final Bundle bundle) {
        mMonthlyRentListView=(PullRefreshListView) findViewById(R.id.lv_monthlyRent_list);
        monthlyRentListAdapter=new MonthlyRentListAdapter(this.getActivity(), list);
        /*mMonthlyRentListView.setAdapter(monthlyRentListAdapter);*/
        mAddMonthlyRentCarRelativeLayout=(RelativeLayout) findViewById(R.id.rl_add_monthlyRent);

        mListLoadMoreAdapter = new LoadMoreAdapter<>(monthlyRentListAdapter);
        mListLoadMoreAdapter.setIsPullMode(false);
        mListLoadMoreAdapter.setAbsListView(mMonthlyRentListView);
        mMonthlyRentListView.setAdapter(mListLoadMoreAdapter);
        mMonthlyRentListView.setPullRefreshEnable(false);

        mListLoadMoreAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public boolean hasMore() {
                return true;
            }

            @Override
            public void onLoadMore() {
                postGetOrderInfo(customerId, "0", mPageIndex);
            }
        });


        mMonthlyRentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle itemBundle =  new Bundle();
                itemBundle.putParcelable("data", monthlyRentListAdapter.getItems().get(position));
                replaceFragment(MonthlyRentCarfinishPayFragment.class, "MonthlyRentCarfinishPayFragment", itemBundle);
            }
        });

        mMonthlyRentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(position);
                return true;
            }
        });

        mAddMonthlyRentCarRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(AddMonthlyRentCarFragment.class, "AddMonthlyRentCarFragment", null);
            }
        });
    }

    public void showDeleteDialog(final int position){
        CommonDialog dialog = CommonDialog.creatDialog(getActivity());
        //dialog.setTitle(R.string.dialog_delete_title);
        dialog.setMessage(R.string.dialog_delete_content);
        dialog.setLeftButtonInfo(getString(R.string.common_dialog_confirm), new CommonDialog.OnButtonClickListener() {

            @Override
            public void onClick(View view) {
                FeeOrderInfo item = monthlyRentListAdapter.get(position);
                deleteRentOrder(item.getID());
                monthlyRentListAdapter.remove(position);
            }

        });
        dialog.setRightButtonInfo(getString(R.string.common_dialog_cancel), new CommonDialog.OnButtonClickListener() {
            @Override
            public void onClick(View view) {
                // do nothing
            }
        });
        dialog.show();
    }

    public void deleteRentOrder(String orderId){
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_DELETEORDER));
        apiTask.setParams(ApiContants.instance(getActivity()).deleteOrder(orderId));
        apiTask.execute(new OnDataLoader<String>() {
            public void onStart() {
            }

            @Override
            public void onSuccess(String result) {
                if (isEnable()) {
                    showToast("删除成功！");
                }
            }

            @Override
            public void onFailure(String code, String message) {
                if (isEnable()) showToast(message);
            }
        });
    }

    @Override
    protected void initData(Bundle bundle) {
        postGetOrderInfo(customerId, "0", mPageIndex);
    }

    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return false;
    }

    public void postGetOrderInfo(String customer_id,String index, final int pageIndex) {

        if (pageIndex == -1) {
            mListLoadMoreAdapter.addMoreData(new ArrayList<FeeOrderInfo>());
            return;
        }

        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_GETORDERINFO));
        apiTask.setParams(ApiContants.instance(getActivity()).getGetOrderInfo(customer_id, index, "" + pageIndex));
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
                /*if (!feeOrderInfoList.isEmpty()){
                    monthlyRentListAdapter.getItems().clear();
                    monthlyRentListAdapter.getItems().addAll(feeOrderInfoList);
                    monthlyRentListAdapter.notifyDataSetChanged();
                    //mMonthlyRentListView.deferNotifyDataSetChanged();
                }
                for (FeeOrderInfo feeOrderInfo : feeOrderInfoList) {
                    Log.i("fee", feeOrderInfo.toString());
                }*/
                if (pageIndex <= 1) {
                    if (feeOrderInfoList != null && !feeOrderInfoList.isEmpty()) {
                        mListLoadMoreAdapter.addMoreData(feeOrderInfoList);
                    }
                } else {
                    if (feeOrderInfoList != null && !feeOrderInfoList.isEmpty()) {
                        mListLoadMoreAdapter.addMoreData(feeOrderInfoList);
                    }
                }

                if (feeOrderInfoList != null && feeOrderInfoList.size() == 10) {
                    mPageIndex++;
                } else {
                    mPageIndex = -1;
                }

                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(String code, String message) {
                showToast(message);
                mPageIndex = -1;
                loadingDialog.dismiss();
            }
        });
    }

}
