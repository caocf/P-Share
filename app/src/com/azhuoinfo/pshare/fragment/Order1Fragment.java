package com.azhuoinfo.pshare.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.fragment.adapter.ImageAdapter;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.UnfinishedOrderInfo;
import com.azhuoinfo.pshare.model.UserAuth;
import com.azhuoinfo.pshare.view.listview.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class Order1Fragment extends BaseContentFragment{
    private RelativeLayout mOrder1RelativeLayout;
    private RelativeLayout mOrder2RelativeLayout;
    private ScrollView mOrder3ScrollView;
    private ScrollView mOrder4ScrollView;
    /*
    * order2
    * */
    //取消预约
    private RelativeLayout mRelativeLayout;
    private RelativeLayout mOrder3RelativeLayout;
    private ImageView mAnimationImageView;
    //代泊员头像
    private ImageView mParkerHeadImageView;
    //代泊员ID
    private TextView mParkerIDTextView;
    //代泊员职务
    private TextView mParkerLevelTextView;
    //代泊员负责区域
    private TextView mParkerAreaTextView;
    //代泊员联系方式
    private TextView mParkerMobileTextView;
    private RelativeLayout mParkerMobileRelativeLayout;
    //预约时间
    private TextView mAppointmentTimeTextView;
    //预约取车时间
    private TextView mAppointmentMakeCarTimeTextView;
    //停车计时
    private TextView mCountdownTimeTextView;
    //用户车牌号
    private TextView mCarNumberTextView;
    private GridView mImageGridView;
    private ImageView mLeft;
    private ImageView mRight;
    private AccountVerify mAccountVerify;
    private TextView tv;
    //GridView的数据的集合
    private List<Map<String, Object>> mList;
    //所有图片路径的集合
    private List<String> urls;
    //仅包括三个图片路径的集合
    private List<String> show;
    //当前相机返回的照片的存储路径
    private String path;
    private int left=0,right=2;
    private int listSize;
    private String order_state;
    private CustomerInfo customerInfo;
    private String customerId;
    private String orderId;
    private String parakerId;
    private String parkerLevel;
    private String parkerName;
    private String parkerMobile;
    private String orderPlanBegin;
    private String orderPlanEnd;
    private String carNumber;
    private String orderPath;
    private List<String> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
        customerInfo=(CustomerInfo)this.app.getSession().get("customerInfo");
        customerId=customerInfo.getCustomer_Id();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.mine_order_state,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
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

    }

    @Override
    protected void initViews(Bundle bundle) {
        mOrder1RelativeLayout=(RelativeLayout) findViewById(R.id.fragment_order1);
        mOrder2RelativeLayout=(RelativeLayout) findViewById(R.id.fragment_order2);
        mOrder3ScrollView=(ScrollView) findViewById(R.id.fragment_order3);
        mOrder4ScrollView=(ScrollView) findViewById(R.id.fragment_order4);

    }

    @Override
    protected void initData(Bundle bundle) {
        postUnfinishedOrder(customerId);

    }

    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return true;
    }
    public void initOrderState(){
        if(listSize==0){
            Log.e("mOrder1RelativeLayout",listSize+"");
            mOrder1RelativeLayout.setVisibility(View.VISIBLE);
            mOrder2RelativeLayout.setVisibility(View.GONE);
            mOrder3ScrollView.setVisibility(View.GONE);
            mOrder4ScrollView.setVisibility(View.GONE);
        }else if(listSize>0&&order_state.equals("1")){
            Log.e("mOrder2RelativeLayout",order_state+"");
            mOrder1RelativeLayout.setVisibility(View.GONE);
            mOrder2RelativeLayout.setVisibility(View.VISIBLE);
            mOrder3ScrollView.setVisibility(View.GONE);
            mOrder4ScrollView.setVisibility(View.GONE);
            initViewsOrder2();
        }else if(listSize>0&&order_state.equals("2")){
            Log.e("mOrder3ScrollView",order_state+"");
            mOrder1RelativeLayout.setVisibility(View.GONE);
            mOrder2RelativeLayout.setVisibility(View.GONE);
            mOrder3ScrollView.setVisibility(View.VISIBLE);
            mOrder4ScrollView.setVisibility(View.GONE);
            initViewsOrder3();
        }else if((listSize>0)&&(order_state.equals("3")||order_state.equals("4"))){
            Log.e("mOrder4ScrollView",order_state+"");
            mOrder1RelativeLayout.setVisibility(View.GONE);
            mOrder2RelativeLayout.setVisibility(View.GONE);
            mOrder3ScrollView.setVisibility(View.GONE);
            mOrder4ScrollView.setVisibility(View.VISIBLE);
            initViewsOrder4();
        }
    }
    public void postUnfinishedOrder(String customerId){
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_UNFINISHEDORDER));
        apiTask.setParams(ApiContants.instance(getActivity()).unFinishedOrder(customerId));
        apiTask.setRoot("orderInfo");
        apiTask.execute(new OnDataLoader<List<UnfinishedOrderInfo>>() {
            @Override
            public void onStart() {
            }
            @Override
            public void onSuccess(boolean page, List<UnfinishedOrderInfo> unfinishedOrderInfos) {
                if(isEnable()){
                    Log.e(TAG, unfinishedOrderInfos.size() + "");
                    listSize = unfinishedOrderInfos.size();
                    if (listSize != 0) {
                        for (int i = 0; i < unfinishedOrderInfos.size(); i++) {
                            UnfinishedOrderInfo unfinishedOrderInfo = unfinishedOrderInfos.get(i);
                            order_state = unfinishedOrderInfo.getOrder_state();
                            orderId = unfinishedOrderInfo.getOrder_id();
                            parakerId = unfinishedOrderInfo.getParker_id();
                            parkerLevel = unfinishedOrderInfo.getParker_level();
                            parkerMobile = unfinishedOrderInfo.getParker_mobile();
                            parkerName = unfinishedOrderInfo.getParker_name();
                            orderPlanBegin = unfinishedOrderInfo.getOrder_plan_begin();
                            orderPlanEnd = unfinishedOrderInfo.getOrder_plan_end();
                            carNumber = unfinishedOrderInfo.getCar_number();
                            orderPath = unfinishedOrderInfo.getOrder_path();
                            Log.e(TAG, order_state);
                        }
                    }
                    initOrderState();
                }
            }

            @Override
            public void onFailure(String code, String message) {

            }
        });
    }
    public void postCancelOrder(String orderSn){
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_CANCELORDER));
        apiTask.setParams(ApiContants.instance(getActivity()).userCancelOrder(orderSn));
        apiTask.execute(new OnDataLoader<UserAuth>() {
            @Override
            public void onStart() {
                if (getActivity() != null) {

                }
            }

            @Override
            public void onSuccess(boolean page, UserAuth auth) {
                mOrder1RelativeLayout.setVisibility(View.VISIBLE);
                mOrder2RelativeLayout.setVisibility(View.GONE);
                mOrder3ScrollView.setVisibility(View.GONE);
                mOrder4ScrollView.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "订单已取消", Toast.LENGTH_SHORT);
            }
            @Override
            public void onFailure(String code, String message) {
                mobi.cangol.mobile.logging.Log.d(TAG, "code=:" + code + ",message=" + message);
                if (getActivity() != null) {
                }
            }
        });
    }
    public void initViewsOrder2(){
        mRelativeLayout=(RelativeLayout) findViewById(R.id.rl_unselect_order);
        mAnimationImageView=(ImageView) findViewById(R.id.iv_in_order);
        ((AnimationDrawable)mAnimationImageView.getBackground()).start();
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCancelOrder(orderId);
            }
        });
    }
    public void initViewsOrder3(){
//        mParkerHeadImageView=(ImageView) findViewById(R.id.iv_parker_head);
        mParkerIDTextView=(TextView) findViewById(R.id.tv_parker_id);
        mParkerLevelTextView=(TextView) findViewById(R.id.tv_parker_Level);
        mParkerAreaTextView=(TextView) findViewById(R.id.tv_parker_area);
        mParkerMobileRelativeLayout=(RelativeLayout) findViewById(R.id.rl_parker_mobile);
        mParkerMobileTextView=(TextView) findViewById(R.id.tv_parker_mobile4);
        mAppointmentTimeTextView=(TextView) findViewById(R.id.tv_appointment_time4);
        mOrder3RelativeLayout=(RelativeLayout) findViewById(R.id.rl_unselect_order3);
        if(order_state.equals("2")){
            //mParkerHeadImageView.setImageBitmap();
            mParkerIDTextView.setText(parakerId + "");
            mParkerAreaTextView.setText(parkerName + "");
            mParkerMobileTextView.setText(parkerMobile+"");
            mParkerLevelTextView.setText(parkerLevel+"");
            mAppointmentTimeTextView.setText(orderPlanBegin + "");
            mParkerMobileRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + parkerMobile));
                    startActivity(intent);
                }
            });
            Log.e(TAG, "取消预约");
            mOrder3RelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "取消预约");
                    postCancelOrder(orderId);
                }
            });
        }
    }
    public void initViewsOrder4() {
        //mParkerHeadImageView = (ImageView) findViewById(R.id.iv_parker_head);
        mParkerIDTextView = (TextView) findViewById(R.id.tv_parker_id4);
        mParkerLevelTextView = (TextView) findViewById(R.id.tv_parker_Level4);
        mParkerAreaTextView = (TextView) findViewById(R.id.tv_parker_area4);
        mParkerMobileRelativeLayout = (RelativeLayout) findViewById(R.id.rl_parker_mobile);
        mParkerMobileTextView = (TextView) findViewById(R.id.tv_parker_mobile4);
        mAppointmentTimeTextView = (TextView) findViewById(R.id.tv_appointment_time4);
        mAppointmentMakeCarTimeTextView = (TextView) findViewById(R.id.tv_appointment_make_car_time4);
        mCarNumberTextView = (TextView) findViewById(R.id.tv_car_number);
        mImageGridView = (GridView) findViewById(R.id.gridview_photos);
        mLeft = (ImageView) findViewById(R.id.iv_left);
        mRight = (ImageView) findViewById(R.id.iv_right);
        if (order_state.equals("3") || order_state.equals("4")) {
            Log.e(TAG,parakerId+":"+parkerName+":"+parakerId+":"+parkerMobile+":"+parkerLevel+":"+orderPlanBegin+":"+orderPlanEnd);
            mParkerIDTextView.setText(parakerId + "");
            mParkerAreaTextView.setText(parkerName + "");
            mParkerMobileTextView.setText(parkerMobile + "");
            mParkerLevelTextView.setText(parkerLevel + "");
            mAppointmentTimeTextView.setText(orderPlanBegin + "");
            mAppointmentMakeCarTimeTextView.setText(orderPlanEnd + "");
            mCarNumberTextView.setText(carNumber + "");
            mParkerMobileRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + parkerMobile));
                    startActivity(intent);
                }
            });
            mList = new ArrayList<Map<String, Object>>();
            urls = new ArrayList<String>();
            show = new ArrayList<String>();
            saveList();
            showImage(show);
            mLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toLeft();
                }
            });
            mRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toRight();
                }
            });
        }
    }
    /**
     * 对图片显示的数据初始化
     */
    private void saveList() {
        String[] orderPaths = orderPath.split(",");
        for (int i = 0; i < orderPaths.length ; i++) {
            urls.add(orderPaths[i]);
        }
        for (int j = 0; j < 3; j++) {
            show.add(urls.get(j));
        }
        Log.d("saveList", urls.size() + "");
    }

    /**
     * 对图片的显示
     *
     * @param urls
     */
    private void showImage(List<String> urls) {
       /* mShow.setVisibility(View.VISIBLE);
        mGrid.setVisibility(View.GONE);*/
        ImageAdapter adapter = new ImageAdapter(getActivity(), urls);
        mImageGridView.setAdapter(adapter);
        Log.d("showImage", urls.toString());
    }

    private void toLeft() {
        if (left == 0) {
            Toast.makeText(getActivity(),"当前位于最左方!",Toast.LENGTH_SHORT).show();
        } else {
            show.set(0, urls.get(left - 1));
            show.set(1, urls.get(left));
            show.set(2, urls.get(left + 1));
            left--;
            right--;
            showImage(show);
        }
    }

    private void toRight() {
        if (right == urls.size() - 1) {
            Toast.makeText(getActivity(),"当前位于最右方!",Toast.LENGTH_SHORT).show();
            //showToast("当前位于最右方!");
        } else {
            show.set(0, urls.get(right - 1));
            show.set(1, urls.get(right));
            show.set(2, urls.get(right + 1));
            left++;
            right++;
            showImage(show);
        }
    }
}