package com.azhuoinfo.pshare.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class CountDownTextView extends TextView {
	public static long DAY = 24 * 60 * 60 * 1000;
	public static long HOUR = 60 * 60 * 1000;
	public static long MINUTE = 60 * 1000;
	public static long SECOND = 1000;
	public static long INTERVAL = 1000;

	private Handler mCountDownHandler;
	private OnCountDownListener mOnCountDownListener;
	private long mInterval = INTERVAL;
	private String mTips;

	private long millisInFuture;

	public CountDownTextView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CountDownTextView(Context context) {
		super(context);
	}

	public CountDownTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setInterval(long interval) {
		this.mInterval = interval;
	}

	public void setTips(String tips) {
		mTips = tips;
	}

	public void setTips(int resId) {
		mTips = getResources().getString(resId);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		if (mCountDownHandler != null)
			mCountDownHandler.removeMessages(1);
		super.setText(text, type);
	}

	public void stopTime() {
		if (mCountDownHandler != null)
			mCountDownHandler.removeMessages(1);
	}

	public void starTimeByStopTimeInFuture(long stopTimeInFuture) {
		Log.d(">>", "starTimeByMillisInFuture="+stopTimeInFuture);
		millisInFuture = stopTimeInFuture - System.currentTimeMillis();
		if (mCountDownHandler != null)
			mCountDownHandler.removeMessages(1);
		if (millisInFuture > 0) {
			mCountDownHandler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					if (msg.what == 1) {
						millisInFuture = millisInFuture - 1000;
						if(millisInFuture>0){
							updateTextView(millisInFuture);
							mCountDownHandler.sendEmptyMessageDelayed(1, 1000L);
						}else{
							mCountDownHandler.removeMessages(1);
							if (mOnCountDownListener != null)
								mOnCountDownListener.onFinish();
						}
					}
				}

			};
			mCountDownHandler.sendEmptyMessage(1);
		} else {
			Log.d(">>","stopTime=" + stopTimeInFuture + ",curTime="+ System.currentTimeMillis());
			if (mOnCountDownListener != null)
				mOnCountDownListener.onFinish();
		}
	}

	/**
	 * 传入还有多久结束,开始倒计时
	 * 
	 * @param millisInFuture
	 *            单位为毫秒
	 */
	public void starTimeByMillisInFuture(long millis) {
		Log.d(">>", "starTimeByMillisInFuture="+millis);
		if (mCountDownHandler != null)
			mCountDownHandler.removeMessages(1);
		this.millisInFuture = millis;
		if (millisInFuture > 0) {

			mCountDownHandler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					if (msg.what == 1) {
						if(millisInFuture>0){
							millisInFuture = millisInFuture - 1000;
							updateTextView(millisInFuture);
							mCountDownHandler.sendEmptyMessageDelayed(1, 1000L);
						}else{
							mCountDownHandler.removeMessages(1);
							if (mOnCountDownListener != null)
								mOnCountDownListener.onFinish();
						}
					}
				}

			};
			mCountDownHandler.sendEmptyMessage(1);
		} else {
			if (mOnCountDownListener != null)
				mOnCountDownListener.onFinish();
		}

	}
	private void updateTextView(long millis){
		setText(TextUtils.isEmpty(mTips) ? formatTime(millis): mTips + formatTime(millis));
	}
	private String formatTime(long time) {

		StringBuilder builder = new StringBuilder();
		long day = time / DAY;
		long hour = (time % DAY) / HOUR;
		long minute = (time % HOUR) / MINUTE;
		long second = (time % MINUTE) / SECOND;

		if (day > 0) {
			builder.append(day + "天");
			if (mInterval <= HOUR)
				builder.append(new java.text.DecimalFormat("00").format(hour)
						+ "时");
			if (mInterval <= MINUTE)
				builder.append(new java.text.DecimalFormat("00").format(minute)
						+ "分");
			if (mInterval <= SECOND)
				builder.append(new java.text.DecimalFormat("00").format(second)
						+ "秒");
		} else {
			if (hour > 0) {
				if (mInterval <= HOUR)
					builder.append(new java.text.DecimalFormat("00")
							.format(hour) + "时");
				if (mInterval <= MINUTE)
					builder.append(new java.text.DecimalFormat("00")
							.format(minute) + "分");
				if (mInterval <= SECOND)
					builder.append(new java.text.DecimalFormat("00")
							.format(second) + "秒");
			} else {
				if (minute > 0) {
					if (mInterval <= MINUTE)
						builder.append(new java.text.DecimalFormat("00")
								.format(minute) + "分");
					if (mInterval <= SECOND)
						builder.append(new java.text.DecimalFormat("00")
								.format(second) + "秒");
				} else {
					if (mInterval <= SECOND)
						builder.append(new java.text.DecimalFormat("00")
								.format(second) + "秒");
				}
			}
		}
		return builder.toString();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mCountDownHandler != null)
			mCountDownHandler.removeMessages(1);
		mCountDownHandler = null;
	}

	public void setOnCountDownListener(OnCountDownListener onCountDownListener) {
		this.mOnCountDownListener = onCountDownListener;
	}

	public interface OnCountDownListener {
		void onFinish();
	}
}
