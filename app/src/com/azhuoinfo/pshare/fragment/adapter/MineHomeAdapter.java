package com.azhuoinfo.pshare.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.view.listview.BaseAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineHomeAdapter extends BaseAdapter<String> {

	private List<String> list;
	private Context context;
	public static Map<Integer, Boolean> isSelected;

	public MineHomeAdapter(Context context) {
		super(context);
	}

	public MineHomeAdapter(Context context, List<String> items) {
		super(context, items);
		this.context=context;
		this.list=items;
		init();
	}
	public void init(){
		isSelected = new HashMap<Integer, Boolean>();
		for (int i=0; i<list.size(); i++) {
			isSelected.put(i, false);
		}
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView = this.mInflater.inflate(R.layout.listview_item_minehome, parent, false);
			holder=new ViewHolder();
			holder.mCheckBox=(CheckBox) convertView.findViewById(R.id.cb_minehome);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		holder.mCheckBox.setChecked(isSelected.get(position));
		holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//先把链表中的数据取代掉
				isSelected.put(position, isChecked);
				if (buttonView.isChecked()) {
					for (int i = 0; i < list.size(); i++) {
						//把其他的checkbox设置为false
						if (i != position) {
							isSelected.put(i, false);
						}
					}
				}
				//通知适配器更改
				MineHomeAdapter.this.notifyDataSetChanged();
			}
		});
		return convertView;
	}
	public final class ViewHolder{
		View layout;
		public CheckBox mCheckBox;
	}
}
