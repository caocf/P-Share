package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.fragment.adapter.CarListAdapter;
import com.azhuoinfo.pshare.fragment.adapter.MineHomeAdapter;
import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.service.AppService;
import mobi.cangol.mobile.service.global.GlobalData;

import com.azhuoinfo.pshare.fragment.adapter.MineHomeAdapter.ViewHolder;
import com.azhuoinfo.pshare.model.CarList;
import com.azhuoinfo.pshare.model.Parking;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class MineHomeFragment extends BaseContentFragment{

    //添加进入的停车地址列表
    private ListView mListView;
    //添加停车地址
    private TextView mAddTexView;
    private AccountVerify mAccountVerify;
    private MineHomeAdapter mDataAdapter;
    private GlobalData mGlobalData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
        mGlobalData = (GlobalData) getAppService(AppService.GLOBAL_DATA);
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
        mListView=(ListView) view.findViewById(R.id.lv_mine_home_list);
        mAddTexView= (TextView) view.findViewById(R.id.tv_mine_home_add);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.tv_mine_home);
        mAddTexView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentFragment(AddMineHomeFragment.class, "AddMineHomeFragment", null);
            }
        });
        mDataAdapter= new MineHomeAdapter(this.getActivity());
        mListView.setAdapter(mDataAdapter);
        mDataAdapter.setSelectedMode(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Parking item= (Parking) parent.getItemAtPosition(position);
                mDataAdapter.setDefault(item.getParking_id());
                mGlobalData.save("default_id",item.getParking_id());
            }
        });
    }

    @Override
    protected void initData(Bundle bundle) {
        mDataAdapter.setDefault((String) mGlobalData.get("default_id"));
        getSearchSaveParkList(mAccountVerify.getCustomer_Id());
    }

    private void getSearchSaveParkList(String customer_id) {
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_SEARCHSAVEPARKLIST));
        apiTask.setParams(ApiContants.instance(getActivity()).searchSaveParkList(customer_id));
        apiTask.setRoot("parkingList");
        apiTask.execute(new OnDataLoader<List<Parking>>() {
            public void onStart() {
            }

            @Override
            public void onSuccess(boolean page, List<Parking> list) {
                if (isEnable()) {
                    updateViews(list);
                }
            }

            @Override
            public void onFailure(String code, String message) {
                showToast(message);
            }
        });
    }

    private void updateViews(List<Parking> list) {
        if(list!=null&&list.size()>0){
            mDataAdapter.clear();
            mDataAdapter.addAll(list);
        }else{
            showToast(R.string.common_empty);
        }
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
