package com.cangol.mobile.camerautils;

import android.hardware.Camera;
import android.os.Build;

abstract class CameraInterface 
{
	/**
	 * 开启摄像头
	 * @return 返回一个新的摄像头
	 */
	public abstract Camera open();
	
	/**
	 * Android API<9 版本未提供CameraInfo支持 
	 */
	private static final class InterfaceBeforeApi9 extends CameraInterface
	{
		@Override
		public Camera open() 
		{
			return Camera.open();
		}
	}
	
	/**
	 * Android API>=9 版本提供CameraInfo支持 ,支持多个摄像头 CameraInfo可获取摄像头位置信息
	 *
	 */
	private static final class InterfaceApi9 extends CameraInterface
	{

		@Override
		public Camera open() 
		{
			// 获取摄像头数量
			final int nNumOfCameras = Camera.getNumberOfCameras();
			if( 0 >= nNumOfCameras )
				return null;
			
			int nPos = -1;
			for( int nIdx = 0; nIdx < nNumOfCameras; nIdx++ )
			{
				Camera.CameraInfo pInfo = new Camera.CameraInfo();
				Camera.getCameraInfo(nIdx, pInfo);
				
				if( null != pInfo )
				{
					// 优先使用后置摄像头
					if( Camera.CameraInfo.CAMERA_FACING_BACK == pInfo.facing )
					{
						nPos = nIdx;
						break;
					}
					// 无后置则使用前置摄像头
					else if( Camera.CameraInfo.CAMERA_FACING_FRONT == pInfo.facing )
					{
						if( 0 > nPos )
						{
							nPos = nIdx;
						}
					}
				}
			}
			
			if( 0 > nPos )
				nPos = 0;
			
			return Camera.open(nPos);
		}
	}
	
	/**
	 * 根据API >=9 配置对应接口
	 * @return
	 */
	public static CameraInterface getInterface()
	{
		final int nVersion = Integer.valueOf(Build.VERSION.SDK);
		return (nVersion >= 9 ? new InterfaceApi9() : new InterfaceBeforeApi9());
	}
}



