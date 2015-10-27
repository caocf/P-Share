package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.fragment.adapter.CarListAdapter;
import com.azhuoinfo.pshare.model.CarList;
import com.azhuoinfo.pshare.view.CommonDialog;
import com.azhuoinfo.pshare.view.PromptView;

import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 * 产权/月租，添加产权月租车牌选择
 */
public class AddMonthylyCarNumberFragment extends BaseContentFragment{
    private PromptView mPromptView;
    private ListView mListView;
    private CarListAdapter mDataAdapter;
    private TextView mAddCarTextView;
    private AccountVerify mAccountVerify;

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
        mPromptView= (PromptView) findViewById(R.id.promptView);
        mListView=(ListView) findViewById(R.id.lv_list_car);
        mAddCarTextView= (TextView) findViewById(R.id.tv_listcar_add);
    }

    @Override
    protected void initViews(final Bundle bundle) {
        this.setTitle(R.string.mine_carList);
        mAddCarTextView.setVisibility(View.GONE);
        mDataAdapter=new CarListAdapter(this.getActivity());
        mListView.setAdapter(mDataAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarList item=(CarList)parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("carList", item);
                setResult(RESULT_OK, bundle);
                popBackStack();
            }
        });
    }
    @Override
    protected void initData(Bundle bundle) {
        if(mAccountVerify.isLogin())
            postCarList(mAccountVerify.getCustomer_Id());
        else
            mAccountVerify.showLoginDialog(this);
    }
    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return false;
    }
    protected void updateViews(List<CarList> list) {
        if(list!=null&&list.size()>0){
            mDataAdapter.clear();
            mDataAdapter.addAll(list);
        }else{
            showToast(R.string.common_empty);
        }
    }
    public void postCarList(String customerId){
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_CARLIST));
        apiTask.setParams(ApiContants.instance(getActivity()).userCarList(customerId));
        apiTask.setRoot("orderInfo");
        apiTask.execute(new OnDataLoader<List<CarList>>() {
            public void onStart() {
                if (isEnable())
                    mPromptView.showLoading();
            }
            @Override
            public void onSuccess(List<CarList> list) {
                if (isEnable()) {
                    updateViews(list);
                    mPromptView.showContent();
                }
            }
            @Override
            public void onFailure(String code, String message) {
                mPromptView.showContent();
                showToast(message);
            }
        });
    }
}
