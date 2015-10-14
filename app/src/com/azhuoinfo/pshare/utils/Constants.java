package com.azhuoinfo.pshare.utils;

public class Constants {
	// 开发模式 显示log、生命周期等
	public static final boolean DEVMODE = true;

	public static final int DATABASE_VERSION = 1;

	public static final String DATABASE_NAME = "app";

	public static final String SHARED = "app";

	public static final String TAG = "app_";

	public static final String APP_DIR = "/pshare";

	public static final String APP_TEMP = APP_DIR + "/temp";

	public static final String APP_IMAGE = APP_DIR + "/image";

	public static final String APP_WALLPAPER = APP_DIR + "/wallpaper";

	public static final String APP_DB = "app.db";

	public static final int LOGIN_REQUEST_CODE = 0x001;

	public static final int PAGE_SIZE = 10;

	public static final String KEY_MESSAGE_LAST_TIME="message_last_time_";
	public static final String KEY_USED_VERSION="is_new_version";
	public static final String KEY_EXIT_CODE="exit_code";
	public static final String KEY_EXIT_VERSION="exit_version";
	public static final String KEY_CHECK_UPGRADE="check_upgrade";
	public static final String KEY_LAST_LOGIN_DATE="last_login_date";
	public static final String KEY_IS_LOADING="is_loading";
	public static final String KEY_IS_NEW_USER="is_new_user";
	
	public static final String ACTION_PICK = "APP.ACTION_PICK";
	public static final String ACTION_MULTIPLE_PICK = "APP.ACTION_MULTIPLE_PICK";
	
	public static final String DATA_GALLERY_PICK = "DATA_GALLERY_PICK";
	public static final String DATA_GALLERY_MULTIPLE_PICK = "DATA_GALLERY_MULTIPLE_PICK";
	
	public static final int MAX_SELECTED=8;
}
