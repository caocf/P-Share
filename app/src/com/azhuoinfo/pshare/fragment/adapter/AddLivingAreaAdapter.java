package com.azhuoinfo.pshare.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.model.Parking;
import com.azhuoinfo.pshare.view.listview.BaseAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class AddLivingAreaAdapter extends BaseAdapter<Parking> {
    private ImageLoader mImageLoader;
    public AddLivingAreaAdapter(Context context) {
        super(context);
        mImageLoader=ImageLoader.getInstance();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.listview_item_add_living_area, parent, false);
            holder = new ViewHolder();
            holder.mImageView = (ImageView) convertView.findViewById(R.id.iv_car);
            holder.mParkingNameTextView = (TextView) convertView.findViewById(R.id.tv_parking_name);
            holder.mParkingAddressTextView = (TextView) convertView.findViewById(R.id.tv_parking_address);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Parking item=getItem(position);

        holder.mParkingNameTextView.setText(item.getParking_name());
        holder.mParkingAddressTextView.setText(item.getParking_address());
        //mImageLoader.displayImage(item.getParking_path(),holder.mImageView);
        return convertView;
    }

    static class ViewHolder {
        ImageView mImageView;
        TextView mParkingNameTextView;
        TextView mParkingAddressTextView;
    }
}
