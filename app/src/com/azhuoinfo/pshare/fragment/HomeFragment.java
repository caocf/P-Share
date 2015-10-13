package com.azhuoinfo.pshare.fragment;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.Parking;

import java.util.List;

import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.view.SearchView;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;


public class HomeFragment extends BaseContentFragment implements LocationSource, AMapLocationListener ,AMap.OnInfoWindowClickListener,AMap.OnMarkerClickListener {
	private Button mMineHomeButton;
	private int mOnClick=0;
	private LinearLayout mMineHomeButtonLinearLayout;
	private Button mChangeButton;
	private AccountVerify mAccountVerify;

    private AMapLocation mAMapLocation;
	private MapView mMapView;
    private AMap mAmap;
    private LocationSource.OnLocationChangedListener mListener;
    private LocationManagerProxy mAMapLocationManager;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CustomerInfo customerInfo=(CustomerInfo)this.app.getSession().get("customerInfo");
		this.getCustomActionBar().setCustomHomeAsUpIndicator(R.drawable.homepager_user, R.drawable.left_head);
		mAccountVerify = AccountVerify.getInstance(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_home, container, false);
		return v;
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
		mMineHomeButton=(Button) view.findViewById(R.id.button_mine_home);
		mChangeButton=(Button) view.findViewById(R.id.button_change);
		mMineHomeButtonLinearLayout=(LinearLayout) view.findViewById(R.id.ll_mine_home);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_home);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);// 必须要写
        mAmap = mMapView.getMap();

		mChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(MineHomeFragment.class, "MineHomeFragment", null);
            }
        });
		mMineHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClick++;
                if (mOnClick % 2 == 1) {
                    AnimationSet set = new AnimationSet(true);
                    TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0);
                    set.addAnimation(translate);
                    set.setDuration(300);
                    set.setFillAfter(true);
                    mMineHomeButtonLinearLayout.offsetTopAndBottom(-mMineHomeButtonLinearLayout.getHeight());
                    mMineHomeButtonLinearLayout.startAnimation(set);
                } else {
                    AnimationSet set = new AnimationSet(true);
                    TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                            Animation.RELATIVE_TO_SELF, -0.5f, Animation.RELATIVE_TO_SELF, 0);
                    set.addAnimation(translate);
                    set.setDuration(300);
                    set.setFillAfter(true);
                    mMineHomeButtonLinearLayout.offsetTopAndBottom(mMineHomeButtonLinearLayout.getHeight());
                    mMineHomeButtonLinearLayout.startAnimation(set);
                }
            }
        });

	}

	@Override
	protected void initData(Bundle savedInstanceState) {
        init();
	}

    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    protected boolean onMenuActionCreated(ActionMenu actionMenu) {
        super.onMenuActionCreated(actionMenu);
        actionMenu.addMenu(1, R.string.menu_search, R.drawable.search1, 1);
        actionMenu.addMenu(2, R.string.menu_list_car, R.drawable.list_car, 1);
        return true;
    }
    @Override
    public boolean onMenuActionSelected(ActionMenuItem action) {
        switch(action.getId()){
            case 1:
                SearchView searchView=this.getCustomActionBar().startSearchMode();
                searchView.setOnSearchTextListener(new SearchView.OnSearchTextListener() {
                    @Override
                    public boolean onSearchText(String s) {
                        getSearchParkListbyName(s);
                        return true;
                    }
                });
                break;
            case 2:
                replaceFragment(ParkingDetailsFragment.class,"ParkingDetailsFragment",null);
                break;
        }
        return super.onMenuActionSelected(action);
    }


    /**
     * 初始化AMap对象
     */
    private void init() {
        if (mAmap == null) {
            mAmap = mMapView.getMap();
        }
        setUpMap();
    }
    private void setUpMap() {
        mAmap.setLocationSource(this);// 设置定位监听
        mAmap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        //mAmap.getUiSettings().setZoomControlsEnabled(false);
        mAmap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式：定位（AMap.LOCATION_TYPE_LOCATE）、跟随（AMap.LOCATION_TYPE_MAP_FOLLOW）
        // 地图根据面向方向旋转（AMap.LOCATION_TYPE_MAP_ROTATE）三种模式
        mAmap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        mAmap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        mAmap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        //mAmap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
        mAmap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = mInflater.inflate(R.layout.view_mark_content, null, false);
                Parking parking = (Parking) marker.getObject();
                TextView name = (TextView) view.findViewById(R.id.view_marker_name);
                TextView num = (TextView) view.findViewById(R.id.view_marker_num);
                TextView distance = (TextView) view.findViewById(R.id.view_marker_distance);
                TextView address = (TextView) view.findViewById(R.id.view_marker_address);

                name.setText("" + parking.getParking_name());
                if (parking.getParking_can_use() > 0) {
                    num.setText("空:" + parking.getParking_can_use());
                } else {
                    num.setText("满:" + parking.getParking_can_use());
                }
                address.setText("" + parking.getParking_address());
                int s = (int) AMapUtils.calculateLineDistance(new LatLng(Double.parseDouble(parking.getParking_latitude()), Double.parseDouble(parking.getParking_longitude())),
                        new LatLng(mAMapLocation.getLatitude(), mAMapLocation.getLongitude()));
                distance.setText(s + "米");
                return view;
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        deactivate();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    /**
     * 激活定位
     */
    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        if (mAMapLocationManager == null) {
            mAMapLocationManager = LocationManagerProxy.getInstance(this.getActivity());
            //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
            //在定位结束后，在合适的生命周期调用destroy()方法
            //其中如果间隔时间为-1，则定位只定一次
            mAMapLocationManager.requestLocationData(
                    LocationProviderProxy.AMapNetwork, 60*1000, 10, this);
        }
    }
    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mAMapLocationManager != null) {
            mAMapLocationManager.removeUpdates(this);
            mAMapLocationManager.destroy();
        }
        mAMapLocationManager = null;
    }
	@Override
	public boolean isCleanStack() {
		return true;
	}

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.getAMapException().getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                mAMapLocation=aMapLocation;
                Double geoLat = aMapLocation.getLatitude();
                Double geoLng = aMapLocation.getLongitude();
                getSearchParkListByLL("" + geoLat, "" + geoLng);
            }
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
    public void getSearchParkListbyName(String name) {
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_SEARCHPARKLISTBYNAME));
        apiTask.setParams(ApiContants.instance(getActivity()).searchParkListbyName(name));
        apiTask.setRoot("parkingList");
        apiTask.execute(new OnDataLoader<List<Parking>>() {

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(boolean page, List<Parking> list) {
                if (isEnable()) {
                    if(list!=null&&list.size()>0){
                        drawMarker(list);
                    }else{
                        showToast("没有搜索停车场");
                    }
                }
            }

            @Override
            public void onFailure(String code, String message) {
                if (isEnable()) {
                    showToast(message);
                }
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
            }

            @Override
            public void onSuccess(boolean page, List<Parking> list) {
                if (isEnable()) {
                    if(list!=null&&list.size()>0){
                        drawMarker(list);
                    }else{
                        showToast("附近没有停车场");
                    }
                }
            }

            @Override
            public void onFailure(String code, String message) {
                if (isEnable()) {
                    showToast(message);
                }
            }
        });
    }
    public void drawMarker(List<Parking> list){
        //mAmap.clear();
        if(mListener!=null&&mAMapLocation!=null)
            mListener.onLocationChanged(mAMapLocation);// 显示系统小蓝点
        MarkerOptions markerOption=null;
        Parking parking=null;
        Marker marker=null;
        for (int i=0;i<list.size();i++){
            parking=list.get(i);
            markerOption = new MarkerOptions();
            markerOption.position(new LatLng(Double.parseDouble(parking.getParking_latitude()), Double.parseDouble(parking.getParking_longitude())));
            markerOption.title(parking.getParking_name()).snippet(parking.getParking_address());
            markerOption.draggable(true);
            markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.empty));
            marker=mAmap.addMarker(markerOption);
            marker.setObject(parking);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Parking parking= (Parking) marker.getObject();
        Bundle bundle=new Bundle();
        bundle.putParcelable("parking", parking);
        replaceFragment(ParkingDetailsItemFragment.class, "ParkingDetailsItemFragment", bundle);
    }
}
