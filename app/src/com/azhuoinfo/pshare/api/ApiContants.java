package com.azhuoinfo.pshare.api;

import android.content.Context;

import com.azhuoinfo.pshare.view.touchgallery.GalleryViewPager;

import org.OpenUDID.OpenUDID_manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobi.cangol.mobile.CoreApplication;
import mobi.cangol.mobile.utils.DeviceInfo;
import mobi.cangol.mobile.utils.StringUtils;

public class ApiContants {
	public static final String CLIENT_TYPE = "1";
    public static final String VERSION = "1.0.0";
	/**
	 * test server address
	 */
	public static String TEST_SERVER_URI = "http://139.196.12.103/";
	/**
	 * release server address
	 */
	public static String RELEASE_SERVER_URI = "http://139.196.12.103/";

	private Context context;
	public static boolean DEBUG = true;// true:测试环境 false 生产环境

	private ApiContants(Context context) {
		this.context = context;
		DEBUG = ((CoreApplication) context.getApplicationContext()).isDevMode();
	}

	public static ApiContants instance(Context context) {
		if (context != null) {
			return new ApiContants(context);
		} else {
			throw new IllegalStateException("context isn't null");
		}
	}

	public String getActionUrl(String action) {
		return getServerUrl(CLIENT_TYPE,VERSION) + action;
	}
    public String getServerUrl(String client_type,String version) {
        return (DEBUG ? TEST_SERVER_URI : RELEASE_SERVER_URI)+client_type+"/"+version;

    }
	/**
	 * 签名 暂时不使用
	 * @param params
	 * @return
	 */
	public String sign(HashMap<String, String> params) {
		StringBuilder sb = new StringBuilder(DeviceInfo.getMD5Fingerprint(context));
		List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(params.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});
		for (int i = 0; i < infoIds.size(); i++) {
			if (StringUtils.isNotEmpty(infoIds.get(i).getValue()))
				sb.append(infoIds.get(i).getValue());
		}
		String sign = StringUtils.md5(sb.toString());
		return sign;
	}

	/**
	 * 基本参数，暂时不使用
	 * @return
	 */
	public HashMap<String, String> getBaseParams() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", "");
		params.put("client",
				OpenUDID_manager.isInitialized() ? OpenUDID_manager.getOpenUDID() : DeviceInfo.getDeviceId(context));
		params.put("time", ""+System.currentTimeMillis());
		return params;
	}

	public static String API_CUSTOMER_INIT = "/customer/init";

	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> init() {
		HashMap<String, String> params = new HashMap<String, String>();
        return params;
	}

    public static String API_CUSTOMER_REGISTER = "/customer/register";
    /**
     *
     * @return
     */
    public HashMap<String, String> register(String mobile,String password) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("customer_mobile", mobile);
        params.put("customer_password", password);
        return params;
    }
	public static String API_CUSTOMER_LOGIN="/customer/login";
	/**
	 *
	 * @return
	 */
	public HashMap<String, String> login(String mobile,String password) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("customer_mobile", mobile);
		params.put("customer_password", password);
		return params;
	}



}
