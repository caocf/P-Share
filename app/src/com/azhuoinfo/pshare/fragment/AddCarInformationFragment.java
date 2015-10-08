package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class AddCarInformationFragment extends BaseContentFragment{

    //返回到上个页面
    //private Button activity_back;

    //点击出现一个自定义的list列表选择车的归属地
    private RelativeLayout mCarLocationRelativeLayout;
    //设置选择车的归属地
    private TextView mCarLocation1TextView;
    private TextView mCarLocationTextView;
    private LinearLayout mCarLocationLinearLayout;
    private GridView mCarArea1GridView;
    private GridView mCarArea2GridView;
    //设置车牌号
    private EditText mCarNumbrEditText;
    //设置车型
    private EditText mCarSizeEditText;
    //确定信息携带数据跳转到车列表界面将信息添加到其list中
    private Button mConfirmButton;

    private AccountVerify mAccountVerify;

    private String[] carArea1={"辽","吉","黑","冀","晋","陕","鲁","皖","苏","浙","豫","鄂","湘","赣","台",
            "闽","滇","琼","川","黔","粤","甘","青", "渝","沪","津","京","宁","蒙","藏","新","贵", "港","澳"};
    private List<Map<String,String>> carList1;

    private String[] carArea2={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T",
            "U","V","W","X","Y","Z"};
    private List<Map<String,String>> carList2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
        carList1=new ArrayList<Map<String,String>>();
        carList2=new ArrayList<Map<String,String>>();
        for (int i=0;i<carArea1.length;i++){
            HashMap<String,String> map=new HashMap<String,String>();
            map.put("area1",carArea1[i]);
            carList1.add(map);
        }
        for (int i=0;i<carArea2.length;i++){
            HashMap<String,String> map=new HashMap<String,String>();
            map.put("area2",carArea2[i]);
            carList2.add(map);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_add_car_information,container,false);
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
        mCarLocationRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_car_location);
        mCarLocationLinearLayout=(LinearLayout) view.findViewById(R.id.ll_car_area);
        mCarArea1GridView=(GridView) view.findViewById(R.id.gv_car_area1);
        mCarArea2GridView=(GridView) view.findViewById(R.id.gv_car_area2);
        mCarLocationTextView=(TextView) view.findViewById(R.id.tv_car_location);
        mCarLocation1TextView=(TextView) view.findViewById(R.id.tv_car_location1);

        mCarNumbrEditText=(EditText) view.findViewById(R.id.et_car_number);
        mCarSizeEditText=(EditText) view.findViewById(R.id.et_car_size);

        mConfirmButton=(Button) view.findViewById(R.id.button_confirm);
    }

    @Override
    protected void initViews(Bundle bundle) {
        //this.setTitle(R.string.add_car_information);

        final SimpleAdapter adapter1=new SimpleAdapter(getActivity(),carList1,R.layout.list_item_car_area,
                new String[]{"area1"},new int[]{R.id.button_car_area});
        mCarArea1GridView.setAdapter(adapter1);
        Log.e(TAG, carList1.size() + "");
        Log.e(TAG, carList1.get(1).toString());

        SimpleAdapter adapter2=new SimpleAdapter(getActivity(),carList2,R.layout.list_item_car_area,
                new String[]{"area2"},new int[]{R.id.button_car_area});
        mCarArea2GridView.setAdapter(adapter2);

        mCarLocationRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCarLocationLinearLayout.setVisibility(View.VISIBLE);
            }
        });
        mCarArea1GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> item = (HashMap<String, String>) parent.getItemAtPosition(position);
                Log.e(TAG, (String) item.get("area1"));
                setTitle(item.get("area1"));
                mCarLocation1TextView.setText(item.get("area1").toString());
                //replaceFragment(MonthlyRentCarFinishPayFragment.class, "MonthlyRentCarFinishPayFragment", null);
            }
        });
        mCarArea2GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCarLocationTextView.setText(carList2.get(1).toString());
            }
        });
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return false;
    }
}
