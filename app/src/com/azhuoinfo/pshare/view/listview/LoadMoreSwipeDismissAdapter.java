package com.azhuoinfo.pshare.view.listview;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissListViewTouchListener;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeOnScrollListener;

import java.util.List;

public class LoadMoreSwipeDismissAdapter<T> extends SwipeDismissAdapter {

    public LoadMoreSwipeDismissAdapter(ArrayAdapter<T> baseAdapter,OnDismissCallback mOnDismissCallback,View footerView) {
        super(baseAdapter,mOnDismissCallback,null);
        this.baseAdapter=baseAdapter;
		this.mFooterView=footerView;
    }
    
    @Override
    protected SwipeDismissListViewTouchListener createListViewTouchListener(AbsListView listView){
      return new SwipeDismissListViewTouchListener(listView, this.mOnDismissCallback, makeScrollListener());
    }
    @Override
    public void setAbsListView(final AbsListView listView) {
        super.setAbsListView(listView);
        mListView=(ListView) listView;
		mListView.addFooterView(mFooterView);
    }
    
	private OnLoadMoreListener mOnLoadCallback;
	private View mFooterView;
	private ListView mListView;
	private ArrayAdapter<T> baseAdapter;
	private boolean mIsLoadingMore = false;
	
	public SwipeOnScrollListener makeScrollListener() {
        return new SwipeOnScrollListener() {
        	@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
        		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE&&
						view.getLastVisiblePosition()==(view.getCount()-1)) {
        			if(mOnLoadCallback!=null)
        			if(!mIsLoadingMore&&mOnLoadCallback.hasMore()){
        				mIsLoadingMore=true;
        				showLoading();
        				mOnLoadCallback.onLoadMore();
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
		mIsLoadingMore = false;
		hideLoading();
	}
	
	
	public void setOnLoadCallback(OnLoadMoreListener mOnLoadCallback) {
		this.mOnLoadCallback = mOnLoadCallback;
		hideLoading();
	}
    
}
