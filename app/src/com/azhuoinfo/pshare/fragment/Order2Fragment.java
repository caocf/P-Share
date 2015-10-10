package com.azhuoinfo.pshare.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.fragment.adapter.MonthlyRentListAdapter;
import com.azhuoinfo.pshare.model.UserAuth;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class Order2Fragment extends BaseContentFragment{
    //取消预约
    private RelativeLayout mRelativeLayout;

    private ImageView mImageView;

    private AccountVerify mAccountVerify;
    private AnimationDrawable animationDrawable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_order2,container,false);
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
        mRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_unselect_order);
        mImageView=(ImageView) view.findViewById(R.id.iv_in_order);

    }

    @Override
    protected void initViews(Bundle bundle) {
        ((AnimationDrawable)mImageView.getBackground()).start();
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
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
    public void postCancelOrder(String orderSn){
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_CANCELORDER));
        apiTask.setParams(ApiContants.instance(getActivity()).userCancelOrder(orderSn));
        apiTask.execute(new OnDataLoader<UserAuth>() {
            @Override
            public void onStart() {
                if (getActivity() != null){

                }
            }
            @Override
            public void onSuccess(boolean page, UserAuth auth) {
                Toast.makeText(getActivity(),"订单已取消",Toast.LENGTH_SHORT);

            }
            @Override
            public void onFailure(String code, String message) {
                mobi.cangol.mobile.logging.Log.d(TAG, "code=:" + code + ",message=" + message);
                if (getActivity() != null) {

                }
            }
        });
    }
}
