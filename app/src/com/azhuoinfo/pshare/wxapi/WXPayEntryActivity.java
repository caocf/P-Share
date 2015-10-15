package com.azhuoinfo.pshare.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.utils.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.sdk.modelpay.PayResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.sdk.pay.PayManager;
import mobi.cangol.mobile.sdk.pay.wechat.WechatPay;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "WXPayEntryActivity";

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_result);
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		// api.handleIntent(getIntent(), this);
		handIntent(getIntent());
	}
	private void handIntent(Intent intent) {
		Log.e(TAG, "handIntent ");
		int i = intent.getIntExtra("_wxapi_command_type", 0);
		switch (i) {
			case 4 :
				BaseReq req = new ShowMessageFromWX.Req(intent.getExtras());
				onReq(req);
			case 5 :
				PayResp resp = new PayResp(intent.getExtras());
				onResp(resp);
		}
	}
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		// setIntent(intent);
		// api.handleIntent(intent, this);
		handIntent(getIntent());
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.e(TAG, "onPayFinish, errCode = " + resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX){
			WechatPay wechatPay = (WechatPay) PayManager.getInstance(this).getPay(this, PayManager.PAY_TYPE_WECHAT);
			wechatPay.onResp(resp);
		}
		finish();
	}
}