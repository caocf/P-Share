package com.azhuoinfo.pshare.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.ApiResult2;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.model.CarList;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.Parking;
import com.azhuoinfo.pshare.model.Success;
import com.azhuoinfo.pshare.model.UnitPrice;
import com.azhuoinfo.pshare.view.CountryDialog;
import com.azhuoinfo.pshare.view.LoadingDialog;
import com.azhuoinfo.pshare.view.SingleAreaDialog;
import com.azhuoinfo.pshare.view.SingleWheelDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 * 缴费模块产权/月租，添加产权月租
 */
public class AddMonthlyRentCarFragment extends BaseContentFragment {
    //返回上一个页面
    //private Button mBackButton;

    //缴费类型
    private TextView mPayTypeTextView;
    //设置国省市
    private RelativeLayout mCPCRelativeLayout;
    //国
    private TextView mCountryTextView;
    //省
    private TextView mProvinceTextView;
    //市
    private TextView mCityTextView;
    //设置区
    private RelativeLayout mAreaRelativeLayout;
    private TextView mAreaTextView;
    //设置小区
    private RelativeLayout mLivingAreaRelativeLayout;
    private TextView mLivingAreaTextView;
    //时间段
    private RelativeLayout mTimeFrameRelativeLayout;
    private TextView mTimeFrameTextView;
    //开始时间
    private RelativeLayout mMonthlyrentBegintTimeRelativeLayout;
    private TextView mMonthlyrentBegintTimeTextView;
    //结束时间
    private TextView mMonthlyrentEndTimeTextView;
    //单价
    private TextView mMonthlyrentPriceTextView;
    //姓名
    private EditText mCustomerNameEditText;
    //车牌
    private RelativeLayout mCarNumberRelativeLayout;
    private TextView mCarNumberTextView;
    //确定添加
    private Button mConfirmButton;
    private AccountVerify mAccountVerify;
    protected String carNumber;

    protected String mCurrentProviceName;
    protected String mCurrentCityName;
    protected String mCurrentDistrictName = "";
    protected String mCurrentZipCode = "";
    private CustomerInfo customerInfo;

    private String customerId;

    public AddMonthlyRentCarFragment() {
        super();
        setArguments(new Bundle());
    }

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
        return inflater.inflate(R.layout.fragment_add_monthlyrent_car, container, false);
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
        //mBackButton=(Button) view.findViewById(R.id.activity_back);
        mPayTypeTextView = (TextView)view.findViewById(R.id.tv_type);
        mCPCRelativeLayout = (RelativeLayout) view.findViewById(R.id.rl_CPC);
        mCountryTextView = (TextView) view.findViewById(R.id.tv_country);
        mProvinceTextView = (TextView) view.findViewById(R.id.tv_province);
        mCityTextView = (TextView) view.findViewById(R.id.tv_city);

        mAreaRelativeLayout = (RelativeLayout) view.findViewById(R.id.rl_area);
        mAreaTextView = (TextView) view.findViewById(R.id.tv_area);

        mLivingAreaRelativeLayout = (RelativeLayout) view.findViewById(R.id.rl_living_area);
        mLivingAreaTextView = (TextView) view.findViewById(R.id.tv_living_area);

        mTimeFrameRelativeLayout = (RelativeLayout) view.findViewById(R.id.rl_time_frame);
        mTimeFrameTextView = (TextView) view.findViewById(R.id.tv_time_frame);

        mMonthlyrentBegintTimeRelativeLayout = (RelativeLayout) view.findViewById(R.id.rl_monthlyrent_begint_time);
        mMonthlyrentBegintTimeTextView = (TextView) view.findViewById(R.id.tv_monthlyrent_begint_time);

        mMonthlyrentEndTimeTextView = (TextView) view.findViewById(R.id.tv_monthlyrent_end_ime);

        mMonthlyrentPriceTextView = (TextView) findViewById(R.id.tv_monthlyrent_price);

        mCustomerNameEditText = (EditText) view.findViewById(R.id.et_customer_name);

