package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.model.CustomerInfo;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class UserCenterFragment extends BaseContentFragment{

    //用户的头像设置
    private ImageView mCustomerHeadImageView;
    //用户名
    private TextView mCustomerNicknameTextView;
    //用户Id
    private TextView mCustomerIdTextView;
    //用户积分
    private TextView mCustomerPointsTextView;
    //用户诚信分(余)
    private TextView mCustomerFaithPoints1TextView;
    //用户诚信分(满)
    private TextView mCustomerFaithPoints2TextView;
    //到个人中心详情
    private LinearLayout mEditorInformationLinearLayout;
    //生成的二维码图片
    private ImageView mQRCodeImageView;
    private AccountVerify mAccountVerify;
    private CustomerInfo customerInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerInfo=(CustomerInfo)this.app.getSession().get("customerInfo");
        Log.e(TAG, customerInfo.getCustomer_Id().toString());
        this.getCustomActionBar().setCustomHomeAsUpIndicator(R.drawable.left_head, R.drawable.left_head);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_centerr,container,false);
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
        mCustomerHeadImageView=(ImageView) view.findViewById(R.id.iv_customer_head);
        mCustomerNicknameTextView=(TextView) view.findViewById(R.id.tv_customer_nickname);
        mCustomerIdTextView=(TextView) view.findViewById(R.id.tv_customer_id);
        mCustomerPointsTextView=(TextView) view.findViewById(R.id.tv_customer_points);
        mCustomerFaithPoints1TextView=(TextView) view.findViewById(R.id.tv_customer_faith_points1);
        mCustomerFaithPoints2TextView=(TextView) view.findViewById(R.id.tv_customer_faith_points2);
        mEditorInformationLinearLayout=(LinearLayout) view.findViewById(R.id.ll_user_center);
        mQRCodeImageView=(ImageView) view.findViewById(R.id.iv_QR_code);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.user_center);
        mEditorInformationLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentFragment(UserCenterEditorFragment.class, "UserCenterEditorFragment", null, ModuleMenuIDS.MODULE_HOME);
            }
        });
        mCustomerIdTextView.setText(customerInfo.getCustomer_Id().toString());
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
