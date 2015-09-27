package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.fragment.adapter.CarListAdapter;
import com.azhuoinfo.pshare.fragment.adapter.ParkingDetailsAdapter;

import java.util.ArrayList;

import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class ParkingDetailsFragment extends BaseContentFragment{

    //返回到上个页面
    //private ImageView activity_back;
    //车场列表
    private ListView mParkingDetailsListView;

    private AccountVerify mAccountVerify;
    private ArrayList<String> list=new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getCustomActionBar().setCustomHomeAsUpIndicator(R.drawable.left_head,R.drawable.left_head);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_parkingdetails,container,false);
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
        mParkingDetailsListView=(ListView) findViewById(R.id.lv_parking_details);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.parking_details);
        for(int i=0;i<4;i++){
            list.add(""+i);
        }
        mParkingDetailsListView.setAdapter(new ParkingDetailsAdapter(this.getActivity(), list));
        mParkingDetailsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                replaceFragment(ParkingDetailsItemFragment.class, "ParkingDetailsItemFragment", null);
            }
        });

    }
    @Override
    protected boolean onMenuActionCreated(ActionMenu actionMenu) {
        super.onMenuActionCreated(actionMenu);
        actionMenu.add(new ActionMenuItem(1, "搜索", R.drawable.search1, 1));
        actionMenu.add(new ActionMenuItem(2, "地图首页", R.drawable.list_car, 2));
        return true;
    }
    @Override
    public boolean onMenuActionSelected(ActionMenuItem action) {
        switch(action.getId()){
            case 1:
                this.getCustomActionBar().startSearchMode();
                break;
            case 2:
                replaceFragment(HomeFragment.class,"HomeFragment",null);
                break;
        }
        return super.onMenuActionSelected(action);
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
        return false;
    }
}
