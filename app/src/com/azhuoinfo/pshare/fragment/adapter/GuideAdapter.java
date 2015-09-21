package com.azhuoinfo.pshare.fragment.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class GuideAdapter extends PagerAdapter {
	private Context context;
	private int[] resIds;
	public GuideAdapter(Context context,int[] resIds){
		this.context= context;
		this.resIds=resIds;
	}

	@Override
	public int getCount() {
		return resIds.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public void destroyItem(ViewGroup view, int position, Object object) {
		view.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		ImageView imgView = new ImageView(context);
		imgView.setImageResource(resIds[position]);
		imgView.setScaleType(ScaleType.CENTER_CROP);
		imgView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		view.addView(imgView);
		return imgView;
	}
	
}
