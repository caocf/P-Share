package mobi.cangol.mobile.sdk.pay;

public abstract class PlaceOrderCallback {
	public String out_trade_no;//订单号
	public String subject;//商品名称
	public String detail;//商品详情
	public String total_fee;//总金额
    public String notify_url;//回调地址
	
	public  PlaceOrderCallback(String subject, String detail, String total_fee,String notify_url){
		this.out_trade_no=null;
		this.subject=subject;
		this.detail=detail;
		this.total_fee=total_fee;
        this.notify_url=notify_url;
	}
    public abstract String getOrderId();
	
}
