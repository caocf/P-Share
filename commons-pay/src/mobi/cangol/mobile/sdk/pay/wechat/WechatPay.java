package mobi.cangol.mobile.sdk.pay.wechat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import mobi.cangol.mobile.sdk.pay.OnPayResultListener;
import mobi.cangol.mobile.sdk.pay.PayInterface;
import mobi.cangol.mobile.sdk.pay.PayManager;
import mobi.cangol.mobile.sdk.pay.PlaceOrderCallback;
import mobi.cangol.mobile.sdk.utils.HttpUtils;
import mobi.cangol.mobile.sdk.utils.MD5;

public class WechatPay extends PayInterface implements IWXAPIEventHandler {
	public static final String TAG = "WechatPay";
	private Context context;
	private IWXAPI msgApi;
	private String appId;
	private String apiKey;
	private String partnerId;
	private String notifyUrl;
	private AppRegister appRegister;
	private OnPayResultListener onPayResultListener; 
	private String orderId;
	
	public WechatPay(int payType) {
		super(payType);
		this.setUsable(true);
	}

	@Override
	public void initPay(Context context,String... args) {
		this.context=context;
		appId=args[0];
		apiKey=args[1];
		partnerId=args[2];
		notifyUrl=args[3];
		
		
//		appRegister=new AppRegister();
//		IntentFilter filter=new IntentFilter();
//		filter.addAction("com.tencent.mm.plugin.openapi.Intent.ACTION_REFRESH_WXAPP");
//		context.registerReceiver(appRegister, filter);
		
		msgApi = WXAPIFactory.createWXAPI(context, appId);
		msgApi.registerApp(appId);
		
	}

	@Override
	public void destory() {
		//context.unregisterReceiver(appRegister);
	}
	
	class AppRegister extends BroadcastReceiver{
		//微信发出的广播事件ACTION，用以通知app进行注册
		@Override
		public void onReceive(Context context, Intent data) {
			final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
			api.registerApp(appId);
		}
	}

	@Override
	public void onReq(BaseReq req) {
		
	}

	@Override
	public void onResp(BaseResp resp) {
		int errCode=resp.errCode;
		if(errCode==0){
			if(onPayResultListener!=null)
				onPayResultListener.onSuccess(orderId, "支付成功");
		}else if(errCode==-1){
			if(onPayResultListener!=null)
				onPayResultListener.onFailuire(orderId, "支付错误");
		}else{
			if(onPayResultListener!=null)
				onPayResultListener.onCancel(orderId, "用户取消");
		}		
	}
	@Override
	public void toPay(final Context context, final String subject, final String detail,  final String total_fee, final OnPayResultListener onPayResultListener) {
		this.onPayResultListener=onPayResultListener;
		
		AsyncTask<Void,Void,String> asyncTask=new AsyncTask<Void,Void,String>(){
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}
			@Override
			protected String doInBackground(Void... params) {
				//发起同意下单请求,获取预支付交易会话ID(prepay_id)
				String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
				orderId=genOutTradNo();
				String entity = genProductArgs(orderId,subject,detail,total_fee);
				String content=HttpUtils.getInstance(context).executePost(url, entity);
				if (PayManager.DEBUG)
					Log.d(TAG, "content="+content);
				Map<String,String> xml=decodeXml(content);
				return xml.get("prepay_id");
			}
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (PayManager.DEBUG)
					Log.d(TAG, "result="+result);
				//发送支付请求
				msgApi.sendReq(genPayReq(result));
			}
		};
		asyncTask.execute();
	}
	
	@Override
	public void toPay(final Context context, final PlaceOrderCallback orderCallback, OnPayResultListener onPayResultListener) {
		this.onPayResultListener=onPayResultListener;
		
		AsyncTask<Void,Void,String> asyncTask=new AsyncTask<Void,Void,String>(){
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}
			@Override
			protected String doInBackground(Void... params) {
				//这里发起获同意下单请求
				String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
				orderId=orderCallback.getOrderId();
				String entity = genProductArgs(orderId,orderCallback.subject,orderCallback.detail,orderCallback.total_fee);
				String content=HttpUtils.getInstance(context).executePost(url, entity);
				if (PayManager.DEBUG)
					Log.d(TAG, "content="+content);
				Map<String,String> xml=decodeXml(content);
				return xml.get("prepay_id");
			}
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (PayManager.DEBUG)
					Log.d(TAG, "result="+result);
				msgApi.registerApp(appId);
				msgApi.sendReq(genPayReq(result));
			}
		};
		asyncTask.execute();
	}

	/**
	 * 生成订单信息
	 * @param orderId
	 * @param subject
	 * @param detail
	 * @param price
	 * @return
	 */
	private String genProductArgs(String orderId,String subject,String detail,String price) {
		StringBuffer xml = new StringBuffer();
		try {
			String nonceStr = genNonceStr();
			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams.add(new BasicNameValuePair("appid",appId));
			packageParams.add(new BasicNameValuePair("body", subject));
			packageParams.add(new BasicNameValuePair("detail", detail));
			packageParams.add(new BasicNameValuePair("mch_id",partnerId));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url", notifyUrl));
			packageParams.add(new BasicNameValuePair("out_trade_no", orderId));
			packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));
			packageParams.add(new BasicNameValuePair("total_fee", price));
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));

			String sign =sign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));

			String xmlstring = toXml(packageParams);
			return xmlstring;
		} catch (Exception e) {
			Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
			return null;
		}
	}
	/**
	 * 解析统一下单结果->参数map
	 * @param content
	 * @return
	 */
	public Map<String, String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName = parser.getName();
				switch (event) {
					case XmlPullParser.START_DOCUMENT :

						break;
					case XmlPullParser.START_TAG :

						if ("xml".equals(nodeName) == false) {
							// 实例化student对象
							xml.put(nodeName, parser.nextText());
						}
						break;
					case XmlPullParser.END_TAG :
						break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			Log.e("orion", e.toString());
		}
		return null;

	}
	/**
	 * 将订单参数信息 组合为字符串
	 * @param params
	 * @return
	 */
	private String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<" + params.get(i).getName() + ">");

			sb.append(params.get(i).getValue());
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");

		Log.e("orion", sb.toString());
		return sb.toString();
	}
	/**
	 * 签名
	 * @param params
	 * @return
	 */
	public String sign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}	
		sb.append("key="+apiKey);
		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		return appSign;
	}
	/**
	 * 生成订单号
	 * @return
	 */
	private String genOutTradNo() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	/**
	 * 生成支付请求
	 * @param prepay_id
	 * @return
	 */
	private PayReq genPayReq(String prepay_id) {
		PayReq req = new PayReq();
		req.appId =appId;
		req.partnerId =partnerId;
		req.prepayId = prepay_id;
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());

		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = sign(signParams);
		
		if (PayManager.DEBUG)
			Log.d(TAG,"sign=" + req.sign);
		
		return req;
	}
	/**
	 * 随机字符串
	 * @return
	 */
	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	/**
	 * 支付时间戳
	 * @return
	 */
	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	
}
