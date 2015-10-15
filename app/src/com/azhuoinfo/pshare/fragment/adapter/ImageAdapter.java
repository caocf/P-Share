package com.azhuoinfo.pshare.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.BaseAdapter;
import com.azhuoinfo.pshare.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Azhuo on 2015/10/14.
 */
public class ImageAdapter extends BaseAdapter{

    private Context context;
    private List<String> urls;

    public ImageAdapter(Context context){
        this.context = context;
    }

    public ImageAdapter(Context context,List<String> urls){
        this.context = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_image,null);
            holder.image = (ImageView) convertView.findViewById(R.id.iv_car_path_image);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        if(urls.get(position) == null) {
            holder.image.setImageResource(R.drawable.userhead);
        }else{
            ImageLoader loader=ImageLoader.getInstance();
            loader.displayImage(urls.get(position),holder.image);
            //ImageUtils.getBitmap(urls.get(position),holder.image);
        }
        return convertView;
    }

    class ViewHolder{
        ImageView image;
    }
}
