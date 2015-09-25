package com.azhuoinfo.pshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.azhuoinfo.pshare.R;

public class LoginActivity extends BaseActivity {
    //定义返回到上一页的控件
	private ImageView activity_back;
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
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		init();
	}
	public void init(){
		setContentView(R.layout.activity_login);
		//找到各个控件
		activity_back=(ImageView) findViewById(R.id.activity_back);
		loginActivity_editText_Phone=(EditText) findViewById(R.id.loginActivity_editText_Phone);
		loginActivity_editText_Password=(EditText) findViewById(R.id.loginActivity_editText_Password);		
		login=(Button) findViewById(R.id.login);
		ll_LoginActivity_back_registerActivity=(LinearLayout) findViewById(R.id.ll_LoginActivity_back_registerActivity);
		loginActivity_forget_password=(TextView) findViewById(R.id.loginActivity_forget_password);
		//对各个控件设置监听
		activity_back.setOnClickListener(this);
		loginActivity_editText_Phone.setOnClickListener(this);
		loginActivity_editText_Password.setOnClickListener(this);		
		login.setOnClickListener(this);
		ll_LoginActivity_back_registerActivity.setOnClickListener(this);
		loginActivity_forget_password.setOnClickListener(this);		
		
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.activity_back:
			finish();
			break;	
		case R.id.loginActivity_editText_Phone:
			
			break;		
		
		case R.id.loginActivity_editText_Password:
			
			break;		
		case R.id.login:
			startActivity(new Intent(LoginActivity.this, MainActivity.class));
			break;
			
		case R.id.ll_LoginActivity_back_registerActivity:
			startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
			break;
		case R.id.loginActivity_forget_password:
			startActivity(new Intent(LoginActivity.this,RetrievePassWordActivity.class));
			break;
		default:
			break;
		}
	}
}
