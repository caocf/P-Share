package mobi.cangol.mobile.sdk.pay;

import android.content.Context;

public abstract class PayInterface {
	
	private boolean mUsable;
	
	private int mPayType;
	
	public PayInterface(int payType){
		mPayType=payType;
	}
	
	public boolean isUsable() {
		return mUsable;
	}

	public void setUsable(boolean mUsable) {
		this.mUsable = mUsable;
	}
	
	public int getPayType() {
		return mPayType;
	}

	abstract public void destory();
	
	abstract public void initPay(Context context,String... args);
	
	abstract public void toPay(Context context,String subject,String detail,String total_fee,String notify_url, OnPayResultListener onPayResultListener) ;
	
	abstract public void toPay(Context context, PlaceOrderCallback orderCallback, OnPayResultListener onPayResultListener) ;
		
		
}
