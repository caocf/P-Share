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
    public HashMap<String, String> register(String mobile,String password,String smsCode) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("customer_mobile", mobile);
        params.put("customer_password", password);
		params.put("smsCode",smsCode);
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
	public HashMap<String, String> setUserInfo(String customerId,String customerNickmane,String customerJob,
												   String customerRegion,String customerMobile,String customerEmail) {
		HashMap<String, String> params = new HashMap<String, String>();
		if(customerNickmane == null||customerNickmane.length()==0){
			customerNickmane = "";
		}
		if(customerJob == null||customerJob.length()==0){
			customerJob = "";
		}
		if(customerRegion == null||customerRegion.length()==0){
			customerRegion = "";
		}
		if(customerMobile == null||customerMobile.length()==0){
			customerMobile = "";
		}
		if(customerEmail == null||customerEmail.length()==0){
			customerEmail = "";
		}
		params.put("customer_id", customerId );
		params.put("customer_nickname",customerNickmane);
		params.put("customer_age",String.valueOf(100));
		params.put("customer_job",customerJob);
		params.put("customer_region",customerRegion);
		params.put("customer_mobile",customerMobile);
		params.put("customer_email",customerEmail);
		return params;
	}
	public static String API_CUSTOMER_CREATEORDER="/customer/createOrder";

	/**
	 *
	 * @return
	 */
	public HashMap<String, String> userCreateOrder(String customerId,String parkingId,String orderPlanBegin,String order_img_count) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("customer_id", customerId );
		params.put("parking_id",parkingId);
		params.put("order_plan_begin",orderPlanBegin);
		params.put("order_img_count",order_img_count);
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
	public HashMap<String, String> userCancelOrder(String orderId){
		HashMap<String, String> params = new HashMap<String, String>();
		//params.put("customer_id", customerId );
		params.put("order_id",orderId);
		return params;
	}
	public static String API_CUSTOMER_CARLIST="/customer/carList";
	/**
	 *customer_id，car_brand，car_number，car_color，car_size，owner_id_number（未确定是否需
	 * @return
	 */
	public HashMap<String, String> userCarList(String customerId){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("customer_id", customerId );
		//params.put("order_id",orderId);
		return params;
	}
    public static String API_CUSTOMER_DELETECAR="/customer/deleteCar";
    /**
     * 未实现
     *，car_id
     * @return
     */
    public HashMap<String, String> deleteCar(String customerId,String car_id){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("customer_id", customerId );
        params.put("car_id", car_id );
        return params;
    }

    public static String API_CUSTOMER_DELETEPARKING="/customer/deleteParking";
    /**
     * 未实现
     *，parking_id
     * @return
     */
    public HashMap<String, String> deleteParking(String customerId,String parking_id){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("customer_id", customerId );
        params.put("parking_id", parking_id );
        return params;
    }
	public static String API_CUSTOMER_SENDSMSCODE="/customer/sendSmsCode";
	/**
	 *customer_id，car_brand，car_number，car_color，car_size，owner_id_number（未确定是否需
	 * @return
	 */
	public HashMap<String, String> userSendSmsCode(String customMerobile){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("customer_mobile", customMerobile );
		//params.put("order_id",orderId);
		return params;
	}
	public static String API_CUSTOMER_VERIFYSMSCODE="/customer/verifySmsCode";
	/**
	 *customer_id，car_brand，car_number，car_color，car_size，owner_id_number（未确定是否需
	 * @return
	 */
	public HashMap<String, String> userVerifySmsCode(String customMerobile,String smsCode){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("customer_mobile", customMerobile );
		params.put("smsCode",smsCode);
		//params.put("order_id",orderId);
		return params;
	}

    public static String API_CUSTOMER_SEARCHPARKLISTBYLL="/customer/searchParkListByLL";

    /**
     * 某停车场附近的停车场列表
     * parking_latitude
     * @param parking_latitude
     * @param parking_longitude
     * @return
     */
    public HashMap<String, String> searchParkListByLL(String parking_latitude,String parking_longitude){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("parking_latitude", parking_latitude );
        params.put("parking_longitude",parking_longitude);
        return params;
    }

    public static String API_CUSTOMER_SEARCHPARKLISTBYAREA="/customer/searchParkListbyArea";

    /**
     * 搜索停车场列表
     * @param parking_area
     * @return
     */
    public HashMap<String, String> searchParkListbyArea(String parking_area){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("parking_area", parking_area );
        return params;
    }

    public static String API_CUSTOMER_SEARCHPARKLISTBYNAME="/customer/searchParkListbyName";

    /**
     * 搜索停车场列表
     * @param name
     * @return
     */
    public HashMap<String, String> searchParkListbyName(String name){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name );
        return params;
    }
    public static String API_CUSTOMER_SEARCHSAVEPARKLIST="/customer/searchSaveParkList";
    /**
     * 停车场收藏清单
     * @param customer_id
     * @return
     */
    public HashMap<String, String> searchSaveParkList(String customer_id){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("customer_id", customer_id );
        return params;
    }
    public static String API_CUSTOMER_SAVEPARKING="/customer/saveParking";
    /**
     * 收藏停车场
     * @param customer_id
     * @return
     */
    public HashMap<String, String> saveParking(String customer_id,String parking_id){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("customer_id", customer_id );
        params.put("parking_id", parking_id );
        return params;
    }
	public static String API_CUSTOMER_HISTORYORDER="/customer/historyOrder";
	/**
	 * 收藏停车场
	 * @param customer_id
	 * @return
	 */
	public HashMap<String, String> historyOrder(String customer_id){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("customer_id", customer_id );
		return params;
	}


    public static String API_CUSTOMER_SEARCHPARKBYID="/customer/searchParkbyId";
    /**
     * 收藏停车场
     * @param parking_id
     * @return
     */
    public HashMap<String, String> searchParkbyId(String parking_id){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("parking_id", parking_id );
        return params;
    }
	public static String API_CUSTOMER_GETCAR="/customer/getCar";
	/**
	 * 收藏停车场
	 * @param customer_id
	 * @param order_id
	 * @return
	 */
	public HashMap<String, String> getCar(String customer_id,String order_id){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("parking_id", customer_id );
		params.put("order_id",order_id);
		return params;
	}

	public static String API_CUSTOMER_CALCULATEPAY="/customer/calculatePay";
	/**
	 * 支付
	 * @param order_id
	 * @param parking_id
	 * @return
	 */
	public HashMap<String, String> getCalculatePay(String order_id,String parking_id){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("order_id",order_id);
		params.put("parking_id", parking_id );
		return params;
	}

	public static String API_CUSTOMER_COMMENT="/customer/comment";
	/**
	 * 发送评论
	 * @param order_id
	 * @param comment_operater_id
	 * @param comment_level
	 * @param comment_content
	 * @return
	 */
	public HashMap<String, String> getComment(String order_id, String comment_operater_id, String comment_level, String comment_content){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("order_id",order_id);
		params.put("comment_operater_id",comment_operater_id );
		params.put("comment_level",comment_level );
		params.put("comment_content",comment_content );
		return params;
	}


	public static String API_CUSTOMER_UNITPRICE="/customer/getunitprice";
	/**
	 *
	 * @return
	 */
	public HashMap<String, String> getUnitPrice(String villageId, String carNumber){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("villageId", villageId);
		params.put("carNumber", carNumber);
		//params.put("order_id",orderId);
		return params;
	}


	public static String API_CUSTOMER_POSTORDERINFO="/customer/postorderinfo";
	/**
	 *
	 * @return
	 */
	public HashMap<String, String> getPostOrderInfo(String villageId,
													String village_name,
													String carNumber,
													String price,
													String validity_start_time,
													String validity_end_time,
													String customer_id,
													String customer_name,
													String timequantum,
													String order_type,
													String area,
													String county){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("villageId",villageId);
		params.put("village_name",village_name);
		params.put("carNumber",carNumber);
		params.put("price",price);
		params.put("validity_start_time",validity_start_time);
		params.put("validity_end_time",validity_end_time);
		params.put("customer_id",customer_id);
		params.put("customer_name",customer_name);
		params.put("timequantum",timequantum);
		params.put("order_type",order_type);
		params.put("area",area);
		params.put("county",county);
		return params;
	}


	public static String API_CUSTOMER_GETORDERINFO="/customer/getorderinfo";
	/**
	 *
	 * @return
	 */
	public HashMap<String, String> getGetOrderInfo(String customer_id,String index){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("customer_id",customer_id);
		params.put("index",index);
		return params;
	}


	public static String API_CUSTOMER_DELETEORDER="/customer/delOrder";
	/**
	 * @return
	 */
	public HashMap<String, String> deleteOrder(String id){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id );
		return params;
	}
}
