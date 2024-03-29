package com.azhuoinfo.pshare.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.fragment.HomeFragment;
import com.azhuoinfo.pshare.fragment.LoginAndRegister;
import com.azhuoinfo.pshare.fragment.MenuFragment;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.utils.Constants;

import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.navigation.DrawerNavigationFragmentActivity;
import mobi.cangol.mobile.sdk.pay.PayManager;
import mobi.cangol.mobile.service.session.SessionService;
import mobi.cangol.mobile.utils.DeviceInfo;

@SuppressLint("ResourceAsColor")
public class MainActivity extends DrawerNavigationFragmentActivity {
	private static long back_pressed;
	private boolean isBackPressed;
	private AccountVerify mAccountVerify;
	private SessionService mSessionService;
    private PayManager mPayManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        this.setFloatActionBarEnabled(true);
		this.getCustomActionBar().setTitleGravity(Gravity.CENTER);
		initStatus();
		if (savedInstanceState == null) {
			// 启用动画
			this.getCustomFragmentManager().setDefaultAnimation(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            this.setMenuFragment(MenuFragment.class, null);
            if(mSessionService.getSerializable(AccountVerify.KEY_USER)!=null){
                CustomerInfo user= (CustomerInfo) mSessionService.getSerializable(AccountVerify.KEY_USER);
                Log.d("CustomerInfo="+user);
                mAccountVerify.setUser(user);
                this.setContentFragment(HomeFragment.class, "HomeFragment", null, ModuleMenuIDS.MODULE_HOME);
            }else{
				this.setContentFragment(LoginAndRegister.class,"LoginAndRegister",null);
			}
		} else {
			Log.d("savedInstanceState=" + savedInstanceState);
		}
		findViews();
		initViews(savedInstanceState);
		initData(savedInstanceState);
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
	public void initViews(Bundle savedInstanceState) {

	}
	@Override
	public void initData(Bundle savedInstanceState) {

	}
	private void initStatus() {
		mSessionService =getSession();
		mAccountVerify = AccountVerify.getInstance(this);
        mPayManager=PayManager.getInstance(this);

        mPayManager.initPay(this, PayManager.PAY_TYPE_WECHAT, Constants.APP_ID, Constants.API_KEY, Constants.MCH_ID);

        mPayManager.initPay(this, PayManager.PAY_TYPE_ALIPAY, Constants.SELLER, Constants.PARTNERID, Constants.RSA_PRIVATE, Constants.RSA_PUBLIC);

    }
	@Override
	protected void onDestroy() {
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
}
