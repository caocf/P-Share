package com.azhuoinfo.pshare.fragment;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.fragment.adapter.MyPagerAdapter;
import com.azhuoinfo.pshare.model.CarList;
import com.azhuoinfo.pshare.model.Parking;
import com.azhuoinfo.pshare.view.AreaDialog;
import com.azhuoinfo.pshare.view.CountryDialog;
import com.azhuoinfo.pshare.view.SingleAreaDialog;

import java.util.ArrayList;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 * 缴费模块产权/月租，添加产权月租
 */
public class AddMonthlyRentCarFragment extends BaseContentFragment{
    //返回上一个页面
    //private Button mBackButton;

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
    private String carNumber;

    protected String mCurrentProviceName;
    protected String mCurrentCityName;
    protected String mCurrentDistrictName ="";
    protected String mCurrentZipCode ="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_add_monthlyrent_car,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState!=null) {
            carNumber = savedInstanceState.getString("carNumber");
            mCurrentCountry = savedInstanceState.getString("mCurrentCountry");
            mCurrentProviceName = savedInstanceState.getString("mCurrentProviceName");
            mCurrentCityName = savedInstanceState.getString("mCurrentCityName");
            mCurrentDistrictName = savedInstanceState.getString("mCurrentDistrictName");
            mCurrentZipCode = savedInstanceState.getString("mCurrentZipCode");
            mParkingId = savedInstanceState.getString("mParkingId");
            mParking_name = savedInstanceState.getString("mParking_name");
            carNumber = savedInstanceState.getString("carNumber");
        }
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
        mCPCRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_CPC);
        mCountryTextView=(TextView) view.findViewById(R.id.tv_country);
        mProvinceTextView=(TextView) view.findViewById(R.id.tv_province);
        mCityTextView=(TextView) view.findViewById(R.id.tv_city);

        mAreaRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_area);
        mAreaTextView=(TextView) view.findViewById(R.id.tv_area);

        mLivingAreaRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_living_area);
        mLivingAreaTextView=(TextView) view.findViewById(R.id.tv_living_area);

        mTimeFrameRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_time_frame);
        mTimeFrameTextView=(TextView) view.findViewById(R.id.tv_time_frame);

        mMonthlyrentBegintTimeRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_monthlyrent_begint_time);
        mMonthlyrentBegintTimeTextView=(TextView) view.findViewById(R.id.tv_monthlyrent_begint_time);

        mMonthlyrentEndTimeTextView=(TextView) view.findViewById(R.id.tv_monthlyrent_end_ime);

        mMonthlyrentPriceTextView=(TextView) findViewById(R.id.tv_monthlyrent_price);

        mCustomerNameEditText=(EditText) view.findViewById(R.id.et_customer_name);

        mCarNumberRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_car_number);
        mCarNumberTextView=(TextView) view.findViewById(R.id.tv_car_number);

        mConfirmButton=(Button) findViewById(R.id.button_confirm);

    }
String mCurrentCountry;
    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.add_rent);
        mCountryTextView.setText(mCurrentCountry);
        mProvinceTextView.setText(mCurrentProviceName);
        mCityTextView.setText(mCurrentCityName);
        mAreaTextView.setText(mCurrentDistrictName);
        mCarNumberTextView.setText(carNumber);
        mLivingAreaTextView.setText(mParking_name);

    }

    @Override
    protected void initData(Bundle bundle) {
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
                SingleAreaDialog dialog = SingleAreaDialog.creatDialog(getActivity(),mCurrentProviceName,mCurrentCityName);
                dialog.setOnSelectListener(new SingleAreaDialog.OnSelectListener() {
                    @Override
                    public void onSelect(String province, String city, String district, String zipcode) {
                        /*mCountryTextView.setText(province);
                        mProvinceTextView.setText(city);
                        mCityTextView.setText(district);
                        */
                        mCurrentDistrictName = district;
                        mAreaTextView.setText(district);
                    }
                });

            }
        });

        mLivingAreaRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentDistrictName!=null && !mCurrentDistrictName.isEmpty()) {
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
                replaceFragment(AddMonthylyCarNumberFragment.class, "AddMonthylyCarNumberFragment", null,2);
            }
        });
    }

    String mParkingId;
    String mParking_name;
    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if (requestCode == 1) {
                Parking item = data.getParcelable("parking");
                mParkingId = item.getParking_id();
                mParking_name = item.getParking_name();
                mLivingAreaTextView.setText(item.getParking_name());
            }else if (requestCode == 2){
                CarList carListItem = data.getParcelable("carList");
                carNumber = carListItem.getCar_number();
                mCarNumberTextView.setText(carListItem.getCar_number());
            }

        }
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
        outState.putString("carNumber", carNumber);

        outState.putString("mCurrentCountry", mCurrentCountry);
        outState.putString("mCurrentProviceName", mCurrentProviceName);
        outState.putString("mCurrentCityName",mCurrentCityName);
        outState.putString("mCurrentDistrictName",mCurrentDistrictName);
        outState.putString("mCurrentZipCode",mCurrentZipCode);
        outState.putString("mParkingId",mParkingId);
        outState.putString("mParking_name",mParking_name);

    }
}
