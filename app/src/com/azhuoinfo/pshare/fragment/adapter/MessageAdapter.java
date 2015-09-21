package com.azhuoinfo.pshare.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhuoinfo.pshare.model.Message;
import com.azhuoinfo.pshare.view.listview.BaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import com.azhuoinfo.pshare.R;
import mobi.cangol.mobile.utils.StringUtils;
import mobi.cangol.mobile.utils.TimeUtils;

public class MessageAdapter extends BaseAdapter<Message> {
	private ImageLoader mImageLoader;
	private DisplayImageOptions mDisplayImageOptions;
	public MessageAdapter(Context context) {
		super(context);
		mImageLoader=ImageLoader.getInstance();
		mDisplayImageOptions= new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.ic_photo_default)
        .showImageForEmptyUri(R.drawable.ic_photo_default)
        .showImageOnFail(R.drawable.ic_photo_default)
        .build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		final Message item=getItem(position);
		if(null!=convertView){
			holder=(ViewHolder)convertView.getTag();
		}else{
			convertView = this.mInflater.inflate(R.layout.listview_item_message, parent, false);
			holder=new ViewHolder();
			holder.layout=convertView.findViewById(R.id.listview_item_message_layout);
			holder.title=(TextView) convertView.findViewById(R.id.listview_item_message_title);
			holder.content=(TextView) convertView.findViewById(R.id.listview_item_message_content);
			holder.time=(TextView) convertView.findViewById(R.id.listview_item_message_time);
			holder.image=(ImageView) convertView.findViewById(R.id.listview_item_message_image);
			convertView.setTag(holder);
		}
		holder.title.setText(StringUtils.null2Empty(item.getTitle()));
		holder.content.setText(StringUtils.null2Empty(item.getContent()));
		holder.time.setText(TimeUtils.formatLatelyTime(item.getTimestamp()));
		mImageLoader.displayImage(item.getImage(),holder.image,mDisplayImageOptions);
		if(item.getStatus()==0){
			holder.title.getPaint().setFakeBoldText(true);
			holder.content.getPaint().setFakeBoldText(true);
			holder.content.setTextColor(mContext.getResources().getColor(R.color.common_title));
			holder.time.setTextColor(mContext.getResources().getColor(R.color.text_blue));
		}else{
			holder.title.getPaint().setFakeBoldText(false);
			holder.content.getPaint().setFakeBoldText(false);
			holder.content.setTextColor(mContext.getResources().getColor(R.color.common_content));
			holder.time.setTextColor(mContext.getResources().getColor(R.color.common_hint));
		}
		
		return convertView;
	}
	static class ViewHolder{
		View layout;
		TextView title;
		TextView content;
		TextView time;
		ImageView image;
	}
}
