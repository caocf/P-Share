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
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import mobi.cangol.mobile.base.BaseMenuFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.utils.StringUtils;

public class MenuFragment extends BaseMenuFragment implements AccountVerify.OnLoginListener{

	//定义设置控件
	private ImageView mInstallImageView;
	//点击到个人中心
	private LinearLayout mPersonalCenterLinearLayout;
	//用户的头像设置
	private ImageView mCustomerHeadImageView;
	//用户名
	private TextView mCustomerNicknameTextView;
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
    private ImageLoader mImageLoader;
    private CustomerInfo mCustomerInfo;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mImageLoader=ImageLoader.getInstance();
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
		mCustomerPointsTextView=(TextView) v.findViewById(R.id.tv_customer_points);
		mParkingAreaRelativeLayout=(RelativeLayout) v.findViewById(R.id.rl_parkingArea);
		mPointsRelativeLayout=(RelativeLayout) v.findViewById(R.id.rl_points);
		mOrderRelativeLayout=(RelativeLayout) v.findViewById(R.id.rl_order);
		mCarListRelativeLayout=(RelativeLayout) v.findViewById(R.id.rl_car_list);
		mMonthlyRentRelativeLayout=(RelativeLayout) v.findViewById(R.id.rl_monthlyRent_list);

        mAccountVerify.registerLoginListener(this);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {

		mInstallImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setContentFragment(SetUpFragment.class, "SetUpFragment", null, ModuleMenuIDS.MODULE_SETTINGS);
				showMenu(false);
			}
		});
		mPersonalCenterLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setContentFragment(UserCenterFragment.class, "UserCenterFragment", null, ModuleMenuIDS.MODULE_USER);
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
				setContentFragment(MineOrderFragment.class, "MineOrderFragment", null, ModuleMenuIDS.MODULE_ORDER);
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
				setContentFragment(MonthlyRentFragment.class, "MonthlyRentFragment", null, ModuleMenuIDS.MODULE_FEE);
				showMenu(false);
			}
		});
		mPointsRelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setContentFragment(PointsFragment.class, "PointsFragment", null, ModuleMenuIDS.MODULE_SCORE);
				showMenu(false);
			}
		});
        updateViews();
        Log.e("initViews updateViews " + mCustomerInfo);
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
    private void updateViews(){
        mCustomerInfo=mAccountVerify.getUser();
        if(isEnable())
        if(mCustomerInfo!=null){
			if (!StringUtils.isEmpty(mCustomerInfo.getCustomer_head())){
				String customer_head = null;
				if(mCustomerInfo.getCustomer_head().endsWith(",")){
					customer_head = mCustomerInfo.getCustomer_head().substring(0, mCustomerInfo.getCustomer_head().length()-1);
				}else{
					customer_head = mCustomerInfo.getCustomer_head();
				}
				ImageLoader loader = ImageLoader.getInstance();
				loader.displayImage(customer_head,mCustomerHeadImageView);
			}
            mCustomerNicknameTextView.setText(StringUtils.trimToEmpty(mCustomerInfo.getCustomer_nickname()));
            mCustomerPointsTextView.setText(StringUtils.null2Zero(mCustomerInfo.getCustomer_point()));
        }else{
            mCustomerNicknameTextView.setText("未登录");
            mCustomerPointsTextView.setText("");
            mCustomerHeadImageView.setImageResource(R.drawable.userhead);
        }
    }
    @Override
    public void login() {
        updateViews();
        Log.e(" login updateViews "+mCustomerInfo);
    }

    @Override
    public void logout() {

    }

    @Override
    public void expire() {

    }

    @Override
    public void update() {
        updateViews();
        Log.e("update updateViews " + mCustomerInfo);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAccountVerify.unregisterLoginListener(this);
    }
}
