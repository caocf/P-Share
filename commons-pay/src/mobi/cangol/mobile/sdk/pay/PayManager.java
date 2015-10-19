package mobi.cangol.mobile.sdk.pay;

import mobi.cangol.mobile.sdk.pay.alipay.AlipayPay;
import mobi.cangol.mobile.sdk.pay.wechat.WechatPay;
import android.content.Context;

public class PayManager {

	private static final String TAG = "PayManager";
	private static final String PAY_EORROR = "无有效的支付方式";// 为集成支付宝

	public static final int PAY_TYPE_ALIPAY = 0;// 使用支付宝
	public static final int PAY_TYPE_WECHAT = 1;// 使用微信支付
	private static PayManager instance;
	private PayInterface aliPay;
	private PayInterface webchatPay;
    public static boolean DEBUG=true;
	private PayManager(Context context) {

	}

	public static PayManager getInstance(Context context) {
		if (instance == null) {
			instance = new PayManager(context);
		}
		return instance;
	}
	public void initPay(Context context,int payType,String... args){
		switch (payType) {
			case PAY_TYPE_WECHAT :
				webchatPay = new WechatPay(PAY_TYPE_WECHAT);
				webchatPay.initPay(context, args);
				break;
			case PAY_TYPE_ALIPAY :
				aliPay = new AlipayPay(PAY_TYPE_ALIPAY);
				aliPay.initPay(context, args);
				break;
		}
	}
	/**
	 * 初始化 设置|更换 运营商类型
	 * 
	 * @param context
	 * @param operatorType
	 */
	public PayInterface getPay(final Context context, final int payType) {
		switch (payType) {
			case PAY_TYPE_WECHAT :
				if(webchatPay!=null){
					return webchatPay;
				}else{
					throw new IllegalStateException("payType="+payType+" PayInterface is init");
				}
			case PAY_TYPE_ALIPAY :
				if(aliPay!=null){
					return aliPay;
				}else{
					throw new IllegalStateException("payType="+payType+" PayInterface is init");
				}
		}
		return null;
	}

	/**
	 * 支付
	 * 
	 * @param context
	 * @param order
	 * @param onPayResultListener
	 */
	public void toPay(Context context, int payType, PlaceOrderCallback orderCallback, OnPayResultListener onPayResultListener) {
		PayInterface pay = getPay(context, payType);
		if (pay.isUsable()) {
			pay.toPay(context, orderCallback, onPayResultListener);
		} else {
			if (onPayResultListener != null)
				onPayResultListener.onFailuire(null, PAY_EORROR);
		}
	}
	/**
	 * 支付
	 * 
	 * @param context
	 * @param subject,detail,total_fee
	 * @param onPayResultListener
	 */
	public void toPay(Context context, int payType, String subject,String detail, String total_fee, OnPayResultListener onPayResultListener) {
		PayInterface pay = getPay(context, payType);
		if (pay.isUsable()) {
			pay.toPay(context,subject,detail,total_fee, onPayResultListener);
		} else {
			if (onPayResultListener != null)
				onPayResultListener.onFailuire(null, PAY_EORROR);
		}
	}

    /**
     * 销毁
     */
	public void destory(){
		webchatPay.destory();
		aliPay.destory();
	}
	
}
