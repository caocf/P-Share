package com.azhuoinfo.pshare.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.azhuoinfo.pshare.R;
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
public class SingleWheelDialog {
	private final static String TAG = "SingleWheelDialog";

    String[] strings;

	private AlertDialog.Builder builder;
	private Context context;
	private AlertDialog dialog;
	private View mLayoutView;

    int selectedIndex = 0;

    private WheelView mViewDistrict;
    private Button mBtnConfirm;

    private OnSelectListener mOnSelectListener;


    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener{
        void onSelect(int index);
    }


	private SingleWheelDialog(Context context, OnSelectListener onSelectListener,String[] strings) {
		this.context = context;
        this.strings = strings;
        if (strings!=null && strings.length>0){
            selectedIndex = 0;
        }
        mOnSelectListener = onSelectListener;
		//fix bug 低版本 nosuchmethod
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			builder = new AlertDialog.Builder(context);
		} else {
			builder = new AlertDialog.Builder(context,R.style.TransparentDialog_Bottom);
		}
		this.dialog = builder.create();
		dialog.show();
        dialog.setContentView(R.layout.dialog_wheel_single_area);
		dialog.setCanceledOnTouchOutside(true);
		initViews();
	}

	public static SingleWheelDialog creatDialog(Context context, OnSelectListener onSelectListener,String[] strings) {
		return new SingleWheelDialog(context, onSelectListener,strings);
	}

	public boolean isShow() {
		return dialog.isShowing();
	}

	public SingleWheelDialog self() {
		return this;
	}

	public SingleWheelDialog show() {
		dialog.show();
		return this;
	}

	public SingleWheelDialog dismiss() {
		dialog.cancel();
		return this;
	}

	private void initViews() {
		mLayoutView= dialog.findViewById(R.id.dialog_area_layout);/*
        mViewProvince = (WheelView)dialog.findViewById(R.id.id_province);
        mViewCity = (WheelView) dialog.findViewById(R.id.id_city);*/
        mViewDistrict = (WheelView) dialog.findViewById(R.id.id_district);
        mBtnConfirm = (Button) dialog.findViewById(R.id.btn_confirm);
        mLayoutView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }

        });
        mViewDistrict.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectedIndex = newValue;
            }
        });
        mBtnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSelectListener != null)
                    mOnSelectListener.onSelect(selectedIndex);
                dismiss();
            }
        });
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this.context, strings));
        mViewDistrict.setVisibleItems(7);

	}
}
