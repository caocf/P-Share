package mobi.cangol.mobile.sdk.utils;


public interface OnDataLoader{

	void onStart();

	void onSuccess(String response);

	void onFailure(String errorCode,String errorResponse) ;
	 
}
