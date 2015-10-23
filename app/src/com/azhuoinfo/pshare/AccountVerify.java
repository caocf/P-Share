package com.azhuoinfo.pshare;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.azhuoinfo.pshare.activity.MainActivity;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.fragment.LoginFragment;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.utils.Constants;
import com.azhuoinfo.pshare.view.CommonDialog;

import java.util.ArrayList;

import mobi.cangol.mobile.base.BaseFragment;
import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.service.AppService;
import mobi.cangol.mobile.service.global.GlobalData;

public class AccountVerify {
	private static final String TAG="AccountVerify";
	public static final String KEY_USER="customerInfo";
	private static final String KEY_REGISTRATION_ID="REGISTRATION_ID";
	private CustomerInfo user;
	private boolean isLogin;
	private Application application;
	private ApiContants apiContants;
	private SharedPreferences sp;
    private GlobalData mGlobalData;
	private AccountVerify(MobileApplication application) {
		this.application = application;
		apiContants=ApiContants.instance(application);
		sp=application.getSharedPreferences(Constants.SHARED, Activity.MODE_PRIVATE);
        mGlobalData= (GlobalData) application.getAppService(AppService.GLOBAL_DATA);
	}

	public static AccountVerify getInstance(Context context) {
		MobileApplication app = (MobileApplication) context
				.getApplicationContext();
		if (null == app.getAccountVerify()) {
			app.setAccountVerify(new AccountVerify(app));
		}
		return app.getAccountVerify();
	}
	/**
	 * 设置当前用户（并执行 通知登录listener|和pushID注册操作）
	 */
	public void login(CustomerInfo user) {
        isLogin=true;
        this.user = user;
        mGlobalData.save(KEY_USER,user);
        notifyLogin();
		//注册推送ID 暂时停用
		//pushReg();
	}

	public void setUser(CustomerInfo user) {
        isLogin=true;
        this.user = user;
        mGlobalData.save(KEY_USER,user);
		notifyUpdate();
	}
	
	public CustomerInfo getUser() {
        return user;
	}
    public String getCustomer_Id() {
        if(user!=null&&isLogin){
            return user.getCustomer_Id();
        }
        return null;
    }
	/**
	 * 判断是否登录
	 * @return
	 */
	public boolean isLogin() {
		return isLogin;
	}
	/**
	 * 注销操作
	 */
	public void logout() {
		isLogin = false;
		user = null;
        mGlobalData.remove(KEY_USER);
		notifyLogout();
	}
	
	protected ArrayList<OnLoginListener> listeners = new ArrayList<OnLoginListener>();  
	/**
	 * 注册登录listener
	 */
	public void registerLoginListener(OnLoginListener onLoginListener) {
		if (onLoginListener == null) {  
	        throw new IllegalArgumentException("The OnLoginListener is null.");  
	    }  
        synchronized(listeners) {  
            if (listeners.contains(onLoginListener)) {  
            	Log.d(TAG,"OnLoginListener " + onLoginListener + " is already registered.");
                //throw new IllegalStateException("OnLoginListener " + onLoginListener + " is already registered.");  
            }
        	if(!listeners.contains(onLoginListener))
        		listeners.add(onLoginListener);  
            
        }  
	}
	/**
	 * 移除登录listener
	 */
	public void unregisterLoginListener(OnLoginListener onLoginListener) {
		if (onLoginListener == null) {  
	        throw new IllegalArgumentException("The OnLoginListener is null.");  
	    }  
        synchronized(listeners) {  
        	 if (listeners.contains(onLoginListener)) {  
        		 listeners.remove(onLoginListener);  
            }else{
            	Log.d(TAG,"OnLoginListener " + onLoginListener + " is not exist.");
            	//throw new IllegalStateException("OnLoginListener " + onLoginListener + " is not exist.");  
            }
        }
	}
	public void notifyLogin(){
		for(OnLoginListener listener:listeners){
			if(listener!=null)listener.login();
		}
	}
	
	public void notifyLogout(){
		for(OnLoginListener listener:listeners){
			if(listener!=null)listener.logout();
		}
	}
	public void notifyExpire(){
		for(OnLoginListener listener:listeners){
			if(listener!=null)listener.expire();
		}
	}
	public void notifyUpdate(){
		for(OnLoginListener listener:listeners){
			if(listener!=null)listener.update();
		}
	}
    public interface OnLoginListener{
		void login();
		void logout();
		void expire();
		void update();
	}
    public void showLoginDialog(final Activity activity) {
        CommonDialog mLoginDialog = CommonDialog.creatDialog(activity);
        mLoginDialog.setTitle(R.string.dialog_expire_title);
        mLoginDialog.setMessage(R.string.dialog_expire_content);
        mLoginDialog.setLeftButtonInfo(activity.getString(R.string.common_dialog_confirm), new CommonDialog.OnButtonClickListener() {

            @Override
            public void onClick(View view) {
                if(activity instanceof MainActivity){
                    ((MainActivity)activity).replaceFragment(LoginFragment.class, "LoginFragment", null);
                }
            }

        });
        mLoginDialog.setRightButtonInfo(activity.getString(R.string.common_dialog_cancel), new CommonDialog.OnButtonClickListener() {
            @Override
            public void onClick(View view) {
                // do nothing
            }
        });
        mLoginDialog.show();
    }

	public void showLoginDialog(final BaseFragment fragment) {
		CommonDialog mLoginDialog = CommonDialog.creatDialog(fragment.getActivity());
        mLoginDialog.setTitle(R.string.dialog_expire_title);
        mLoginDialog.setMessage(R.string.dialog_expire_content);
        mLoginDialog.setLeftButtonInfo(fragment.getString(R.string.common_dialog_confirm), new CommonDialog.OnButtonClickListener() {

            @Override
            public void onClick(View view) {
                if (fragment.getParentFragment() == null) {
                    //to content userLogin
                    fragment.replaceFragment(LoginFragment.class, "LoginFragment", null);
                } else {
                    //to content sub userLogin
                    fragment.replaceParentFragment(LoginFragment.class, "LoginFragment", null);
                }
            }

        });
        mLoginDialog.setRightButtonInfo(fragment.getString(R.string.common_dialog_cancel), new CommonDialog.OnButtonClickListener() {
            @Override
            public void onClick(View view) {
                // do nothing
            }
        });
        mLoginDialog.show();
	}
}
