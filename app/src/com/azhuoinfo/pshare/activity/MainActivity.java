package com.azhuoinfo.pshare.activity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.AccountVerify.OnLoginListener;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.db.MessageService;
import com.azhuoinfo.pshare.fragment.HomeFragment;
import com.azhuoinfo.pshare.fragment.LoginAndRegister;
import com.azhuoinfo.pshare.fragment.MenuFragment;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.Upgrade;
import com.azhuoinfo.pshare.model.User;
import com.azhuoinfo.pshare.utils.Constants;
import com.azhuoinfo.pshare.view.CommonDialog;

import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.navigation.SlidingNavigationFragmentActivity;
import mobi.cangol.mobile.service.AppService;
import mobi.cangol.mobile.service.global.GlobalData;
import mobi.cangol.mobile.service.status.StatusListener;
import mobi.cangol.mobile.service.status.StatusService;
import mobi.cangol.mobile.service.upgrade.UpgradeService;
import mobi.cangol.mobile.utils.DeviceInfo;
import mobi.cangol.mobile.utils.StringUtils;
import mobi.cangol.mobile.utils.TimeUtils;

@SuppressLint("ResourceAsColor")
public class MainActivity extends SlidingNavigationFragmentActivity implements OnLoginListener, StatusListener {
	private static long back_pressed;
	private boolean isBackPressed;
	private CommonDialog mLoginDialog;
	private AccountVerify mAccountVerify;
	private GlobalData mGlobalData;
	private UpgradeService mUpgradeService;
	private ApiContants mApiContants;
	private boolean isLogin=false;
	private MessageService mMessageService;
	private CustomerInfo customerInfo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		customerInfo=(CustomerInfo)this.app.getSession().get("customerInfo");
		Bundle bundle=new Bundle();
		bundle.putString("customer_point",customerInfo.getCustomer_point());
		bundle.putString("customer_head",customerInfo.getCustomer_head());
		bundle.putString("customer_nickname",customerInfo.getCustomer_nickname());
		//isLogin=(boolean)this.app.getSession().get("isLogin");
        this.setFloatActionBarEnabled(true);
		this.getCustomActionBar().setTitleGravity(Gravity.CENTER);
		initStatus();
		if (savedInstanceState == null) {
			// 启用动画
			this.getCustomFragmentManager().setDefaultAnimation(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
			this.setMenuFragment(MenuFragment.class, null);
			if(isLogin){
				this.setContentFragment(HomeFragment.class, "HomeFragment", null, ModuleMenuIDS.MODULE_HOME);
				isLogin=false;
			}else{
				this.setContentFragment(LoginAndRegister.class,"LoginAndRegister",null);
				isLogin=true;
			}
			if (mGlobalData.get(Constants.KEY_CHECK_UPGRADE) == null || !TimeUtils.getCurrentDate().equals(mGlobalData.get(Constants.KEY_CHECK_UPGRADE))) {
				// 判断升级提示是否检测过，每天只检测一次
				checkUpgrade();
			}
		} else {
			Log.d("savedInstanceState=" + savedInstanceState);
		}
		// 替换背景
		// this.getWindow().setBackgroundDrawableResource(R.drawable.activity_bg);
		findViews();
		initViews(savedInstanceState);
		initData(savedInstanceState);
	}
	@Override
	protected void onStart() {
		super.onStart();
	}
	@Override
	public int getContentFrameId() {
		return R.id.layout_main;
	}
	@Override
	public void findViews() {
		Log.d("getMD5Fingerprint=" + DeviceInfo.getMD5Fingerprint(this));
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void initViews(Bundle savedInstanceState) {

	}
	@Override
	public void initData(Bundle savedInstanceState) {
		StatusService statusService = (StatusService) getAppService(AppService.STATUS_SERVICE);
		statusService.registerStatusListener(this);
		if (savedInstanceState == null) {
			NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.cancelAll();
		} else {

		}
		if (!mAccountVerify.isLogin()) {
			mAccountVerify.verifyToken();
		}
	}
	private void initStatus() {

		mGlobalData = (GlobalData) getAppService(AppService.GLOBAL_DATA);
		mUpgradeService = (UpgradeService) getAppService(AppService.UPGRADE_SERVICE);
		mApiContants = ApiContants.instance(this);

		mAccountVerify = AccountVerify.getInstance(this);
		mAccountVerify.registerLoginListener(this);

		mMessageService = new MessageService(this);
	}
	@Override
	protected void onDestroy() {
		mAccountVerify.unregisterLoginListener(this);
		mGlobalData.save(Constants.KEY_EXIT_CODE, "0");
		mGlobalData.save(Constants.KEY_EXIT_VERSION, DeviceInfo.getAppVersion(this));
		super.onDestroy();
		if (isBackPressed) {
			Log.e("app.exit");
			app.exit();
		}

	}
	@Override
	public void onBack() {
		if (back_pressed + 2000 > System.currentTimeMillis()) {
			isBackPressed = true;
			super.onBack();
		} else {
			showToast(R.string.app_exit, 2000);
			back_pressed = System.currentTimeMillis();
		}
	}
	
	private void showUpgradeDialog(final Upgrade upgrade) {
		CommonDialog dialog = CommonDialog.creatDialog(this);
		dialog.setTitle(R.string.dialog_upgrade_title);
		dialog.setMessage(upgrade.getDesc());
		// 当前版本低于 最新版本的最低支持版本 则强制升级
		if (DeviceInfo.getAppVersion(this).compareTo(upgrade.getMinVersion()) < 0) {
			// 强制升级
			dialog.setRightButtonInfo(getString(R.string.common_dialog_confirm), new CommonDialog.OnButtonClickListener() {
				@Override
				public void onClick(View view) {
					// do nothing
					mUpgradeService.upgrade(getString(R.string.app_name), upgrade.getUrl(), true);

				}
			});
			dialog.setLeftButtonInfo(getString(R.string.common_dialog_exit), new CommonDialog.OnButtonClickListener() {

				@Override
				public void onClick(View view) {
					finish();
				}
			});
			dialog.setCanceledOnTouchOutside(false);
			dialog.setCancelable(false);
		} else {
			dialog.setRightButtonInfo(getString(R.string.common_dialog_cancel), new CommonDialog.OnButtonClickListener() {
				@Override
				public void onClick(View view) {
					// do nothing

				}
			});
			dialog.setLeftButtonInfo(getString(R.string.common_dialog_confirm), new CommonDialog.OnButtonClickListener() {

				@Override
				public void onClick(View view) {
					// /统计
					mUpgradeService.upgradeApk(getString(R.string.app_name), upgrade.getUrl(), true);

				}
			});
			// dialog.setOnCancelListener(new OnCancelListener(){
			//
			// @Override
			// public void onCancel(DialogInterface dialog) {
			// toMain();
			// }
			//
			// });
			dialog.setCanceledOnTouchOutside(false);
			dialog.setCancelable(false);
		}
		dialog.show();
	}

	// 检测升级
	private void checkUpgrade() {
//		ApiTask apiTask = ApiTask.build(this, "checkUpgrade");
//		apiTask.setUrl(mApiContants.getActionUrl(ApiContants.API_COMMON_UPGRADE));
//		apiTask.setParams(mApiContants.upgrade());
//		apiTask.execute(new OnDataLoader<Upgrade>() {
//			@Override
//			public void onStart() {
//			}
//
//			@Override
//			public void onSuccess(int totalPage, Upgrade upgrade) {
//				try {
//					Log.e("upgrade=" + upgrade);
//					if (DeviceInfo.getAppVersion(MainActivity.this).compareTo(upgrade.getVersion()) < 0) {
//						showUpgradeDialog(upgrade);
//						if (DeviceInfo.getAppVersion(MainActivity.this).compareTo(upgrade.getMinVersion()) >= 0)
//							// 记录更新提示日期
//							mGlobalData.save(Constants.KEY_CHECK_UPGRADE, TimeUtils.getCurrentDate());
//					}
//
//				} catch (Exception e) {
//					Log.d(TAG, "Exception", e);
//				}
//			}
//
//			@Override
//			public void onFailure(String errorCode, String errorResponse) {
//				Log.d(TAG, "errorCode:" + errorCode + "," + errorResponse);
//			}
//
//		});

	}
	private void getUserInfo(String userId, String userToken) {
//		ApiTask apiTask = ApiTask.build(this, "getUserInfo");
//		apiTask.setUrl(mApiContants.getActionUrl(ApiContants.API_USER_PROFILE));
//		apiTask.setParams(mApiContants.userProfile(userId, userToken));
//		apiTask.execute(new OnDataLoader<User>() {
//			@Override
//			public void onStart() {
//			}
//
//			@Override
//			public void onSuccess(int totalPage, User user) {
//				try {
//					if (user != null) {
//						mAccountVerify.setUser(user);
//					} else {
//						showToast(R.string.common_error);
//					}
//				} catch (Exception e) {
//					Log.d(TAG, "Exception", e);
//				}
//			}
//
//			@Override
//			public void onFailure(String errorCode, String errorResponse) {
//				Log.d(TAG, "errorCode:" + errorCode + "," + errorResponse);
//			}
//
//		});

	}

	@Override
	public void login() {
		// 获取用户信息
		getUserInfo(mAccountVerify.getUserId(), mAccountVerify.getUserToken());
		// 每天第一次登录
		String date = (String) mGlobalData.get(Constants.KEY_LAST_LOGIN_DATE);
		Log.d("last_login_date:" + date);
		if (!StringUtils.isEmpty(date) && !TimeUtils.getCurrentDate().equals(date)) {
			Log.d("loginPerDay");
			showToast("欢迎回来！");
		}
		mGlobalData.save(Constants.KEY_LAST_LOGIN_DATE, TimeUtils.getCurrentDate());
	}

	@Override
	public void logout() {

	}

	@Override
	public void expire() {
		// token过期提示重新登录
		if (!app.mSession.getBoolean(Constants.KEY_IS_LOADING)) {
			if (mLoginDialog != null && mLoginDialog.isShow()) {
				return;
			}
			showLoginDialog();
		}

	}
	private void showLoginDialog() {
		//startActivity(new Intent(this,LoginAndRegisterActivity.class));
		
	}
	@Override
	public void callStateIdle() {
	}

	@Override
	public void callStateOffhook() {
	}

	@Override
	public void callStateRinging() {
	}

	@Override
	public void networkConnect(Context arg0) {
		Log.d("networkConnect");
		if (!mAccountVerify.isLogin()) {
			if (StringUtils.isNotBlank(mAccountVerify.getUserId()) && StringUtils.isNotBlank(mAccountVerify.getUserToken())) {
				mAccountVerify.verifyToken();
			}
		}
	}

	@Override
	public void networkDisconnect(Context arg0) {
		Log.d("networkDisconnect messagerbar 提示");
	}

	@Override
	public void networkTo3G(Context arg0) {
	}

	@Override
	public void storageMount(Context arg0) {
	}

	@Override
	public void storageRemove(Context arg0) {

	}
	@Override
	public void update() {

	}

}
