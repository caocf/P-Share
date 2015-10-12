/**
 * Define the constants for zxing.
 */

package com.google.zxing.client.android;

/**
 * 资源配配置文件（如修改资源id 请在此配置）
 * @Description ResConfig.java 
 */
 final class ResConfig {
	static final int RES_RAW_BEEP              = R.raw.beep;
	static final int RES_ID_DECODE             = R.id.decode;
	static final int RES_ID_QUIT               = R.id.quit;
	static final int RES_ID_DECODE_SUCC        = R.id.decode_succeeded;
	static final int RES_ID_DECODE_FAIL        = R.id.decode_failed;
	//static final int RES_ID_AUTO_FOCUS         = R.id.auto_focus;
	static final int RES_ID_RESTART_PREVIEW    = R.id.restart_preview;
	static final int RES_ID_RETURN_RESULT      = R.id.return_scan_result;
	static final int RES_ID_LAUNCH_QUERY       = R.id.launch_product_query;
	
	// CaptureActivity Resource definition.
	static final int RES_LAYOUT_CAPTURE        = R.layout.capture;
	static final int RES_ID_PREVIEW_VIEW       = R.id.preview_view;
	static final int RES_ID_RESULT_VIEW        = R.id.result_view;
	static final int RES_ID_VIEWFINDER_VIEW    = R.id.viewfinder_view;
	static final int RES_ID_STATUS_VIEW        = R.id.status_view;
	static final int RES_ID_FORMAT_TEXT_VIEW   = R.id.format_text_view;
	static final int RES_ID_BARCODE_IMG_VIEW   = R.id.barcode_image_view;
	static final int RES_ID_TYPE_TEXT_VIEW     = R.id.type_text_view;
	static final int RES_ID_TIME_TEXT_VIEW     = R.id.time_text_view;
	static final int RES_ID_META_TEXT_VIEW     = R.id.meta_text_view;
	static final int RES_ID_META_TEXT_LABEL    = R.id.meta_text_view_label;
	static final int RES_ID_CONTENTS_VIEW      = R.id.contents_text_view;
	static final int RES_ID_SUPPLEMENT_VIEW    = R.id.contents_supplement_text_view;
	
	// Color
	static final int RES_COLOR_RESULT_POINTS   = R.color.result_points;
	static final int RES_COLOR_RESULT_BORDER   = R.color.result_image_border;
	static final int RES_COLOR_VIEWFINDER_MASK = R.color.viewfinder_mask;
	static final int RES_COLOR_RESULT_VIEW     = R.color.result_view;
	static final int RES_COLOR_FINDER_FRAME    = R.color.viewfinder_frame;
	static final int RES_COLOR_FINDER_LASER    = R.color.viewfinder_laser;
	static final int RES_COLOR_POSSIBLE_POINTS = R.color.possible_result_points;
	
	// Drawable.
	static final int RES_DRAWABLE_ICON         = R.drawable.launcher_icon;
	static final int RES_IMG_SCANLINE          = R.drawable.scan_line;  
	
	// String
	static final int RES_STRING_APP_NAME       = R.string.app_name;
	static final int RES_STRING_MSG_BUG        = R.string.msg_camera_framework_bug;
	static final int RES_STRING_OKAY           = R.string.okay;
	static final int RES_STRING_MSG_DEFAULT    = R.string.msg_default_status;
}
