package com.azhuoinfo.pshare.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;

import mobi.cangol.mobile.utils.TimeUtils;

@SuppressLint("SimpleDateFormat")
public class DateTimePicker {
	public interface OnTimeSetListener {
		void OnTimeSet(String time);
	}
	public interface OnDateSetListener {
		void OnDateSet(String date);
	}
	public static boolean isValidDate(String str) {
		boolean convertSuccess = true;
		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try {
			df.setLenient(false);
			df.parse(str);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}
	public static boolean isValidTime(String str) {
		boolean convertSuccess = true;
		java.text.DateFormat df = new java.text.SimpleDateFormat("HH:mm");
		try {
			df.setLenient(false);
			df.parse(str);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}
	public static void setDate(Context context, String dateStr,final OnDateSetListener onDateSetListener) {
		if (dateStr == null || "".equals(dateStr)) {
			dateStr = TimeUtils.getCurrentDate();
		} else if (!isValidDate(dateStr)) {
			// 日期格式不正确
			Toast.makeText(context, "日期格式不正确", 0).show();
			return;
		}
		int year = Integer.parseInt(dateStr.substring(0, 4));
		int month = Integer.parseInt(dateStr.substring(5, 7)) - 1;
		int day = Integer.parseInt(dateStr.substring(8, 10));
		new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
				if(onDateSetListener!=null)
					onDateSetListener.OnDateSet(TimeUtils.getFormatDate(year, monthOfYear, dayOfMonth));
			}
		}, year, month, day).show();
	}

	public static void setTime(Context context, String timeStr,final OnTimeSetListener onTimeSetListener) {
		if (timeStr == null || "".equals(timeStr)) {
			timeStr = TimeUtils.getCurrentHoursMinutes();
		} else if (!isValidTime(timeStr)) {
			// 时间格式不正确
			Toast.makeText(context, "时间格式不正确", 0).show();
			return;
		}
		int hour = Integer.parseInt(timeStr.substring(0, 2));
		int minute = Integer.parseInt(timeStr.substring(3));
		new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker picker, int h, int m) {
				if(onTimeSetListener!=null)
					onTimeSetListener.OnTimeSet(TimeUtils.getFormatTime(h, m));
			}
		}, hour, minute, true).show();
	}
}
