/*
 * Copyright 2014 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.azhuoinfo.pshare.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nhaarman.listviewanimations.swinginadapters.SingleAnimationAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;

/**
 * An implementation of the AnimationAdapter class which applies a
 * swing-in-from-the-left-animation to views.
 */
@SuppressWarnings("UnusedDeclaration")
public class LeftInAnimationAdapter extends SingleAnimationAdapter {

    private static final String TRANSLATION_X = "translationX";

    public LeftInAnimationAdapter( final BaseAdapter baseAdapter) {
        super(baseAdapter);
    }

    @Override
    protected Animator getAnimator( final ViewGroup parent, final View view) {
    	PropertyValuesHolder pvhTX = PropertyValuesHolder.ofFloat(TRANSLATION_X, 0 - parent.getWidth(), 0);
    	PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 0f,1f);
		PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY",0f,1f);
		PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat("alpha",0f,1f);
		ObjectAnimator anim1 =ObjectAnimator.ofPropertyValuesHolder(view,pvhTX, pvhX, pvhY,pvhA);
		//anim1.setInterpolator(new DecelerateInterpolator());
        return anim1;
    }
}
