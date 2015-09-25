package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;

import java.util.ArrayList;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class AddCarInformationFragment extends BaseContentFragment{

    //返回到上个页面
    //private Button activity_back;

    //点击出现一个自定义的list列表选择车的归属地
    private RelativeLayout mCarLocationRelativeLayout;
    //设置选择车的归属地
    private TextView mCarLocationTextView;
    //设置车牌号
    private EditText mCarNumbrEditText;
    //设置车型
    private EditText mCarSizeEditText;
    //确定信息携带数据跳转到车列表界面将信息添加到其list中
    private Button mConfirmButton;

    private AccountVerify mAccountVerify;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_add_car_information,container,false);
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
        mCarLocationRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_car_location);
        mCarLocationTextView=(TextView) view.findViewById(R.id.tv_car_location);

        mCarNumbrEditText=(EditText) view.findViewById(R.id.et_car_number);
        mCarSizeEditText=(EditText) view.findViewById(R.id.et_car_size);

        mConfirmButton=(Button) view.findViewById(R.id.button_confirm);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.add_car_information);
        mCarLocationRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
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
        return false;
    }
}
