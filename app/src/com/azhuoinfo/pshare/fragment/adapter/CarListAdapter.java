package com.azhuoinfo.pshare.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.model.CarList;
import com.azhuoinfo.pshare.model.Message;
import com.azhuoinfo.pshare.view.listview.BaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.utils.StringUtils;
import mobi.cangol.mobile.utils.TimeUtils;

public class CarListAdapter extends BaseAdapter<CarList> {


	public CarListAdapter(Context context) {
		super(context);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView = this.mInflater.inflate(R.layout.listview_item_car, parent, false);
			holder=new ViewHolder();
			holder.mCarImageView=(ImageView) convertView.findViewById(R.id.iv_car);
			holder.mCarNumberTextView=(TextView) convertView.findViewById(R.id.tv_car_number);
			holder.mCarBrandTextView=(TextView) convertView.findViewById(R.id.tv_car_brand);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
        CarList item=this.getItem(position);
		holder.mCarNumberTextView.setText(item.getCar_number());
		holder.mCarBrandTextView.setText(item.getCar_brand());
		return convertView;
	}
	static class ViewHolder {
		 ImageView mCarImageView;
		 TextView mCarNumberTextView;
		 TextView mCarBrandTextView;
	}
}
