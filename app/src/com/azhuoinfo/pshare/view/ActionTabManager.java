package com.azhuoinfo.pshare.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.HashMap;

import mobi.cangol.mobile.base.BaseFragment;

/**
 * @Description:
 * @version $Revision: 1.0 $
 * @author Cangol
 */
public class ActionTabManager {
	private static final String CUR_TAG = "ActionTabManager_TabId";
    protected final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
    private TabInfo mLastTab;
    private FragmentManager mFragmentManager;
    private final Context mContext;
    private final int mContainerId;
    public static final class TabInfo {
        private final String tag;
        private final Class<?> clss;
        public Bundle args;
        public BaseFragment fragment;

        TabInfo(String _tag, Class<?> _class, Bundle _args) {
            tag = _tag;
            clss = _class;
            args = _args;
        }
    }

    public ActionTabManager(Context context, FragmentManager fragmentManager, int containerId) {
    	mFragmentManager =  fragmentManager;
        mContainerId = containerId;
        mContext=context;
    }
	
    public BaseFragment getCurrentTab(){
    	if(mLastTab!=null){
    		return mLastTab.fragment;
    	}
    	return null;
    }
    
    public void addTab(int id, Class<?> clss, String tag,Bundle args) {
        String tabId = ""+id;

        TabInfo info = new TabInfo(tag, clss, args);
        info.fragment = (BaseFragment)mFragmentManager.findFragmentByTag(tag);
        if (info.fragment != null && !info.fragment.isDetached()) {
            FragmentTransaction ft =mFragmentManager.beginTransaction();
            ft.detach(info.fragment);
            ft.commit();
        }
        mTabs.put(tabId, info);
    }
	
	public void setTabSelected(String tabId) {
		 TabInfo newTab = mTabs.get(tabId);
         if (mLastTab != newTab) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            if (mLastTab != null) {
                if (mLastTab.fragment != null) {
                    ft.detach(mLastTab.fragment);
                }
            }
            if (newTab != null) {
                if (newTab.fragment == null) {
                    newTab.fragment = (BaseFragment) Fragment.instantiate(mContext,
                            newTab.clss.getName(), newTab.args);
                    ft.add(mContainerId, newTab.fragment, newTab.tag);
                } else {
                    ft.attach(newTab.fragment);
                }
            }
            mLastTab = newTab;
            ft.commit();
            mFragmentManager.executePendingTransactions();
        }
	}
}
