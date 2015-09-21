package com.azhuoinfo.pshare.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;

import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import mobi.cangol.mobile.utils.FileUtils;

public class GalleryUtils {
	public static final String IMAGE_BIG = "_big";
	public static final String IMAGE_MEDIUM = "_medium";
	public static final String IMAGE_SMALL = "_small";
	public static final String IMAGE_TINY = "_tiny";
	
	/**
	 * 获取系统相处图片
	 * @param activity
	 * @return
	 */
	public static ArrayList<String> getGalleryPhotos(Activity activity) {
		ArrayList<String> galleryList = new ArrayList<String>();
		try {
			final String[] columns = { MediaStore.Images.Media.DATA,MediaStore.Images.Media._ID };
			final String orderBy = MediaStore.Images.Media._ID;
			Cursor imagecursor =activity.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,null, null, orderBy);
			if (imagecursor != null && imagecursor.getCount() > 0) {
				while (imagecursor.moveToNext()) {
					int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
					String item = imagecursor.getString(dataColumnIndex);
					galleryList.add(Uri.fromFile(new File(item)).toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// show newest photo at beginning of the list
		Collections.reverse(galleryList);
		return galleryList;
	}
	/**
	 * 获取适应的图片地址
	 * @param context
	 * @param url
	 * @return
	 */
	public static String getResizerImageUrl(Context context,String url) {
		if(url==null)return "";
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics(); 
		String tag=null;
		if(displayMetrics.heightPixels<=480){
			tag=IMAGE_SMALL;
		}else if(displayMetrics.heightPixels<=854){
			tag=IMAGE_SMALL;
		}else if(displayMetrics.heightPixels<=1280&&displayMetrics.density<=2){
			tag=IMAGE_MEDIUM;
		}else if(displayMetrics.heightPixels<=1920&&displayMetrics.density<=3){
			tag=IMAGE_MEDIUM;
		}else{
			tag=IMAGE_BIG;
		}
		tag=url.substring(0, url.lastIndexOf("."))+tag+url.substring(url.lastIndexOf("."));
		return tag;
	}
	public static String getImageThumb(String imagePath){
		return String.format("%s_thumb.jpg", imagePath.toLowerCase().replace(".jpg", ""));
	}
	public static File getTempFile(Context context,String name) {
		File cacheDir = null;
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),Constants.APP_TEMP);
		else
			cacheDir = context.getApplicationContext().getCacheDir();
		if (!cacheDir.exists()){
			cacheDir.mkdirs();
		}else{
			if (cacheDir.isFile()) {
				cacheDir.deleteOnExit();
				cacheDir.mkdirs();
			}
		}
		File temp = new File(cacheDir,name);
		if (!temp.exists()) {
			try {
				temp.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}
	/**
	 * 启动系统缩放裁剪程序
	 * @param context
	 * @param resultCode 返回code
	 * @param uri 图片uri
	 * @param outx 目标宽度
	 * @param outy 目标高度
	 * @param outfile 目标文件路径
	 */
	public static void startSystemPhotoCrop(Fragment context,int resultCode,Uri uri,int outx,int outy,File outfile) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", outx);
		intent.putExtra("outputY", outy);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outfile));
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		try {
			context.startActivityForResult(intent, resultCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保存到相册，并返回相册url
	 * 无存储卡 还是有问题
	 * @param context
	 * @param bitmap
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static Uri saveGalleryImage(Context context, Bitmap bitmap) throws FileNotFoundException{
	    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
	    String path =null;
	    try{
	    	path=Images.Media.insertImage(context.getContentResolver(), bitmap, ""+UUID.randomUUID(), null);
	    }catch (Exception e){
	    	FileUtils.newFolder(StorageUtils.getOwnCacheDirectory(context, Constants.APP_WALLPAPER).getAbsolutePath());
		    path=Images.Media.insertImage(context.getContentResolver(), StorageUtils.getOwnCacheDirectory(context, Constants.APP_WALLPAPER).getAbsolutePath()+"/", ""+UUID.randomUUID()+".jpg", null);
	    }finally{
	    	if(path!=null)
		    	return Uri.parse(path);
	    }
	    return null;
	}
	/**
	 * 保存图片到文件
	 * @param path
	 * @param name
	 * @param bitmap
	 */
	public static void saveImage(String path, String name,Bitmap bitmap) {
		if (android.os.Environment.getExternalStorageState().equals(
		android.os.Environment.MEDIA_MOUNTED)) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File photoFile = new File(path, name); 
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (bitmap != null) {
					if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100,fileOutputStream)) {
						fileOutputStream.flush();
					}
				}
			} catch (FileNotFoundException e) {
				photoFile.delete();
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				e.printStackTrace();
			} finally {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	   
	/**
	 * 通过url获取真实保存地址
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getRealPathFromURI(Context context,Uri uri) {
	    Cursor cursor = context.getContentResolver().query(uri, null, null, null, null); 
	    cursor.moveToFirst(); 
	    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
	    return cursor.getString(idx); 
	}
}
