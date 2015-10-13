package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class PointsFragment extends BaseContentFragment{

    //返回到上个页面
    //private ImageView activity_back;
    //当前积分
    private TextView mCustomerPointsTextView;
    //赚积分模块
    private ImageView mEarnPointsImageView;
    //未兑换模块
    private ImageView mFreeparkingTicket1ImageView;
    //已兑换模块
    private ImageView mFreeparkingTicket2ImageView;
    //兑换
    private RelativeLayout mPointsExchangeRelativeLayout;
    //已兑换
    private RelativeLayout mPointsExchangedRelativeLayout;
    private AccountVerify mAccountVerify;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_points,container,false);
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
        mCustomerPointsTextView=(TextView) view.findViewById(R.id.tv_customer_points);

        mEarnPointsImageView=(ImageView) view.findViewById(R.id.iv_points_earn_points);

        mPointsExchangeRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_points_exchange);
        mFreeparkingTicket1ImageView=(ImageView) view.findViewById(R.id.iv_points_exchange);

        mPointsExchangedRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_points_exchanged);
        mFreeparkingTicket2ImageView=(ImageView) view.findViewById(R.id.iv_points_exchanged);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.integration);

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
}
