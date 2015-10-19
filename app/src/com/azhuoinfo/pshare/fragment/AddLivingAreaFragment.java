package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.fragment.adapter.AddLivingAreaAdapter;
import com.azhuoinfo.pshare.fragment.adapter.MineHomeAdapter;
import com.azhuoinfo.pshare.model.Parking;
import com.azhuoinfo.pshare.view.PromptView;
import com.azhuoinfo.pshare.view.SearchEditText;

import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class AddLivingAreaFragment extends BaseContentFragment{
    //添加进入的停车地址列表
    private ListView mListView;
    private PromptView mPromptView;
    private SearchEditText mSearchEditText;
    private AccountVerify mAccountVerify;
    private AddLivingAreaAdapter mDataAdapter;
    private String mArea;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
        mArea=this.getArguments().getString("area");
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
        mSearchEditText=(SearchEditText) view.findViewById(R.id.et_search);
        mPromptView=(PromptView) findViewById(R.id.promptView);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.tv_add_living_area);
        mDataAdapter=new AddLivingAreaAdapter(this.getActivity());
        mListView.setAdapter(mDataAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Parking item = (Parking) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("parking", item);
                setResult(RESULT_OK, bundle);
                popBackStack();
            }
        });
        mSearchEditText.setOnClickSearchListener(new SearchEditText.OnClickSearchListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mSearchEditText.getText())) {
                    String name = mSearchEditText.getText().toString();
                    getSearchParkListbyName(name);
                }else{
                    showToast("清输入关键词搜索!");
                }
            }
        });
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(!TextUtils.isEmpty(v.getText())) {
                    String name = v.getText().toString();
                    getSearchParkListbyName(name);
                }else{
                    showToast("清输入关键词搜索!");
                }
                return false;
            }
        });
    }

    @Override
    protected void initData(Bundle bundle) {
        searchParkListbyArea(mArea);
    }
    private void searchParkListbyArea(String area) {
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_SEARCHPARKLISTBYAREA));
        apiTask.setParams(ApiContants.instance(getActivity()).searchParkListbyArea(area));
        apiTask.setRoot("parkingList");
        apiTask.execute(new OnDataLoader<List<Parking>>() {
            @Override
            public void onStart() {
                if (isEnable())
                    mPromptView.showLoading();
            }
            @Override
            public void onSuccess(boolean page, List<Parking> list) {
                  if (isEnable()) {
                    updateViews(list);
                }
            }
            @Override
            public void onFailure(String code, String message) {
                if (isEnable())
                    mPromptView.showEmpty();
            }
        });
    }
    private void getSearchParkListbyName(String name) {
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_SEARCHPARKLISTBYNAME));
        apiTask.setParams(ApiContants.instance(getActivity()).searchParkListbyName(name));
        apiTask.setRoot("parkingList");
        apiTask.execute(new OnDataLoader<List<Parking>>() {
            @Override
            public void onStart() {
                if (isEnable())
                    mPromptView.showLoading();
            }

            @Override
            public void onSuccess(boolean page, List<Parking> list) {
                if (isEnable()) {
                    updateViews(list);
                }
            }

            @Override
            public void onFailure(String code, String message) {
                if (isEnable())
                    mPromptView.showEmpty();
            }
        });
    }
    private void updateViews(List<Parking> list) {
        if(list!=null&&list.size()>0){
            mDataAdapter.clear();
            mDataAdapter.addAll(list);
            if(mDataAdapter.getCount()>0){
                mPromptView.showContent();
            }else{
                mPromptView.showPrompt(R.string.common_empty);
            }
        }else{
            mPromptView.showPrompt(R.string.common_empty);
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
