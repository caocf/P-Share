package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.model.FeeOrderInfo;
import com.azhuoinfo.pshare.utils.Constants;
import com.azhuoinfo.pshare.view.CommonDialog;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.sdk.pay.OnPayResultListener;
import mobi.cangol.mobile.sdk.pay.PayManager;
import mobi.cangol.mobile.sdk.pay.PlaceOrderCallback;

/**
 * Created by Azhuo on 2015/9/22.
 * 产权/月租完成支付
 */
public class MonthlyRentCarfinishPayFragment extends BaseContentFragment{
    //返回上一个页面
    //private Button mBackButton;

    //国
    private TextView mCountryTextView;/*
    //省
    private TextView mProvinceTextView;
    //市
    private TextView mCityTextView;*/
    //区
    private TextView mAreaTextView;
    //设置小区
    private TextView mLivingAreaTextView;
    //时间段
    private TextView mTimeFrameTextView;
    //开始时间
    private TextView mMonthlyrentBegintTimeTextView;
    //结束时间
    private TextView mMonthlyrentEndTimeTextView;
    //单价
    private TextView mMonthlyrentPriceTextView;
    //姓名
    private TextView mCustomerNameEditText;
    //车牌
    private TextView mCarNumberTextView;

    private Button mButtonFinishPay;



    private AccountVerify mAccountVerify;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_monthlyrent_car_finish_pay,container,false);
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

        mCountryTextView=(TextView) view.findViewById(R.id.tv_country);/*
        mProvinceTextView=(TextView) view.findViewById(R.id.tv_province);
        mCityTextView=(TextView) view.findViewById(R.id.tv_city);*/

        mAreaTextView=(TextView) view.findViewById(R.id.tv_area);

        mLivingAreaTextView=(TextView) view.findViewById(R.id.tv_living_area);

        mTimeFrameTextView=(TextView) view.findViewById(R.id.tv_time_frame);

        mMonthlyrentBegintTimeTextView=(TextView) view.findViewById(R.id.tv_monthlyrent_begint_time);

        mMonthlyrentEndTimeTextView=(TextView) view.findViewById(R.id.tv_monthlyrent_end_time);

        mMonthlyrentPriceTextView=(TextView) findViewById(R.id.tv_monthlyrent_price);

        mCustomerNameEditText=(TextView) view.findViewById(R.id.tv_customer_name);

        mCarNumberTextView=(TextView) view.findViewById(R.id.tv_car_number);

        mButtonFinishPay=(Button) view.findViewById(R.id.button_finish_pay);
    }

    @Override
    protected void initViews(Bundle bundle) {
       this.setTitle(R.string.rent);

    }

    FeeOrderInfo mFeeOrderInfo;
    @Override
    protected void initData(Bundle bundle) {
        Bundle data = getArguments();
        mFeeOrderInfo = data.getParcelable("data");
        mCountryTextView.setText(mFeeOrderInfo.getArea());
        mAreaTextView.setText(mFeeOrderInfo.getCounty());
        mLivingAreaTextView.setText(mFeeOrderInfo.getVillageName());
        mTimeFrameTextView.setText(mFeeOrderInfo.getTimeQuantum());
        mMonthlyrentBegintTimeTextView.setText(mFeeOrderInfo.getValidityStartTime());
        mMonthlyrentEndTimeTextView.setText(mFeeOrderInfo.getValidityEndTime());
        mMonthlyrentPriceTextView.setText(mFeeOrderInfo.getPrice());
        mCustomerNameEditText.setText(mFeeOrderInfo.getCustomerName());
        mCarNumberTextView.setText(mFeeOrderInfo.getCarNumber());

        if (mFeeOrderInfo.getOrderStatus().equals("0")){
            mButtonFinishPay.setText("点击支付");
            mButtonFinishPay.setBackgroundResource(R.drawable.button);
            mButtonFinishPay.setEnabled(true);
            mButtonFinishPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPayMethodDialog();
                }
            });
        }else {
            mButtonFinishPay.setText("已支付");
            mButtonFinishPay.setBackgroundResource(R.drawable.button_false);
            mButtonFinishPay.setEnabled(false);
        }
    }

    CommonDialog payDialog;
    String totalPay;
    private void showPayMethodDialog() {
        totalPay = mFeeOrderInfo.getAmount();
        payDialog = CommonDialog.creatDialog(this.getActivity());
        payDialog.setContentView(R.layout.list_menu_pay_pager);
        final RadioGroup radioGroup = (RadioGroup)payDialog.findViewById(R.id.rg);

        TextView tv_money;
        tv_money = (TextView)payDialog.findViewById(R.id.tv_money);
        tv_money.setText(String.format("%.2f", Double.parseDouble(totalPay)));

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
                final String paySubject = "口袋停-产权月租";
                final String payDesc = "产权月租";
                int checkedId = radioGroup.getCheckedRadioButtonId();
                if (checkedId == R.id.alipay) {;
                    toPay(PayManager.PAY_TYPE_ALIPAY, paySubject, payDesc, "0.01", Constants.ALIPAY_NOTIFY_URL);
                } else if (checkedId == R.id.wechatpay) {
                    int fee = (int) Double.parseDouble(totalPay) * 100;
                    fee = 1;
                    toPay(PayManager.PAY_TYPE_WECHAT, paySubject, payDesc, "" + fee, Constants.WECHAT_NOTIFY_URL);
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

    protected void toPay(int payType,final String subject,final String desc,final String price,String notify_url) {
        PayManager.getInstance(getActivity()).toPay(this.getActivity(), payType, new PlaceOrderCallback(subject, desc, price,notify_url) {
            @Override
            public String getOrderId() {
                //自定义订单号
                return mFeeOrderInfo.getID();
            }
        }, new OnPayResultListener() {
            @Override
            public void onSuccess(String billingIndex, String msg) {
                showToast("订单:" + billingIndex + " " + msg);
                payDialog.dismiss();
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

    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return false;
    }
}
