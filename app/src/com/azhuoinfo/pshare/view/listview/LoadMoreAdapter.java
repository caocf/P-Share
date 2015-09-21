package com.azhuoinfo.pshare.view.listview;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.BaseAdapterDecorator;

import java.util.List;
/**
 * @Description:
 * @version $Revision: 1.0 $
 * @author Cangol
 */
public class LoadMoreAdapter<T> extends BaseAdapterDecorator implements OnScrollListener{
	private OnLoadMoreListener mOnLoadMoreListener;
	private LoadMoreFooter mFooterView;
	private ListView mListView;
	private ArrayAdapter<T> baseAdapter;
	private OnScrollListener mOnScrollListener;
	private boolean mIsPullMode=true;//false 点击查看更多模式; true 滑动加载模式
	public LoadMoreAdapter(ArrayAdapter<T> baseAdapter) {
		super(baseAdapter);
		this.baseAdapter=baseAdapter;
	}
	public void setIsPullMode(boolean isPullMode) {
		this.mIsPullMode = isPullMode;
	}
	@Override
	public void setAbsListView(AbsListView listView) {
		super.setAbsListView(listView);
		mListView=(ListView) listView;
		mFooterView=new LoadMoreFooter(mListView.getContext());
		mListView.addFooterView(mFooterView);
		mListView.setOnScrollListener(this);
		if(mIsPullMode){
			mFooterView.showHide();
		}else{
			mFooterView.showNormal();
		}
			
		mFooterView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(!mIsPullMode&&!mFooterView.isLoading()){
					if(mOnLoadMoreListener.hasMore()){
						startLoad();
						mOnLoadMoreListener.onLoadMore();
					}
				}
			}
			
		});
	}
	
	public void setOnScrollListener(OnScrollListener onScrollListener){
		this.mOnScrollListener=onScrollListener;
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(mOnScrollListener!=null)
			mOnScrollListener.onScrollStateChanged(view, scrollState);
		
		if(mOnLoadMoreListener!=null&&mIsPullMode)
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE&&
					view.getLastVisiblePosition()==(view.getCount()-1)) {
				
				if(mOnLoadMoreListener.hasMore()){
					startLoad();
					mOnLoadMoreListener.onLoadMore();
				}
			}
		
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if(mOnScrollListener!=null)
			mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		
	}
	private void startLoad(){
		mFooterView.showLoading();
		mListView.setFooterDividersEnabled(true);
	}
	private void stopLoad(){
		if(mIsPullMode||!mOnLoadMoreListener.hasMore()){
			mFooterView.showHide();
		}else{
			mFooterView.showNormal();
		}
		mListView.setFooterDividersEnabled(false);
	}
	
	public void addMoreData(List<T> list){
		baseAdapter.addAll(list);
		stopLoad();
	}

	public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
		this.mOnLoadMoreListener = onLoadMoreListener;
	}
}
