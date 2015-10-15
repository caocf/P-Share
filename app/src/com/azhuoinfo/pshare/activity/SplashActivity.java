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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFullScreen(true);
        this.setActionbarShow(false);
        setContentView(R.layout.activity_splashing);
        initFragmentStack(R.id.layout_splashing);
        new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				toMain();
			}
		}, 100L);
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