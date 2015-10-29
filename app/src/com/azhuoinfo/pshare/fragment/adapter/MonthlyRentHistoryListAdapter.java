package com.azhuoinfo.pshare.fragment.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.model.FeeOrderInfo;
import com.azhuoinfo.pshare.view.listview.BaseAdapter;

public class MonthlyRentHistoryListAdapter extends BaseAdapter<FeeOrderInfo> {
	
	private Context context;
	private ArrayList<FeeOrderInfo> list;

	public MonthlyRentHistoryListAdapter(Context context, ArrayList<FeeOrderInfo> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
		this.list=list;
		this.context=context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder mViewHolder;
		if (convertView==null) {
			convertView = this.mInflater.inflate(R.layout.listview_item_historymonthlyrent, parent, false);
			mViewHolder=new ViewHolder();
			mViewHolder.tv_living_area=(TextView) convertView.findViewById(R.id.tv_living_area);
			mViewHolder.start_year_time=(TextView) convertView.findViewById(R.id.start_year_time);
			mViewHolder.car_number=(TextView) convertView.findViewById(R.id.car_number);
			mViewHolder.pay_year_time=(TextView) convertView.findViewById(R.id.pay_year_time);

			convertView.setTag(mViewHolder);
		}else{
			mViewHolder=(ViewHolder) convertView.getTag();
		}

		FeeOrderInfo feeOrderInfo = getItems().get(position);

		mViewHolder.tv_living_area.setText(feeOrderInfo.getVillageName());
		mViewHolder.start_year_time.setText(String.format("%s-%s", feeOrderInfo.getValidityStartTime(), feeOrderInfo.getValidityEndTime()));
		mViewHolder.car_number.setText(feeOrderInfo.getCarNumber());
		mViewHolder.pay_year_time.setText("");

		return convertView;
	}
	class ViewHolder{
		//小区
		private TextView tv_living_area;
		//开始的年份
		private TextView start_year_time;
		//车牌号
		private TextView car_number;
		private TextView pay_year_time;
	}

}
