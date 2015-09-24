package com.azhuoinfo.pshare.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.view.listview.BaseAdapter;

import java.util.List;

/**
 * Created by Azhuo on 2015/9/23.
 */
public class HistoryOrderAdapter extends BaseAdapter<String>{
    private List<String> list;
    private Context context;
    public HistoryOrderAdapter(Context context) {
        super(context);
    }

    public HistoryOrderAdapter(Context context, List<String> items) {
        super(context, items);
        this.context=context;
        this.list=items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView = this.mInflater.inflate(R.layout.listview_item_history_order, parent, false);
            holder.mParkingNameTextView=(TextView) convertView.findViewById(R.id.tv_parking_name);
            holder.mParkingAddressTextView=(TextView) convertView.findViewById(R.id.tv_parking_address);
            holder.mOrFinishTextView=(TextView) convertView.findViewById(R.id.tv_or_finish);
            holder.mOrderPayTimeTextView=(TextView) convertView.findViewById(R.id.tv_order_pay_time);
            holder.mOrderPayMoneyTextView=(TextView) convertView.findViewById(R.id.tv_order_pay_money);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder{
        View layout;
        private TextView mParkingNameTextView;
        private TextView mParkingAddressTextView;
        private TextView mOrFinishTextView;
        private TextView mOrderPayTimeTextView;
        private TextView mOrderPayMoneyTextView;
}

}
