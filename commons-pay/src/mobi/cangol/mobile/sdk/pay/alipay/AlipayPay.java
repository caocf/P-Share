package mobi.cangol.mobile.sdk.pay.alipay;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import mobi.cangol.mobile.sdk.pay.OnPayResultListener;
import mobi.cangol.mobile.sdk.pay.PayInterface;
import mobi.cangol.mobile.sdk.pay.PayManager;
import mobi.cangol.mobile.sdk.pay.PlaceOrderCallback;
import mobi.cangol.mobile.sdk.utils.Rsa;

public class AlipayPay extends PayInterface {
	public static final String TAG = "AlipayPay";
	private Context context;
	private String seller;
	private String partnerId;
	private String rsaPrivate;
	private String rsaPublic;
	private String notifyUrl;
	private String orderId;
	public AlipayPay(int payType){
		super(payType);
		this.setUsable(true);
	}

	@Override
	public void initPay(Context context,String ... args) {
		this.context=context;
		seller=args[0];
		partnerId=args[1];
		rsaPrivate=args[2];
		rsaPublic=args[3];
		notifyUrl=args[4];
	}
	@Override
	public void destory() {
		
	}
	/**
	 * 	支付
	 * 订单号由客户端生成getOutTradeNo
	 */
	@Override
	public void toPay(final Context context, final String subject, final String detail, final String total_fee,final OnPayResultListener onPayResultListener) {
		AsyncTask<Void,Void,String> asyncTask=new AsyncTask<Void,Void,String>(){
			
			@Override
			protected String doInBackground(Void... params) {
				//生成订单号
				orderId=getOutTradeNo();
				// 生成订单信息
				String orderInfo = getOrderInfo(orderId,subject,detail,total_fee);
				// 对订单做RSA 签名
				String sign = sign(orderInfo);
				if (PayManager.DEBUG)
					Log.d(TAG, "sign=="+sign);
				try {
					// 仅需对sign 做URL编码
					sign = URLEncoder.encode(sign, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				// 完整的符合支付宝参数规范的订单信息
				final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"+ getSignType();
				
				// 构造PayTask 对象
				PayTask alipay = new PayTask((Activity) context);
				// 调用支付接口，获取支付结果
				String resultStr = alipay.pay(payInfo);
				return resultStr;
			}
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (PayManager.DEBUG)
					Log.d(TAG, "result="+result);
				if(TextUtils.isEmpty(result)){
					if(null != onPayResultListener){
						onPayResultListener.onFailuire(orderId,"未知原因(支付宝返回结果为空)");
					}
					return;
				}
				if(null != onPayResultListener){
					PayResult payResult = new PayResult(result);
					if("9000".equals(payResult.getResultStatus())){
						onPayResultListener.onSuccess(orderId, payResult.getMemo());
					}else{
						onPayResultListener.onFailuire(orderId, payResult.getMemo());
					}
				}
			}
		};
		//执行
		asyncTask.execute();
	}
	/**
	 * 支付
	 * 订单号生成可自定义,由异步回调方法 orderCallback.getOrderId()
	 */
	@Override
	public void toPay(final Context context, final PlaceOrderCallback orderCallback, final OnPayResultListener onPayResultListener) {
		AsyncTask<Void,Void,String> asyncTask=new AsyncTask<Void,Void,String>(){
			
			@Override
			protected String doInBackground(Void... params) {
				//生成订单号
				String orderId=orderCallback.getOrderId();
				// 订单
				String orderInfo = getOrderInfo(orderId,orderCallback.subject,orderCallback.detail,orderCallback.total_fee);
				// 对订单做RSA 签名
				String sign = sign(orderInfo);
				if (PayManager.DEBUG)
					Log.d(TAG, "sign=="+sign);
				try {
					// 仅需对sign 做URL编码
					sign = URLEncoder.encode(sign, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				// 完整的符合支付宝参数规范的订单信息
				final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"+ getSignType();
				
				// 构造PayTask 对象
				PayTask alipay = new PayTask((Activity) context);
				// 调用支付接口，获取支付结果
				String resultStr = alipay.pay(payInfo);
				return resultStr;
			}
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (PayManager.DEBUG)
					Log.d(TAG, "result="+result);
				if(TextUtils.isEmpty(result)){
					if(null != onPayResultListener){
						onPayResultListener.onFailuire(orderId,"未知原因(支付宝返回结果为空)");
					}
					return;
				}
				if(null != onPayResultListener){
					PayResult payResult = new PayResult(result);
					if("9000".equals(payResult.getResultStatus())){
						onPayResultListener.onSuccess(orderId, payResult.getMemo());
					}else{
						onPayResultListener.onFailuire(orderId, payResult.getMemo());
					}
				}
			}
		};
		//执行
		asyncTask.execute();
	}
	
	public String getOrderInfo(String outTradeNo,String subject, String body, String price) {
		
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + partnerId + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + seller + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + outTradeNo + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + notifyUrl+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}
	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}
	/**
	 * sign the order info. 对订单信息进行签名
	 * @param content 待签名订单信息
	 */
	public String sign(String content) {
		return Rsa.sign(content, rsaPrivate);
	}
	/**
	 * get the sign type we use. 获取签名方式
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}
}
