package com.azhuoinfo.pshare.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.fragment.LoginFragment;
import com.azhuoinfo.pshare.fragment.RegisterFragment;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/25.
 */
public class LoginAndRegisterActivity extends BaseActivity {

    private Button mLoginButton;
    private Button mRegisterButton;
    private AccountVerify mAccountVerify;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_logo);
    }

}
