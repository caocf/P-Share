package com.azhuoinfo.pshare.view.listview;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.AnimateDismissAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;

import java.util.List;

public class LoadMoreAnimDismissAdapter<T> extends AnimateDismissAdapter {
	private OnLoadMoreListener mOnLoadMoreListener;
	private View mFooterView;
	private ListView mListView;
	private ArrayAdapter<T> baseAdapter;
	public LoadMoreAnimDismissAdapter(BaseAdapter<T> baseAdapter,OnDismissCallback callback,View footerView) {
		super(baseAdapter,callback);
		this.baseAdapter=baseAdapter;
		this.mFooterView=footerView;
	}
	@Override
	public void setAbsListView(AbsListView listView) {
		super.setAbsListView(listView);
		listView.setOnScrollListener(makeScrollListener());
		mListView=(ListView) listView;
		mListView.addFooterView(mFooterView);
	}
	public AbsListView.OnScrollListener makeScrollListener() {
        return new AbsListView.OnScrollListener() {
        	@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
        		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE&&
						view.getLastVisiblePosition()==(view.getCount()-1)) {
        			if(mOnLoadMoreListener!=null)
        			if(mOnLoadMoreListener.hasMore()){
        				showLoading();
        				mOnLoadMoreListener.onLoadMore();
        			}
        		}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
			}
        };
    }
	private void showLoading(){
		if(mListView.getFooterViewsCount()==0){
			mListView.addFooterView(mFooterView);
		}
	}
	private void hideLoading(){
		if(mListView.getFooterViewsCount()>0){
			mListView.removeFooterView(mFooterView);
		}
	}
	public void addMoreData(List<T> list){
		baseAdapter.addAll(list);
		hideLoading();
	}
	public void onLoadMoreComplete(){
		hideLoading();
	}
	public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
		this.mOnLoadMoreListener = mOnLoadMoreListener;
	}
	
	
}
