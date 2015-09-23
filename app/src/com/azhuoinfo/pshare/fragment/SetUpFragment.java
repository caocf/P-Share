package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class SetUpFragment extends BaseContentFragment{

    //版本更新
    private RelativeLayout mVersionUpdateRelativeLayout;
    //最新版本号
    private TextView mNewVersionNumberTextView;
    //意见反馈
    private RelativeLayout mSuggestionFeedbackRelativeLayout;
    //评分打气
    private RelativeLayout mScoreCheerRelativeLayout;
    //关于我们
    private RelativeLayout mAboutUsRelativeLayout;
    //推送
    private RelativeLayout mPushRelativeLayout;
    //退出登录
    private Button mLogOutButton;
    private AccountVerify mAccountVerify;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_set_up,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    protected void findViews(View view) {
        mVersionUpdateRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_version_update);
        mNewVersionNumberTextView=(TextView) view.findViewById(R.id.tv_new_version_number);
        mSuggestionFeedbackRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_suggestion_feedback);
        mScoreCheerRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl__cheer_score);
        mAboutUsRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_about_us);
        mPushRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_push);
        mLogOutButton=(Button) view.findViewById(R.id.button_log_out);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.install);
        mAboutUsRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentFragment(AboutUsFragment.class, "AboutUsFragment", null, ModuleMenuIDS.MODULE_HOME);
            }
        });
        mSuggestionFeedbackRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentFragment(SuggestionFeedbackFragment.class, "SuggestionFeedbackFragment", null, ModuleMenuIDS.MODULE_HOME);
            }
        });
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return true;
    }
}
