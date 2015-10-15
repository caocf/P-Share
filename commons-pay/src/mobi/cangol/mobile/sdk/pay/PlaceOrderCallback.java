package mobi.cangol.mobile.sdk.pay;

public abstract class PlaceOrderCallback {
	public String out_trade_no;//订单号
	public String subject;//商品名称
	public String detail;//商品详情
	public String total_fee;//总金额
	
	public  PlaceOrderCallback(String subject, String detail, String total_fee){
		this.out_trade_no=null;
		this.subject=subject;
		this.detail=detail;
		this.total_fee=total_fee;
	}
    public abstract String getOrderId();
	
}
