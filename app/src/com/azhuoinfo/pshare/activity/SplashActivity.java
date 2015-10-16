package com.azhuoinfo.pshare.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.fragment.GuideFragment;
//import com.azhuoinfo.pshare.fragment.LoginAndRegisterActivity;
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
    private boolean isGuide=false;//为测试guide提供方便开启guide
    private GlobalData mGlobalData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFullScreen(true);
        this.setActionbarShow(false);
        setContentView(R.layout.activity_splashing);
        initFragmentStack(R.id.layout_splashing);
        mGlobalData = (GlobalData) getAppService(AppService.GLOBAL_DATA);
        checkGuide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isGuide) {
                    toGuide();
                } else {
                    toMain();
                }
            }
        }, 1500L);
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
        this.replaceFragment(GuideFragment.class, "GuideFragment", null);

        mGlobalData.save(Constants.KEY_USED_VERSION, DeviceInfo.getAppVersion(this));
    }
    public void toMain(){
    	startActivity(new Intent(SplashActivity.this, MainActivity.class));
    	finish();
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
}