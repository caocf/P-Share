package com.azhuoinfo.pshare.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.view.listview.BaseAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Azhuo on 2015/10/14.
 */
public class ImageAdapter extends BaseAdapter{

    private List list;
    public ImageAdapter(Context context) {
        super(context);
    }
    public ImageAdapter(Context context,List<String> list){
        super(context);
        this.list=list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=this.mInflater.inflate(R.layout.item_image,null);
            holder.mImageView=(ImageView) convertView.findViewById(R.id.iv_car_path_image);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        return null;
        //holder.mImageView.setImageBitmap();
    }
    class ViewHolder{
        ImageView mImageView;
    }
}
