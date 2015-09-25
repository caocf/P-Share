package com.azhuoinfo.pshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhuoinfo.pshare.R;


public class RetrievePassWordActivity extends BaseActivity {
	//定义返回到上个页面的控件
	private ImageView activity_back;
	//定义填写手机号码的控件
	private EditText retrieveActivity_editText_Phone;
	//获取验证码
	private TextView get_code;
	//定义输入验证码的控件
	private EditText retrieveActivity_editText_Password;
	//下一步
	private Button next_step;	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		init();
	}
	private void init(){
		setContentView(R.layout.activity_retrievepassword);
		
		//找到各个控件
		activity_back=(ImageView) findViewById(R.id.activity_back);
		retrieveActivity_editText_Phone=(EditText) findViewById(R.id.retrieveActivity_editText_Phone);
		get_code=(TextView) findViewById(R.id.get_code);
		retrieveActivity_editText_Password=(EditText) findViewById(R.id.retrieveActivity_editText_Code);
		next_step=(Button) findViewById(R.id.next_step);
		//对各个控件设置监听
		activity_back.setOnClickListener(this);
		retrieveActivity_editText_Phone.setOnClickListener(this);
		get_code.setOnClickListener(this);
		retrieveActivity_editText_Password.setOnClickListener(this);
		next_step.setOnClickListener(this);
		
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.activity_back:
			finish();
			break;
		case R.id.retrieveActivity_editText_Phone:
			
			break;
		case R.id.get_code:
			
			break;
		case R.id.retrieveActivity_editText_Code:
			
			break;
		case R.id.next_step:
			startActivity(new Intent(RetrievePassWordActivity.this, ResetPassWordActivity.class));
			break;
		default:
			break;
		}
	}
}
