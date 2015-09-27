package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

public class LoginFragment extends BaseContentFragment {

	//定义填写手机号码的控件
	private EditText loginActivity_editText_Phone;
	//定义登录密码的控件
	private EditText loginActivity_editText_Password;
	//定义登录账号的控件
	private Button login;
	//定义返回到注册页面的控件
	private LinearLayout ll_LoginActivity_back_registerActivity;
	//定义跳转到忘记密码页面的控件
	private TextView loginActivity_forget_password;

	private AccountVerify mAccountVerify;
	private static final String TAG = "LoginFragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getCustomActionBar().setCustomHomeAsUpIndicator(R.drawable.left_head, R.drawable.left_head);
		mAccountVerify = AccountVerify.getInstance(getActivity());
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
	/*url:http://cgi.shangan.com/{client_type}/{version}/customer/register
	method:POST
	request:
	customer_mobile  手机号
	customer_password  密码

	response:
	{
		"code": "返回码",
			"msg": "返回信息",
			"timestamp": "时间戳",
			"datas": {
		"token":"令牌"
	}
	}*/
	@Override
	protected void findViews(View view) {
		loginActivity_editText_Phone=(EditText) view.findViewById(R.id.loginActivity_editText_Phone);
		loginActivity_editText_Password=(EditText) view.findViewById(R.id.loginActivity_editText_Password);
		login=(Button) view.findViewById(R.id.login);
		ll_LoginActivity_back_registerActivity=(LinearLayout) view.findViewById(R.id.ll_LoginActivity_back_registerActivity);
		loginActivity_forget_password=(TextView) view.findViewById(R.id.loginActivity_forget_password);
	}

	@Override
	protected void initViews(Bundle bundle) {
		this.setTitle(R.string.login);
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				replaceFragment(HomeFragment.class,"HomeFragment",null);
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
		if(!checkEdit()){
			return;
		}
		/*RequestParams params=new RequestParams();
		params.addBodyParameter("customer_mobile",loginActivity_editText_Phone.getText().toString());
		params.addBodyParameter("customer_password",loginActivity_editText_Password.getText().toString());
			// TODO Auto-generated method stub
		String httpUrl="http://cgi.shangan.com/{client_type}/{version}/customer/register";
		HttpUtils http=new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST,
				httpUrl,
				params,
				new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						replaceFragment(LoginFragment.class, "LoginFragment", null);
					}
					@Override
					public void onFailure(HttpException e, String s) {
						Log.i(TAG, "请求失败");
					}
				});*/
				}
	private boolean checkEdit(){
		if(loginActivity_editText_Phone.getText().toString().trim().equals("")){
			Toast.makeText(this.getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
		}else if(loginActivity_editText_Password.getText().toString().trim().equals("")){
			Toast.makeText(this.getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
		}else{
			return true;
		}
		return false;
	}

	@Override
	protected FragmentInfo getNavigtionUpToFragment() {
		return null;
	}
}
