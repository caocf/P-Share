package com.azhuoinfo.pshare.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.view.LoadingDialog;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.service.AppService;
import mobi.cangol.mobile.service.session.SessionService;

/**
* 登录
* */
public class LoginFragment extends BaseContentFragment {

	//定义填写手机号码的控件
	private EditText mMobileEditText;
	//定义登录密码的控件
	private EditText mPasswordEditText;
	//定义登录账号的控件
	private Button mLoginButton;
	//定义返回到注册页面的控件
	private LinearLayout ll_LoginActivity_back_registerActivity;
	//定义跳转到忘记密码页面的控件
	private TextView loginActivity_forget_password;

	private AccountVerify mAccountVerify;
    private SessionService mSessionService;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getCustomActionBar().setCustomHomeAsUpIndicator(R.drawable.left_head, R.drawable.left_head);
		mAccountVerify = AccountVerify.getInstance(getActivity());
        mSessionService= getSession();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment_login,container,false);
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
		mMobileEditText=(EditText) view.findViewById(R.id.loginActivity_editText_Phone);
		mPasswordEditText=(EditText) view.findViewById(R.id.loginActivity_editText_Password);
		mLoginButton=(Button) view.findViewById(R.id.login);
		ll_LoginActivity_back_registerActivity=(LinearLayout) view.findViewById(R.id.ll_LoginActivity_back_registerActivity);
		loginActivity_forget_password=(TextView) view.findViewById(R.id.loginActivity_forget_password);
	}

	@Override
	protected void initViews(Bundle bundle) {
		this.setTitle(R.string.login);
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkEdit();
			}
		});
		loginActivity_forget_password.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				replaceFragment(RetrievePassWordFragment.class,"RetrievePassWordFragment",null);
			}
		});
		ll_LoginActivity_back_registerActivity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				replaceFragment(RegisterFragment.class,"RegisterFragment",null);
			}
		});

	}

	@Override
	protected void initData(Bundle bundle) {
		preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
		editor = preferences.edit();

		if(preferences.contains("user")){
			mMobileEditText.setText(preferences.getString("user",null));
		}
	}
	@Override
	protected FragmentInfo getNavigtionUpToFragment() {
		return null;
	}

	private boolean checkEdit(){
		if(mMobileEditText.getText().toString().trim().equals("")){
			Toast.makeText(this.getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
		}else if(mPasswordEditText.getText().toString().trim().equals("")){
			Toast.makeText(this.getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
		}else{
			postLogin(mMobileEditText.getText().toString(), mPasswordEditText.getText().toString());
		}
		return false;
	}
	public void postLogin(String mobile,String password) {
		ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_LOGIN));
		apiTask.setParams(ApiContants.instance(getActivity()).login(mobile, password));
		apiTask.setRoot("customerInfo");
		apiTask.execute(new OnDataLoader<CustomerInfo>() {
            LoadingDialog mLoadingDialog;

            @Override
            public void onStart() {
                if (isEnable())
                    if (mLoadingDialog == null) mLoadingDialog = LoadingDialog.show(getActivity());
            }

            @Override
            public void onSuccess(CustomerInfo customerInfo) {
                if (isEnable()) {
                    if (mLoadingDialog != null) mLoadingDialog.dismiss();
                    if (customerInfo != null) {
						editor.putString("user", customerInfo.getCustomer_mobile());
						editor.commit();
                        Log.d(TAG, "" + customerInfo);
                        mAccountVerify.login(customerInfo);
                        replaceFragment(HomeFragment.class, "HomeFragment", null);
                    } else {
                        showToast("无数据");
                    }
                }
            }

            @Override
            public void onFailure(String code, String message) {
                if (isEnable()) {
                    if (mLoadingDialog != null) mLoadingDialog.dismiss();
                    showToast(message);
                }
			}
		});
	}

	@Override
	public boolean onSupportNavigateUp() {
		hideInputMethod();
		return super.onSupportNavigateUp();
	}

	void hideInputMethod(){
		((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
