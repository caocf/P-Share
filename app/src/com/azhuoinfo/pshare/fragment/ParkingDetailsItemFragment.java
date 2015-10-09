package com.azhuoinfo.pshare.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

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
    //预约
    private RelativeLayout mAppointmentRelativeLayout;
    private TextView mAppointmentTextView;

    private LinearLayout mOrderTextLinearLayout;

    private AccountVerify mAccountVerify;
    private Calendar calendar;
    private TimePickerDialog mTimeDialog;
    private int year,monthOfYear,dayOfMonth,hourOfDay,minute;
    private String customer_id;
    private String mTimeText;
    private ArrayList<String> list=new ArrayList<String>();
    private CustomerInfo customerInfo;
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

        mAppointmentRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_appointment);
        mAppointmentTextView=(TextView) view.findViewById(R.id.tv_appointment);

        mOrderTextLinearLayout=(LinearLayout) view.findViewById(R.id.ll_order_text);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.yard_details);
        mAppointmentTimeRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                monthOfYear = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);
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
        mAppointmentRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count % 2 == 1) {
                    if (mTimeText == null) {
                        Toast.makeText(getActivity(), "时间不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        postCreateOrder(customer_id, "3", mTimeText);
                    }
                } else {
                    mAppointmentTextView.setText(R.string.appointment);
                    mOrderTextLinearLayout.setVisibility(View.INVISIBLE);
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
        return false;
    }
    public void postUnfinishedOrder(String customerId){
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_UNFINISHEDORDER));
        apiTask.setParams(ApiContants.instance(getActivity()).unFinishedOrder(customerId));
        //apiTask.setRoot("orderInfo");
        apiTask.execute(new OnDataLoader<UnfinishedOrderInfo>() {
            @Override
            public void onStart() {

            }
            @Override
            public void onSuccess(boolean page, UnfinishedOrderInfo unfinishedOrderInfo) {
                Log.e(TAG,unfinishedOrderInfo+"");
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
        apiTask.setParams(ApiContants.instance(getActivity()).userCreateOrder(customerId, parkingId,orderPlanBegin));
        apiTask.setRoot("orderInfo");
        apiTask.execute(new OnDataLoader<OrderInfo>(){
            @Override
            public void onStart() {

            }
            @Override
            public void onSuccess(boolean page, OrderInfo orderInfos) {
                mAppointmentTextView.setText(R.string.tv_unselect_order);
                mOrderTextLinearLayout.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(String code, String message) {
                Log.e(TAG,"请求数据失败");
            }
        });
    }
}