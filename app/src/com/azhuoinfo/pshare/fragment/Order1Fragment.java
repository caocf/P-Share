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
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class Order1Fragment extends BaseContentFragment{
    /*
    * order2
    * */
    //取消预约
    private RelativeLayout mRelativeLayout;
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
    private GridView mPhotosGridView;
    private ImageView mLeftImageView;
    private ImageView mRightImageView;

    private AccountVerify mAccountVerify;
    private TextView tv;
    private int listSize;
    private String order_state;
    private CustomerInfo customerInfo;
    private String customerId;
    private String orderId;
    private String parakerId;
    private String parkerLevel;
    private String parkerName;
    private String parkerMobile;
    private String creatAt;
    private String orderPlanEnd;
    private String carNumber;
    private String orderPath;
    private HashMap<Integer,String> show;
    private List<String> list;
    private int left,right;

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
        postUnfinishedOrder(customerId);
        if(listSize==0){
            return inflater.inflate(R.layout.fragment_order1,container,false);
        }else if(listSize>0&&order_state.equals("1")){
            return inflater.inflate(R.layout.fragment_order2,container,false);
        }else if(listSize>0&&order_state.equals("2")){
            return inflater.inflate(R.layout.fragment_order3,container,false);
        }else if(listSize>0&&order_state.equals("3")||order_state.equals("4")||order_state.equals("5")){
            return inflater.inflate(R.layout.fragment_order4,container,false);
        }
        return inflater.inflate(R.layout.fragment_order1,container,false);
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
       // mAppointmentTimeTextView=(TextView) findViewById(R.id.text);
        /*Message message = handeler.obtainMessage(1);     // Message
        handeler.sendMessageDelayed(message, 1000);*/

    }

    @Override
    protected void initData(Bundle bundle) {
        if(listSize>0&&order_state.equals("1")){
            initViewsOrder2();
        }else if(listSize>0&&order_state.equals("2")){
            initViewsOrder3();
        }else if((listSize>0)&&(order_state.equals("3")||order_state.equals("4")||order_state.equals("5"))){
            initViewsOrder4();
        }
    }

    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return true;
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
                        creatAt = unfinishedOrderInfo.getCreate_at();
                        orderPlanEnd = unfinishedOrderInfo.getOrder_plan_end();
                        carNumber = unfinishedOrderInfo.getCar_number();
                        orderPath = unfinishedOrderInfo.getOrder_path();
                        Log.e(TAG, order_state);
                    }
                }
                //initTable();
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
        mParkerHeadImageView=(ImageView) findViewById(R.id.iv_parker_head);
        mParkerIDTextView=(TextView) findViewById(R.id.tv_parker_id);
        mParkerLevelTextView=(TextView) findViewById(R.id.tv_parker_Level);
        mParkerAreaTextView=(TextView) findViewById(R.id.tv_parker_area);
        mParkerMobileRelativeLayout=(RelativeLayout) findViewById(R.id.rl_parker_mobile);
        mParkerMobileTextView=(TextView) findViewById(R.id.tv_parker_mobile);
        mAppointmentTimeTextView=(TextView) findViewById(R.id.tv_appointment_time);
        mRelativeLayout=(RelativeLayout) findViewById(R.id.rl_unselect_order);

        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCancelOrder(orderId);

            }
        });
        if(order_state.equals("2")){
            //mParkerHeadImageView.setImageBitmap();
            mParkerIDTextView.setText(parakerId+"");
            mParkerAreaTextView.setText(parkerName+"");
            mParkerMobileTextView.setText(parkerMobile+"");
            mParkerLevelTextView.setText(parkerLevel+"");
            mAppointmentTimeTextView.setText(creatAt + "");
        }
        mParkerMobileRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+parkerMobile));
                startActivity(intent);
            }
        });
    }
    public void initViewsOrder4(){
        mParkerHeadImageView=(ImageView) findViewById(R.id.iv_parker_head);
        mParkerIDTextView=(TextView) findViewById(R.id.tv_parker_id);
        mParkerLevelTextView=(TextView) findViewById(R.id.tv_parker_Level);
        mParkerAreaTextView=(TextView) findViewById(R.id.tv_parker_area);
        mParkerMobileRelativeLayout=(RelativeLayout) findViewById(R.id.rl_parker_mobile);
        mParkerMobileTextView=(TextView) findViewById(R.id.tv_parker_mobile);
        mAppointmentTimeTextView=(TextView) findViewById(R.id.tv_appointment_time);

        mAppointmentMakeCarTimeTextView=(TextView) findViewById(R.id.tv_appointment_make_car_time);
        mCountdownTimeTextView=(TextView) findViewById(R.id.tv_countdown_time);
        mCarNumberTextView=(TextView) findViewById(R.id.tv_car_number);
        mPhotosGridView=(GridView) findViewById(R.id.gridview_photos);
        mLeftImageView=(ImageView) findViewById(R.id.iv_left);
        mRightImageView=(ImageView) findViewById(R.id.iv_right);
        if(order_state.equals("3")||order_state.equals("4")||order_state.equals("5")){
            mParkerIDTextView.setText(parakerId+"");
            mParkerAreaTextView.setText(parkerName+"");
            mParkerMobileTextView.setText(parkerMobile+"");
            mParkerLevelTextView.setText(parkerLevel+"");
            mAppointmentTimeTextView.setText(creatAt+"");
            mAppointmentMakeCarTimeTextView.setText(orderPlanEnd + "");
            mCarNumberTextView.setText(carNumber + "");
            mParkerMobileRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + parkerMobile));
                    startActivity(intent);
                }
            });
            String [] url=orderPath.split(",");
           list=new ArrayList<String>();
            for (int i=0;i<url.length;i++){
                HashMap<String,String> map=new HashMap<String,String>();
                map.put("url",url[i]);
                list.add(url[i]);
            }
            mPhotosGridView.setAdapter(new ImageAdapter(getActivity(),list));
        }

    }
   /* public void showImages(){

        String[] strArr = orderPath.split(",");
    }
    public void toLeft(){
        if(left==0){
            showToast("图片已在最左边");
        }else{
            show.put(0,)
        }
    }*/

}