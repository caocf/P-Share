package com.azhuoinfo.pshare;

import android.content.Context;
import android.content.SharedPreferences;

import com.azhuoinfo.pshare.db.DatabaseHelper;
import com.azhuoinfo.pshare.utils.Constants;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.OpenUDID.OpenUDID_manager;

import mobi.cangol.mobile.CoreApplication;
import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.service.AppService;
import mobi.cangol.mobile.service.ServiceProperty;
import mobi.cangol.mobile.service.conf.ConfigService;
import mobi.cangol.mobile.service.crash.CrashReportListener;
import mobi.cangol.mobile.service.crash.CrashService;
import mobi.cangol.mobile.service.global.GlobalData;
import mobi.cangol.mobile.service.status.StatusService;
import mobi.cangol.mobile.utils.DeviceInfo;
import mobi.cangol.mobile.utils.FileUtils;

public class MobileApplication extends CoreApplication {
	public final String TAG = "MobileApplication";
	private static final boolean LIFECYCLE = Constants.DEVMODE;
	private GlobalData globalData;
	private StatusService statusService;
	private AccountVerify accountVerify;
	private DatabaseHelper databaseHelper;
	private long starttime;
	private AppStatusListener appStatusListener;
	@Override
	public void onCreate() {
		if (LIFECYCLE)
			Log.v("onCreate "+System.currentTimeMillis());
		this.setDevMode(Constants.DEVMODE);
		super.onCreate();
		init();
	}

	/**
	 * 
	 */
	public void init() {
		if (LIFECYCLE)
			Log.v("init");
		
		Log.v("starttime:"+(starttime=System.currentTimeMillis()));
		initStatus();
		Log.v("initStatus QOS:"+((System.currentTimeMillis()-starttime)/1000.0f));
		
		Log.v("starttime:"+(starttime=System.currentTimeMillis()));
		initAppService();
		Log.v("initAppService QOS:"+((System.currentTimeMillis()-starttime)/1000.0f));
		
		Log.v("starttime:"+(starttime=System.currentTimeMillis()));
		initDataBase();
		Log.v("initDataBase QOS:"+((System.currentTimeMillis()-starttime)/1000.0f));
		
		Log.v("starttime:"+(starttime=System.currentTimeMillis()));
		initDownload();
		Log.v("initDownload QOS:"+((System.currentTimeMillis()-starttime)/1000.0f));
		
		Log.v("starttime:"+(starttime=System.currentTimeMillis()));
		initPush();
		Log.v("initPush QOS:"+((System.currentTimeMillis()-starttime)/1000.0f));
		
		Log.v("starttime:"+(starttime=System.currentTimeMillis()));
		initImageLoader();
		Log.v("initImageLoader QOS:"+((System.currentTimeMillis()-starttime)/1000.0f));
		
		SharedPreferences sharedPreferences=this.getSharedPreferences(Constants.SHARED, Context.MODE_PRIVATE);
		if(!sharedPreferences.contains("CHANNEL_ID")){
			String channel_id=String.valueOf(DeviceInfo.getAppMetaData(this, "CHANNEL_ID")) ;
			sharedPreferences.edit().putString("CHANNEL_ID", channel_id).commit();
		}
	}
	
	private void initImageLoader(){
		ImageLoaderConfiguration config =null;
		DisplayImageOptions dio = new DisplayImageOptions.Builder()
        .cacheOnDisk(true)
        .imageScaleType(ImageScaleType.EXACTLY)
        .build();
		if(this.isDevMode()){
			config = new ImageLoaderConfiguration.Builder(this)
			.defaultDisplayImageOptions(dio)
			.threadPriority(Thread.NORM_PRIORITY - 2)
			.denyCacheImageMultipleSizesInMemory()
			.diskCache(new UnlimitedDiscCache(StorageUtils.getOwnCacheDirectory(getApplicationContext(), Constants.APP_IMAGE)))
			.diskCacheFileNameGenerator(new Md5FileNameGenerator())
			.diskCacheSize(200*1024*1024)
			.memoryCache(new WeakMemoryCache())
			.tasksProcessingOrder(QueueProcessingType.LIFO)
			//.writeDebugLogs() 
			.build();
		}else{
			
			config = new ImageLoaderConfiguration.Builder(this)
			.defaultDisplayImageOptions(dio)
			.threadPriority(Thread.NORM_PRIORITY - 2)
			.denyCacheImageMultipleSizesInMemory()
			.diskCache(new UnlimitedDiscCache(StorageUtils.getOwnCacheDirectory(getApplicationContext(), Constants.APP_IMAGE)))
			.diskCacheFileNameGenerator(new Md5FileNameGenerator())
			.diskCacheSize(200*1024*1024)
			//.memoryCache(new WeakMemoryCache())
			.tasksProcessingOrder(QueueProcessingType.LIFO)
			.build();
		}
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
	
	private void initPush(){
		Log.d("初始化JPush");
	}

	private void initStatus() {
		if (LIFECYCLE)
			Log.v("initStatus");
		Log.d("MD5Fingerprint "+DeviceInfo.getMD5Fingerprint(this));
		
		Log.d("初始化OpenUDID");
		OpenUDID_manager.sync(this);
		
		Log.d("初始化AccountVerify");
		accountVerify = AccountVerify.getInstance(this);
	}

	public void initAppService() {
		if (LIFECYCLE)
			Log.v("initAppService");
		
		Log.d("初始化ConfigService");
		ConfigService configService = (ConfigService) getAppService(AppService.CONFIG_SERVICE);
		ServiceProperty p=configService.getServiceProperty();
		p.putString(ConfigService.APP_DIR, Constants.APP_DIR);
		p.putString(ConfigService.SHARED_NAME, Constants.SHARED);
		
		Log.v("getAppDir:" + configService.getAppDir());
		
		Log.d("初始化StatusService");
		statusService = (StatusService) getAppService(AppService.STATUS_SERVICE);
		appStatusListener = new AppStatusListener();
		statusService.registerStatusListener(appStatusListener);
		
		Log.d("初始化GlobalData");
		globalData=(GlobalData) getAppService(AppService.GLOBAL_DATA);
		
		
		Log.d("初始化CrashService");
		CrashService crashService = (CrashService) getAppService(AppService.CRASH_SERVICE);
		crashService.setDebug(false);
		Log.d("开发模式 不报告crash");
		if(!this.isDevMode())
			crashService.report(new CrashReportListener(){
	
				@Override
				public void report(String path,String error,String position,String context,String timestamp,String fatal) {
					Log.d("sendException "+error);
					FileUtils.delFileAsync(path);
				}
				
		});
		
	}


	public void initDataBase() {
		databaseHelper=DatabaseHelper.createDataBaseHelper(this);
		Log.d(TAG,"Database: Name="+databaseHelper.getDataBaseName()+", Version="+databaseHelper.getDataBaseVersion());
	}

	public void initDownload() {
		
	}
	public AccountVerify getAccountVerify() {
		return accountVerify;
	}

	public void setAccountVerify(AccountVerify accountVerify) {
		this.accountVerify = accountVerify;
	}

	@Override
	public void exit() {
		//完全退出,停止PUSH
		//JPushInterface.stopPush(this);
		//JPushInterface.onKillProcess(this);
		ImageLoader.getInstance().destroy();
		super.exit();
	}
	
}
