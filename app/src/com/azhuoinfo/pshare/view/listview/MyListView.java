package com.azhuoinfo.pshare.view.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MyListView extends ListView {
	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	//重写事件拦截的方法
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN://按下
			requestDisallowInterceptTouchEvent(true); //ScrollView对ListView取消拦截
			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:

			break; 
		case MotionEvent.ACTION_CANCEL: //取消的时候.
			requestDisallowInterceptTouchEvent(false); // ScrollView对ListView进行拦截
			break;
		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	//定义高度
	private int maxHeight;

	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	//对定义的高度进行册测算
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if (maxHeight > -1) {
			//MeasureSpec。makeMeasureSpec测量容器里child的规则
			//1.实际需要测量的高度
			//2.测量的模式 (UNSPECIFIED,EXACTLY,AT_MOST)
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
		}
		//重新传入heightMeasureSpec新的规则进行测量
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
