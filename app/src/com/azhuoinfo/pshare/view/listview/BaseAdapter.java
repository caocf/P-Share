package com.azhuoinfo.pshare.view.listview;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;

import com.nhaarman.listviewanimations.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseAdapter<T> extends ArrayAdapter<T> {
	protected LayoutInflater mInflater;
	protected Context mContext;
	public static final int TYPE_SELECT = 1;
	public static final int TYPE_UNSELECT = 0;
	protected boolean mSelectedMode;
	private List<T> mSelect = new ArrayList<T>();
	public BaseAdapter(Context context) {
		this(context, null);
	}
	
	public BaseAdapter(Context context, List<T> items) {
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mItems = new ArrayList<T>();
		if (items != null) {
			mItems.addAll(items);
		}
	
	}
	public Resources getResources() {
		return 	mContext.getResources();
	}
	public boolean isSelectedMode() {
		return mSelectedMode;
	}

	public void setSelectedMode(boolean isSelectedMode) {
		if (this.mSelectedMode == isSelectedMode)
			return;
		this.mSelectedMode = isSelectedMode;
		mSelect.clear();
		notifyDataSetChanged();
	}

	@Override
	public boolean remove(final Object object) {
		mSelect.remove((T) object);
		return super.remove(object);
	}

	@Override
	public T remove(final int location) {
		if (location < mSelect.size())
			mSelect.remove(getItem(location));
		return super.remove(location);
	}

	@Override
	public Collection<T> removePositions(final Collection<Integer> locations) {
		for (Integer position : locations) {
			if (position < mSelect.size())
				mSelect.remove(getItem(position));
		}
		return super.removePositions(locations);
	}

	@Override
	public boolean removeAll(Collection<?> objects) {
		for (Object object : objects) {
			mSelect.remove((T) object);
		}
		return super.removeAll(objects);
	}

	public boolean getItemSelected(int position) {
		if (getItemViewSelectType(position) == TYPE_SELECT) {
			return true;
		}
		return false;
	}

	public int getItemViewSelectType(int position) {
		return mSelect.contains(getItem(position)) ? TYPE_SELECT
				: TYPE_UNSELECT;
	}

	public void invertSelected(int position) {
		if (mSelect.contains(getItem(position))) {
			mSelect.remove(getItem(position));
		} else {
			mSelect.add(getItem(position));
		}
		notifyDataSetChanged();
	}
	public void selectAll() {
		for (int i = 0; i < getCount(); i++) {
			if (!mSelect.contains(getItem(i))) {
				mSelect.add(getItem(i));
			}
		}
		notifyDataSetChanged();
	}

	public void invertAll() {
		for (int i = 0; i < getCount(); i++) {
			if (mSelect.contains(getItem(i))) {
				mSelect.remove(getItem(i));
			} else {
				mSelect.add(getItem(i));
			}
		}
		notifyDataSetChanged();
	}

	public List<Integer> getPositionSelected() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < mSelect.size(); i++) {
			list.add(indexOf(mSelect.get(i)));
		}
		return list;
	}

	public List<T> getSelected() {
		return mSelect;
	}
	public List<T> getItems() {
		return mItems;
	}
}