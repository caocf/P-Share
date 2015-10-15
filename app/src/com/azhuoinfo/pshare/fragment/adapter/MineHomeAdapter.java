package com.azhuoinfo.pshare.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.model.Parking;
import com.azhuoinfo.pshare.view.listview.BaseAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobi.cangol.mobile.utils.StringUtils;

public class MineHomeAdapter extends BaseAdapter<Parking> {

    private String defaultId;
	public MineHomeAdapter(Context context) {
		super(context);
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView = this.mInflater.inflate(R.layout.listview_item_minehome, parent, false);
			holder=new ViewHolder();
			/*holder.name= (TextView) convertView.findViewById(R.id.tv_minehome_name);
            holder.icon= (ImageView) convertView.findViewById(R.id.tv_minehome_icon);*/
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
        Parking item=getItem(position);
        holder.name.setText(""+item.getParking_name()+" "+item.getParking_address());
        if(item.getParking_id().equals(defaultId)){
            holder.icon.setImageResource(android.R.drawable.checkbox_on_background);
        }else{
            holder.icon.setImageResource(android.R.drawable.checkbox_off_background);
        }
		return convertView;
	}

    public void setDefault(String defaultId) {
        this.defaultId=defaultId;
        notifyDataSetChanged();
    }

    public final class ViewHolder{
		TextView name;
        ImageView icon;
	}
}
