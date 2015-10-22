
package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.activity.SplashActivity;
import com.azhuoinfo.pshare.fragment.adapter.GuideAdapter;
import com.azhuoinfo.pshare.view.pager.CirclePageIndicator;

import mobi.cangol.mobile.actionbar.ActionBarActivity;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.service.AppService;
import mobi.cangol.mobile.service.global.GlobalData;
import mobi.cangol.mobile.utils.DeviceInfo;

public class ExplainFragment extends BaseContentFragment {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_explain, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews(getView());
        initViews(savedInstanceState);

    }


    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void findViews(View view) {
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ((ActionBarActivity) this.getActivity()).setActionbarShow(false);
        this.findViewById(R.id.explain_layout).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(HomeFragment.class, "HomeFragment", null);
                popBackStack();
            }
        });
        this.findViewById(R.id.imageView_explain).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(HomeFragment.class, "HomeFragment", null);
                popBackStack();
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ActionBarActivity) this.getActivity()).setActionbarShow(true);
    }

    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }
}
