package com.azhuoinfo.pshare.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.azhuoinfo.pshare.R;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/24.
 */
public class LoginAndRegisterActivity extends BaseContentFragment{
    private Button mloginButton;
    private Button mRegisterButton;




    public void findViews(){
       // setContentView(R.layout.fragment_logo);
        mloginButton=(Button) findViewById(R.id.button_login);
        mRegisterButton=(Button) findViewById(R.id.button_register);
    }

    @Override
    protected void findViews(View view) {

    }

    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }
}
