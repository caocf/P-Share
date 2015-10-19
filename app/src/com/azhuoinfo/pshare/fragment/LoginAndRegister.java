package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;

import java.util.List;

import mobi.cangol.mobile.actionbar.ActionBarActivity;
import mobi.cangol.mobile.base.BaseActionBarActivity;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/25.
 * 登录注册
 */
public class LoginAndRegister extends BaseContentFragment {

    private Button mLoginButton;
    private Button mRegisterButton;
    private AccountVerify mAccountVerify;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_logo,null);
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
      //  ( (BaseActionBarActivity)this.getActivity()).setWindowBackground(R.drawable.launch_screen_image);
        mLoginButton=(Button) view.findViewById(R.id.button_login);
        mRegisterButton=(Button)view.findViewById(R.id.button_register);
    }

    @Override
    protected void initViews(Bundle bundle) {
        ((ActionBarActivity)this.getActivity()).setActionbarShow(false);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(LoginFragment.class,"LoginFragment",null);
            }
        });
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(RegisterFragment.class,"RegisterFragment",null);
            }
        });
    }
    @Override
    protected void initData(Bundle bundle) {
        this.setMenuEnable(false);
    }
    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return true;
    }

    @Override
    public void onDestroyView() {
       ((ActionBarActivity)this.getActivity()).setActionbarShow(true);
      //  ( (BaseActionBarActivity)this.getActivity()).setWindowBackground(R.color.activity_background);
        super.onDestroy();
    }

}
