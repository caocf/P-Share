package com.azhuoinfo.pshare.fragment;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amap.api.services.help.Tip;
import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.ApiResult;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.api.task.ResultFactory;
import com.azhuoinfo.pshare.model.ChargeStandard;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.OrderInfo;
import com.azhuoinfo.pshare.model.Parking;
import com.azhuoinfo.pshare.model.UnfinishedOrderInfo;
import com.azhuoinfo.pshare.model.UserAuth;
import com.azhuoinfo.pshare.view.CountDownTextView;
import com.azhuoinfo.pshare.view.LoadingDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mobi.cangol.mobile.actionbar.ActionBarActivity;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.view.SearchView;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.http.extras.PollingHttpClient;
import mobi.cangol.mobile.http.extras.PollingResponseHandler;

/**
 * Created by Azhuo on 2015/9/22.
 * 周边车场详情
 */
public class ParkingDetailsItemFragment extends BaseContentFragment{
    //名称
    private TextView mParkingNameTextView;
    //地址
    private TextView mParkingAddressTextView;
    //距离
    private TextView mParkingDistanceTextView;
    //剩余车辆
    private TextView mParkingCanUseTextView;
    //价格
    private TextView mParkingPriceTextView;
    //时间
    private RelativeLayout mAppointmentTimeRelativeLayout;
    private TextView mAppointmentTimeTextView;
    ImageView mIV_Park;
    ImageView mIV_WashCar;
    CheckBox mCB_WashCar;
/*    private CheckBox mCheckBox0;
    private CheckBox mCheckBox1;
    private CheckBox mCheckBox2;
    private CheckBox mCheckBox3;
    private CheckBox mCheckBox4;*/
    /*//预约
    private RelativeLayout mAppointmentRelativeLayout;
    private TextView mAppointmentTextView;*/
    //代泊
    private LinearLayout mStopLinearLayout;
    //立即代泊
    private Button mImmediateButton;
    //预约代泊
    private Button mAppointmentButton;
    //取消代泊
    private Button mCancelButton;
    //接单倒计时
    private LinearLayout mOrderTextLinearLayout;
    private CountDownTextView mOrderCountDownTextView;
    private TextView mOrderTextView;

    private AccountVerify mAccountVerify;
    private Calendar calendar;
    private TimePickerDialog mTimeDialog;
    private int year,monthOfYear,dayOfMonth,hourOfDay,minute;
    private String customer_id;
    //预约代泊时间
    private String mTimeText;
    //立即代泊时间
    private String mImmediateTimeText;
    private String strAppointmentNeed="";
    private ArrayList<String> list=new ArrayList<String>();
    private List<CheckBox> checkBoxs=new ArrayList<CheckBox>();
    private CustomerInfo customerInfo;
    private int listSize;
    private String order_id;
    private int count=0;
    private Parking parking ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerInfo=(CustomerInfo)this.app.getSession().getSerializable("customerInfo");
        customer_id=customerInfo.getCustomer_Id().toString();
        mAccountVerify = AccountVerify.getInstance(getActivity());
        parking=this.getArguments().getParcelable("parking");
        customerInfo=(CustomerInfo)this.app.getSession().getSerializable("customerInfo");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_parkingdetailsitem,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initViews(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (pollingHttpClient!=null) {
            PollingUnfinishedOrder(customer_id);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (pollingHttpClient!=null) {
            pollingHttpClient.cancelRequests(getActivity(), true);
        }
    }

    @Override
    protected void findViews(View view) {
        mParkingNameTextView=(TextView) view.findViewById(R.id.tv_parking_name);
        mParkingAddressTextView=(TextView) view.findViewById(R.id.tv_parking_address);
        mParkingDistanceTextView=(TextView) view.findViewById(R.id.tv_parking_distance);
        mParkingCanUseTextView=(TextView) view.findViewById(R.id.tv_parking_can_use);
        mParkingPriceTextView=(TextView) view.findViewById(R.id.tv_parking_price);
        mIV_Park = (ImageView)view.findViewById(R.id.iv_list_head);
        mIV_WashCar = (ImageView)view.findViewById(R.id.iv_washcar);
        mCB_WashCar = (CheckBox)view.findViewById(R.id.cb_washcar);
        mAppointmentTimeRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_parking_appointment_time);
        mAppointmentTimeTextView=(TextView) view.findViewById(R.id.tv_parking_appointment_time);
        mStopLinearLayout=(LinearLayout) view.findViewById(R.id.ll_stop_button);
        mImmediateButton=(Button) view.findViewById(R.id.immediate_stop);
        mAppointmentButton=(Button) view.findViewById(R.id.appointment_stop);
        mCancelButton=(Button) view.findViewById(R.id.cancel_stop);

        mOrderTextLinearLayout=(LinearLayout) view.findViewById(R.id.ll_order_text);
        mOrderCountDownTextView=(CountDownTextView) view.findViewById(R.id.tv_order_text);
        mOrderTextView=(TextView) findViewById(R.id.text_order_text);
    }

