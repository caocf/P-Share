package com.azhuoinfo.pshare.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.SetUserInfo;

import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class UserCenterFinishFragment extends BaseContentFragment{
   /*
    *actionbar
    * */
    //个人中心是否显示可编辑状态
    private int count=0;
    private boolean isEditor=false;
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
    /*
    *编辑信息
    * */
    //设置用户昵称
    private TextView mCustomerNickNameTextView;
    //设置用户手机号
    private TextView mCustomerMobileTextView;
    //设置用户性别
    private RelativeLayout mCustomerSexRelativeLayout;
    //选择列表中的性别设置进去
    private TextView mCustomerSexTextView;
    //设置用户职业
    private TextView mCustomerJobTextView;
    //设置用户家乡
    private RelativeLayout mCustomerRegionRelativeLayout;
    private TextView mCustomerRegionTextView;
    //设置用户邮箱
    private TextView mCustomerEmailTextView;

    private AccountVerify mAccountVerify;
    private CustomerInfo customerInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerInfo=(CustomerInfo)this.app.getSession().get("customerInfo");
        //Log.e(TAG, customerInfo.getCustomer_Id().toString());
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_center_finish,container,false);
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
        mCustomerNicknameTextView=(TextView) view.findViewById(R.id.et_customer_nickname);
        mCustomerIdTextView=(TextView) view.findViewById(R.id.tv_customer_id);
        mCustomerPointsTextView=(TextView) view.findViewById(R.id.tv_customer_points);
        mCustomerFaithPoints1TextView=(TextView) view.findViewById(R.id.tv_customer_faith_points1);
        mCustomerFaithPoints2TextView=(TextView) view.findViewById(R.id.tv_customer_faith_points2);
        //编辑信息
        mCustomerNickNameTextView=(TextView) view.findViewById(R.id.et_customer_nickname);
        mCustomerMobileTextView=(TextView) view.findViewById(R.id.et_customer_mobile);

        mCustomerSexRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_customer_sex);
        mCustomerSexTextView=(TextView) view.findViewById(R.id.tv_customer_sex);

        mCustomerJobTextView=(TextView) view.findViewById(R.id.et_customer_job);
        mCustomerRegionRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_customer_region);
        mCustomerRegionTextView=(TextView) view.findViewById(R.id.tv_customer_region);

        mCustomerEmailTextView=(TextView) view.findViewById(R.id.et_customer_email);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.user_center);

        mCustomerNicknameTextView.setText(customerInfo.getCustomer_nickname().toString());
        mCustomerIdTextView.setText(customerInfo.getCustomer_Id().toString());
        mCustomerMobileTextView.setText(customerInfo.getCustomer_mobile().toString());
        if(customerInfo.getCustomer_sex().equals("3")){
            mCustomerSexTextView.setText("");
        }else if (customerInfo.getCustomer_sex().equals("1")){
            mCustomerSexTextView.setText("先生");
        }else{
            mCustomerSexTextView.setText("女士");
        }
        mCustomerJobTextView.setText(customerInfo.getCustomer_job().toString());
        mCustomerRegionTextView.setText(customerInfo.getCustomer_region().toString());
        mCustomerEmailTextView.setText(customerInfo.getCustomer_email().toString());

    }

    @Override
    protected void initData(Bundle bundle) {

    }
    @Override
    protected boolean onMenuActionCreated(final ActionMenu actionMenu) {
        super.onMenuActionCreated(actionMenu);
        actionMenu.addMenu(1, R.string.menu_edit, R.drawable.editor, 1);
        return true;
    }
    @Override
    public boolean onMenuActionSelected(final ActionMenuItem action){
        switch(action.getId()){
            case 1:
                replaceFragment(UserCenterEditorFragment.class,"UserCenterEditorFragment",null);
                break;
        }
        return super.onMenuActionSelected(action);
    }

    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return true;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
