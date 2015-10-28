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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
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
import com.azhuoinfo.pshare.api.task.ResultFactory;
import com.azhuoinfo.pshare.fragment.adapter.ImageAdapter;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.OrderPay;
import com.azhuoinfo.pshare.model.UnfinishedOrderInfo;
import com.azhuoinfo.pshare.model.UserAuth;
import com.azhuoinfo.pshare.model.comment;
import com.azhuoinfo.pshare.utils.Constants;
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
import java.util.TimerTask;

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
    private String parking_id;
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
        customerInfo=(CustomerInfo)this.getSession().getSerializable("customerInfo");
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

        mOrder1RelativeLayout=(RelativeLayout) findViewById(R.id.fg_order1);
        mOrder2RelativeLayout=(RelativeLayout) findViewById(R.id.fg_order2);
        mOrder3ScrollView=(ScrollView) findViewById(R.id.fg_order3);
        mOrder4ScrollView=(ScrollView) findViewById(R.id.fg_order4);
        mToPayRelativeLayout=(RelativeLayout) findViewById(R.id.fg_list_menu_pay_pager);
    }

    LoadingDialog loadingDialog;
    @Override
    protected void initData(Bundle bundle) {
       /* handler.postDelayed(runnable, 2000);//每两秒执行一次runnable.*/
        //postUnfinishedOrder(customerId);
        //initOrderState();
        loadingDialog = LoadingDialog.show(getActivity());
        updateHandler = new Handler(new Handler.Callback() {
            String last_order_state = "";
            @Override
            public boolean handleMessage(Message msg) {
                if (order_state == null || listSize == 0){
                    initOrderState();
                }else {
                    Log.d("updateHandler", "receive order_state " + last_order_state + " " + order_state);
                    if (!last_order_state.equals(order_state)) {
                        Log.d("updateHandler", "update order_state " + last_order_state + " " + order_state);
                        initOrderState();
                        Log.d("order_string", "last_order " + last_order_state);
                        Log.d("order_string", "order " + order_state);
                        last_order_state = new String(order_state);
                        if (order_state.equals("3") || order_state.equals("4")) {
                            dateDiff(orderActualBegin, (String) msg.obj, "yyyy-MM-dd HH:mm:ss");
                        }
                    }
                }
                if (loadingDialog.isShowing()){
                    loadingDialog.dismiss();
                }
                return false;
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        PollingUnfinishedOrder(customerId);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (pollingHttpClient!=null) {
            pollingHttpClient.cancelRequests(getActivity(), false);
        }
        if (timer2!=null) {
            timer2.cancel();
            timer2.purge();
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
    public void initOrderState(){
        if (
        mOrder2RelativeLayout == null||
        mOrder3ScrollView == null||
        mOrder4ScrollView == null||
        mToPayRelativeLayout == null||
        mOrder1RelativeLayout == null
                )
            return;
        if(listSize==0||order_state.equals("6")|| order_state == null || order_state.isEmpty()) {
            Log.e("mOrder1RelativeLayout", listSize+"");

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
        }else if(listSize>0&&order_state.equals("2")) {
            Log.e("mOrder3ScrollView", order_state + "");
            mOrder1RelativeLayout.setVisibility(View.GONE);
            mOrder2RelativeLayout.setVisibility(View.GONE);
            mOrder4ScrollView.setVisibility(View.GONE);
            mOrder3ScrollView.setVisibility(View.VISIBLE);
            initViewsOrder3();
        }else if((listSize>0)&&(order_state.equals("3")||order_state.equals("4")||order_state.equals("8")|| order_state.equals("9"))) {
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

    Handler updateHandler;

    PollingHttpClient pollingHttpClient;
    public void PollingUnfinishedOrder(final String customerId){
        pollingHttpClient = new PollingHttpClient();
        PollingUnfinishedOrderHandler pollingUnfinishedOrderHandler = new PollingUnfinishedOrderHandler();
        pollingHttpClient.send(
                getActivity(),
                ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_UNFINISHEDORDER),
                ApiContants.instance(getActivity()).unFinishedOrder(customerId),
                pollingUnfinishedOrderHandler,
                18, 10000);
    }
    class PollingUnfinishedOrderHandler extends PollingResponseHandler{

        @Override
        public void onStart() {
            Log.d("ResponseHandler", "onStart");
        }

        @Override
        public boolean isFailResponse(String content) {
            Log.d("ResponseHandler", "isFailResponse content=" + content);
            try {
                ApiResult<UnfinishedOrderInfo> apiResult = (ApiResult<UnfinishedOrderInfo>) ResultFactory.getInstance().parserResult(UnfinishedOrderInfo.class, new JSONObject(content), "orderInfo");
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
                            parking_id = unfinishedOrderInfo.getParking_id();
                            orderPlanBegin = unfinishedOrderInfo.getOrder_plan_begin();
                            orderPlanEnd = unfinishedOrderInfo.getOrder_plan_end();
                            carNumber = unfinishedOrderInfo.getCar_number();
                            orderPath = unfinishedOrderInfo.getOrder_path();
                            parkingPath=unfinishedOrderInfo.getParking_path();
                            orderActualBegin=unfinishedOrderInfo.getOrder_actual_begin_start();
                            Log.e(TAG, "order_state is " + order_state);
                        }
                    }

                    Message message = updateHandler.obtainMessage(1,apiResult.getTimestamp());
                    updateHandler.sendMessage(message);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        public void onPollingFinish(int execTimes, String content) {
            Log.d("ResponseHandler", "execTimes=" + execTimes + " content:" + content);
        }

        @Override
        public void onSuccess(int statusCode, String content) {
            Log.d("ResponseHandler", "statusCode=" + statusCode + " content:" + content);
        }

        @Override
        public void onFailure(Throwable error, String content) {
            Log.d("ResponseHandler", "error=" + error + " content:" + content);
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
            public void onSuccess(List<UnfinishedOrderInfo> unfinishedOrderInfos) {
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
                            parking_id = unfinishedOrderInfo.getParking_id();
                            orderPlanBegin = unfinishedOrderInfo.getOrder_plan_begin();
                            orderPlanEnd = unfinishedOrderInfo.getOrder_plan_end();
                            carNumber = unfinishedOrderInfo.getCar_number();
                            orderPath = unfinishedOrderInfo.getOrder_path();
                            parkingPath=unfinishedOrderInfo.getParking_path();
                            orderActualBegin=unfinishedOrderInfo.getOrder_actual_begin_start();
                            Log.e(TAG, order_state);
                            if (order_state.equals("3") || order_state.equals("4")) {
                                dateDiff(orderActualBegin, ((ApiResult)this.getResult()).getTimestamp(), "yyyy-MM-dd HH:mm:ss");
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
            public void onSuccess(UserAuth auth) {
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
            public void onSuccess(UserAuth userAuth) {
                mFinishButton.setVisibility(View.VISIBLE);
                mGetCarButton.setVisibility(View.GONE);
/*                mFinishButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPayMethodDialog();
                    }
                });*/
            }

            @Override
            public void onFailure(String code, String message) {

            }
        });
    }


    String totalPay;
    public void postGetcalculatePay(final String order_id,String parking_id){
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_CALCULATEPAY));
        apiTask.setParams(ApiContants.instance(getActivity()).getCalculatePay(order_id, parking_id));
        apiTask.execute(new OnDataLoader<OrderPay>() {
            LoadingDialog loadingDialog;
            @Override
            public void onStart() {
                loadingDialog = LoadingDialog.show(getActivity());
            }

            @Override
            public void onSuccess(OrderPay orderPay) {
                totalPay = orderPay.toTalPay();
                showPayMethodDialog();
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(String code, String message) {
                loadingDialog.dismiss();
            }
        });
    }


    public void postAssessment(String order_id, String comment_operater_id,  String comment_level,String comment_content){
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_CALCULATEPAY));
        apiTask.setParams(ApiContants.instance(getActivity()).getComment(order_id, comment_operater_id, comment_level, comment_content));
        apiTask.execute(new OnDataLoader<comment>() {
            LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = LoadingDialog.show(getActivity());
            }

            @Override
            public void onSuccess(comment comment) {
                showToast("评论发送成功");
                commentDialog.dismiss();
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(String code, String message) {
                loadingDialog.dismiss();
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
        if (order_state.equals("3") || order_state.equals("4") || order_state.equals("8")|| order_state.equals("9")){
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

            mFinishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mToPayRelativeLayout.setVisibility(View.VISIBLE);
                }
            });

            if (order_state.equals("3")){
                mFinishButton.setVisibility(View.GONE);
                mGetCarButton.setVisibility(View.INVISIBLE);
            }else if (order_state.equals("4")){
                mFinishButton.setVisibility(View.GONE);
                mGetCarButton.setVisibility(View.VISIBLE);
            } if (order_state.equals("8")||order_state.equals("9")){
                if(order_state.equals("8")){
                    mFinishButton.setVisibility(View.VISIBLE);
                    mFinishButton.setEnabled(false);
                    mFinishButton.setBackgroundResource(R.drawable.button_false);
                    mGetCarButton.setVisibility(View.GONE);

                }else {
                    mFinishButton.setVisibility(View.VISIBLE);
                    mFinishButton.setEnabled(true);
                    mFinishButton.setBackgroundResource(R.drawable.button);
                    mGetCarButton.setVisibility(View.GONE);
                }
                mFinishButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postGetcalculatePay(orderId, parking_id);
                        //showPayMethodDialog();
                        //mToPayRelativeLayout.setVisibility(View.VISIBLE);
                    }
                });
            }
            mGetCarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postGetCar(customerId, orderId);
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
    protected void toPay(int payType,final String subject,final String desc,final String price,String notify_url) {
        PayManager.getInstance(getActivity()).toPay(this.getActivity(), payType, new PlaceOrderCallback(subject, desc, price,notify_url) {
            @Override
            public String getOrderId() {
                //自定义订单号
                return orderId;
            }
        }, new OnPayResultListener() {
            @Override
            public void onSuccess(String billingIndex, String msg) {
                showToast("订单:" + billingIndex + " " + msg);
                payDialog.dismiss();
                showAssessmentDialog();
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

    CommonDialog commentDialog;
    private void showAssessmentDialog() {
        commentDialog = CommonDialog.creatDialog(this.getActivity());
        commentDialog.setContentView(R.layout.assessment_pager);
        final RadioGroup radioGroup = (RadioGroup)commentDialog.findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.as_1) {
                    Log.e("as", "as_1");
                } else if (checkedId == R.id.as_2) {
                    Log.e("as", "as_2");
                } else if (checkedId == R.id.as_3) {
                    Log.e("as", "as_3");
                }
            }
        });

        Button payButton = (Button)commentDialog.findViewById(R.id.btn_pay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = radioGroup.getCheckedRadioButtonId();
                int level = 1;
                if (checkedId == R.id.as_1) {
                    Log.e("OnButtonClick", "as1");
                    level = 1;
                } else if (checkedId == R.id.as_2) {
                    Log.e("OnButtonClick", "as2");
                    level = 2;
                } else if (checkedId == R.id.as_3) {
                    Log.e("OnButtonClick", "as3");
                    level = 3;
                }
                EditText et = (EditText)commentDialog.findViewById(R.id.comment_content);
                postAssessment(orderId,customerId,""+level,et.getText().toString());
            }
        });

        ImageView imageView = (ImageView)commentDialog.findViewById(R.id.iv_close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentDialog.dismiss();
            }
        });
        commentDialog.setCanceledOnTouchOutside(true);
        commentDialog.show();
    }


    CommonDialog payDialog;
    private void showPayMethodDialog() {
        payDialog = CommonDialog.creatDialog(this.getActivity());
        payDialog.setContentView(R.layout.list_menu_pay_pager);
        final RadioGroup radioGroup = (RadioGroup)payDialog.findViewById(R.id.rg);

        TextView tv_money;
        tv_money = (TextView)payDialog.findViewById(R.id.tv_money);
        tv_money.setText(String.format("%.2f",Double.parseDouble(totalPay)));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.alipay) {
                    Log.e("onCheck", "alipay");
                } else if (checkedId == R.id.wechatpay) {
                    Log.e("onCheck", "wechatpay");
                }
            }
        });

        Button payButton = (Button)payDialog.findViewById(R.id.btn_pay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String paySubject = "口袋停-停车缴费";
                final String payDesc = "停车缴费";
                int checkedId = radioGroup.getCheckedRadioButtonId();
                if (checkedId == R.id.alipay) {
                    Log.e("OnButtonClick", "alipay");
                    //toPay(PayManager.PAY_TYPE_ALIPAY, paySubject, payDesc, totalPay);
                    toPay(PayManager.PAY_TYPE_ALIPAY, paySubject, payDesc, "0.01",Constants.ALIPAY_NOTIFY_URL);
                } else if (checkedId == R.id.wechatpay) {
                    Log.e("OnButtonClick", "wechatpay");
                    int fee = (int)Double.parseDouble(totalPay) * 100;
                    fee = 1;
                    toPay(PayManager.PAY_TYPE_WECHAT, paySubject, payDesc, "" + fee,Constants.WECHAT_NOTIFY_URL);
                }
            }
        });

        ImageView imageView = (ImageView)payDialog.findViewById(R.id.iv_close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog.dismiss();
            }
        });
        payDialog.setCanceledOnTouchOutside(true);
        payDialog.show();
    }

    /**
    * 时间计时
    * @param orderActualBegin
     * @param currentTime
     * @param format
    * */
    Thread timeThread;
    boolean isStopTimer = false;
    Timer timer2;
    public void dateDiff(String orderActualBegin, String currentTime, String format) {
        timer2 = new Timer();

        //按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        try {
            //获得两个时间的毫秒时间差异
            diff = sd.parse(currentTime).getTime() - sd.parse(orderActualBegin).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

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


        timer2.schedule(new TimerTask() {

            final long nh = 1000 * 60 * 60;//一小时的毫秒数
            final long nm = 1000 * 60;//一分钟的毫秒数
            final long ns = 1000;//一秒钟的毫秒数
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
                Log.e("str", str);
            }
        },0,1000);
    }

    @Override
    public void onDestroy() {
        //handler.removeCallbacks();
        super.onDestroy();
    }
}
