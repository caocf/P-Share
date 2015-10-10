package com.azhuoinfo.pshare.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.fragment.adapter.ParkingDetailsAdapter;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.OrderInfo;
import com.azhuoinfo.pshare.model.UnfinishedOrderInfo;
import com.azhuoinfo.pshare.model.UserAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mobi.cangol.mobile.Session;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
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

    private LinearLayout mOrderTextLinearLayout;

    private AccountVerify mAccountVerify;
    private Calendar calendar;
    private TimePickerDialog mTimeDialog;
    private int year,monthOfYear,dayOfMonth,hourOfDay,minute;
    private String customer_id;
    //预约代泊时间
    private String mTimeText;
    //立即代泊时间
    private String mImmediateTimeText;
    private ArrayList<String> list=new ArrayList<String>();
    private CustomerInfo customerInfo;
    private int listSize;
    private String order_sn;
    private int count=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerInfo=(CustomerInfo)this.app.getSession().get("customerInfo");
        customer_id=customerInfo.getCustomer_Id().toString();
        mAccountVerify = AccountVerify.getInstance(getActivity());
        customerInfo=(CustomerInfo)this.app.getSession().get("customerInfo");
        //while (true){
            postUnfinishedOrder(customer_id);
        //}
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    protected void findViews(View view) {
        mParkingNameTextView=(TextView) view.findViewById(R.id.tv_parking_name);
        mParkingAddressTextView=(TextView) view.findViewById(R.id.tv_parking_address);
        mParkingDistanceTextView=(TextView) view.findViewById(R.id.tv_parking_distance);
        mParkingCanUseTextView=(TextView) view.findViewById(R.id.tv_parking_can_use);
        mParkingPriceTextView=(TextView) view.findViewById(R.id.tv_parking_price);

        mAppointmentTimeRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_parking_appointment_time);
        mAppointmentTimeTextView=(TextView) view.findViewById(R.id.tv_parking_appointment_time);

        mStopLinearLayout=(LinearLayout) view.findViewById(R.id.ll_stop_button);
        mImmediateButton=(Button) view.findViewById(R.id.immediate_stop);
        mAppointmentButton=(Button) view.findViewById(R.id.appointment_stop);
        mCancelButton=(Button) view.findViewById(R.id.cancel_stop);

        /*mAppointmentRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_appointment);
        mAppointmentTextView=(TextView) view.findViewById(R.id.tv_appointment);*/

        mOrderTextLinearLayout=(LinearLayout) view.findViewById(R.id.ll_order_text);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.yard_details);
        if(listSize>0){
            mStopLinearLayout.setVisibility(View.GONE);
            mCancelButton.setVisibility(View.VISIBLE);
        }else{
            mStopLinearLayout.setVisibility(View.VISIBLE);
            mCancelButton.setVisibility(View.GONE);
        }
        mAppointmentTimeRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                monthOfYear = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);
                mImmediateTimeText = year + "/" + (monthOfYear + 1) % 12 + "/" + dayOfMonth + " " + hourOfDay + ":" + minute;
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
        mImmediateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listSize > 0) {
                    Toast.makeText(getActivity(), "已有订单", Toast.LENGTH_SHORT).show();
                } else {
                    postCreateOrder(customer_id, "3", mImmediateTimeText);
                }
            }
        });
        mAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimeText == null) {
                    Toast.makeText(getActivity(), "时间不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (listSize > 0) {
                        Toast.makeText(getActivity(), "已有订单", Toast.LENGTH_SHORT).show();
                    } else {
                        postCreateOrder(customer_id, "3", mTimeText);
                    }
                }
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCancelOrder(customer_id, order_sn);
            }
        });
    }
    @Override
    protected void initData(Bundle bundle) {

    }
    @Override
    protected FragmentInfo getNavigtionUpToFragment(){
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return false;
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
                Log.e(TAG,unfinishedOrderInfos.size()+"");
                listSize=unfinishedOrderInfos.size();
                Session session=getSession();
                session.put("unfinishedOrderInfos",unfinishedOrderInfos);
                /*for(int i=0;i<unfinishedOrderInfos.size();i++){

                }*/
            }
            @Override
            public void onFailure(String code, String message) {

            }
        });
    }
    public void postCreateOrder(String customerId,String parkingId,String orderPlanBegin) {
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_CREATEORDER));
        apiTask.setParams(ApiContants.instance(getActivity()).userCreateOrder(customerId, parkingId, orderPlanBegin));
        apiTask.setRoot("orderInfo");
        apiTask.execute(new OnDataLoader<OrderInfo>(){
            @Override
            public void onStart(){

            }
            @Override
            public void onSuccess(boolean page, OrderInfo orderInfos) {
                order_sn=orderInfos.getOrder_sn();
                mStopLinearLayout.setVisibility(View.GONE);
                mCancelButton.setVisibility(View.VISIBLE);
                mOrderTextLinearLayout.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(String code, String message) {
                Log.e(TAG,"请求数据失败");
            }
        });
    }
    public void postCancelOrder(String customerId,String orderSn){
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_CANCELORDER));
        apiTask.setParams(ApiContants.instance(getActivity()).userCancelOrder(customerId, orderSn));
        apiTask.execute(new OnDataLoader<UserAuth>() {
            @Override
            public void onStart() {
                if (getActivity() != null){
                }
            }
            @Override
            public void onSuccess(boolean page, UserAuth auth) {

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