package com.azhuoinfo.pshare.fragment;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.fragment.adapter.ParkingDetailsAdapter;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.Parking;
import com.azhuoinfo.pshare.model.UnfinishedOrderInfo;
import com.azhuoinfo.pshare.view.PromptView;

import java.util.List;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.view.SearchView;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 * 首页右边：周边车场列表
 */
public class ParkingDetailsFragment extends BaseContentFragment implements AMapLocationListener {

    private PromptView mPromptView;
    private ListView mParkingDetailsListView;
    private ParkingDetailsAdapter mDataAdapter;
    private AccountVerify mAccountVerify;
    private LocationManagerProxy mLocationManagerProxy;
    private AMapLocation mAMapLocation;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mPromptView=(PromptView) findViewById(R.id.promptView);
        mParkingDetailsListView=(ListView) findViewById(R.id.lv_parking_details);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.parking_details);
        mDataAdapter=new ParkingDetailsAdapter(this.getActivity());
        mParkingDetailsListView.setAdapter(mDataAdapter);
        mParkingDetailsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Parking item = (Parking) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("parking", item);
                replaceFragment(ParkingDetailsItemFragment.class, "ParkingDetailsItemFragment", bundle);
            }
        });

    }
    @Override
    protected boolean onMenuActionCreated(ActionMenu actionMenu) {
        super.onMenuActionCreated(actionMenu);
        actionMenu.addMenu(1, R.string.menu_search, R.drawable.search1, 1);
        actionMenu.addMenu(2, R.string.menu_maps, R.drawable.car_list_right, 1);
        return true;
    }
    @Override
    public boolean onMenuActionSelected(ActionMenuItem action) {
        switch(action.getId()){
            case 1:
                final SearchView searchView=this.getCustomActionBar().startSearchMode();
                searchView.setSearchTextHint("我想停在哪里附近？");
                searchView.setActioImageResource(R.drawable.actionbar_search);
                searchView.setOnActionClickListener(new SearchView.OnActionClickListener() {
                    @Override
                    public boolean onActionClick(String s) {
                        getSearchParkListbyName(s);
                        return true;
                    }
                });
                searchView.setOnSearchTextListener(new SearchView.OnSearchTextListener() {
                    @Override
                    public boolean onSearchText(String s) {
                        getSearchParkListbyName(s);
                        return true;
                    }
                });
                break;
            case 2:
                popBackStack();
                break;
        }
        return super.onMenuActionSelected(action);
    }

    @Override
    protected void initData(Bundle bundle) {
        initLocation();
    }
    protected void updateViews(List<Parking> list) {
        if(list!=null&&list.size()>0){
            mDataAdapter.clear();
            mDataAdapter.addAll(list);
            mDataAdapter.setaMapLocation(mAMapLocation);
            if(mDataAdapter.getCount()>0){
                mPromptView.showContent();
            }else{
                mPromptView.showPrompt(R.string.common_empty);
            }
        }else{
            mPromptView.showPrompt(R.string.common_empty);
        }
    }
    public void getSearchParkListbyName(String name) {
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
    public void getSearchParkListByLL(String latitude,String longitude) {
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_SEARCHPARKLISTBYLL));
        apiTask.setParams(ApiContants.instance(getActivity()).searchParkListByLL(latitude, longitude));
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
    /**
     * 初始化定位
     */
    private void initLocation() {
        mLocationManagerProxy = LocationManagerProxy.getInstance(this.getActivity());
        //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
        //在定位结束后，在合适的生命周期调用destroy()方法
        //其中如果间隔时间为-1，则定位只定一次
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);
        mLocationManagerProxy.setGpsEnable(true);
    }
    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0){
                mAMapLocation=aMapLocation;
                Double geoLat = aMapLocation.getLatitude();
                Double geoLng = aMapLocation.getLongitude();
                getSearchParkListByLL("" + geoLat, "" + geoLng);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLocationManagerProxy.destroy();
    }

}
