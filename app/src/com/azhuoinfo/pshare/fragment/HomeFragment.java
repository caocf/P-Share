package com.azhuoinfo.pshare.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
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
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.MyLocationStyleCreator;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiSearch;
import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.model.Parking;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.Inflater;

import mobi.cangol.mobile.actionbar.ActionBarActivity;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.view.SearchView;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.sdk.utils.HttpUtils;
import mobi.cangol.mobile.service.session.SessionService;
import mobi.cangol.mobile.utils.BitmapUtils;
import mobi.cangol.mobile.utils.DeviceInfo;


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
    private SessionService mSessionService;
    private static final int ZOOM=15;
    private TextView mParkAddressTextView;

    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAccountVerify = AccountVerify.getInstance(getActivity());
        mSessionService = getSession();
	}

    //View contentView;
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
        mParkAddressTextView = (TextView)view.findViewById(R.id.tv_parking_address);
        mParkChangeTextView=(TextView) view.findViewById(R.id.tv_parking_change);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(R.string.title_home);
        ((ActionBarActivity) this.getActivity()).setStatusBarTintColor(getActivity().getResources().getColor(R.color.actionbar_background));
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);// 必须要写
        mAmap = mMapView.getMap();
        mAmap.moveCamera(CameraUpdateFactory.zoomTo(ZOOM));
        mParkChangeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(MineHomeFragment.class, "MineHomeFragment", null);
            }
        });
        this.findViewById(R.id.layout_parking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDefaultParking != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("parking", mDefaultParking);
                    replaceFragment(ParkingDetailsItemFragment.class, "ParkingDetailsItemFragment", bundle);
                }
            }
        });

        if (mSessionService.getBoolean("IsHomeLayoutOpen",false)){
            mMineHomeButtonLinearLayout.setVisibility(View.VISIBLE);
        }

		mMineHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMineHomeButtonLinearLayout.getVisibility() == View.VISIBLE) {
                    mMineHomeButtonLinearLayout.setVisibility(View.GONE);
                    mSessionService.put("IsHomeLayoutOpen",false);
                } else {
                    mSessionService.put("IsHomeLayoutOpen",true);
                    mMineHomeButtonLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        showcase();

	}

    //InputMethodManager imm;
	@Override
	protected void initData(Bundle savedInstanceState) {
        init();
        if(mSessionService.getSerializable("default_parking")!=null){
            mDefaultParking= (Parking) mSessionService.getSerializable("default_parking");
            getSearchParkbyId(mDefaultParking.getParking_id());
        }else{
            Log.e("default_id not");
            updateDefaltParking(null);
        }
    }
    private void showcase(){
        SharedPreferences  preferences=getActivity().getPreferences(Context.MODE_PRIVATE);
        if(!preferences.getBoolean("showcase_home",false)){
            ((ActionBarActivity)this.getActivity()).setMaskView(R.layout.layout_showcase_home);
            ((ActionBarActivity)this.getActivity()).displayMaskView(true);
            this.getActivity().findViewById(R.id.imageView_showcase).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActionBarActivity) getActivity()).displayMaskView(false);
                }
            });
            preferences.edit().putBoolean("showcase_home",true).commit();
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

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            android.util.Log.e("Text Change", "before " + s);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            android.util.Log.e("Text Change", "on     " + s);
            if (isEnable()) {
                String newText = s.toString().trim();

                final Inputtips inputTips = new Inputtips(getActivity(),
                        new Inputtips.InputtipsListener() {

                            @Override
                            public void onGetInputtips(List<Tip> tipList, int rCode) {
                                if (rCode == 0) {// 正确返回
                                    mTipList = tipList;
                                    List<String> listString = new ArrayList<String>();

                                    for (int i = 0; i < tipList.size(); i++) {
                                        listString.add(tipList.get(i).getName());
                                        android.util.Log.e("search result", listString.get(i));
                                    }
                                    if (mArrayAdapter!=null){
                                        mArrayAdapter.clear();
                                        mArrayAdapter.addAll(listString);
                                    }
                                }
                            }
                        });

                try {
                    inputTips.requestInputtips(newText, "021");// 第一个参数表示提示关键字，第二个参数默认代表全国，也可以为城市区号
                } catch (AMapException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    List<Tip> mTipList;

    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索

    ArrayAdapter<String> mArrayAdapter;
    @Override
    public boolean onMenuActionSelected(ActionMenuItem action) {
        switch(action.getId()){
            case 1:
                final SearchView searchView;
                searchView=((ActionBarActivity)getActivity()).startSearchMode();
                searchView.setSearchTextHint("我想停在哪里附近？");
                searchView.setActioImageResource(R.drawable.actionbar_search);
                searchView.setSearchHistoryEnable(false);
                LinearLayout linearLayout = (LinearLayout)searchView.findViewById(R.id.actionbar_search_content);
                final View view = getActivity().getLayoutInflater().inflate(R.layout.popupview,linearLayout,true);
                ListView innerlistView = (ListView)view.findViewById(R.id.list);
                innerlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Tip tip = mTipList.get(0);
                        searchView.hide();
                        mAmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(tip.getPoint().getLatitude(), tip.getPoint().getLongitude()), ZOOM + 2));
                    }
                });

                mArrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1);
                innerlistView.setAdapter(mArrayAdapter);
                searchView.setOnActionClickListener(new SearchView.OnActionClickListener() {
                    @Override
                    public boolean onActionClick(String s) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(getActivity().getCurrentFocus(), InputMethodManager.HIDE_NOT_ALWAYS);
                        Log.e("visibility", "act");
                        return true;
                    }
                });
                searchView.geSearchEditText().removeTextChangedListener(textWatcher);
                searchView.geSearchEditText().addTextChangedListener(textWatcher);
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
            //默认上海坐标
            mAmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(31.22,121.48),ZOOM));
        }
        setUpMap();
    }
    private void setUpMap() {
        mAmap.setLocationSource(this);// 设置定位监听
        mAmap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mAmap.getUiSettings().setScaleControlsEnabled(true);
        mAmap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式：定位（AMap.LOCATION_TYPE_LOCATE）、跟随（AMap.LOCATION_TYPE_MAP_FOLLOW）
        // 地图根据面向方向旋转（AMap.LOCATION_TYPE_MAP_ROTATE）三种模式
        mAmap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        mAmap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        //mAmap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        mAmap.getUiSettings().setZoomControlsEnabled(false);
        mAmap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        mAmap.getUiSettings().setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
        mAmap.getUiSettings().setCompassEnabled(true);
        //// 设置自定义InfoWindow样式
        mAmap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = mInflater.inflate(R.layout.view_mark_content2, null, false);
                final Parking parking = (Parking) marker.getObject();
                TextView name = (TextView) view.findViewById(R.id.view_marker_name);
                TextView num = (TextView) view.findViewById(R.id.view_marker_num);
                TextView distance = (TextView) view.findViewById(R.id.view_marker_distance);

                name.setText("" + parking.getParking_name());
                num.setText("剩余车位：" + parking.getParking_can_use());
                distance.setText("距离：" + parking.getParking_distance() + "米");

                LinearLayout ll_park_item = (LinearLayout)view.findViewById(R.id.park_item);
                LinearLayout ll_park_navigate = (LinearLayout)view.findViewById(R.id.park_navigate);

                ll_park_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("parking", parking);
                        replaceFragment(ParkingDetailsItemFragment.class, "ParkingDetailsItemFragment", bundle);
                    }
                });

                ll_park_navigate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupNavi(v,parking);
                    }
                });

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
            public void onSuccess(Parking parking) {
                if (isEnable()) {
                    mSessionService.saveSerializable("default_parking", parking);
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
            mParkNumTextView.setText("剩余车位:"+parking.getParking_can_use());
            mParkAddressTextView.setText(parking.getParking_address());
            mMineHomeButton.setText(""+parking.getParking_can_use());
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
            public void onSuccess(List<Parking> list) {
                if (isEnable()) {
                        drawMarker(list,true);
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
            public void onSuccess(List<Parking> list) {
                if (isEnable()) {
                    drawMarker(list, false);
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
    public void sort(List<Parking> list){
        if(list!=null&&list.size()>0){
            for (int i=0;i<list.size();i++){
                Parking parking =list.get(i);
                int s = (int) AMapUtils.calculateLineDistance(new LatLng(Double.parseDouble(parking.getParking_latitude()), Double.parseDouble(parking.getParking_longitude())),
                        new LatLng(mAMapLocation.getLatitude(), mAMapLocation.getLongitude()));
                parking.setParking_distance(""+s);
            }
        }
        Collections.sort(list, new Comparator<Parking>() {
            @Override
            public int compare(Parking lhs, Parking rhs) {
                return Integer.parseInt(lhs.getParking_distance()) - Integer.parseInt(rhs.getParking_distance());
            }
        });
    }
    public void drawMarker(List<Parking> list,boolean move){
        if(list!=null&&list.size()>0){
            if(mAMapLocation!=null)
            sort(list);
            MarkerOptions markerOption=null;
            Parking parking=null;
            Marker marker=null;
            for (int i=0;i<list.size();i++){
                parking=list.get(i);
                markerOption = new MarkerOptions();
                markerOption.position(new LatLng(Double.parseDouble(parking.getParking_latitude()), Double.parseDouble(parking.getParking_longitude())));
                markerOption.title(parking.getParking_name()).snippet(parking.getParking_address());
                markerOption.draggable(true);
                if(parking.getParking_can_use()==0){
                    Bitmap bitmap=BitmapUtils.addWatermark(BitmapFactory.decodeResource(getResources(),R.drawable.full),
                            22f* DeviceInfo.getDensity(getActivity()),
                            18* DeviceInfo.getDensity(getActivity()),
                            ""+parking.getParking_can_use(),
                            getResources().getColor(R.color.parking_full),
                            (int) (12* DeviceInfo.getDensity(getActivity())),
                            100);
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                }else if(parking.getParking_can_use()<5){
                    Bitmap bitmap=BitmapUtils.addWatermark(BitmapFactory.decodeResource(getResources(),R.drawable.lack),
                            22f* DeviceInfo.getDensity(getActivity()),
                            18* DeviceInfo.getDensity(getActivity()),
                            ""+parking.getParking_can_use(),
                            getResources().getColor(R.color.parking_lack),
                            (int) (12* DeviceInfo.getDensity(getActivity())),
                            100);
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                }else{
                    int l=parking.getParking_can_use()>9?2:1;
                    Bitmap bitmap=BitmapUtils.addWatermark(BitmapFactory.decodeResource(getResources(),R.drawable.empty),
                            l==2?21f* DeviceInfo.getDensity(getActivity()):22* DeviceInfo.getDensity(getActivity()),
                            18* DeviceInfo.getDensity(getActivity()),
                            ""+parking.getParking_can_use(),
                            getResources().getColor(R.color.parking_empty),
                            (int) (12* DeviceInfo.getDensity(getActivity())),
                            100);
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                }

                //markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.empty));
                markerOption.anchor(0.2f,1.0f);
                marker=mAmap.addMarker(markerOption);
                marker.setObject(parking);
                if(i==0&&move){
                    marker.showInfoWindow();
                    mAmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(parking.getParking_latitude()), Double.parseDouble(parking.getParking_longitude())),ZOOM));
                }
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

    void popupNavi(View view,final Parking parking){
        PopupMenu popupMenu = new PopupMenu(getActivity(),view);
        popupMenu.inflate(R.menu.navi_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_autonavi:
                        if (isAppInstalled(getActivity(), "com.autonavi.minimap")) {
                            shareToAutoNavi(parking);
                        } else {
                            showToast("高德地图未安装");
                        }
                        break;
                    case R.id.action_baidunavi:
                        if (isAppInstalled(getActivity(), "com.baidu.BaiduMap")) {
                            shareToBaidu(parking);
                        } else {
                            showToast("百度地图未安装");
                        }
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    void shareToAutoNavi(Parking parking){
        String uriString = String.format(
                "androidamap://navi?sourceApplication=%s&poiname=%s&lat=%s&lon=%s&dev=1&style=2",
                "口袋停",
                parking.getParking_name(),
                parking.getParking_latitude(),
                parking.getParking_longitude()
        );
        try {
            Intent intent = Intent.parseUri(uriString,0);
            intent.setPackage("com.autonavi.minimap");
            startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    void shareToBaidu(Parking parking){
        String uriString = String.format(
                "intent://map/direction?origin=latlng:%s,%s|name:%s&destination=%s&mode=driving&src=口袋停|口袋停#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end",
                parking.getParking_latitude(),
                parking.getParking_longitude(),
                "我的位置",
                parking.getParking_name()
        );

        try {
            Intent intent = Intent.parseUri(uriString,0);
            startActivity(intent); //启动调用
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private boolean isAppInstalled(Context context,String packagename)
    {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        }catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if(packageInfo ==null){
            //System.out.println("没有安装");
            return false;
        }else{
            //System.out.println("已经安装");
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
