package com.azhuoinfo.pshare.fragment.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.view.listview.BaseAdapter;

import java.util.List;

/**
 * Created by weixuewu on 15/10/13.
 */
public class CarIdAdapter extends BaseAdapter<String> {

    public CarIdAdapter(Context context, List<String> items) {
        super(context, items);
    }
    public void singleSelected(int position) {
        if (mSelect.contains(getItem(position))) {
            mSelect.remove(getItem(position));
        } else {
            mSelect.clear();
            mSelect.add(getItem(position));
        }
        notifyDataSetChanged();
    }
    public AbsListView.LayoutParams getLayoutParams() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView. LayoutParams.MATCH_PARENT);
        layoutParams.width = (int) (displayMetrics.widthPixels-displayMetrics.density*8*2)/9;
        layoutParams.height = layoutParams.width;
        return layoutParams;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView = this.mInflater.inflate(R.layout.gridview_item_id, parent, false);
            holder=new ViewHolder();
            holder.mName=(TextView) convertView.findViewById(R.id.gridview_item_id_name);
            holder.mName.setLayoutParams(getLayoutParams());
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        String item=this.getItem(position);
        holder.mName.setText(item);
        if(this.getItemSelected(position)){
            holder.mName.setBackgroundResource(R.drawable.bg_carid_selector);
            holder.mName.setTextColor(getResources().getColor(R.color.actionbar_background));
        }else{
            holder.mName.setBackgroundResource(R.color.text_white);
            holder.mName.setTextColor(getResources().getColor(R.color.text_light_gray));
        }
        return convertView;
    }
    static class ViewHolder {
        TextView mName;
    }
}
