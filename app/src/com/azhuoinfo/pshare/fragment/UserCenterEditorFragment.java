package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
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
    private boolean isEditor=true;
    //返回到上个页面
    private Button mBackButton;
    //完成个人信息内容的编辑保存到FinishActivity中
    private ImageView mEditorFinishImageView;

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
        mBackButton=(Button) view.findViewById(R.id.activity_back);
        mEditorFinishImageView=(ImageView) view.findViewById(R.id.iv_editor_finish);

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
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentFragment(UserCenterFragment.class, "UserCenterFragment", null, ModuleMenuIDS.MODULE_HOME);
            }
        });
        mEditorFinishImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditor) {
                    mCustomerNickNameEditText.setEnabled(true);
                    mCustomerMobileEditText.setEnabled(true);
                    mCustomerJobEditText.setEnabled(true);
                    mCustomerEmailEditText.setEnabled(true);
                    mCustomerSexImageView.setVisibility(View.VISIBLE);
                    mCustomerRegionImageView.setVisibility(View.VISIBLE);
                    mEditorFinishImageView.setImageResource(R.drawable.finish);
                    isEditor = false;
                } else {
                    mCustomerNickNameEditText.setEnabled(false);
                    mCustomerMobileEditText.setEnabled(false);
                    mCustomerJobEditText.setEnabled(false);
                    mCustomerEmailEditText.setEnabled(false);
                    mCustomerSexImageView.setVisibility(View.INVISIBLE);
                    mCustomerRegionImageView.setVisibility(View.INVISIBLE);
                    mEditorFinishImageView.setImageResource(R.drawable.editor);
                    isEditor = true;
                }
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

    /*虚拟键盘的处理*//*
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }*/
}
