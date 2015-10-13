package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.fragment.adapter.AddLivingAreaAdapter;
import com.azhuoinfo.pshare.fragment.adapter.MineHomeAdapter;
import com.azhuoinfo.pshare.fragment.adapter.MineHomeAdapter.ViewHolder;

import java.util.ArrayList;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class AddLivingAreaFragment extends BaseContentFragment{

    //添加进入的停车地址列表
    private ListView mListView;
    //搜索
    private EditText mSearchEditText;

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
        return inflater.inflate(R.layout.fragment_add_living_area,container,false);
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
        mListView=(ListView) view.findViewById(R.id.lv_parking_address);
        mSearchEditText=(EditText) view.findViewById(R.id.et_search);

    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.tv_add_living_area);
        list.clear();
        for(int i=0;i<5;i++){
            list.add(""+i);
        }
        mListView.setAdapter(new AddLivingAreaAdapter(this.getActivity(), list));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                replaceFragment(AddMineHomeFragment.class,"AddMineHomeFragment",null);
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
        return false;
    }
}
