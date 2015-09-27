package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;


public class RetrievePassWordFragment extends BaseContentFragment {
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

	private AccountVerify mAccountVerify;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getCustomActionBar().setCustomHomeAsUpIndicator(R.drawable.left_head, R.drawable.left_head);
		mAccountVerify = AccountVerify.getInstance(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment_retrievepassword,container,false);
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
		this.setTitle(R.string.retrieve_password);
		retrieveActivity_editText_Phone=(EditText) view.findViewById(R.id.retrieveActivity_editText_Phone);
		get_code=(TextView) view.findViewById(R.id.get_code);
		retrieveActivity_editText_Password=(EditText) view.findViewById(R.id.retrieveActivity_editText_Code);
		next_step=(Button) view.findViewById(R.id.next_step);
	}

	@Override
	protected void initViews(Bundle bundle) {
		next_step.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				replaceFragment(ResetPassWordFragment.class,"ResetPassWordFragment",null);
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
}
