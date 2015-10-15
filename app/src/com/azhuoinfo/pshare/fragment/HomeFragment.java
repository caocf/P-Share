package com.azhuoinfo.pshare.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
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
import com.azhuoinfo.pshare.model.Parking;

import java.util.List;

import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.view.SearchView;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.service.AppService;
import mobi.cangol.mobile.service.global.GlobalData;
import mobi.cangol.mobile.utils.BitmapUtils;


public class HomeFragment extends BaseContentFragment implements LocationSource, AMapLocationListener ,AMap.OnInfoWindowClickListener,AMap.OnMarkerClickListener {
	private Button mMineHomeButton;
	private LinearLayout mMineHomeButtonLinearLayout;
    private TextView mParkNameTextView;
    private TextView mParkNumTextView;
    private TextView mParkDistanceTextView;
    private TextView mParkChangeTextView;
	private AccountVerify mAccountVerify;

    private AMapLocation mAMapLocation;
	private MapView mMapView;
    private AMap mAmap;
    private LocationSource.OnLocationChangedListener mListener;
    private LocationManagerProxy mAMapLocationManager;

    private Parking mDefaultParking;
    private GlobalData mGlobalData;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getCustomActionBar().setCustomHomeAsUpIndicator(R.drawable.homepager_user, R.drawable.left_head);
		mAccountVerify = AccountVerify.getInstance(getActivity());
        mGlobalData = (GlobalData) getAppService(AppService.GLOBAL_DATA);
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
		mMineHomeButtonLinearLayout=(LinearLayout) view.findViewById(R.id.ll_mine_home);
        mParkNameTextView=(TextView) view.findViewById(R.id.tv_parking_name);
        mParkNumTextView=(TextView) view.findViewById(R.id.tv_parking_num);
        mParkDistanceTextView=(TextView) view.findViewById(R.id.tv_parking_distance);
        mParkChangeTextView=(TextView) view.findViewById(R.id.tv_parking_change);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_home);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);// 必须要写
        mAmap = mMapView.getMap();
        mAmap.moveCamera(CameraUpdateFactory.zoomTo(15));
        mParkChangeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(MineHomeFragment.class, "MineHomeFragment", null);
            }
        });
        this.findViewById(R.id.layout_parking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDefaultParking!=null){
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("parking", mDefaultParking);
                    replaceFragment(ParkingDetailsItemFragment.class, "ParkingDetailsItemFragment", bundle);
                }
            }
        });
		mMineHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMineHomeButtonLinearLayout.getVisibility() == View.VISIBLE) {
                    mMineHomeButtonLinearLayout.setVisibility(View.GONE);
                } else {
                    mMineHomeButtonLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });

	}

	@Override
	protected void initData(Bundle savedInstanceState) {
        init();

        if(mGlobalData.get("default_parking")!=null){
            mDefaultParking= (Parking) mGlobalData.get("default_parking");
            getSearchParkbyId(mDefaultParking.getParking_id());
        }else{
            Log.e("default_id not");
            updateDefaltParking(null);
        }

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
        mAmap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式：定位（AMap.LOCATION_TYPE_LOCATE）、跟随（AMap.LOCATION_TYPE_MAP_FOLLOW）
        // 地图根据面向方向旋转（AMap.LOCATION_TYPE_MAP_ROTATE）三种模式
        mAmap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        mAmap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        mAmap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        mAmap.getUiSettings().setZoomControlsEnabled(false);
        mAmap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        mAmap.getUiSettings().setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
        mAmap.getUiSettings().setCompassEnabled(true);
        //// 设置自定义InfoWindow样式
        mAmap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = mInflater.inflate(R.layout.view_mark_content, null, false);
                Parking parking = (Parking) marker.getObject();
                TextView name = (TextView) view.findViewById(R.id.view_marker_name);
                TextView num = (TextView) view.findViewById(R.id.view_marker_num);
                TextView distance = (TextView) view.findViewById(R.id.view_marker_distance);
                TextView address = (TextView) view.findViewById(R.id.view_marker_address);
                TextView price = (TextView) view.findViewById(R.id.view_marker_price);

                name.setText("" + parking.getParking_name());
                if (parking.getParking_can_use() > 0) {
                    num.setText("空:" + parking.getParking_can_use());
                } else {
                    num.setText("满:" + parking.getParking_can_use());
                }
                price.setText(parking.getParking_charging_standard()+"元");
                address.setText("" + parking.getParking_address());
                distance.setText(parking.getParking_distance() + "米");
                return view;
            }

            @Override
            public View getInfoContents(Marker marker) {

                return null;
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
            showToast("正在定位中....");
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
                updateDefaltParking(mDefaultParking);
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

    public void getSearchParkbyId(String id) {
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_SEARCHPARKBYID));
        apiTask.setParams(ApiContants.instance(getActivity()).searchParkbyId(id));
        apiTask.setRoot("parking");
        apiTask.execute(new OnDataLoader<Parking>() {

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(boolean page, Parking parking) {
                if (isEnable()) {
                    mGlobalData.save("default_parking",parking);
                    mDefaultParking=parking;
                    updateDefaltParking(parking);
                }
            }

            @Override
            public void onFailure(String code, String message) {

            }
        });
    }

    private void updateDefaltParking(Parking parking) {
        if(parking!=null){
            mParkNameTextView.setText(parking.getParking_name());
            if(parking.getParking_can_use()>0){
                mParkNumTextView.setText("空:"+parking.getParking_can_use());
            }else{
                mParkNumTextView.setText("满:"+parking.getParking_can_use());
            }
            mMineHomeButton.setText(""+parking.getParking_can_use());
            if(mAMapLocation!=null){
                int s = (int) AMapUtils.calculateLineDistance(new LatLng(Double.parseDouble(parking.getParking_latitude()), Double.parseDouble(parking.getParking_longitude())),
                        new LatLng(mAMapLocation.getLatitude(), mAMapLocation.getLongitude()));
                parking.setParking_distance("" + s);
                mParkDistanceTextView.setText(s+"米");
            }
        }else{

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
            }

            @Override
            public void onSuccess(boolean page, List<Parking> list) {
                if (isEnable()) {
                        drawMarker(list);
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
                    drawMarker(list);
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
        if(list!=null&&list.size()>0){
            //mAmap.clear();//清除marker信息，（清除掉了当前位置）
            mAmap.setLocationSource(this);
            mAmap.getUiSettings().setMyLocationButtonEnabled(true);
            mAmap.setMyLocationEnabled(true);

            MarkerOptions markerOption=null;
            Parking parking=null;
            Marker marker=null;
            for (int i=0;i<list.size();i++){
                parking=list.get(i);
                int s = (int) AMapUtils.calculateLineDistance(new LatLng(Double.parseDouble(parking.getParking_latitude()), Double.parseDouble(parking.getParking_longitude())),
                        new LatLng(mAMapLocation.getLatitude(), mAMapLocation.getLongitude()));
                parking.setParking_distance(""+s);
                markerOption = new MarkerOptions();
                markerOption.position(new LatLng(Double.parseDouble(parking.getParking_latitude()), Double.parseDouble(parking.getParking_longitude())));
                markerOption.title(parking.getParking_name()).snippet(parking.getParking_address());
                markerOption.draggable(true);
                if(parking.getParking_can_use()>0){
                    int l=parking.getParking_can_use()>9?2:1;
                    Bitmap bitmap=BitmapUtils.addWatermark(BitmapFactory.decodeResource(getResources(),R.drawable.empty),
                            l==2?30-12:30-6,
                            36,
                            ""+parking.getParking_can_use(),
                            getResources().getColor(R.color.menu_text_pressed),
                            24,
                            100);
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                }else{
                    Bitmap bitmap=BitmapUtils.addWatermark(BitmapFactory.decodeResource(getResources(),R.drawable.full),
                            24,
                            36,
                            ""+parking.getParking_can_use(),
                            getResources().getColor(R.color.text_light_gray),
                            24,
                            100);
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                }

                //markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.custom_map_image));
                marker=mAmap.addMarker(markerOption);
                marker.setObject(parking);
                if(i==0)
                marker.showInfoWindow();
            }
        }else{
            showToast("附近没有停车场");
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
