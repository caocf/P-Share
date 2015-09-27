package com.azhuoinfo.pshare.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.view.listview.BaseAdapter;

import java.util.List;

public class AddLivingAreaAdapter extends BaseAdapter<String> {

	private List<String> list;
	private Context context;

	public AddLivingAreaAdapter(Context context) {
		super(context);
	}

	public AddLivingAreaAdapter(Context context, List<String> items) {
		super(context, items);
		this.context=context;
		this.list=items;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView = this.mInflater.inflate(R.layout.listview_item_add_living_area, parent, false);
			holder=new ViewHolder();
			holder.mImageView=(ImageView) convertView.findViewById(R.id.iv_car);
			holder.mParkingNameTextView=(TextView) convertView.findViewById(R.id.tv_parking_name);
			holder.mParkingAddressTextView=(TextView) convertView.findViewById(R.id.tv_parking_address);
			convertView.setTag(holder);

		}else{
			holder=(ViewHolder)convertView.getTag();
		}

		return convertView;
	}
	static class ViewHolder{
		View layout;
		private ImageView mImageView;
		private TextView mParkingNameTextView;
		private TextView mParkingAddressTextView;
	}
}
