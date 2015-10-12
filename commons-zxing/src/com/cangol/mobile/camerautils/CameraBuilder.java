package com.cangol.mobile.camerautils;

import android.hardware.Camera;
/**
 * 摄像头构造器
 * @Description CameraBuilder.java 
 */
public class CameraBuilder 
{
	/**
	 * 产生一个摄像头构造器
	 * @return
	 */
	public static CameraBuilder build()
	{
		CameraBuilder pBuilder = new CameraBuilder();
		pBuilder.init();
		return pBuilder;
	}
	
	/**
	 * 开启摄像头
	 * @return
	 */
	public Camera open()
	{
		return (null != mInterface ? mInterface.open() : null);
	}
	
	/**
	 * 默认构造方法
	 */
	private CameraBuilder()
	{
	}
	
	/**
	 * 初始化摄像头构造接口
	 */
	private void init()
	{
		mInterface = CameraInterface.getInterface();
	}
	
	CameraInterface mInterface = null;
}
