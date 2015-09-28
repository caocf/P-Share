package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.model.UserAuth;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;

public class HomeFragment extends BaseContentFragment {
	private AccountVerify mAccountVerify;
    private ApiContants mApiContants;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAccountVerify = AccountVerify.getInstance(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_home, container, false);
		return v;
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
	}
	
	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_home);
		
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
        postRegister("18201952413", "111111");
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

    @Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected FragmentInfo getNavigtionUpToFragment() {
		return null;
	}
	


	@Override
	public boolean isCleanStack() {
		return true;
	}

}
