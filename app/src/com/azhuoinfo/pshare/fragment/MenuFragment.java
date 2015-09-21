package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;

import com.azhuoinfo.pshare.R;

import mobi.cangol.mobile.base.BaseMenuFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.service.global.GlobalData;

public class MenuFragment extends BaseMenuFragment {
	public TextView mActivityTextView;
	public TextView mDiscoveryTextView;
	public TextView mMineTextView;

	private AccountVerify mAccountVerify;
	private GlobalData mGlobalData;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAccountVerify = AccountVerify.getInstance(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_menu, container, false);
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
	protected void findViews(View v) {
        mActivityTextView = (TextView) v.findViewById(R.id.textview_menu_activity);
        mDiscoveryTextView = (TextView) v.findViewById(R.id.textview_menu_discovery);
		mMineTextView = (TextView) v.findViewById(R.id.textview_menu_mine);

	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
        mActivityTextView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				setContentFragment(HomeFragment.class, "HomeFragment", null, ModuleMenuIDS.MODULE_HOME);
			}
		
		});
        mDiscoveryTextView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

			}
		
		});
		mMineTextView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

			}
		
		});
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		if(savedInstanceState!=null){
			updateFocus(this.getCurrentModuleId());
		}else{
			this.setCurrentModuleId(this.getCurrentModuleId());
		}
	}
	@Override
	protected void onContentChange(int moduleId) {
		if(this.getView()!=null){
			updateFocus(moduleId);
		}
	}
	
	protected void updateFocus(int moduleId) {
		switch (moduleId) {
		case ModuleMenuIDS.MODULE_HOME:
            mActivityTextView.setSelected(true);
            mDiscoveryTextView.setSelected(false);
			mMineTextView.setSelected(false);
			break;
		case ModuleMenuIDS.MODULE_DISCOVERY:
            mActivityTextView.setSelected(false);
            mDiscoveryTextView.setSelected(true);
			mMineTextView.setSelected(false);
			break;
		case ModuleMenuIDS.MODULE_MINE:
            mActivityTextView.setSelected(false);
            mDiscoveryTextView.setSelected(false);
			mMineTextView.setSelected(true);
			break;
		}
	}
	@Override
	protected FragmentInfo getNavigtionUpToFragment() {
		return null;
	}
	
	@Override
	protected void onClosed() {

	}

	@Override
	protected void onOpen() {

	}
}
