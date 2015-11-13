package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.azhuoinfo.pshare.view.listview.LoadMoreAdapter;
import com.azhuoinfo.pshare.view.listview.OnLoadMoreListener;
import com.azhuoinfo.pshare.view.listview.pull.PullRefreshListView;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 * 我的车辆模块车列表
 */
public class CarListFragment extends BaseContentFragment {
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
        return inflater.inflate(R.layout.fragment_car_list, container, false);
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
        mListView = (PullRefreshListView) findViewById(R.id.lv_list_car);
        mAddCarTextView = (TextView) findViewById(R.id.tv_listcar_add);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.mine_carList);

        mDataAdapter = new CarListAdapter(this.getActivity());
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

        mAddCarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentFragment(AddCarInformationFragment.class, "AddCarInformationFragment", null, ModuleMenuIDS.MODULE_HOME);
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(position);
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        postCarList(mAccountVerify.getCustomer_Id(), mPageIndex);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPageIndex = 1;
        mDataAdapter.clear();
    }

    @Override
    protected void initData(Bundle bundle) {
    }

    public void showDeleteDialog(final int position) {
        CommonDialog dialog = CommonDialog.creatDialog(getActivity());
        dialog.setMessage(R.string.dialog_delete_content);
        dialog.setLeftButtonInfo(getString(R.string.common_dialog_confirm), new CommonDialog.OnButtonClickListener() {

            @Override
            public void onClick(View view) {
                CarList item = mDataAdapter.get(position);
                deleteCar(item.getCar_id());
                mDataAdapter.remove(position);
            }

        });
        dialog.setRightButtonInfo(getString(R.string.common_dialog_cancel), new CommonDialog.OnButtonClickListener() {
            @Override
            public void onClick(View view) {
                // do nothing
            }
        });
        dialog.show();
    }

    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return true;
    }

    protected void updateViews(List<CarList> list) {
        if (list != null && list.size() > 0) {
            Log.e("updateViews", list + "");
            mListLoadMoreAdapter.addMoreData(list);
            if (mListLoadMoreAdapter.getCount() > 0) {

            } else {

            }
        } else {

        }
    }

    public void postCarList(String customerId, final int pageIndex) {
        if (pageIndex == -1) {
            mListLoadMoreAdapter.addMoreData(new ArrayList<CarList>());
            return;
        }
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_CARLIST));
        apiTask.setParams(ApiContants.instance(getActivity()).userCarList(customerId, "" + pageIndex));
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
                    if (pageIndex <= 1) {
                        updateViews(list);

                    } else {
                        if (list != null && !list.isEmpty()) {
                            mListLoadMoreAdapter.addMoreData(list);
                        }
                    }

                    if (list != null && list.size() == 10) {
                        mPageIndex++;
                    } else {
                        mPageIndex = -1;
                    }

                }
            }

            @Override
            public void onFailure(String code, String message) {
                mPageIndex = -1;
                loadingDialog.dismiss();
                //showToast(message);
            }
        });
    }

    public void deleteCar(String car_id) {
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_DELETECAR));
        apiTask.setParams(ApiContants.instance(getActivity()).deleteCar(mAccountVerify.getCustomer_Id(), car_id));
        apiTask.execute(new OnDataLoader<String>() {
            public void onStart() {
            }

            @Override
            public void onSuccess(String result) {
                if (isEnable()) {
                    showToast("删除成功！");
                }
            }

            @Override
            public void onFailure(String code, String message) {
                if (isEnable()) showToast(message);
            }
        });
    }
}
