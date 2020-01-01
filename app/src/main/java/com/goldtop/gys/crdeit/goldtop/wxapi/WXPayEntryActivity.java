package com.goldtop.gys.crdeit.goldtop.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.VipActivity;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

		IWXAPI api = WXAPIFactory.createWXAPI(WXPayEntryActivity.this, null);
		api.registerApp("wx6ba6c1cfaec6442b");
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {
		//Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			switch (resp.errCode) {
				case 0:
					Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT);
					if (UserModel.custLevelSample.indexOf("NORMAL")!=-1) {
						UserModel.custLevelSample = "MEMBER";
					}
					break;
				case -1:
					Toast.makeText(getApplicationContext(), "支付取消", Toast.LENGTH_SHORT);
					break;
				case -2:
					Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT);
					break;
			}

		}
		finish();
	}
}