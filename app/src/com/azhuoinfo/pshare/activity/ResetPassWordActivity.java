package com.azhuoinfo.pshare.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.azhuoinfo.pshare.R;

public class ResetPassWordActivity extends BaseActivity {
	//定义返回到上个页面的控件
	private ImageView activity_back;
	//定义新密码控件
	private EditText resetActivity_editText_Password;
	//定义确认新密码控件
	private EditText resetActivity_editText_confrimPassword;
	//定义执行提交的控件
	private Button submit;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		init();
	}
	public void init(){
		setContentView(R.layout.activity_resetpassword);
		//找到各个控件
		activity_back=(ImageView) findViewById(R.id.activity_back);
		resetActivity_editText_Password=(EditText) findViewById(R.id.resetActivity_editText_Password);
		resetActivity_editText_confrimPassword=(EditText) findViewById(R.id.resetActivity_editText_confrimPassword);
		submit=(Button) findViewById(R.id.submit);
		//对各个控件设置监听
		activity_back.setOnClickListener(this);
		resetActivity_editText_Password.setOnClickListener(this);
		resetActivity_editText_confrimPassword.setOnClickListener(this);
		submit.setOnClickListener(this);
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.activity_back:
			finish();
			break;
		case R.id.resetActivity_editText_Password:
					
			break;
		case R.id.resetActivity_editText_confrimPassword:
			
			break;
		case R.id.submit:
			
			break;

		default:
			break;
		}
	}
}