        mCarNumberRelativeLayout = (RelativeLayout) view.findViewById(R.id.rl_car_number);
        mCarNumberTextView = (TextView) view.findViewById(R.id.tv_car_number);

        mConfirmButton = (Button) findViewById(R.id.button_confirm);

    }

    protected String mCurrentCountry;

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.add_rent);
    }

    protected String mTimeFrame;
    protected String mBeginTime;
    protected String mEndTime;

    @Override
    protected void initData(Bundle bundle) {

        Bundle ArgBundle = getArguments();
        if (!ArgBundle.isEmpty() ){
            carNumber = ArgBundle.getString("carNumber");
            mCurrentCountry = ArgBundle.getString("mCurrentCountry");
            mCurrentProviceName = ArgBundle.getString("mCurrentProviceName");
            mCurrentCityName = ArgBundle.getString("mCurrentCityName");
            mCurrentDistrictName = ArgBundle.getString("mCurrentDistrictName");
            mCurrentZipCode = ArgBundle.getString("mCurrentZipCode");
            mParkingId = ArgBundle.getString("mParkingId");
            mParking_name = ArgBundle.getString("mParking_name");
            carNumber = ArgBundle.getString("carNumber");
            mTimeFrame = ArgBundle.getString("mTimeFrame");
            mBeginTime = ArgBundle.getString("mBeginTime");
            mEndTime = ArgBundle.getString("mEndTime");
            mOrderType = ArgBundle.getString("mOrderType");
            mPrice = ArgBundle.getString("mPrice");

            mCountryTextView.setText(mCurrentCountry);
            mProvinceTextView.setText(mCurrentProviceName);
            mCityTextView.setText(mCurrentCityName);
            mAreaTextView.setText(mCurrentDistrictName);
            mCarNumberTextView.setText(carNumber);
            mLivingAreaTextView.setText(mParking_name);

            mTimeFrameTextView.setText(mTimeFrame);
            mMonthlyrentBegintTimeTextView.setText(mBeginTime);
            mMonthlyrentEndTimeTextView.setText(mEndTime);

            mMonthlyrentPriceTextView.setText(mPrice);
            mPayTypeTextView.setText(mOrderType);

        }else {
            //mCurrentCountry = "";
            mCountryTextView.setText(mCurrentCountry);
            //mCurrentProviceName = "";
            mProvinceTextView.setText(mCurrentProviceName);
            mLivingAreaTextView.setText(mParking_name);
            //mCurrentCityName = "";
            mCityTextView.setText(mCurrentCityName);
            mAreaTextView.setText(mCurrentDistrictName);
            mTimeFrame = "1";
            mTimeFrameTextView.setText(mTimeFrame);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            mBeginTime = simpleDateFormat.format(calendar.getTime());
            mMonthlyrentBegintTimeTextView.setText(mBeginTime);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            mEndTime = simpleDateFormat.format(calendar.getTime());
            mMonthlyrentEndTimeTextView.setText(mEndTime);
        }


        mCPCRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountryDialog dialog = CountryDialog.creatDialog(getActivity());
                dialog.setOnSelectListener(new CountryDialog.OnSelectListener() {
                    @Override
                    public void onSelect(String province, String city, String district, String zipcode) {
                        mCurrentCountry = province;
                        mCountryTextView.setText(province);
                        mCurrentProviceName = city;
                        mProvinceTextView.setText(city);
                        mCurrentCityName = district;
                        mCityTextView.setText(district);
                    }
                });
            }
        });


        mAreaRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleAreaDialog dialog = SingleAreaDialog.creatDialog(getActivity(), mCurrentProviceName, mCurrentCityName);
                dialog.setOnSelectListener(new SingleAreaDialog.OnSelectListener() {
                    @Override
                    public void onSelect(String province, String city, String district, String zipcode) {
                        mCurrentDistrictName = district;
                        mAreaTextView.setText(district);
                    }
                });

            }
        });


        mTimeFrameRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                final int dif = 12;
                final String[] array = new String[dif];
                ArrayList<String> list = new ArrayList<String>(dif);
                for (int i = 0; i < dif; i++) {
                    list.add("" + (i + 1));
                }

                SingleWheelDialog singleWheelDialog = SingleWheelDialog.creatDialog(getActivity(), new SingleWheelDialog.OnSelectListener() {
                    @Override
                    public void onSelect(int index) {
                        Log.i("SingleWheelDialog", "" + index);
                        mTimeFrame = array[index];
                        int addMonth = Integer.parseInt(mTimeFrame) - 1;
                        mTimeFrameTextView.setText(array[index]);
                        String stime = mMonthlyrentBegintTimeTextView.getText().toString();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date = simpleDateFormat.parse(stime);
                            date.getTime();
                            Calendar calendar = new GregorianCalendar();
                            calendar.setTime(date);
                            calendar.add(Calendar.MONTH, addMonth);
                            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                            mEndTime = simpleDateFormat.format(calendar.getTime());
                            mMonthlyrentEndTimeTextView.setText(mEndTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, list.toArray(array)).show();
            }
        });



        mMonthlyrentBegintTimeRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Log.d("datePicker", "" + year + "  " + monthOfYear + "  " + dayOfMonth);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getMinimum(Calendar.DAY_OF_MONTH));
                final DatePicker dp = dpd.getDatePicker();
                if (dp != null) {
                    ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
                }

                dpd.show();

                dpd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        DatePicker datePicker = ((DatePickerDialog) dialog).getDatePicker();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                        calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));

                        mBeginTime = simpleDateFormat.format(calendar.getTime());
                        mMonthlyrentBegintTimeTextView.setText(mBeginTime);
                        int addMomth = Integer.parseInt(mTimeFrame) - 1;

                        calendar.set(datePicker.getYear() + (datePicker.getMonth() + addMomth) / 12, (datePicker.getMonth() + addMomth) % 12, datePicker.getDayOfMonth());
                        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));


                        mEndTime = simpleDateFormat.format(calendar.getTime());
                        mMonthlyrentEndTimeTextView.setText(mEndTime);
                    }
                });
            }
        });

        mLivingAreaRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentDistrictName != null && !mCurrentDistrictName.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("area", mCurrentDistrictName);
                    replaceFragment(AddLivingAreaFragment.class, "AddLivingAreaFragment", bundle, 1);
                } else {
                    showToast("请先选择省市区");
                }
            }
        });

        mCarNumberRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(AddMonthylyCarNumberFragment.class, "AddMonthylyCarNumberFragment", null, 2);
            }
        });

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = mCustomerNameEditText.getText().toString();
                if(mParkingId == null || mParkingId.isEmpty()){
                    showToast("小区信息不能为空");
                    return;
                }
                if(carNumber == null || carNumber.isEmpty()){
                    showToast("车号信息不能为空");
                    return;
                }
                if(mPrice == null || mPrice.isEmpty()){
                    showToast("月单价信息不正确");
                    return;
                }
                if(mBeginTime == null || mBeginTime.isEmpty()){
                    showToast("开始时间不能为空");
                    return;
                }
                if(mEndTime == null || mEndTime.isEmpty()){
                    showToast("结束时间不能为空");
                    return;
                }

                if(mName == null || mName.isEmpty()){
                    showToast("姓名不能为空");
                    return;
                }
                if(mTimeFrame == null || mTimeFrame.isEmpty()){
                    showToast("停车时间不能为空");
                    return;
                }
                if (mCurrentProviceName == null || mCurrentCityName.isEmpty()){
                    showToast("国省市不能为空");
                    return;
                }
                if(mCurrentDistrictName == null || mCurrentDistrictName.isEmpty()){
                    showToast("区不能为空");
                    return;
                }
                postPostOrderInfo(
                        mParkingId,
                        mParking_name,
                        carNumber,
                        mPrice,
                        mBeginTime,
                        mEndTime,
                        customerId,
                        mName,
                        mTimeFrame,
                        mOrderType,
                        mCurrentCountry+"-"+mCurrentProviceName+"-"+mCurrentCityName,
                        mCurrentDistrictName);
            }
        });
    }

    String mName;
    String mPrice;
    String mOrderType;
    String mParkingId;
    String mParking_name;

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Parking item = data.getParcelable("parking");
                mParkingId = item.getParking_id();
                mParking_name = item.getParking_name();
                mLivingAreaTextView.setText(item.getParking_name());
                if (carNumber != null && !carNumber.isEmpty()) {
                    postUnitPrice(mParkingId, carNumber);
                }else {
                    showToast("请输入车牌号");
                }

            } else if (requestCode == 2) {
                CarList carListItem = data.getParcelable("carList");
                carNumber = carListItem.getCar_number();
                mCarNumberTextView.setText(carListItem.getCar_number());
                if (mParkingId != null && !mParkingId.isEmpty()) {
                    postUnitPrice(mParkingId, carNumber);
                }else {
                    showToast("请设置停车场");
                }
            }
        }
    }

    public void postUnitPrice(String villageId, String carNumber) {
        villageId = "zghy20151013000001";
        carNumber = "苏E23233";
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_UNITPRICE));
        apiTask.setParams(ApiContants.instance(getActivity()).getUnitPrice(villageId, carNumber));
        apiTask.setResultClass(ApiResult2.class);
        apiTask.execute(new OnDataLoader<UnitPrice>() {

            LoadingDialog loadingDialog;

            public void onStart() {
                if (isEnable()) {
                    loadingDialog = LoadingDialog.show(getActivity());
                }
            }

            @Override
            public void onSuccess(UnitPrice unitPrice) {
                Log.e("Month price", unitPrice.toString());
                mPrice = unitPrice.getUnitPrice();
                mMonthlyrentPriceTextView.setText(mPrice);

                mOrderType = unitPrice.getUnitPriceType();
                if (mOrderType.equals("0")) {
                    mPayTypeTextView.setText("产权");
                } else {
                    mPayTypeTextView.setText("月租");
                }

                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(String code, String message) {
                showToast(message);
                loadingDialog.dismiss();
            }
        });
    }


    public void postPostOrderInfo(String villageId,
                                  String village_name,
                                  String carNumber,
                                  String price,
                                  String validity_start_time,
                                  String validity_end_time,
                                  String customer_id,
                                  String customer_name,
                                  String timequantum,
                                  String order_type,
                                  String area,
                                  String county) {
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_POSTORDERINFO));
        apiTask.setParams(ApiContants.instance(getActivity()).getPostOrderInfo(villageId,
                village_name,
                carNumber,
                price,
                validity_start_time,
                validity_end_time,
                customer_id,
                customer_name,
                timequantum,
                order_type,
                area,
                county));

        apiTask.execute(new OnDataLoader<Success>() {

            LoadingDialog loadingDialog;

            public void onStart() {
                if (isEnable()) {
                    loadingDialog = LoadingDialog.show(getActivity());
                }
            }

            @Override
            public void onSuccess(Success success) {
                if (success.getSuccess().equals("1")){
                    showToast("提交成功");
                }
                loadingDialog.dismiss();
                popBackStack();
            }

            @Override
            public void onFailure(String code, String message) {
                showToast(message);
                loadingDialog.dismiss();
            }
        });
    }


    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Bundle bundle = this.getArguments();
        bundle.putString("carNumber", carNumber);

        bundle.putString("mCurrentCountry", mCurrentCountry);
        bundle.putString("mCurrentProviceName", mCurrentProviceName);
        bundle.putString("mCurrentCityName", mCurrentCityName);
        bundle.putString("mCurrentDistrictName", mCurrentDistrictName);
        bundle.putString("mCurrentZipCode", mCurrentZipCode);

        bundle.putString("mParkingId", mParkingId);
        bundle.putString("mParking_name", mParking_name);

        bundle.putString("mTimeFrame",mTimeFrame);
        bundle.putString("mBeginTime", mBeginTime);
        bundle.putString("mEndTime", mEndTime);

        bundle.putString("mPrice", mPrice);
        bundle.putString("mOrderType", mOrderType);
    }
}
