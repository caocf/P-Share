package mobi.cangol.mobile.sdk.pay;

public interface OnPayResultListener {
	
	void onSuccess(String billingIndex,String msg);
	
	void onFailuire(String billingIndex,String errorMsg);
	
	void onCancel(String billingIndex, String errorMsg);
}
