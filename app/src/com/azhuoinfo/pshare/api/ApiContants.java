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

	public static String API_CUSTOMER_RESETPWD="/customer/resetPwd";
	/**
	 *
	 * @return
	 */
	public HashMap<String, String> resetPwd(String mobile,String password) {
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


	/*customer_id，customer_nickname，customer_sex，customer_job，customer_region，customer_mobile，customer_email
customer_age*/
	public static  String API_CUSTOMER_SETUSERINFO="/customer/setUserInfo";
	/**
	 *
	 * @return
	 */
	public HashMap<String, String> setUserInfo(String customerId,String customerNickmane,
												   String customerSex,String customerJob,
												   String customerRegion,String customerMobile,String customerEmail,String customerAge) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("customer_id", customerId );
		params.put("customer_nickname",customerNickmane);
		//params.put("customer_head",customerHead);
		params.put("customer_sex",customerSex);
		params.put("customer_job",customerJob);
		params.put("customer_region",customerRegion);
		params.put("customer_mobile",customerMobile);
		params.put("customer_email",customerEmail);
		params.put("customer_age",customerAge);
		return params;
	}
	public static String API_CUSTOMER_CREATEORDER="/customer/createOrder";

	/**
	 *
	 * @return
	 */
	public HashMap<String, String> userCreateOrder(String customerId,String parkingId,String orderPlanBegin) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("customer_id", customerId );
		params.put("parking_id",parkingId);
		params.put("order_plan_begin",orderPlanBegin);
		return params;
	}
	public static String API_CUSTOMER_ADDCAR="/customer/addCar";
	/**
	 *customer_id，car_brand，car_number，car_color，car_size，owner_id_number（未确定是否需
	 * @return
	 */
	public HashMap<String, String> useAddCar(String customerId,String carBrand,String carColor,
											 String carSize,String ownerIdNumber,String carNumber){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("customer_id", customerId );
		params.put("car_brand",carBrand);
		params.put("car_color",carColor);
		params.put("car_size",carSize);
		params.put("owner_id_number",ownerIdNumber);
		params.put("car_number",carNumber);
		return params;
	}
	public static String API_CUSTOMER_UNFINISHEDORDER="/customer/unfinishedOrder";
	/**
	 *customer_id，car_brand，car_number，car_color，car_size，owner_id_number（未确定是否需
	 * @return
	 */
	public HashMap<String, String> unFinishedOrder(String customerId){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("customer_id", customerId );
		return params;
	}
	public static String API_CUSTOMER_CANCELORDER="/customer/cancelOrder";
	/**
	 *customer_id，car_brand，car_number，car_color，car_size，owner_id_number（未确定是否需
	 * @return
	 */
	public HashMap<String, String> userCancelOrder(String customerId,String orderSn){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("customer_id", customerId );
		params.put("order_sn",orderSn);
		return params;
	}
}
