package com.azhuoinfo.pshare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.model.UserAuth;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;


public class RegisterFragment extends BaseContentFragment {
	//定义返回到上个页面的控件
	private ImageView activity_back;
	//定义填写手机号码的控件
	private EditText registerActivity_editText_Phone;
	//定义注册密码的控件
	private EditText registerActivity_editText_Password;
	//获取验证码
	private TextView get_code;
	//定义输入验证码的控件
	private EditText registerActivity_editText_Code;
	//定义注册账号的控件
	private Button register;
	//定义返回到登录页面的控件
	private RelativeLayout rl_registerActivity_backLoginActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment_register,container,false);
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
		registerActivity_editText_Phone=(EditText) view.findViewById(R.id.registerActivity_editText_Phone);
		registerActivity_editText_Password=(EditText) view.findViewById(R.id.registerActivity_editText_Password);
		get_code=(TextView) view.findViewById(R.id.get_code);
		registerActivity_editText_Code=(EditText) view.findViewById(R.id.registerActivity_editText_Code);
		register=(Button) view.findViewById(R.id.register);
		rl_registerActivity_backLoginActivity=(RelativeLayout) view.findViewById(R.id.rl_registerActivity_backLoginActivity);
	}

	@Override
	protected void initViews(Bundle bundle) {
		this.setTitle(R.string.registered);
		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				replaceFragment(LoginFragment.class,"LoginFragment",null);
			}
		});
		rl_registerActivity_backLoginActivity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				replaceFragment(LoginFragment.class,"LoginFragment",null);
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
	private void postRegister(String mobile,String password) {
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_REGISTER));
		apiTask.setParams(ApiContants.instance(getActivity()).register(mobile, password));
		apiTask.setRoot(null);
		apiTask.execute(new OnDataLoader<UserAuth>() {

			@Override
			public void onStart() {
				if (getActivity() != null){

				}
			}

			@Override
			public void onSuccess(boolean page, UserAuth auth) {
				if (getActivity() != null) {

				}
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
