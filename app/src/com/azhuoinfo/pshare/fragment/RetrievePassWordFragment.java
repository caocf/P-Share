package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.model.UserAuth;
import com.azhuoinfo.pshare.model.UserCode;
import com.azhuoinfo.pshare.view.CountDownTextView;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;
/**
* 忘记密码
*
* */

public class RetrievePassWordFragment extends BaseContentFragment {
	//定义返回到上个页面的控件
	private ImageView activity_back;
	//定义填写手机号码的控件
	private EditText mCustomerMobileEditText;
	//获取验证码
	private CountDownTextView mCodeTextView;
	//定义输入验证码的控件
	private EditText mCodeEditText;
	//下一步
	private Button next_step;

	private AccountVerify mAccountVerify;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		this.setTitle(R.string.reset_password);
		mCustomerMobileEditText=(EditText) view.findViewById(R.id.retrieveActivity_editText_Phone);
		mCodeTextView=(CountDownTextView) view.findViewById(R.id.get_code);
		mCodeEditText=(EditText) view.findViewById(R.id.retrieveActivity_editText_Code);
		next_step=(Button) view.findViewById(R.id.next_step);
	}

	@Override
	protected void initViews(final Bundle bundle) {
/*		mCodeTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				postSendSmsCode(mCustomerMobileEditText.getText().toString());
			}
		});*/

		mCodeTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mCustomerMobileEditText.getText() != null && mCustomerMobileEditText.getText().length()>0) {
					postSendSmsCode(mCustomerMobileEditText.getText().toString());
					mCodeTextView.starTimeByMillisInFuture(60 * 1000);
					mCodeTextView.setEnabled(false);
				} else {
					Toast.makeText(getActivity(), "请输入手机号", Toast.LENGTH_SHORT).show();
				}

			}
		});
		mCodeTextView.setOnCountDownListener(new CountDownTextView.OnCountDownListener() {
			@Override
			public void onFinish() {
				mCodeTextView.setEnabled(true);
				mCodeTextView.setText(R.string.get_code);
			}
		});


		next_step.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e(TAG, "mCodeEditText:" + mCodeEditText.getText().toString());
				if (!mCustomerMobileEditText.getText().toString().equals("")) {
					if (!mCodeEditText.getText().toString().equals("")) {
						postVerifySmsCode(mCustomerMobileEditText.getText().toString(), mCodeEditText.getText().toString());
					} else {
						Toast.makeText(getActivity(),"验证码未输入",Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getActivity(),"手机号不能为空",Toast.LENGTH_SHORT).show();
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

	@Override
	public boolean isCleanStack() {
		return false;
	}
	public void postSendSmsCode(String mobile){
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_SENDSMSCODE));
		apiTask.setParams(ApiContants.instance(getActivity()).userSendSmsCode(mobile));
		apiTask.setRoot(null);
		apiTask.execute(new OnDataLoader<UserCode>() {
			@Override
			public void onStart() {

			}

			@Override
			public void onSuccess(UserCode userAuth) {
				Log.e(TAG,userAuth+"");
			}

			@Override
			public void onFailure(String code, String message) {

			}
		});
	}
	public void postVerifySmsCode(String mobile,String smsCode){
		ApiTask apiTask=ApiTask.build(this.getActivity(),TAG);
		apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_VERIFYSMSCODE));
		apiTask.setParams(ApiContants.instance(getActivity()).userVerifySmsCode(mobile, smsCode));
		apiTask.setRoot(null);
		apiTask.execute(new OnDataLoader<UserCode>() {
			@Override
			public void onStart() {
				if (getActivity() != null) {

				}
			}
			@Override
			public void onSuccess(UserCode auth) {
				Log.e(TAG,"vCode:"+auth);
				Bundle bundle = new Bundle();
				bundle.putString("customer_mobile", mCustomerMobileEditText.getText().toString());
				if(mCustomerMobileEditText.getText()!=null){
					replaceFragment(ResetPassWordFragment.class, "ResetPassWordFragment", bundle);
				}
			}
			@Override
			public void onFailure(String code, String message) {
				Log.d(TAG, "code=:" + code + ",message=" + message);
				if (getActivity() != null) {
					Toast.makeText(getActivity(), "验证码不正确", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}
}
