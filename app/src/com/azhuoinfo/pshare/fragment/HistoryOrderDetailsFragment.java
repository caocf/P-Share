package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.model.OrderList;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class HistoryOrderDetailsFragment extends BaseContentFragment{

    //代泊员头像
    private ImageView mParkerHeadImageView;
    //代泊员ID
    private TextView mParkerIDTextView;
    //代泊员职务
    private TextView mParkerPositionTextView;
    //代泊员负责区域
    private TextView mParkerAreaTextView;
    //代泊员联系方式
    private TextView mParkerMobileTextView;
    //订单日期
    private TextView mOrderDateTextView;
    //停车时长
    private TextView mStopCarTimeTextView;
    //支付金额
    private TextView mPayMoneyTextView;
    //用户车牌号
    private TextView mCarNumberTextView;

    private AccountVerify mAccountVerify;
    private OrderList orderList;
    private String parakerId;
    private String parkerLevel;
    private String parkerMobile;
    private String orderActualBegin;
    private String parkerName;
    private String orderPlanEnd;
    private String carNumber;
    private String orderDuration;
    private String orderTotalFee;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
        orderList=(OrderList)this.getArguments().get("orderList");
        parakerId=orderList.getParker_id();
        parkerLevel=orderList.getParkerLevel();
        parkerMobile=orderList.getParker_mobile();
        orderActualBegin=orderList.getOrder_actual_begin();
        orderDuration=orderList.getOrder_duration();
        orderTotalFee=orderList.getOrder_total_fee();
        parkerName=orderList.getParker_name();
        carNumber=orderList.getCar_number();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_history_order_details,container,false);
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
        mParkerHeadImageView=(ImageView) view.findViewById(R.id.iv_parker_head);
        mParkerIDTextView=(TextView) view.findViewById(R.id.tv_parker_id);
        mParkerPositionTextView=(TextView) view.findViewById(R.id.tv_parker_position);
        mParkerAreaTextView=(TextView) view.findViewById(R.id.tv_parker_area);
        mParkerMobileTextView=(TextView) view.findViewById(R.id.tv_parker_mobile);

        mOrderDateTextView=(TextView) view.findViewById(R.id.tv_history_order_time);
        mStopCarTimeTextView=(TextView) view.findViewById(R.id.tv_stop_car_time);
        mPayMoneyTextView=(TextView)view.findViewById(R.id.tv_pay_money);
        mCarNumberTextView=(TextView) view.findViewById(R.id.tv_car_number);
    }

    @Override
    protected void initViews(Bundle bundle) {
        mParkerIDTextView.setText(parakerId);
        mParkerAreaTextView.setText(parkerName);
        mParkerMobileTextView.setText(parkerMobile);
        mParkerPositionTextView.setText(parkerLevel);
        mPayMoneyTextView.setText(orderTotalFee);
        mOrderDateTextView.setText(orderActualBegin);
        mStopCarTimeTextView.setText(orderDuration);
        mCarNumberTextView.setText(carNumber);



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
}
