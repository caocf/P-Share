package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

	//定义设置控件
	private ImageView mInstallImageView;
	//点击到个人中心
	private LinearLayout mPersonalCenterLinearLayout;
	//用户的头像设置
	private ImageView mCustomerHeadImageView;
	//用户名
	private TextView mCustomerNicknameTextView;
	//用户Id
	private TextView mCustomerIdTextView;
	//用户积分
	private TextView mCustomerPointsTextView;
	//代泊区
	private RelativeLayout mParkingAreaRelativeLayout;
	//优惠/积分父控件
	private RelativeLayout mPointsRelativeLayout;
	//我的订单/预约父控件
	private RelativeLayout mOrderRelativeLayout;
	//我的车列表父控件
	private RelativeLayout mCarListRelativeLayout;
	//我的月租列表
	private RelativeLayout mMonthlyRentRelativeLayout;

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
		mInstallImageView=(ImageView) v.findViewById(R.id.iv_install);
		mPersonalCenterLinearLayout=(LinearLayout) v.findViewById(R.id.ll_personal_center);
		mCustomerHeadImageView=(ImageView) v.findViewById(R.id.iv_customer_head);
		mCustomerNicknameTextView=(TextView) v.findViewById(R.id.tv_customer_nickname);
		mCustomerIdTextView=(TextView) v.findViewById(R.id.tv_customer_id);
		mCustomerPointsTextView=(TextView) v.findViewById(R.id.tv_customer_points);
		mParkingAreaRelativeLayout=(RelativeLayout) v.findViewById(R.id.rl_parkingArea);
		mPointsRelativeLayout=(RelativeLayout) v.findViewById(R.id.rl_points);
		mOrderRelativeLayout=(RelativeLayout) v.findViewById(R.id.rl_order);
		mCarListRelativeLayout=(RelativeLayout) v.findViewById(R.id.rl_car_list);
		mMonthlyRentRelativeLayout=(RelativeLayout) v.findViewById(R.id.rl_monthlyRent_list);
        /*mActivityTextView = (TextView) v.findViewById(R.id.textview_menu_activity);
        mDiscoveryTextView = (TextView) v.findViewById(R.id.textview_menu_discovery);
		mMineTextView = (TextView) v.findViewById(R.id.textview_menu_mine);*/

	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		mInstallImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setContentFragment(SetUpFragment.class, "SetUpFragment", null, ModuleMenuIDS.MODULE_HOME);
				showMenu(false);
			}
		});
		mPersonalCenterLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setContentFragment(UserCenterFragment.class, "UserCenterFragment", null, ModuleMenuIDS.MODULE_HOME);
				showMenu(false);
			}
		});
		mParkingAreaRelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setContentFragment(HomeFragment.class, "HomeFragment", null, ModuleMenuIDS.MODULE_HOME);
				showMenu(false);
			}
		});
		mOrderRelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setContentFragment(OrderFragment.class, "OrderFragment", null, ModuleMenuIDS.MODULE_HOME);
				showMenu(false);
			}
		});
		mCarListRelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setContentFragment(CarListFragment.class, "CarListFragment", null, ModuleMenuIDS.MODULE_HOME);
				showMenu(false);
			}
		});
		mMonthlyRentRelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setContentFragment(MonthlyRentFragment.class, "MonthlyRentFragment", null, ModuleMenuIDS.MODULE_HOME);
				showMenu(false);
			}
		});
		mPointsRelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setContentFragment(PointsFragment.class, "PointsFragment", null, ModuleMenuIDS.MODULE_HOME);
				showMenu(false);
			}
		});

       /* mActivityTextView.setOnClickListener(new OnClickListener(){

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
		
		});*/
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
            /*mActivityTextView.setSelected(true);
            mDiscoveryTextView.setSelected(false);
			mMineTextView.setSelected(false);*/
			break;
		case ModuleMenuIDS.MODULE_DISCOVERY:
           /* mActivityTextView.setSelected(false);
            mDiscoveryTextView.setSelected(true);
			mMineTextView.setSelected(false);*/
			break;
		case ModuleMenuIDS.MODULE_MINE:
           /* mActivityTextView.setSelected(false);
            mDiscoveryTextView.setSelected(false);
			mMineTextView.setSelected(true);*/
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
