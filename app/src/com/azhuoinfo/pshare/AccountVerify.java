package com.azhuoinfo.pshare;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;

import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.model.User;
import com.azhuoinfo.pshare.model.UserAuth;
import com.azhuoinfo.pshare.utils.Constants;
import com.azhuoinfo.pshare.view.CommonDialog;

import java.util.ArrayList;

import mobi.cangol.mobile.base.BaseFragment;
import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.utils.StringUtils;

public class AccountVerify {
	private static final String TAG="AccountVerify";
	private static final String KEY_USERID="userId";
	private static final String KEY_USERTOKEN="token";
	private static final String KEY_REGISTRATION_ID="REGISTRATION_ID";
	private static final String REGISTRATION_ID_USERID="REGISTRATION_ID_USERID";
	private User user;
	private UserAuth userAuth;
	private boolean isLogin;
	private Application application;
	private ApiContants apiContants;
	private SharedPreferences sp;
	
	private AccountVerify(MobileApplication application) {
		this.application = application;
		apiContants=ApiContants.instance(application);
		sp=application.getSharedPreferences(Constants.SHARED, Activity.MODE_PRIVATE);
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
	public void login(UserAuth userAuth) {
		saveUserToken(userAuth.getUserId(),userAuth.getToken());
		setLogin(true);
		notifyLogin();
		//注册推送ID 暂时停用
		//pushReg();
	}

	public void setUser(User user) {
		this.user = user;
		notifyUpdate();
	}
	
	public User getUser() {
		return user;
	}

	/**
	 * 判断是否登录
	 * @return
	 */
	public boolean isLogin() {
		return isLogin;
	}
	/**
	 * 设置登录
	 */
	private void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	/**
	 * 保存用户登录信息
	 */
	private void saveUserToken(String username,String token) {
		Log.d(TAG,"saveUserToken:"+username+","+token);
		sp.edit().putString(KEY_USERID, username).putString(KEY_USERTOKEN, token)
				.commit();
	}
	/**
	 * 获取当前的用户的id
	 * @return
	 */
	public String getUserId() {
		String userId=null;
		if(userAuth!=null&&isLogin){
			userId=userAuth.getUserId();
		}else{
			userId=sp.getString(KEY_USERID,null);
		}
		return userId;
	}
	/**
	 * 获取当前用户token
	 * @return
	 */
	public String getUserToken() {
		String token=null;
		if(userAuth!=null&&isLogin){
			token=userAuth.getToken();
		}else{
			token=sp.getString(KEY_USERTOKEN, null);
		}
		return token;
	}
	
	/**
	 * 注销操作
	 */
	public void logout() {
		isLogin = false;
		user = null;
		sp.edit()
		.remove(KEY_USERTOKEN)
		.remove(KEY_USERID)
		.remove(REGISTRATION_ID_USERID)
		.commit();
		notifyLogout();
	}
	/**
	 * 推送注册
	 */
	public void deviceRegister() {
		String registrationId=sp.getString(KEY_REGISTRATION_ID, null);
		Log.d("registrationId ="+registrationId);
		if(!TextUtils.isEmpty(registrationId)/*&&!getUsername().equals(registrationIdUserID)*/){
			ApiTask apiTask =ApiTask.build(application, "deviceRegister");
			apiTask.setParams(apiContants.deviceRegister(registrationId));
			apiTask.setUrl(apiContants.getActionUrl(ApiContants.API_COMMON_DEVICEREGISTER));
			apiTask.setMethod("POST");
			apiTask.execute(new OnDataLoader<Object>() {

						@Override	
						public void onStart() {
						}

						@Override
						public void onSuccess(int totalPage,Object t) {
							Log.d(TAG,"pushReg success!");
							sp.edit().putString(REGISTRATION_ID_USERID, ""+getUserId()).commit();
						}

						@Override
						public void onFailure(String errorCode, String errorResponse) {
							Log.d(TAG, "pushReg:"+errorCode+","+errorResponse);
						}
					});
		}
	}
	/**
	 * 自动登录
	 */
	public void verifyToken() {
		Log.d("getUsername "+getUserId());
		Log.d("getUserToken "+getUserToken());
		if(StringUtils.isNotBlank(getUserId())&&StringUtils.isNotBlank(getUserToken())){
			ApiTask apiTask =ApiTask.build(application, "verifyToken");
			apiTask.setParams(apiContants.userAuth(getUserId(), getUserToken()));
			apiTask.setUrl(apiContants.getActionUrl(ApiContants.API_USER_AUTH));
			apiTask.execute(new OnDataLoader<UserAuth>() {

						@Override
						public void onStart() {
							
						}

						@Override
						public void onSuccess(int totalPage,UserAuth userAuth) {
							Log.d(TAG,"autoLogin success!");
							login(userAuth);
						}

						@Override
						public void onFailure(String errorCode, String errorResponse) {
							Log.d(TAG, "errorCode:"+errorCode+","+errorResponse);
							Log.d(TAG,"autoLogin fail!");
						}
					});
		}else{
			Log.d(TAG,"userId|token ==null,not autoLogin");
		}
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
	
	public void showLoginDialog(final BaseFragment fragment) {
		CommonDialog mLoginDialog = CommonDialog.creatDialog(fragment.getActivity());
		mLoginDialog.setTitle(R.string.dialog_expire_title)
		.setMessage(R.string.dialog_expire_content)
		.setLeftButtonInfo(fragment.getString(R.string.common_dialog_confirm), new CommonDialog.OnButtonClickListener() {

			@Override
			public void onClick(View view) {
				if(fragment.getParentFragment()==null){
					//to content userLogin
					//fragment.replaceFragment(UserLoginFragment.class,"UserLoginFragment", null);	
				}else{
					//to content sub userLogin
					//fragment.replaceParentFragment(UserLoginFragment.class,"UserLoginFragment", null);	
				}
			}

		}).setRightButtonInfo(fragment.getString(R.string.common_dialog_cancel), new CommonDialog.OnButtonClickListener() {
			@Override
			public void onClick(View view) {
				// do nothing
			}
		});
		mLoginDialog.show();
		
	}
}
