package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.fragment.adapter.MonthlyRentListAdapter;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class Order3Fragment extends BaseContentFragment{

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
    //预约时间
    private TextView mAppointmentTimeTextView;
    //取消预约
    private RelativeLayout mCancelAppointmentRelativeLayout;

    private List<String> list=new ArrayList<String>();
    private AccountVerify mAccountVerify;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_order3,container,false);
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
        mAppointmentTimeTextView=(TextView) view.findViewById(R.id.tv_appointment_time);
        mCancelAppointmentRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_unselect_order);
    }

    @Override
    protected void initViews(Bundle bundle) {
        mCancelAppointmentRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        return true;
    }
}
