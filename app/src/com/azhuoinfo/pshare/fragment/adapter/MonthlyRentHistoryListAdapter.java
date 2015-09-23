package com.azhuoinfo.pshare.fragment.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.view.listview.BaseAdapter;

public class MonthlyRentHistoryListAdapter extends BaseAdapter<String> {
	
	private Context context;
	private ArrayList<String> list;

	public MonthlyRentHistoryListAdapter(Context context, ArrayList<String> list) {
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
			mViewHolder.tv_living_area_address=(TextView) convertView.findViewById(R.id.tv_living_area_address);
			mViewHolder.start_year_time=(TextView) convertView.findViewById(R.id.start_year_time);
			mViewHolder.start_month_time=(TextView) convertView.findViewById(R.id.start_month_time);
			mViewHolder.start_day_time=(TextView) convertView.findViewById(R.id.start_day_time);
			mViewHolder.finish_year_time=(TextView) convertView.findViewById(R.id.finish_year_time);
			mViewHolder.finish_month_time=(TextView) convertView.findViewById(R.id.finish_month_time);
			mViewHolder.finish_day_time=(TextView) convertView.findViewById(R.id.finish_day_time);
			mViewHolder.car_number=(TextView) convertView.findViewById(R.id.car_number);
			mViewHolder.pay_money=(TextView) convertView.findViewById(R.id.pay_money);
			mViewHolder.pay_year_time=(TextView) convertView.findViewById(R.id.pay_year_time);
			mViewHolder.pay_month_time=(TextView) convertView.findViewById(R.id.pay_month_time);
			mViewHolder.pay_day_time=(TextView) convertView.findViewById(R.id.pay_day_time);
			mViewHolder.pay_hours_time=(TextView) convertView.findViewById(R.id.pay_hours_time);
			convertView.setTag(mViewHolder);
		}else{
			mViewHolder=(ViewHolder) convertView.getTag();
		}
		
		return convertView;
	}
	class ViewHolder{
		//小区
		private TextView tv_living_area;
		//具体地址
		private TextView tv_living_area_address;
		//开始的年份
		private TextView start_year_time;
		//开始的月份
		private TextView start_month_time;
		//开始的日
		private TextView start_day_time;
		//结束的年份
		private TextView finish_year_time;
		//结束的月份
		private TextView finish_month_time;
		//结束的天
		private TextView finish_day_time;
		//车牌号
		private TextView car_number;
		//付款金额
		private TextView pay_money;
		//付款年时间
		private TextView pay_year_time;
		//付款月时间
		private TextView pay_month_time;
		//付款日时间
		private TextView pay_day_time;
		//付款小时时间
		private TextView pay_hours_time;
	}
	
}
