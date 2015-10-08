package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class MonthlyRentCarPayFragment extends BaseContentFragment{
    //返回上一个页面
    //private Button mBackButton;

    //设置国省市
    private RelativeLayout mCPCRelativeLayout;
    //国
    private TextView mCountryTextView;
    //省
    private TextView mProvinceTextView;
    //市
    private TextView mCityTextView;
    //设置区
    private RelativeLayout mAreaRelativeLayout;
    private TextView mAreaTextView;
    //设置小区
    private RelativeLayout mLivingAreaRelativeLayout;
    private TextView mLivingAreaTextView;
    //时间段
    private RelativeLayout mTimeFrameRelativeLayout;
    private TextView mTimeFrameTextView;
    //开始时间
    private RelativeLayout mMonthlyrentBegintTimeRelativeLayout;
    private TextView mMonthlyrentBegintTimeTextView;
    //结束时间
    private TextView mMonthlyrentEndTimeTextView;
    //单价
    private TextView mMonthlyrentPriceTextView;
    //姓名
    private EditText mCustomerNameEditText;
    //车牌
    private RelativeLayout mCarNumberRelativeLayout;
    private TextView mCarNumberTextView;
    //支付金额
    private TextView mPayMoneyTextView;
    //确定添加
    private Button mConfirmButton;
    //删除添加
    private Button mDeleteButton;

    private AccountVerify mAccountVerify;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_monthlyrent_car_pay,container,false);
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
        //mBackButton=(Button) view.findViewById(R.id.activity_back);
        mCPCRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_CPC);
        mCountryTextView=(TextView) view.findViewById(R.id.tv_country);
        mProvinceTextView=(TextView) view.findViewById(R.id.tv_province);
        mCityTextView=(TextView) view.findViewById(R.id.tv_city);

        mAreaRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_area);
        mAreaTextView=(TextView) view.findViewById(R.id.tv_area);

        mLivingAreaRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_living_area);
        mLivingAreaTextView=(TextView) view.findViewById(R.id.tv_living_area);

        mTimeFrameRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_time_frame);
        mTimeFrameTextView=(TextView) view.findViewById(R.id.tv_time_frame);

        mMonthlyrentBegintTimeRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_monthlyrent_begint_time);
        mMonthlyrentBegintTimeTextView=(TextView) view.findViewById(R.id.tv_monthlyrent_begint_time);

        mMonthlyrentEndTimeTextView=(TextView) view.findViewById(R.id.tv_monthlyrent_end_time);

        mMonthlyrentPriceTextView=(TextView) findViewById(R.id.tv_monthlyrent_price);

        mCustomerNameEditText=(EditText) view.findViewById(R.id.et_customer_name);

        mCarNumberRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_car_number);
        mCarNumberTextView=(TextView) view.findViewById(R.id.car_number);

        mPayMoneyTextView=(TextView) view.findViewById(R.id.tv_pay_money);

        mConfirmButton=(Button) view.findViewById(R.id.button_confirm);
        mDeleteButton=(Button) view.findViewById(R.id.button_delete_topay);
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
        return false;
    }
}
