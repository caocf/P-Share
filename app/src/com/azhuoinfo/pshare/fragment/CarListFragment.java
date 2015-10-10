package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.fragment.adapter.CarListAdapter;
import com.azhuoinfo.pshare.model.CarList;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.view.listview.MyListView;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class CarListFragment extends BaseContentFragment{

    //返回到上个页面
    //private ImageView activity_back;
    //添加进入的车列表
    private MyListView mCarListView;
    //添加车
    private RelativeLayout mAddCarRelativeLayout;
    private AccountVerify mAccountVerify;
    private ArrayList<String> list=new ArrayList<String>();
    private CustomerInfo customerInfo;
    private String customer_Id;
    private int height;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getCustomActionBar().setCustomHomeAsUpIndicator(R.drawable.left_head, R.drawable.left_head);
        mAccountVerify = AccountVerify.getInstance(getActivity());
        customerInfo=(CustomerInfo)this.app.getSession().get("customerInfo");
        customer_Id=customerInfo.getCustomer_Id();
        postCarList(customer_Id);
        WindowManager wm = getActivity().getWindowManager();
        height = wm.getDefaultDisplay().getHeight();
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
        mCarListView=(MyListView) findViewById(R.id.lv_list_car);
        mAddCarRelativeLayout=(RelativeLayout) findViewById(R.id.rl_add_car_list);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.mine_carList);
        mAddCarRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentFragment(AddCarInformationFragment.class, "AddCarInformationFragment", null, ModuleMenuIDS.MODULE_HOME);
            }
        });
        /*for(int i=0;i<5;i++){
            list.add(""+i);
        }*/
        mCarListView.setMaxHeight(height / 3 * 2);
        mCarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                replaceFragment(MonthlyRentCarFinishPayFragment.class,"MonthlyRentCarFinishPayFragment",null);
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
        return true;
    }
    public void postCarList(String customerId){
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_CARLIST));
        apiTask.setParams(ApiContants.instance(getActivity()).userCarList(customerId));
        apiTask.setRoot("orderInfo");
        apiTask.execute(new OnDataLoader<List<CarList>>() {
            @Override
            public void onStart() {

            }
            @Override
            public void onSuccess(boolean page, List<CarList> carLists) {
                for (int i=0;i<carLists.size();i++){
                    CarList carList=carLists.get(i);
                    Log.e(TAG,carList+"");
                    mCarListView.setAdapter(new CarListAdapter(getActivity(), carLists));
                }
            }
            @Override
            public void onFailure(String code, String message) {

            }
        });
    }
}
