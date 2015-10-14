package com.azhuoinfo.pshare.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.fragment.adapter.CarIdAdapter;
import com.azhuoinfo.pshare.model.CityModel;
import com.azhuoinfo.pshare.model.DistrictModel;
import com.azhuoinfo.pshare.model.ProvinceModel;
import com.azhuoinfo.pshare.utils.XmlParserHandler;
import com.azhuoinfo.pshare.view.wheel.OnWheelChangedListener;
import com.azhuoinfo.pshare.view.wheel.WheelView;
import com.azhuoinfo.pshare.view.wheel.adapters.ArrayWheelAdapter;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

@SuppressLint("ClickableViewAccessibility")
public class AreaDialog {
	private final static String TAG = "CarIdDialog";

    protected String[] mProvinceDatas;
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();
    protected String mCurrentProviceName;
    protected String mCurrentCityName;
    protected String mCurrentDistrictName ="";
    protected String mCurrentZipCode ="";

	private AlertDialog.Builder builder;
	private Context context;
	private AlertDialog dialog;
	private View mLayoutView;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private Button mBtnConfirm;

    private OnSelectListener mOnSelectListener;


    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener{
        void onSelect(String province,String city,String district,String zipcode);
    }


	private AreaDialog(Context context) {
		this.context = context;
		//fix bug 低版本 nosuchmethod
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			builder = new AlertDialog.Builder(context);
		} else {
			builder = new AlertDialog.Builder(context,R.style.TransparentDialog_Bottom);
		}
		this.dialog = builder.create();
		dialog.show();
		dialog.setContentView(R.layout.dialog_wheel_area);
		dialog.setCanceledOnTouchOutside(true);
		initViews();
	}

	public static AreaDialog creatDialog(Context context) {
		return new AreaDialog(context);
	}

	public boolean isShow() {
		return dialog.isShowing();
	}

	public AreaDialog self() {
		return this;
	}

	public AreaDialog show() {
		dialog.show();
		return this;
	}

	public AreaDialog dismiss() {
		dialog.cancel();
		return this;
	}

	private void initViews() {
		mLayoutView= dialog.findViewById(R.id.dialog_area_layout);
        mViewProvince = (WheelView)dialog.findViewById(R.id.id_province);
        mViewCity = (WheelView) dialog.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) dialog.findViewById(R.id.id_district);
        mBtnConfirm = (Button) dialog.findViewById(R.id.btn_confirm);

        mViewProvince.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateCities();
            }
        });
        mViewCity.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateAreas();
            }
        });
        mViewDistrict.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
                mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
            }
        });
        mBtnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnSelectListener!=null)
                    mOnSelectListener.onSelect(mCurrentProviceName,mCurrentCityName,mCurrentDistrictName,mCurrentZipCode);
                dismiss();
            }
        });
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(this.context, mProvinceDatas));
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
	}
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[] { "" };
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this.context, areas));
        mViewDistrict.setCurrentItem(0);
    }
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[] { "" };
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this.context, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    public void initProvinceDatas(){
        List<ProvinceModel> provinceList = null;
        AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            if (provinceList!= null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList!= null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            mProvinceDatas = new String[provinceList.size()];
            for (int i=0; i< provinceList.size(); i++) {
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j=0; j< cityList.size(); j++) {
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k=0; k<districtList.size(); k++) {
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }
}