    @Override
    protected void initViews(Bundle bundle){
        this.setTitle(parking.getParking_name());

        if (parking.getParking_path()!=null && !parking.getParking_path().isEmpty()) {
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(parking.getParking_path(), mIV_Park);
        }else {
            mIV_Park.setImageResource(R.drawable.iv_integration_freeparking_ticket);
        }

        mParkingNameTextView.setText("" + parking.getParking_name());
        mParkingAddressTextView.setText("" + parking.getParking_address());
        Log.e(TAG, "" + parking.getParking_can_use());
        if (parking.getParking_distance()!=null){
            mParkingDistanceTextView.setText(""+parking.getParking_distance());
        }else {
            mParkingDistanceTextView.setText("");
        }
        mParkingCanUseTextView.setText(""+parking.getParking_can_use());
        StringBuilder priceString = new StringBuilder();
        List<ChargeStandard> list = parking.getChargeStandard();
        if (list!=null && !list.isEmpty()){
            priceString.append("停车时长");

            priceString.append(String.format("0-%d小时(含):%d元", list.get(0).getCharge_time(), list.get(0).getCharge_unit()));

            for (int i = 0; i < list.size()-1; i++) {
                priceString.append(String.format("; %d-%d小时(含):%d元",
                        list.get(i).getCharge_time(),list.get(i+1).getCharge_time(), list.get(i+1).getCharge_unit()));
            }
        }else {
            priceString.append("价格未知");
        }

        //mParkingPriceTextView.setText(parking.getParking_charging_standard() + "");
        mParkingPriceTextView.setText(priceString.toString());
        mAppointmentTimeRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTime();
                mTimeDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String text = hourOfDay + ":" + minute;
                        mTimeText = year + "/" + (monthOfYear + 1) % 12 + "/" + dayOfMonth + " " + text;
                        Log.e(TAG, mTimeText);
                        if (hourOfDay < calendar.get(Calendar.HOUR_OF_DAY)) {
                            Toast.makeText(getActivity(), "时间已过,请重新选择", Toast.LENGTH_SHORT).show();
                        } else if (hourOfDay > calendar.get(Calendar.HOUR_OF_DAY) && minute < calendar.get(Calendar.MINUTE)) {
                            Toast.makeText(getActivity(), "时间已过,请重新选择", Toast.LENGTH_SHORT).show();
                        } else {
                            mAppointmentTimeTextView.setText(text);
                        }
                    }
                }, hourOfDay, minute, true);
                mTimeDialog.show();
            }
        });

        mCB_WashCar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    strAppointmentNeed="1";
                    mCB_WashCar.setTextColor(Color.rgb(0xff,0x66,0x03));
                    mIV_WashCar.setImageResource(R.drawable.xiche_select);
                }else {
                    mCB_WashCar.setTextColor(Color.BLACK);
                    strAppointmentNeed="0";
                    mIV_WashCar.setImageResource(R.drawable.xiche);
                }
            }
        });
        mImmediateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTime();
                if (listSize > 0) {
                    Toast.makeText(getActivity(), "已有订单", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("mImmediateTimeText", mImmediateTimeText + "");
                    postCreateOrder(customer_id, parking.getParking_id(), mImmediateTimeText, strAppointmentNeed);
                }
            }
        });
        mAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //postUnfinishedOrder(customer_id);
                if (mTimeText == null) {
                    Toast.makeText(getActivity(), "时间不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (listSize > 0) {
                        Toast.makeText(getActivity(), "已有订单", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("mTimeText", mTimeText + "");
                        postCreateOrder(customer_id, parking.getParking_id(), mTimeText, strAppointmentNeed);
                    }
                }
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //postUnfinishedOrder(customer_id);
                postCancelOrder(order_id);
            }
        });
    }
    @Override
    protected void initData(Bundle bundle) {
        postUnfinishedOrder(customer_id);

        updateHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String order_state = (String)msg.obj;
                if (order_state != null ){
                    if (!order_state.equals("1")){
                        showToast("代泊员已接单");
                        popBackStack();
                    }
                }
                return false;
            }
        });

    }
    @Override
    protected FragmentInfo getNavigtionUpToFragment(){
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return false;
    }

    void setButtonEnable(boolean enable) {
        if (!enable) {
            mImmediateButton.setEnabled(false);
            mImmediateButton.setBackgroundResource(R.drawable.button_false);
            mAppointmentButton.setEnabled(false);
            mAppointmentButton.setBackgroundResource(R.drawable.button_false);
        } else {
            mImmediateButton.setEnabled(true);
            mImmediateButton.setBackgroundResource(R.drawable.button);
            mAppointmentButton.setEnabled(true);
            mAppointmentButton.setBackgroundResource(R.drawable.button);
            mOrderTextView.setText("");
            mOrderTextView.setText("");
        }
    }

    public void postUnfinishedOrder(String customerId){

        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_UNFINISHEDORDER));
        apiTask.setParams(ApiContants.instance(getActivity()).unFinishedOrder(customerId));
        apiTask.setRoot("orderInfo");
        apiTask.execute(new OnDataLoader<List<UnfinishedOrderInfo>>() {
            LoadingDialog loadingDialog;
            @Override
            public void onStart(){
                if (isEnable()) {
                    loadingDialog = LoadingDialog.show(getActivity());
                }
            }
            @Override
            public void onSuccess(List<UnfinishedOrderInfo> unfinishedOrderInfos) {
                if (isEnable()) {
                    Log.e(TAG, unfinishedOrderInfos.size() + "");
                    listSize = unfinishedOrderInfos.size();
                    getSession().put("unfinishedOrderInfos", unfinishedOrderInfos);


                    if (listSize > 0) {
                        setButtonEnable(false);
                    } else {
                        setButtonEnable(true);
                    }
                    loadingDialog.dismiss();
                }
            }
            @Override
            public void onFailure(String code, String message) {
                if (isEnable()) {
                    loadingDialog.dismiss();
                }
            }
        });
    }

    public void postCreateOrder(final String customerId,String parkingId,String orderPlanBegin,String order_img_count) {
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_CREATEORDER));
        apiTask.setParams(ApiContants.instance(getActivity()).userCreateOrder(customerId, parkingId, orderPlanBegin, order_img_count));
        apiTask.setRoot("orderInfo");
        apiTask.execute(new OnDataLoader<OrderInfo>(){
            LoadingDialog loadingDialog;
            @Override
            public void onStart() {
                if (isEnable()) {
                    loadingDialog = LoadingDialog.show(getActivity());
                }
            }
            @Override
            public void onSuccess(OrderInfo orderInfos) {
                if (isEnable()) {
                    mStopLinearLayout.setVisibility(View.GONE);
                    mCancelButton.setVisibility(View.VISIBLE);
                    mOrderTextLinearLayout.setVisibility(View.VISIBLE);
                    order_id = orderInfos.getOrder_id();
                    mOrderTextView.setText("正在为你搭配代泊员...");
                    mOrderCountDownTextView.starTimeByMillisInFuture(3 * 60 * 1000);
                    loadingDialog.dismiss();
                    PollingUnfinishedOrder(customerId);

                    mCB_WashCar.setClickable(false);
                    mAppointmentTimeRelativeLayout.setClickable(false);

                    mOrderCountDownTextView.setOnCountDownListener(new CountDownTextView.OnCountDownListener() {
                        @Override
                        public void onFinish() {
                            pollingHttpClient.cancelRequests(getActivity(), true);
                            postCancelOrder(order_id);
                            mOrderTextView.setText("代泊员正忙，未接单，订单取消");
                            mOrderCountDownTextView.setText("");


                        }
                    });
                }
            }
            @Override
            public void onFailure(String code, String message) {
                if (isEnable()) {
                    Log.e(TAG, "请求数据失败");
                    loadingDialog.dismiss();
                }
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
                if (isEnable()) {
                    loadingDialog = LoadingDialog.show(getActivity());
                }
            }
            @Override
            public void onSuccess(UserAuth auth) {
                if (isEnable()) {
                    listSize = 0;
                    mStopLinearLayout.setVisibility(View.VISIBLE);
                    mCancelButton.setVisibility(View.GONE);
                    mOrderTextLinearLayout.setVisibility(View.GONE);

                    setButtonEnable(true);
                    mCB_WashCar.setClickable(true);
                    mAppointmentTimeRelativeLayout.setClickable(true);
                    loadingDialog.dismiss();
                }
            }
            @Override
            public void onFailure(String code, String message) {
                if (isEnable()) {
                    mobi.cangol.mobile.logging.Log.d(TAG, "code=:" + code + ",message=" + message);
                    loadingDialog.dismiss();
                }
            }
        });
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
    class PollingUnfinishedOrderHandler extends PollingResponseHandler {

        @Override
        public void onStart() {
            Log.d("ResponseHandler", "onStart");
        }

        @Override
        public boolean isFailResponse(String content) {
            if (isEnable()) {
                Log.d("ResponseHandler", "isFailResponse content=" + content);
                try {
                    ApiResult<UnfinishedOrderInfo> apiResult = (ApiResult<UnfinishedOrderInfo>) ResultFactory.getInstance().parserResult(UnfinishedOrderInfo.class, new JSONObject(content), "orderInfo");
                    List<UnfinishedOrderInfo> unfinishedOrderInfos = apiResult.getList();
                    if (isEnable()) {
                        listSize = unfinishedOrderInfos.size();
                        if (listSize != 0) {
                            for (int i = 0; i < unfinishedOrderInfos.size(); i++) {
                                UnfinishedOrderInfo unfinishedOrderInfo = unfinishedOrderInfos.get(i);
                                Message message = updateHandler.obtainMessage(1, unfinishedOrderInfo.getOrder_state());
                                updateHandler.sendMessage(message);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    @Override
    protected boolean onMenuActionCreated(ActionMenu actionMenu) {
        super.onMenuActionCreated(actionMenu);
        actionMenu.addMenu(1, R.string.action_menu_shared, -1, 1);
        actionMenu.addMenu(2, R.string.action_menu_navigation,-1, 1);
        return true;
    }

    @Override
    protected boolean onMenuActionSelected(ActionMenuItem action) {
        switch(action.getId()){
            case 1:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            case 2:
                popupNavi(getCustomActionBar().getActionMenu().getActionMenuItemView(getCustomActionBar().getActionMenu().size()));
        }
        return super.onMenuActionSelected(action);
    }

    /**
    * 取当前时间
    * */
    public void currentTime(){
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        mImmediateTimeText = year + "/" + (monthOfYear + 1) % 12 + "/" + dayOfMonth + " " + hourOfDay + ":" + minute;
    }

    void popupNavi(View view){
        PopupMenu popupMenu = new PopupMenu(getActivity(),view);
        popupMenu.inflate(R.menu.navi_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_autonavi:
                        if (isAppInstalled(getActivity(), "com.autonavi.minimap")) {
                            shareToAutoNavi();
                        } else {
                            showToast("高德地图未安装");
                        }
                        break;
                    case R.id.action_baidunavi:
                        if (isAppInstalled(getActivity(), "com.baidu.BaiduMap")) {
                            shareToBaidu();
                        } else {
                            showToast("百度地图未安装");
                        }
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }
    void shareToAutoNavi(){
        String uriString = String.format(
                "androidamap://navi?sourceApplication=%s&poiname=%s&lat=%s&lon=%s&dev=1&style=2",
                "口袋停",
                parking.getParking_name(),
                parking.getParking_latitude(),
                parking.getParking_longitude()
        );
        try {
            Intent intent = Intent.parseUri(uriString,0);
            intent.setPackage("com.autonavi.minimap");
            startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    void shareToBaidu(){
        String uriString = String.format(
                "intent://map/direction?origin=latlng:%s,%s|name:%s&destination=%s&mode=driving&src=口袋停|口袋停#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end",
                parking.getParking_latitude(),
                parking.getParking_longitude(),
                "我的位置",
                parking.getParking_name()
        );

        try {
            Intent intent = Intent.parseUri(uriString,0);
            startActivity(intent); //启动调用
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private boolean isAppInstalled(Context context,String packagename)
    {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        }catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if(packageInfo ==null){
            //System.out.println("没有安装");
            return false;
        }else{
            //System.out.println("已经安装");
            return true;
        }
    }

}