package com.azhuoinfo.pshare.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.model.Parking;
import com.azhuoinfo.pshare.view.listview.BaseAdapter;


public class ParkingDetailsAdapter extends BaseAdapter<Parking> {

    private AMapLocation aMapLocation;
    public ParkingDetailsAdapter(Context context) {
        super(context);
    }

    public AMapLocation getaMapLocation() {
        return aMapLocation;
    }

    public void setaMapLocation(AMapLocation aMapLocation) {
        this.aMapLocation = aMapLocation;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        final Parking item=getItem(position);
        if(null!=convertView){
            holder=(ViewHolder)convertView.getTag();
        }else{
            convertView = this.mInflater.inflate(R.layout.listview_item_parking_details, parent, false);
            holder = new ViewHolder();
            holder.mParkingNameTextView = (TextView) convertView.findViewById(R.id.tv_parking_name);
            holder.mParkingAddressTextView = (TextView) convertView.findViewById(R.id.tv_parking_address);
            holder.mParkingStatusTextView = (TextView) convertView.findViewById(R.id.tv_parking_status);
            holder.mParkingCanUseTextView = (TextView) convertView.findViewById(R.id.tv_parking_can_use);
            holder.mParkingPriceTextView = (TextView) convertView.findViewById(R.id.tv_parking_price);
            holder.mParkingDistanceTextView = (TextView) convertView.findViewById(R.id.tv_parking_distance);
            convertView.setTag(holder);

        }
        holder.mParkingNameTextView.setText(item.getParking_name());
        holder.mParkingAddressTextView.setText(item.getParking_address());
        if("0".equals(item.getParking_status())){
            holder.mParkingStatusTextView.setText("空:");
        }else{
            holder.mParkingStatusTextView.setText("满:");
        }
        holder.mParkingCanUseTextView.setText(""+item.getParking_can_use());
        holder.mParkingPriceTextView.setText("" + item.getParking_charging_standard() + "元/时");
        if(aMapLocation!=null){
            int s= (int) AMapUtils.calculateLineDistance(new LatLng(Double.parseDouble(item.getParking_latitude()), Double.parseDouble(item.getParking_longitude())),
                    new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
            item.setParking_distance(""+s);
            holder.mParkingDistanceTextView.setText(item.getParking_distance() + "米");
        }else{
            holder.mParkingDistanceTextView.setText("");
        }


        return convertView;
    }

    static class ViewHolder {
        TextView mParkingNameTextView;
        TextView mParkingAddressTextView;
        TextView mParkingStatusTextView;
        TextView mParkingCanUseTextView;
        TextView mParkingPriceTextView;
        TextView mParkingDistanceTextView;
    }
}
