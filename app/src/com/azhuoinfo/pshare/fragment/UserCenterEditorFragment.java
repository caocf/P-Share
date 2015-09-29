package com.azhuoinfo.pshare.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.model.CustomerInfo;

import java.util.logging.Handler;

import mobi.cangol.mobile.Session;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class UserCenterEditorFragment extends BaseContentFragment{
   /*
    *actionbar
    * */
    //个人中心是否显示可编辑状态
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
    private EditText mCustomerNickNameEditText;
    //设置用户手机号
    private EditText mCustomerMobileEditText;
    //设置用户性别
    private RelativeLayout mCustomerSexRelativeLayout;
    //选择列表中的性别设置进去
    private TextView mCustomerSexTextView;
    private ImageView mCustomerSexImageView;
    //设置用户职业
    private EditText mCustomerJobEditText;
    //设置用户家乡
    private RelativeLayout mCustomerRegionRelativeLayout;
    private TextView mCustomerRegionTextView;
    private ImageView mCustomerRegionImageView;
    //设置用户邮箱
    private EditText mCustomerEmailEditText;
    private String[] sex=new String[]{"男","女"};
    private String[] homes=new String[]{"山东","上海","江苏"};
    private int isFinish=R.drawable.editor;

    private AccountVerify mAccountVerify;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_center_editor,container,false);
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
        //编辑信息
        mCustomerNickNameEditText=(EditText) view.findViewById(R.id.et_customer_nickname);
        mCustomerMobileEditText=(EditText) view.findViewById(R.id.et_customer_mobile);

        mCustomerSexRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_customer_sex);
        mCustomerSexTextView=(TextView) view.findViewById(R.id.tv_customer_sex);
        mCustomerSexImageView=(ImageView) view.findViewById(R.id.iv_customer_sex);

        mCustomerJobEditText=(EditText) view.findViewById(R.id.et_customer_job);

        mCustomerRegionRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_customer_region);
        mCustomerRegionTextView=(TextView) view.findViewById(R.id.tv_customer_region);
        mCustomerRegionImageView=(ImageView) view.findViewById(R.id.iv_customer_region);

        mCustomerEmailEditText=(EditText) view.findViewById(R.id.et_customer_email);


    }

    @Override
    protected void initViews(Bundle bundle) {

        this.setTitle(R.string.user_center);
        mCustomerSexRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSexListDialog();
            }
        });
        mCustomerRegionRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegionListDialog();
            }
        });

    }

    @Override
    protected void initData(Bundle bundle) {

    }
    @Override
    protected boolean onMenuActionCreated(ActionMenu actionMenu) {
        super.onMenuActionCreated(actionMenu);
        actionMenu.add(new ActionMenuItem(1, "编辑",R.drawable.editor, 1));
        return true;
    }
    @Override
    public boolean onMenuActionSelected(ActionMenuItem action) {
        switch(action.getId()){
            case 1:
                if (isEditor) {
                   // action.setDrawable(R.drawable.editor);
                    action.setShow(R.drawable.editor);
                    mCustomerNickNameEditText.setEnabled(true);
                    mCustomerMobileEditText.setEnabled(false);
                    mCustomerJobEditText.setEnabled(true);
                    mCustomerEmailEditText.setEnabled(true);
                    mCustomerSexImageView.setVisibility(View.VISIBLE);
                    mCustomerRegionImageView.setVisibility(View.VISIBLE);
                    isFinish=R.drawable.editor;
                    isEditor = false;
                } else {
                    action.setShow(R.drawable.finish);
                   // action.setDrawable(R.drawable.finish);
                    mCustomerNickNameEditText.setEnabled(false);
                    mCustomerMobileEditText.setEnabled(false);
                    mCustomerJobEditText.setEnabled(false);
                    mCustomerEmailEditText.setEnabled(false);
                    mCustomerSexImageView.setVisibility(View.INVISIBLE);
                    mCustomerRegionImageView.setVisibility(View.INVISIBLE);
                    isEditor = true;
                }
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
        return false;
    }

    private void showSexListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

        builder.setTitle("请选择性别");

        builder.setItems(sex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCustomerSexTextView.setText(sex[which]);
            }
        });
        builder.show();
    }
    private void showRegionListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

        builder.setTitle("请您的选择家乡");

        builder.setItems(homes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCustomerRegionTextView.setText(homes[which]);
            }
        });
        builder.show();
    }
    public void postUserInfo(String customerId,String customerNickmane,
                             String customerHead,String customerSex,String customerJob,
                             String customerRegion,String customerMobile,String customerEmail ){
        ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_SETUSERINFO));
        apiTask.setParams(ApiContants.instance(getActivity()).setUserInfo(customerId,customerNickmane,
                customerHead,customerSex,customerJob,customerRegion,customerMobile,customerEmail));
        apiTask.setRoot("customerInfo");
        apiTask.execute(new OnDataLoader<CustomerInfo>() {
            @Override
            public void onStart() {

            }
            @Override
            public void onSuccess(boolean page, CustomerInfo customerInfos) {

            }

            @Override
            public void onFailure(String code, String message) {

            }
        });
    }
}
