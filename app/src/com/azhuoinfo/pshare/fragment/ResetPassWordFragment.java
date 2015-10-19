package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.model.UserAuth;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;
/**
* 重置密码
* */
public class ResetPassWordFragment extends BaseContentFragment {

	//定义新密码控件
	private EditText mCustomerPasswordEditText;
	//定义确认新密码控件
	private EditText mConfrimPasswordEditText;
	//定义执行提交的控件
	private Button submit;
	private String customerMobile;
	private String customerPassword;
	private AccountVerify mAccountVerify;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle=this.getArguments();
		customerMobile=bundle.get("customer_mobile").toString();
		mAccountVerify = AccountVerify.getInstance(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment_resetpassword,container,false);
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
		mCustomerPasswordEditText=(EditText) view.findViewById(R.id.resetActivity_editText_Password);
		mConfrimPasswordEditText=(EditText) view.findViewById(R.id.resetActivity_editText_confrimPassword);
		submit=(Button) view.findViewById(R.id.submit);
	}
	@Override
	protected void initViews(Bundle bundle) {
		this.setTitle(R.string.reset_password);
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e(TAG,mCustomerPasswordEditText.getText().toString());
				Log.e(TAG, mConfrimPasswordEditText.getText().toString());
				if (mCustomerPasswordEditText.getText().toString().equals(mConfrimPasswordEditText.getText().toString())) {
					customerPassword= mCustomerPasswordEditText.getText().toString();
					getResetPwd(customerMobile, customerPassword);
				} else {
					Toast.makeText(getActivity(),"密码不一致",Toast.LENGTH_SHORT).show();
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

	public void getResetPwd(String mobile,String password){
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setMethod("GET");
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_RESETPWD));
		apiTask.setParams(ApiContants.instance(getActivity()).resetPwd(mobile,password));
		apiTask.setRoot(null);
		apiTask.execute(new OnDataLoader<UserAuth>() {
			@Override
			public void onStart() {
				if (getActivity() != null){

				}
			}
			@Override
			public void onSuccess(boolean page, UserAuth auth) {
					replaceFragment(LoginFragment.class,"LoginFragment",null);
			}
			@Override
			public void onFailure(String code, String message) {
				Log.d(TAG, "code=:" + code + ",message=" + message);
				if (getActivity() != null) {

				}
			}
		});
	}

}
