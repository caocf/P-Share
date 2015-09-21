package com.azhuoinfo.pshare.view.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.R;

public class LoadMoreFooter extends LinearLayout {
	private Context mContext;
	
	private View mContainerView;
	private ProgressBar mProgressView;
	private TextView mTextView;
	private boolean mIsLoading;
	public LoadMoreFooter(Context context) {
		super(context);
		initView(context);
	}
	
	public LoadMoreFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
	
	private void initView(Context context) {
		mContext = context;
		mContainerView = (RelativeLayout)LayoutInflater.from(mContext).inflate(R.layout.common_view_footer, null);
		addView(mContainerView,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
		
		mProgressView = (ProgressBar) mContainerView.findViewById(R.id.common_footer_progress);
		mTextView = (TextView)mContainerView.findViewById(R.id.common_footer_text);
		
	}
	
	public boolean isLoading() {
		return mIsLoading;
	}
	public void showNormal() {
		mIsLoading=false;
		mTextView.setVisibility(View.VISIBLE);
		mProgressView.setVisibility(View.INVISIBLE);
		mTextView.setText(R.string.common_footer_hint_normal);
		
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContainerView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContainerView.setLayoutParams(lp);
	}
	
	public void showLoading() {
		mIsLoading=true;
		mTextView.setVisibility(View.VISIBLE);
		mProgressView.setVisibility(View.VISIBLE);
		mTextView.setText(R.string.common_footer_hint_loading);
		
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContainerView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContainerView.setLayoutParams(lp);
	}

	public void showHide() {
		mIsLoading=false;
		mTextView.setVisibility(View.INVISIBLE);
		mProgressView.setVisibility(View.INVISIBLE);
		mTextView.setText(null);
		
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContainerView.getLayoutParams();
		lp.height = 0;
		mContainerView.setLayoutParams(lp);
	}
	
}
