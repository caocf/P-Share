package com.azhuoinfo.pshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.R;


public class RegisterActivity extends BaseActivity {
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
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		init();
	}
	public void init(){
		setContentView(R.layout.activity_register);
		
		//找到各个控件
		activity_back=(ImageView) findViewById(R.id.activity_back);
		registerActivity_editText_Phone=(EditText) findViewById(R.id.registerActivity_editText_Phone);
		registerActivity_editText_Password=(EditText) findViewById(R.id.registerActivity_editText_Password);
		get_code=(TextView) findViewById(R.id.get_code);
		registerActivity_editText_Code=(EditText) findViewById(R.id.registerActivity_editText_Code);
		register=(Button) findViewById(R.id.register);
		rl_registerActivity_backLoginActivity=(RelativeLayout) findViewById(R.id.rl_registerActivity_backLoginActivity);
		
 		//对各个控件设置监听
		activity_back.setOnClickListener(this);
		registerActivity_editText_Phone.setOnClickListener(this);
		registerActivity_editText_Password.setOnClickListener(this);
		get_code.setOnClickListener(this);
		registerActivity_editText_Code.setOnClickListener(this);
		register.setOnClickListener(this);
		rl_registerActivity_backLoginActivity.setOnClickListener(this);
		
	
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.activity_back:
			finish();
			break;
		case R.id.registerActivity_editText_Phone:
			
			break;
		case R.id.registerActivity_editText_Password:
			
			break;
		case R.id.get_code:
			
			break;
		case R.id.registerActivity_editText_Code:
	
			break;
		case R.id.register:
			
			break;
		case R.id.rl_registerActivity_backLoginActivity:
			startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
			break;
		default:
			break;
		}
	}
	
}
