package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.fragment.adapter.ParkingDetailsAdapter;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.OrderInfo;

import java.util.ArrayList;

import mobi.cangol.mobile.Session;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class ParkingDetailsItemFragment extends BaseContentFragment{
    //名称
    private TextView mParkingNameTextView;
    //地址
    private TextView mParkingAddressTextView;
    //距离
    private TextView mParkingDistanceTextView;
    //剩余车辆
    private TextView mParkingCanUseTextView;
    //价格
    private TextView mParkingPriceTextView;
    //预约
    private RelativeLayout mAppointmentRelativeLayout;
    private TextView mAppointmentTextView;

    private AccountVerify mAccountVerify;
    private CustomerInfo customerInfo;
    private String customer_id;
    private ArrayList<String> list=new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerInfo=(CustomerInfo)this.app.getSession().get("customerInfo");
        customer_id=customerInfo.getCustomer_Id().toString();
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_parkingdetailsitem,container,false);
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
        mParkingNameTextView=(TextView) view.findViewById(R.id.tv_parking_name);
        mParkingAddressTextView=(TextView) view.findViewById(R.id.tv_parking_address);
        mParkingDistanceTextView=(TextView) view.findViewById(R.id.tv_parking_distance);
        mParkingCanUseTextView=(TextView) view.findViewById(R.id.tv_parking_can_use);
        mParkingPriceTextView=(TextView) view.findViewById(R.id.tv_parking_price);

        mAppointmentRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_appointment);
        mAppointmentTextView=(TextView) view.findViewById(R.id.tv_appointment);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.yard_details);
        mAppointmentRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCreateOrder(customer_id, "3", "2015/10/10 20:10");
            }
        });
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
        return false;
    }

    public void postCreateOrder(String customerId,String parkingId,String orderPlanBegin) {
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_CREATEORDER));
        apiTask.setParams(ApiContants.instance(getActivity()).userCreateOrder(customerId, parkingId,orderPlanBegin));
        apiTask.setRoot("orderInfo");
        apiTask.execute(new OnDataLoader<OrderInfo>() {
            @Override
            public void onStart() {

            }
            @Override
            public void onSuccess(boolean page, OrderInfo orderInfos) {
                mAppointmentTextView.setText(R.string.tv_unselect_order);
            }
            @Override
            public void onFailure(String code, String message) {
                Log.e(TAG,"请求数据失败");
            }
        });
    }
}
