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
import com.azhuoinfo.pshare.fragment.adapter.MineHomeAdapter;
import java.util.ArrayList;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import com.azhuoinfo.pshare.fragment.adapter.MineHomeAdapter.ViewHolder;
/**
 * Created by Azhuo on 2015/9/22.
 */
public class MineHomeFragment extends BaseContentFragment{

    //添加进入的停车地址列表
    private ListView mListView;
    //添加停车地址
    private RelativeLayout mAddRelativeLayout;

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
        return inflater.inflate(R.layout.fragment_minehome,container,false);
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
        mListView=(ListView) view.findViewById(R.id.lv_mine_home);
        mAddRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_mine_home);

    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.tv_mine_home);
        mAddRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentFragment(AddMineHomeFragment.class, "AddMineHomeFragment", null, ModuleMenuIDS.MODULE_HOME);
            }
        });
        list.clear();
        for(int i=0;i<5;i++){
            list.add(""+i);
        }
        mListView.setAdapter(new MineHomeAdapter(this.getActivity(), list));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewHolder vHolder = (ViewHolder) view.getTag();
                //在每次获取点击的item时将对应的checkBox状态改变，同时修改map的值
                vHolder.mCheckBox.toggle();
                MineHomeAdapter.isSelected.put(position, vHolder.mCheckBox.isChecked());
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
