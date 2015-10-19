package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.fragment.adapter.MonthlyRentListAdapter;
import com.azhuoinfo.pshare.view.AllListView;
import com.azhuoinfo.pshare.view.listview.MyListView;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class MonthlyRentListFragment extends BaseContentFragment{

    //月租车列表
    private AllListView mMonthlyRentListView;
    //添加月租车
    private RelativeLayout mAddMonthlyRentCarRelativeLayout;

    private List<String> list=new ArrayList<String>();
    private AccountVerify mAccountVerify;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_monthlyrent_list,container,false);
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

    }

    @Override
    protected void initViews(Bundle bundle) {
        mMonthlyRentListView=(AllListView) findViewById(R.id.lv_monthlyRent_list);
        list.clear();
        for (int i = 0; i < 4; i++) {
            list.add(""+i);
        }
        mMonthlyRentListView.setAdapter(new MonthlyRentListAdapter(this.getActivity(), list));
        mAddMonthlyRentCarRelativeLayout=(RelativeLayout) findViewById(R.id.rl_add_monthlyRent);

        mMonthlyRentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                replaceParentFragment(AddMonthlyRentCarFragment.class, "AddMonthlyRentCarFragment", null);
            }
        });
        mAddMonthlyRentCarRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceParentFragment(AddMonthlyRentCarFragment.class, "AddMonthlyRentCarFragment", null);
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
