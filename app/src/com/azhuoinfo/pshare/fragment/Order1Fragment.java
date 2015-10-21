package com.azhuoinfo.pshare.fragment;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.ApiResult;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.fragment.adapter.ImageAdapter;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.UnfinishedOrderInfo;
import com.azhuoinfo.pshare.model.UserAuth;
import com.azhuoinfo.pshare.view.CommonDialog;
import com.azhuoinfo.pshare.view.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.http.extras.PollingHttpClient;
import mobi.cangol.mobile.http.extras.PollingResponseHandler;
import mobi.cangol.mobile.sdk.pay.OnPayResultListener;
import mobi.cangol.mobile.sdk.pay.PayManager;
import mobi.cangol.mobile.sdk.pay.PlaceOrderCallback;

/**
 * Created by Azhuo on 2015/9/22.
 * 订单模块中预定
 */
public class Order1Fragment extends BaseContentFragment{
    private RelativeLayout mOrder1RelativeLayout;
    private RelativeLayout mOrder2RelativeLayout;
    private ScrollView mOrder3ScrollView;
    private ScrollView mOrder4ScrollView;
    private RelativeLayout mToPayRelativeLayout;
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
    private Button mFinishButton;
    private Button mGetCarButton;
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
    private String parkingName;
    private  String parkingPath;
    private long diff;
    private List<String> list;
    private Timer timer;
    private Calendar calendar;
    private int year,monthOfYear,dayOfMonth,hourOfDay,minute;
    private Long hour,min,sec;
    private String currentTime;
    private String orderActualBegin;
    private String LgTime;
    private Handler handler;

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
        /*mOrder1RelativeLayout=(RelativeLayout) view.findViewById(R.id.fragment_order1);
        mOrder2RelativeLayout=(RelativeLayout) view.findViewById(R.id.fragment_order2);
        mOrder3ScrollView=(ScrollView) view.findViewById(R.id.fragment_order3);
        mOrder4ScrollView=(ScrollView) view.findViewById(R.id.fragment_order4);
        mToPayRelativeLayout=(RelativeLayout) view.findViewById(R.id.listitem_to_pay);*/
        mOrder1RelativeLayout=(RelativeLayout) view.findViewById(R.id.fg_order1);
        mOrder2RelativeLayout=(RelativeLayout) view.findViewById(R.id.fg_order2);
        mOrder3ScrollView=(ScrollView) view.findViewById(R.id.fg_order3);
        mOrder4ScrollView=(ScrollView) view.findViewById(R.id.fg_order4);
        mToPayRelativeLayout=(RelativeLayout) view.findViewById(R.id.fg_list_menu_pay_pager);
    }

    @Override
    protected void initViews(Bundle bundle) {
/*        mOrder1RelativeLayout=(RelativeLayout) findViewById(R.id.fragment_order1);
        mOrder2RelativeLayout=(RelativeLayout) findViewById(R.id.fragment_order2);
        mOrder3ScrollView=(ScrollView) findViewById(R.id.fragment_order3);
        mOrder4ScrollView=(ScrollView) findViewById(R.id.fragment_order4);
        mToPayRelativeLayout=(RelativeLayout) findViewById(R.id.listitem_to_pay);*/

        /*mOrder1RelativeLayout=(RelativeLayout) findViewById(R.id.fg_order1);
        mOrder2RelativeLayout=(RelativeLayout) findViewById(R.id.fg_order2);
        mOrder3ScrollView=(ScrollView) findViewById(R.id.fg_order3);
        mOrder4ScrollView=(ScrollView) findViewById(R.id.fg_order4);
        mToPayRelativeLayout=(RelativeLayout) findViewById(R.id.fg_list_menu_pay_pager);*/



    }

    @Override
    protected void initData(Bundle bundle) {
       /* handler.postDelayed(runnable, 2000);//每两秒执行一次runnable.*/
        //postUnfinishedOrder(customerId);
        //initOrderState();
        PollingUnfinishedOrder(customerId);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //pollingHttpClient.cancelRequests(getActivity(), true);
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
        if (
        mOrder2RelativeLayout == null||
        mOrder3ScrollView == null||
        mOrder4ScrollView == null||
        mToPayRelativeLayout == null||
        mOrder1RelativeLayout == null
                )
            return;
        if(listSize==0|| order_state == null || order_state.isEmpty()) {
            Log.e("mOrder1RelativeLayout",listSize+"");

            mOrder2RelativeLayout.setVisibility(View.GONE);
            mOrder3ScrollView.setVisibility(View.GONE);
            mOrder4ScrollView.setVisibility(View.GONE);
            mToPayRelativeLayout.setVisibility(View.GONE);
            mOrder1RelativeLayout.setVisibility(View.VISIBLE);
        }else if(listSize>0&&order_state.equals("1")){
            Log.e("mOrder2RelativeLayout", order_state + "");
            mOrder1RelativeLayout.setVisibility(View.GONE);
            mOrder3ScrollView.setVisibility(View.GONE);
            mOrder4ScrollView.setVisibility(View.GONE);
            mToPayRelativeLayout.setVisibility(View.GONE);
            mOrder2RelativeLayout.setVisibility(View.VISIBLE);
            Log.e("mOrder2RelativeLayout", order_state + " finish");

            initViewsOrder2();
            //PollingUnfinishedOrder(customerId);
        }else if(listSize>0&&order_state.equals("2")){
            Log.e("mOrder3ScrollView", order_state + "");
            mOrder1RelativeLayout.setVisibility(View.GONE);
            mOrder2RelativeLayout.setVisibility(View.GONE);
            mOrder4ScrollView.setVisibility(View.GONE);
            mOrder3ScrollView.setVisibility(View.VISIBLE);
            initViewsOrder3();
        }else if((listSize>0)&&(order_state.equals("3")||order_state.equals("4"))) {
            Log.e("mOrder4ScrollView", order_state + "");
            mOrder1RelativeLayout.setVisibility(View.GONE);
            mOrder2RelativeLayout.setVisibility(View.GONE);
            mOrder3ScrollView.setVisibility(View.GONE);
            mToPayRelativeLayout.setVisibility(View.GONE);
            mOrder4ScrollView.setVisibility(View.VISIBLE);
          /*  if(parkingPath!=null){
                mOrder4ScrollView.setVisibility(View.VISIBLE);
            }else {
                mToPayRelativeLayout.setVisibility(View.GONE);
            }*/
            initViewsOrder4();
        }
    }

    PollingHttpClient pollingHttpClient;
    public void PollingUnfinishedOrder(final String customerId){
        pollingHttpClient = new PollingHttpClient();
        PollingUnfinishedOrderHandler pollingUnfinishedOrderHandler = new PollingUnfinishedOrderHandler();
        pollingHttpClient.send(
                getActivity(),
                ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_UNFINISHEDORDER),
                ApiContants.instance(getActivity()).unFinishedOrder(customerId),
                pollingUnfinishedOrderHandler,
                18,10000);
    }

    String last_order_state;
    class PollingUnfinishedOrderHandler extends PollingResponseHandler{

        @Override
        public boolean isFailResponse(String content) {
            Log.d("ResponseHandler", "isFailResponse content=" + content);
            try {
                ApiResult apiResult = ApiResult.parserObject(UnfinishedOrderInfo.class, new JSONObject(content), "orderInfo");
                //List<UnfinishedOrderInfo> unfinishedOrderInfos = apiResult.getList();
                List<UnfinishedOrderInfo> unfinishedOrderInfos = apiResult.getList();
                if(isEnable()){
                    //Log.e(TAG, unfinishedOrderInfos.size() + "");
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
                            parkingName=unfinishedOrderInfo.getParking_name();
                            orderPlanBegin = unfinishedOrderInfo.getOrder_plan_begin();
                            orderPlanEnd = unfinishedOrderInfo.getOrder_plan_end();
                            carNumber = unfinishedOrderInfo.getCar_number();
                            orderPath = unfinishedOrderInfo.getOrder_path();
                            parkingPath=unfinishedOrderInfo.getParking_path();
                            orderActualBegin=unfinishedOrderInfo.getOrder_actual_begin_start();
                            Log.e(TAG,"order_state is " + order_state);
                            if (order_state.equals("3") || order_state.equals("4")) {
                                //dateDiff(orderActualBegin, this.getApiResult().getTimestamp(), "yyyy-MM-dd HH:mm:ss");
                            }
                        }
                    }
                    initOrderState();

                    /*if (!order_state.equals(last_order_state)){
                            initOrderState();
                            last_order_state = new String(order_state);
                        }*/
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        public void onStart() {
            Log.d("ResponseHandler", "onStart");
        }

        @Override
        public void onPollingFinish(int execTimes, String content) {
            Log.d("ResponseHandler", "execTimes=" + execTimes + " content:" + content);
        }

        @Override
        public void onSuccess(int statusCode, String content) {
            Log.d("ResponseHandler", "statusCode=" + statusCode + " content:" + content);
            /*if (unfinishedOrderInfos!=null) {
                if (listSize != 0) {*/


                    //initOrderState();
                //}
            //}

            /*try {
                ApiResult apiResult = ApiResult.parserObject(UnfinishedOrderInfo.class, new JSONObject(content), "orderInfo");
                List<UnfinishedOrderInfo> unfinishedOrderInfos = apiResult.getList();
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
                            parkingName=unfinishedOrderInfo.getParking_name();
                            orderPlanBegin = unfinishedOrderInfo.getOrder_plan_begin();
                            orderPlanEnd = unfinishedOrderInfo.getOrder_plan_end();
                            carNumber = unfinishedOrderInfo.getCar_number();
                            orderPath = unfinishedOrderInfo.getOrder_path();
                            parkingPath=unfinishedOrderInfo.getParking_path();
                            orderActualBegin=unfinishedOrderInfo.getOrder_actual_begin_start();
                            Log.e(TAG, order_state);
                        }
                    }
                    initOrderState();
                    pollingHttpClient.cancelRequests(getActivity(),true);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }*/



/*            List<UnfinishedOrderInfo> unfinishedOrderInfos;
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
                        parkingName=unfinishedOrderInfo.getParking_name();
                        orderPlanBegin = unfinishedOrderInfo.getOrder_plan_begin();
                        orderPlanEnd = unfinishedOrderInfo.getOrder_plan_end();
                        carNumber = unfinishedOrderInfo.getCar_number();
                        orderPath = unfinishedOrderInfo.getOrder_path();
                        parkingPath=unfinishedOrderInfo.getParking_path();
                        orderActualBegin=unfinishedOrderInfo.getOrder_actual_begin_start();
                        Log.e(TAG, order_state);
                    }
                }
                initOrderState();
            }*/
        }

        @Override
        public void onFailure(Throwable error, String content) {
            Log.d("ResponseHandler", "error=" + error + " content:" + content);
            //initOrderState();
            //unfinishedOrderInfos = null;
        }
    }

    public void postUnfinishedOrder(final String customerId){
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_UNFINISHEDORDER));
        apiTask.setParams(ApiContants.instance(getActivity()).unFinishedOrder(customerId));
        apiTask.setRoot("orderInfo");
        apiTask.execute(new OnDataLoader<List<UnfinishedOrderInfo>>() {
            LoadingDialog loadingDialog;
            @Override
            public void onStart() {
                loadingDialog = LoadingDialog.show(getActivity());
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
                            parkingName=unfinishedOrderInfo.getParking_name();
                            orderPlanBegin = unfinishedOrderInfo.getOrder_plan_begin();
                            orderPlanEnd = unfinishedOrderInfo.getOrder_plan_end();
                            carNumber = unfinishedOrderInfo.getCar_number();
                            orderPath = unfinishedOrderInfo.getOrder_path();
                            parkingPath=unfinishedOrderInfo.getParking_path();
                            orderActualBegin=unfinishedOrderInfo.getOrder_actual_begin_start();
                            Log.e(TAG, order_state);
                            if (order_state.equals("3") || order_state.equals("4")) {
                                dateDiff(orderActualBegin, this.getApiResult().getTimestamp(), "yyyy-MM-dd HH:mm:ss");
                            }
                        }
                    }
                    initOrderState();

                    loadingDialog.dismiss();
                }
            }
            @Override
            public void onFailure(String code, String message) {
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });
    }
    public void postCancelOrder(String orderSn){
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_CANCELORDER));
        apiTask.setParams(ApiContants.instance(getActivity()).userCancelOrder(orderSn));
        apiTask.execute(new OnDataLoader<UserAuth>() {
            LoadingDialog loadingDialog;
            @Override
            public void onStart() {
                loadingDialog = LoadingDialog.show(getActivity());
            }
            @Override
            public void onSuccess(boolean page, UserAuth auth) {
                mOrder1RelativeLayout.setVisibility(View.VISIBLE);
                mOrder2RelativeLayout.setVisibility(View.GONE);
                mOrder3ScrollView.setVisibility(View.GONE);
                mOrder4ScrollView.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "订单已取消", Toast.LENGTH_SHORT);
                loadingDialog.dismiss();
            }
            @Override
            public void onFailure(String code, String message) {
                mobi.cangol.mobile.logging.Log.d(TAG, "code=:" + code + ",message=" + message);
                loadingDialog.dismiss();
            }
        });
    }
    public void postGetCar(String customer_id,String order_id){
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_GETCAR));
        apiTask.setParams(ApiContants.instance(getActivity()).getCar(customer_id, order_id));
        apiTask.execute(new OnDataLoader<UserAuth>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(boolean page, UserAuth userAuth) {
                mFinishButton.setVisibility(View.VISIBLE);
                mGetCarButton.setVisibility(View.GONE);
                mFinishButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPayMethodDialog();
                    }
                });
            }

            @Override
            public void onFailure(String code, String message) {

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
        mParkerMobileTextView=(TextView) findViewById(R.id.tv_parker_mobile);
        mAppointmentTimeTextView=(TextView) findViewById(R.id.tv_appointment_time);
        mOrder3RelativeLayout=(RelativeLayout) findViewById(R.id.rl_unselect_order3);
        if(order_state.equals("2")){
            //mParkerHeadImageView.setImageBitmap();
            mParkerIDTextView.setText(parakerId + "");
            mParkerAreaTextView.setText(parkingName + "");
            mParkerMobileTextView.setText(parkerMobile+"");
            mParkerLevelTextView.setText(parkerLevel + "");
            mAppointmentTimeTextView.setText(orderPlanBegin + "");
            Log.e("orderPlanBegin", orderPlanBegin + "");
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
        mCarNumberTextView = (TextView) findViewById(R.id.tv_car_number4);
        mCountdownTimeTextView=(TextView) findViewById(R.id.tv_countdown_time4);
        mImageGridView = (GridView) findViewById(R.id.gridview_photos4);
        mFinishButton=(Button) findViewById(R.id.button_finish_order_pay4);
        mGetCarButton=(Button) findViewById(R.id.button_to_make_car4);
        mLeft = (ImageView) findViewById(R.id.iv_left);
        mRight = (ImageView) findViewById(R.id.iv_right);
        if (order_state.equals("3") || order_state.equals("4")){
            Log.e(TAG, parakerId + ":" + parkerName + ":" + parakerId + ":" + parkerMobile + ":" + parkerLevel + ":" + orderPlanBegin + ":" + orderPlanEnd);
            mParkerIDTextView.setText(parakerId + "");
            mParkerAreaTextView.setText(parkingName + "");
            mParkerMobileTextView.setText(parkerMobile + "");
            mParkerLevelTextView.setText(parkerLevel + "");
            mAppointmentTimeTextView.setText(orderPlanBegin + "");
            Log.e("orderPlanBegin", orderPlanBegin);
            mAppointmentMakeCarTimeTextView.setText(orderPlanEnd + "");
            mCarNumberTextView.setText(carNumber + "");
            mParkerMobileRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + parkerMobile));
                    startActivity(intent);
                }
            });
           // mCountdownTimeTextView.starTimeByStopTimeInFuture();
            Date date = new Date();
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
            LgTime = sdformat.format(date);
           // new Thread(new MyThread()).start();

           // runnable.run();
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
            mGetCarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postGetCar(customerId, orderId);
                }
            });
            mFinishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mToPayRelativeLayout.setVisibility(View.VISIBLE);
                }
            });
        }
    }
    /**
     * 对图片显示的数据初始化
     */
    private void saveList() {
        if(orderPath!=null){
            String[] orderPaths = orderPath.split(",");
            for (int i = 0; i < orderPaths.length ; i++) {
                urls.add(orderPaths[i]);
            }
        }
        if(parkingPath!=null){
            if(parkingPath.contains(",")){
                parkingPath=parkingPath.replaceAll(",","");
                urls.add(parkingPath);
            }
        }
        for (int j = 0; j < 3; j++) {
            show.add(urls.get(j));
        }
        Log.d("saveList", urls.size() + "");
    }
    /**
     * 对图片的显示
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
    private void toRight(){
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
    protected void toPay(int payType,final String subject,final String desc,final String price) {
        PayManager.getInstance(getActivity()).toPay(this.getActivity(), payType, new PlaceOrderCallback(subject, desc, price) {
            @Override
            public String getOrderId() {
                //自定义订单号
                return "128978342715192";
            }
        }, new OnPayResultListener() {
            @Override
            public void onSuccess(String billingIndex, String msg) {
                showToast("订单:" + billingIndex + " " + msg);
            }

            @Override
            public void onFailuire(String billingIndex, String msg) {
                showToast("订单:" + billingIndex + " " + msg);
            }

            @Override
            public void onCancel(String billingIndex, String msg) {
                showToast("订单:" + billingIndex + " " + msg);
            }
        });
    }
    private void showPayMethodDialog() {
        String[] from = this.getResources().getStringArray(R.array.pay_method);
        final CommonDialog dialog = CommonDialog.creatDialog(this.getActivity());
        dialog.setTitle("支付");
        dialog.setListViewInfo(new ArrayAdapter<String>(app,
                        R.layout.common_dialog_textview, from),
                new CommonDialog.OnDialogItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        switch (position) {
                            case 0:
                                toPay(PayManager.PAY_TYPE_ALIPAY,"桃子","桃子一斤","0.02");
                                break;
                            case 1:
                                //交易金额默认为人民币交易，接口中参数支付金额单位为【分】，参数值不能带小数。对账单中的交易金额单位为【元】。
                                toPay(PayManager.PAY_TYPE_WECHAT,"桃子","桃子一斤","1");
                                break;
                            case 2:

                                break;
                        }
                        dialog.dismiss();
                    }
                });
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    /**
    * 时间计时
    * @param orderActualBegin
     * @param currentTime
     * @param format
    * */
    public void dateDiff(String orderActualBegin, String currentTime, String format) {
        //按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        try {
            //获得两个时间的毫秒时间差异
            diff = sd.parse(currentTime).getTime() - sd.parse(orderActualBegin).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        handler = new Handler();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (mCountdownTimeTextView == null){
                    mCountdownTimeTextView=(TextView) findViewById(R.id.tv_countdown_time4);
                }
                mCountdownTimeTextView.setText((String)msg.obj+"");
                return false;
            }
        });
        Runnable runnable = new Runnable() {
            long nh = 1000 * 60 * 60;//一小时的毫秒数
            long nm = 1000 * 60;//一分钟的毫秒数
            long ns = 1000;//一秒钟的毫秒数
            @Override
            public void run() {
                diff+=1000;
                hour = diff / nh;//计算差多少小时
                min = diff %  nh / nm;//计算差多少分钟
                sec = diff % nh % nm / ns;//计算差多少秒
                Log.e("sec",sec+"");
                Log.e("diff",diff+"");
                //String str=hour+":"+min+":"+sec;
                String str=String.format("%02d:%02d:%02d",hour,min,sec);
                Message message = handler.obtainMessage(1,str);
                handler.sendMessage(message);
                Log.e("str2",str);
                Log.e("str",str);
                handler.postDelayed(this, 1000);
            }
        };
        runnable.run();
    }

    @Override
    public void onDestroy() {
        //handler.removeCallbacks();
        super.onDestroy();
    }
}
