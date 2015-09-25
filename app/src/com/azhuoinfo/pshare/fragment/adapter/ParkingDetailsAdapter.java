package com.azhuoinfo.pshare.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.view.listview.BaseAdapter;

import java.util.List;

public class ParkingDetailsAdapter extends BaseAdapter<String> {

	private List<String> list;
	private Context context;

	public ParkingDetailsAdapter(Context context) {
		super(context);
	}

	public ParkingDetailsAdapter(Context context, List<String> items) {
		super(context, items);
		this.context=context;
		this.list=items;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView = this.mInflater.inflate(R.layout.listview_item_parking_details, parent, false);
			holder=new ViewHolder();
			holder.mParkingNameTextView=(TextView) convertView.findViewById(R.id.tv_parking_name);
			holder.mParkingAddressTextView=(TextView) convertView.findViewById(R.id.tv_parking_address);
			holder.mParkingStatusTextView=(TextView) convertView.findViewById(R.id.tv_parking_status);
			holder.mParkingCanUseTextView=(TextView) convertView.findViewById(R.id.tv_parking_can_use);
			holder.mParkingPriceTextView=(TextView) convertView.findViewById(R.id.tv_parking_price);
			holder.mParkingDistanceTextView=(TextView) convertView.findViewById(R.id.tv_parking_distance);
			convertView.setTag(holder);

		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		//holder.mCarNumberTextView.setText(list.get(position).toString());
		return convertView;
	}
	static class ViewHolder{
		View layout;
		private TextView mParkingNameTextView;
		private TextView mParkingAddressTextView;
		private TextView mParkingStatusTextView;
		private TextView mParkingCanUseTextView;
		private TextView mParkingPriceTextView;
		private TextView mParkingDistanceTextView;
	}
}
