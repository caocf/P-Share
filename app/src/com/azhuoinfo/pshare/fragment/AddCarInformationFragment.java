package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.model.AddCarInfo;
import com.azhuoinfo.pshare.view.CarIdDialog;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.utils.StringUtils;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class AddCarInformationFragment extends BaseContentFragment{

    private TextView mCarLocationEditText;
    private EditText mCarNumbrEditText;
    private EditText mCarBrandEditText;
    private EditText mCarSFZIDEditText;
    private Button mConfirmButton;

    private AccountVerify mAccountVerify;
    private String zh,en;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_add_car_information,container,false);
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
        mCarLocationEditText=(TextView) view.findViewById(R.id.et_car_location);
        mCarNumbrEditText=(EditText) view.findViewById(R.id.et_car_number);
        mCarBrandEditText=(EditText) view.findViewById(R.id.et_car_brand);
        mCarSFZIDEditText=(EditText) view.findViewById(R.id.et_car_sfzid);

        mConfirmButton=(Button) view.findViewById(R.id.button_confirm);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.add_car_information);

        mCarLocationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarIdDialog carIdDialog = CarIdDialog.creatDialog(getActivity()).show();
                carIdDialog.setOnIdSelectListener(new CarIdDialog.OnIdSelectListener() {
                    @Override
                    public void onIdSelectZh(String str) {
                        zh = str;
                        mCarLocationEditText.setText(StringUtils.trimToEmpty(zh) + StringUtils.trimToEmpty(en));
                    }

                    @Override
                    public void onIdSelectEn(String str) {
                        en = str;
                        mCarLocationEditText.setText(StringUtils.trimToEmpty(zh) + StringUtils.trimToEmpty(en));
                    }
                });
            }
        });
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String mCarNumber = mCarNumbrEditText.getText().toString();
                    String mCarBrand = mCarBrandEditText.getText().toString();
                    String mCarSfzid = mCarSFZIDEditText.getText().toString();

                    postAddcar(mAccountVerify.getCustomer_Id(), mCarBrand, null, null, mCarSfzid, zh + en + mCarNumber);
                }

            }
        });
    }
    @Override
    protected void initData(Bundle bundle) {

    }
    private boolean validate(){
        if(TextUtils.isEmpty(mCarLocationEditText.getText())){
            showToast(R.string.fill_in_car_address);
            return false;
        }else if(TextUtils.isEmpty(mCarBrandEditText.getText())){
            showToast(R.string.fill_in_car_brand);
            return false;
        }else if(TextUtils.isEmpty(mCarSFZIDEditText.getText())){
            showToast(R.string.fill_in_car_sfzid);
            return false;
        }else if(TextUtils.isEmpty(mCarNumbrEditText.getText())){
            showToast(R.string.Fill_in_car_number);
            return false;
        }
        return true;
    }
    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return false;
    }
    public void postAddcar(String customerId,String carBrand,String carColor,String carSize,String ownerIdNumber,String carNumber){
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_ADDCAR));
        apiTask.setParams(ApiContants.instance(getActivity()).useAddCar(customerId, carBrand, carColor, carSize, ownerIdNumber,carNumber));
        apiTask.execute(new OnDataLoader<AddCarInfo>(){
            @Override
            public void onStart(){

            }
            @Override
            public void onSuccess(boolean page, AddCarInfo addCarInfo) {
                replaceFragment(CarListFragment.class,"CarListFragment",null);
            }
            @Override
            public void onFailure(String code, String message) {

            }
        });
    }
}
