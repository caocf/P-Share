package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

public class ResetPassWordFragment extends BaseContentFragment {

	//定义新密码控件
	private EditText resetActivity_editText_Password;
	//定义确认新密码控件
	private EditText resetActivity_editText_confrimPassword;
	//定义执行提交的控件
	private Button submit;

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
		resetActivity_editText_Password=(EditText) view.findViewById(R.id.resetActivity_editText_Password);
		resetActivity_editText_confrimPassword=(EditText) view.findViewById(R.id.resetActivity_editText_confrimPassword);
		submit=(Button) view.findViewById(R.id.submit);
	}
	@Override
	protected void initViews(Bundle bundle) {
		this.setTitle(R.string.reset_password);
		submit.setOnClickListener(new View.OnClickListener() {
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


}
