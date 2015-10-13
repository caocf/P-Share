package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.model.CustomerInfo;
/*import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;*/

import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

public class HomeFragment extends BaseContentFragment {
	private Button mMineHomeButton;
	private boolean isFrist=true;
	private int mOnClick=0;
	private LinearLayout mMineHomeButtonLinearLayout;
	private Button mChangeButton;
	private AccountVerify mAccountVerify;

	//private MapView mMapView = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//this.getCustomActionBar().setDisplayShowHomeEnabled(false);
		//Bundle bundle=this.getArguments();

		//Log.d("wela",this.getArguments().get("customer_id").toString());
		CustomerInfo customerInfo=(CustomerInfo)this.app.getSession().get("customerInfo");
		Log.e("www", customerInfo.getCustomer_Id().toString());
		this.getCustomActionBar().setCustomHomeAsUpIndicator(R.drawable.homepager_user, R.drawable.homepager_user);
		mAccountVerify = AccountVerify.getInstance(getActivity());
		//SDKInitializer.initialize(getActivity().getApplicationContext());
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
		//mMapView = (MapView) findViewById(R.id.bmapView);
		mMineHomeButton=(Button) view.findViewById(R.id.button_mine_home);
		mChangeButton=(Button) view.findViewById(R.id.button_change);
		mMineHomeButtonLinearLayout=(LinearLayout) view.findViewById(R.id.ll_mine_home);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_home);
		mChangeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mOnClick = 0;
				replaceFragment(MineHomeFragment.class,"MineHomeFragment",null);
			}
		});
		mMineHomeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("home","onClick");
				mOnClick++;
				if(mOnClick%2==1){
					mOnClick = 1;

					Log.i("home","zhankai");
					AnimationSet set = new AnimationSet(true);
					TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,0,Animation.RELATIVE_TO_PARENT,0,
							Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0);
					set.addAnimation(translate);
					set.setDuration(300);
					set.setFillAfter(true);
					mMineHomeButtonLinearLayout.offsetTopAndBottom(-mMineHomeButtonLinearLayout.getHeight());
					mMineHomeButtonLinearLayout.startAnimation(set);
				}
				else{
					mOnClick = 0;
					Log.i("home","heshang");
					AnimationSet set = new AnimationSet(true);
					TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,0,Animation.RELATIVE_TO_PARENT,0,
							Animation.RELATIVE_TO_SELF, -0.5f, Animation.RELATIVE_TO_SELF, 0);
					set.addAnimation(translate);
					set.setDuration(300);
					set.setFillAfter(true);
					mMineHomeButtonLinearLayout.offsetTopAndBottom(mMineHomeButtonLinearLayout.getHeight());
					mMineHomeButtonLinearLayout.startAnimation(set);
				}
			}
		});

	}

	@Override
	protected void initData(Bundle savedInstanceState) {

	}
	@Override
	protected boolean onMenuActionCreated(ActionMenu actionMenu) {
		super.onMenuActionCreated(actionMenu);
		actionMenu.addMenu(1,R.string.menu_search, R.drawable.search1, 1);
		actionMenu.addMenu(2,R.string.menu_list_car, R.drawable.list_car,2);
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

	@Override
	public void onDestroy() {
		super.onDestroy();
		//mMapView.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();
		//mMapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		//mMapView.onPause();
	}
}
