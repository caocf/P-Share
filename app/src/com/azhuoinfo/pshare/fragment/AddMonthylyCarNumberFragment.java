package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.azhuoinfo.pshare.view.LoadingDialog;
import com.azhuoinfo.pshare.view.PromptView;
import com.azhuoinfo.pshare.view.listview.LoadMoreAdapter;
import com.azhuoinfo.pshare.view.listview.OnLoadMoreListener;
import com.azhuoinfo.pshare.view.listview.pull.PullRefreshListView;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 * 产权/月租，添加产权月租车牌选择
 */
public class AddMonthylyCarNumberFragment extends BaseContentFragment{
    //private ListView mListView;
    private CarListAdapter mDataAdapter;
    private TextView mAddCarTextView;
    private AccountVerify mAccountVerify;

    private PullRefreshListView mListView;
    LoadMoreAdapter<CarList> mListLoadMoreAdapter;
    int mPageIndex = 1;

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
        mListView=(PullRefreshListView) findViewById(R.id.lv_list_car);
        mAddCarTextView= (TextView) findViewById(R.id.tv_listcar_add);


    }

    @Override
    protected void initViews(final Bundle bundle) {
        this.setTitle(R.string.mine_carList);
/*        mAddCarTextView.setVisibility(View.GONE);
        mDataAdapter=new CarListAdapter(this.getActivity());
        mListView.setAdapter(mDataAdapter);*/



        mDataAdapter=new CarListAdapter(this.getActivity());
        mListLoadMoreAdapter = new LoadMoreAdapter<>(mDataAdapter);
        mListLoadMoreAdapter.setIsPullMode(false);
        mListLoadMoreAdapter.setAbsListView(mListView);
        mListView.setAdapter(mListLoadMoreAdapter);
        mListView.setPullRefreshEnable(false);

        mListLoadMoreAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public boolean hasMore() {
                return true;
            }

            @Override
            public void onLoadMore() {
                postCarList(mAccountVerify.getCustomer_Id(), mPageIndex);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarList item = (CarList) parent.getItemAtPosition(position);
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
            postCarList(mAccountVerify.getCustomer_Id(),mPageIndex);
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
            Log.e("updateViews", list + "");
            mListLoadMoreAdapter.addMoreData(list);
            if(mListLoadMoreAdapter.getCount()>0){
            }else{

            }
        }else{

        }
    }
    public void postCarList(String customerId,final int pageindex){
        if (pageindex == -1) {
            mListLoadMoreAdapter.addMoreData(new ArrayList<CarList>());
            return;
        }
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_CARLIST));
        apiTask.setParams(ApiContants.instance(getActivity()).userCarList(customerId, "" + pageindex));
        apiTask.setRoot("orderInfo");
        apiTask.execute(new OnDataLoader<List<CarList>>() {
            LoadingDialog loadingDialog;
            public void onStart() {
                if (isEnable())
                    loadingDialog = LoadingDialog.show(getActivity());
            }
            @Override
            public void onSuccess(List<CarList> list) {
                if (isEnable()) {
                    loadingDialog.dismiss();
                    if (pageindex<=1) {
                        updateViews(list);

                    }else {
                        if (list!=null && !list.isEmpty()) {
                            mListLoadMoreAdapter.addMoreData(list);
                        }
                    }

                    /*updateViews(list);
                    mPromptView.showContent();
*/
                    if (list != null && list.size()==10){
                        mPageIndex++;
                    }else {
                        mPageIndex = -1;
                    }

                }
            }
            @Override
            public void onFailure(String code, String message) {
                showToast(message);
                mPageIndex = -1;
                loadingDialog.dismiss();
            }
        });
    }
}
