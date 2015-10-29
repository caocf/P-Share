package com.azhuoinfo.pshare.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.model.FeeOrderInfo;
import com.azhuoinfo.pshare.view.listview.BaseAdapter;

import java.util.List;

/**
 * Created by Azhuo on 2015/9/23.
 */
public class MonthlyRentListAdapter extends BaseAdapter<FeeOrderInfo>{
    private List<FeeOrderInfo> list;
    private Context context;
    public MonthlyRentListAdapter(Context context) {
        super(context);
    }

    public MonthlyRentListAdapter(Context context, List<FeeOrderInfo> items) {
        super(context, items);
        this.context=context;
        this.list=items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView = this.mInflater.inflate(R.layout.listview_item_monthlyrent, parent, false);
            holder.mCarNumberTextView=(TextView) convertView.findViewById(R.id.tv_car_number);
            holder.mLivingAreaTextView=(TextView) convertView.findViewById(R.id.tv_living_area);
            holder.mRentTimeTextView=(TextView) convertView.findViewById(R.id.tv_rent_time);
            holder.mPayButton=(TextView) convertView.findViewById(R.id.button_pay);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }

        FeeOrderInfo feeOrderInfo = getItems().get(position);

        holder.mCarNumberTextView.setText(feeOrderInfo.getCarNumber());
        holder.mLivingAreaTextView.setText(feeOrderInfo.getVillageName());
        holder.mRentTimeTextView.setText(String.format("%s-%s", feeOrderInfo.getValidityStartTime(), feeOrderInfo.getValidityEndTime()));
        if (feeOrderInfo.getOrderStatus().equals("0")) {
            holder.mPayButton.setEnabled(true);
            holder.mPayButton.setBackgroundResource(R.drawable.button);
            holder.mPayButton.setText("未支付");
        }else {
            holder.mPayButton.setEnabled(false);
            holder.mPayButton.setBackgroundResource(R.drawable.button_false);
            holder.mPayButton.setText("已支付");
        }
        return convertView;
    }

    class ViewHolder{
        View layout;
        private TextView mCarNumberTextView;
        private TextView mLivingAreaTextView;
        private TextView mRentTimeTextView;
        private TextView mPayButton;
}

}
