package com.azhuoinfo.pshare.utils;

public class CountDown {
	private int mCount;
	private int mSuccess;
	private int mFail;
	
	public CountDown(int count){
		this.mCount=count;
	}
	
	public void tick(boolean success){
		mCount--;
		if(success){
			mSuccess++;
		}else{
			mFail++;
		}
	}
	public void begin(){
		mSuccess=0;
		mFail=0;
	}
	public boolean end(){
		return mCount==0;
	}
	public boolean isSuccess(){
		return mFail==0;
	}
	public int getCountSuccess(){
		return mSuccess;
	}
}
