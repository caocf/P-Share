
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
import mobi.cangol.mobile.service.session.SessionService;
import mobi.cangol.mobile.utils.DeviceInfo;

public class GuideFragment extends BaseContentFragment {

    private ViewPager mViewPager;
    private CirclePageIndicator mCirclePageIndicator;
    private GuideAdapter mGuideAdapter;
    private SessionService mSessionService;
    private LinearLayout mLinearLayout;
    private boolean isDisplay = false;//在设置里展示
    private boolean isLogin=false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSessionService = getSession();
        if (this.getArguments() != null) {
            isDisplay = this.getArguments().getBoolean("isDisplay", false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_guide, container, false);
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
        mViewPager = (ViewPager) findViewById(R.id.settings_userGuide_viewPager);
        mCirclePageIndicator = (CirclePageIndicator) findViewById(R.id.setting_userGuide_indicator_dot);
        mLinearLayout=(LinearLayout) findViewById(R.id.ll_setting_userGuide_start);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        if (isDisplay) {
            this.setTitle(R.string.title_guide);
            this.findViewById(R.id.setting_userGuide_skip).setVisibility(View.GONE);
        } else {
            ((ActionBarActivity) this.getActivity()).setFullScreen(true);
        }
        int[] guides = {R.drawable.guide1, R.drawable.guide2, R.drawable.guide3};
        mGuideAdapter = new GuideAdapter(this.getActivity(), guides);
        mViewPager.setAdapter(mGuideAdapter);
        mCirclePageIndicator.setViewPager(mViewPager);
        mCirclePageIndicator.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int pos) {
                if (pos == 2 && !isDisplay)
                    findViewById(R.id.ll_setting_userGuide_start).setVisibility(View.VISIBLE);
                else
                    findViewById(R.id.ll_setting_userGuide_start).setVisibility(View.GONE);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        findViewById(R.id.setting_userGuide_start).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                popBackStack();
                mSessionService.saveString("newVersion", DeviceInfo.getAppVersion(getActivity()));
                if (!isDisplay)
                    toMain();
            }

        });
        findViewById(R.id.setting_userGuide_skip).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                popBackStack();
                mSessionService.saveString("newVersion", DeviceInfo.getAppVersion(getActivity()));
                if (!isDisplay) {
                    toMain();
                }
            }
        });
    }
    private void toMain() {
        ((SplashActivity) this.getActivity()).toMain();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isDisplay) {

        } else {
            ((ActionBarActivity) this.getActivity()).setFullScreen(false);
        }
    }

    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }
}
