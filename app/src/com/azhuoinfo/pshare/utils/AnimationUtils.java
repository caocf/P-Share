package com.azhuoinfo.pshare.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

public class AnimationUtils {
	private static final long ANIMATION_DURATION = 300L;

	public static void expand(final View view) {
		final int targtetHeight = view.getMeasuredHeight();
		final LinearLayout.LayoutParams mLayoutParams = ((LinearLayout.LayoutParams) view.getLayoutParams());
		mLayoutParams.bottomMargin = -targtetHeight;
		view.setVisibility(View.VISIBLE);
		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if (interpolatedTime < 1.0f) {
						mLayoutParams.bottomMargin =  -targtetHeight + (int) (targtetHeight * interpolatedTime);
				}else{
						mLayoutParams.bottomMargin = 0;
				}
				view.requestLayout();
			}
		};
		a.setDuration(ANIMATION_DURATION);
		view.startAnimation(a);
		
	}

	public static void collapse(final View view) {
		final int targtetHeight = view.getMeasuredHeight();
		final LinearLayout.LayoutParams mLayoutParams = ((LinearLayout.LayoutParams) view.getLayoutParams());
		mLayoutParams.bottomMargin = 0;
		view.setVisibility(View.VISIBLE);
		
		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if (interpolatedTime < 1.0f) {
					mLayoutParams.bottomMargin = - (int) (targtetHeight * interpolatedTime);
				} else {
					mLayoutParams.bottomMargin = -targtetHeight;
					view.setVisibility(View.GONE);
				}
				view.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};
		a.setDuration(ANIMATION_DURATION);
		view.startAnimation(a);
	}
}
