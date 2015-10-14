package com.azhuoinfo.pshare.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.fragment.adapter.CarIdAdapter;

import java.util.Arrays;
import java.util.List;

@SuppressLint("ClickableViewAccessibility")
public class CarIdDialog {
	private final static String TAG = "CarIdDialog";
	private AlertDialog.Builder builder;
	private Context context;
	private AlertDialog dialog;

	private View mLayoutView;
	private GridView mChGridView;
    private GridView mEnGridView;
    private CarIdAdapter mZhCarIdAdapter;
    private CarIdAdapter mEnCarIdAdapter;

    private String[] zhs={"辽","吉","黑","冀","晋","陕","鲁","皖","苏","浙","豫","鄂","湘","赣","台",
            "闽","滇","琼","川","粤","甘","青", "渝","沪","津","京","宁","蒙","藏","新","贵", "港","澳"};

    private String[] ens={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T",
            "U","V","W","X","Y","Z"};

	private CarIdDialog(Context context) {
		this.context = context;
		//fix bug 低版本 nosuchmethod
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			builder = new AlertDialog.Builder(context);
		} else {
			builder = new AlertDialog.Builder(context,R.style.TransparentDialog_Bottom);
		}
		this.dialog = builder.create();
		dialog.show();
		dialog.setContentView(R.layout.dialog_car_id);
		dialog.setCanceledOnTouchOutside(true);
		initViews();
	}

	public static CarIdDialog creatDialog(Context context) {
		return new CarIdDialog(context);
	}

	public boolean isShow() {
		return dialog.isShowing();
	}

	public CarIdDialog self() {
		return this;
	}

	public CarIdDialog show() {
		dialog.show();
		return this;
	}

	public CarIdDialog dismiss() {
		dialog.cancel();
		return this;
	}

	private void initViews() {
		mLayoutView= dialog.findViewById(R.id.dialog_car_layout);
        mChGridView = (GridView) dialog.findViewById(R.id.gridview_zh);
        mEnGridView = (GridView) dialog.findViewById(R.id.gridview_en);

        List<String> zhList=Arrays.asList(zhs);
        List<String> enList=Arrays.asList(ens);
        mZhCarIdAdapter=new CarIdAdapter(context,zhList);
        mEnCarIdAdapter=new CarIdAdapter(context,enList);
        mZhCarIdAdapter.setSelectedMode(true);
        mEnCarIdAdapter.setSelectedMode(true);
        mChGridView.setAdapter(mZhCarIdAdapter);
        mEnGridView.setAdapter(mEnCarIdAdapter);
		mLayoutView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }

        });
        mChGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mZhCarIdAdapter.singleSelected(position);
                    if(mOnIdSelectListener!=null)
                        mOnIdSelectListener.onIdSelectZh((String) parent.getItemAtPosition(position));
            }

        });
        mEnGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mEnCarIdAdapter.singleSelected(position);
                if(mOnIdSelectListener!=null)
                    mOnIdSelectListener.onIdSelectEn((String) parent.getItemAtPosition(position));
            }

        });

	}

	private OnIdSelectListener mOnIdSelectListener;


    public void setOnIdSelectListener(OnIdSelectListener onIdSelectListener) {
        this.mOnIdSelectListener = onIdSelectListener;
    }

    public interface OnIdSelectListener{
		void onIdSelectZh(String zh);
        void onIdSelectEn(String en);
	}
}
