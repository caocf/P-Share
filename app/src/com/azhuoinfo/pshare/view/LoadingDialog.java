package com.azhuoinfo.pshare.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.azhuoinfo.pshare.R;

/**
 * @Description:
 * @version $Revision: 1.0 $
 * @author xuewu.wei
 * @date: 2012-6-5
 */
public class LoadingDialog extends Dialog {

	private TextView mMessageView;
	private ProgressBar mProgressView;

	private LoadingDialog(Context context) {
		super(context);
	}

	private LoadingDialog(Context context, int theme) {
		super(context, theme);
	}

	private void initViews() {
		mProgressView = (ProgressBar) findViewById(R.id.common_dialog_loading_progress);
		mMessageView = (TextView) findViewById(R.id.common_dialog_loading_message);
	}

	public static LoadingDialog show(Context context) {
		return create(context).show(R.string.common_loading);
	}
	public static LoadingDialog show(Context context,int strId) {
		return create(context).show(strId);
	}
	public static LoadingDialog show(Context context,String str) {
		return create(context).show(str);
	}
	public static LoadingDialog create(Context context) {
		LoadingDialog dialog = new LoadingDialog(context,
				R.style.TransparentDialog);
		dialog.setContentView(R.layout.common_dialog_loading);
		dialog.initViews();
		dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}
	
	public  LoadingDialog show(String message) {
		this.setMessage(message);
		show();
		return this;
	}
	public  LoadingDialog show(int id) {
		this.setMessage(id);
		show();
		return this;
	}
	public LoadingDialog setMessage(int id) {
		if (mMessageView != null) {
			mMessageView.setText(id);
		}
		return this;
	}
	public LoadingDialog setMessage(String message) {
		if (mMessageView != null) {
			mMessageView.setText(message);
		}
		return this;
	}

}
