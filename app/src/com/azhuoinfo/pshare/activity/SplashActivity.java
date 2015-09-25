package com.azhuoinfo.pshare.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.fragment.GuideFragment;
import com.azhuoinfo.pshare.fragment.LoginAndRegisterActivity;
import com.azhuoinfo.pshare.utils.Constants;

import mobi.cangol.mobile.base.BaseActionBarActivity;
import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.service.AppService;
import mobi.cangol.mobile.service.global.GlobalData;
import mobi.cangol.mobile.utils.DeviceInfo;

/**
 * @Description: 
 * @version $Revision: 1.0 $ 
 */
public class SplashActivity extends BaseActionBarActivity {
	//SharedPreferences对象
	private SharedPreferences mSharedPreferences;
	private GlobalData mGlobalData;
	private boolean isGuide=true;//为测试guide提供方便开启guide
	private boolean isLogin=false;//是否登录
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFullScreen(true);
        this.setActionbarShow(false);
        setContentView(R.layout.activity_splashing);
        initFragmentStack(R.id.layout_splashing);
        mGlobalData=(GlobalData) getAppService(AppService.GLOBAL_DATA);
        checkGuide();
		//checkLogin();
        new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				sendLaunch();
				if(isGuide){
					toGuide();
				}else {

					toMain();
				}
			}
		}, 2500L);
    }
    private void checkGuide(){
    	if(isGuide)return;
    	if (mGlobalData.get(Constants.KEY_USED_VERSION) == null) {
    		isGuide=true;
		} else {
			String newVersion = (String) mGlobalData.get(Constants.KEY_USED_VERSION);
			Log.d("newVersion="+newVersion+",AppVersion="+DeviceInfo.getAppVersion(this));
			if (!DeviceInfo.getAppVersion(this).equals(newVersion)) {
				isGuide=true;
			}else {
				isGuide=false;
			}
		}
    }

    private void toGuide(){
    	this.replaceFragment(GuideFragment.class, "GuideFragment",null);
		mGlobalData.save(Constants.KEY_USED_VERSION,DeviceInfo.getAppVersion(this));
    }
    public void toMain(){
    	startActivity(new Intent(SplashActivity.this, MainActivity.class));
    	finish();
    }
	public void toLogin(){
		startActivity(new Intent(SplashActivity.this,LoginAndRegisterActivity.class));
		isLogin=true;
		finish();
	}
	private void sendLaunch(){
		String exitCode="";
		String exitVersion="";
		boolean  isnew=true;
		if(mGlobalData.get(Constants.KEY_IS_NEW_USER)==null){
			mGlobalData.save(Constants.KEY_IS_NEW_USER, false);
		}else{
			isnew=(Boolean) mGlobalData.get(Constants.KEY_IS_NEW_USER);
		}
		if(mGlobalData.get(Constants.KEY_EXIT_CODE)!=null){
			exitCode=(String) mGlobalData.get(Constants.KEY_EXIT_CODE);
		}
		if(mGlobalData.get(Constants.KEY_EXIT_VERSION)!=null){
			exitVersion=(String) mGlobalData.get(Constants.KEY_EXIT_VERSION);
		}
	}
	@Override
	public void findViews() {
	}
	
	@Override
	public void initViews(Bundle savedInstanceState) {
	}
	
	@Override
	public void initData(Bundle savedInstanceState) {
	}
	@Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
}