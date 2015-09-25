package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;

import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

public class HomeFragment extends BaseContentFragment {
	private AccountVerify mAccountVerify;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//this.getCustomActionBar().setDisplayShowHomeEnabled(false);
		this.getCustomActionBar().setCustomHomeAsUpIndicator(R.drawable.homepager_user, R.drawable.homepager_user);

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
		
	}
	@Override
	protected boolean onMenuActionCreated(ActionMenu actionMenu) {
		super.onMenuActionCreated(actionMenu);
		actionMenu.add(new ActionMenuItem(1, "搜索", R.drawable.search1, 1));
		actionMenu.add(new ActionMenuItem(2,"代伯区详情",R.drawable.list_car,2));
		return true;
	}
	@Override
	public boolean onMenuActionSelected(ActionMenuItem action) {
		switch(action.getId()){
			case 1:
				this.getCustomActionBar().startSearchMode();
				break;
			case 2:
				replaceFragment(ParkingDetailsFragment.class,"ParkingDetailsFragment",null);
				break;
		}
		return super.onMenuActionSelected(action);
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
