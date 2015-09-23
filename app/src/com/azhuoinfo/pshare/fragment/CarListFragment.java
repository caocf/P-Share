package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.fragment.adapter.CarListAdapter;

import java.util.ArrayList;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class CarListFragment extends BaseContentFragment{

    //返回到上个页面
    //private ImageView activity_back;
    //添加进入的车列表
    private ListView mCarListView;
    //添加车
    private RelativeLayout mAddCarRelativeLayout;
    private AccountVerify mAccountVerify;
    private ArrayList<String> list=new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_car_list,container,false);
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
        mCarListView=(ListView) findViewById(R.id.lv_list_car);
        mAddCarRelativeLayout=(RelativeLayout) findViewById(R.id.rl_add_car_list);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.mine_carList);
        mAddCarRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentFragment(AddCarInformationFragment.class, "AddCarInformationFragment", null, ModuleMenuIDS.MODULE_HOME);
            }
        });
        for(int i=0;i<10;i++){
            list.add(""+i);
        }
        mCarListView.setAdapter(new CarListAdapter(this.getActivity(), list));
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
