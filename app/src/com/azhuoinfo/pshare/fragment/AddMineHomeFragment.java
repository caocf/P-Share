package com.azhuoinfo.pshare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.azhuoinfo.pshare.model.Parking;
import com.azhuoinfo.pshare.view.AreaDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 *地图房子Button我的家模块添加停车场
 */
public class AddMineHomeFragment extends BaseContentFragment {

    private TextView mPakingTextView;
    private TextView mAreaTextView;
    private Button mConfirmButton;
    private AccountVerify mAccountVerify;
    private String mParkingId=null;
    private String mArea;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_add_mine_home, container, false);
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
        mPakingTextView = (TextView) view.findViewById(R.id.tv_living_parking);
        mAreaTextView = (TextView) view.findViewById(R.id.tv_living_area);
        mConfirmButton = (Button) view.findViewById(R.id.button_confirm);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.tv_add_address);
        mAreaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AreaDialog dialog = AreaDialog.creatDialog(getActivity());
                dialog.setOnSelectListener(new AreaDialog.OnSelectListener() {
                    @Override
                    public void onSelect(String province, String city, String district, String zipcode) {
                        mAreaTextView.setText(province + "," + city + "," + district);
                        mArea = district;
                    }
                });
            }
        });
        mPakingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mArea!=null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("area", mArea);
                    replaceFragment(AddLivingAreaFragment.class, "AddLivingAreaFragment", bundle, 1);
                } else {
                    showToast("请先选择省市区");
                }
            }
        });
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mParkingId != null) {
                    saveParking(mAccountVerify.getCustomer_Id(), mParkingId);
                } else {
                    showToast("请选择停车场");
                }

            }
        });

    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Parking item=data.getParcelable("parking");
            mParkingId=item.getParking_id();
            mPakingTextView.setText(item.getParking_name());
        }
    }

    private void saveParking(String customer_id,String parking_id) {
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_SAVEPARKING));
        apiTask.setParams(ApiContants.instance(getActivity()).saveParking(customer_id, parking_id));
        apiTask.setRoot("parkingList");
        apiTask.execute(new OnDataLoader<List<Parking>>() {
            public void onStart() {
            }

            @Override
            public void onSuccess(List<Parking> list) {
                if (isEnable()) {
                    popBackStack();
                }
            }

            @Override
            public void onFailure(String code, String message) {
                if (isEnable())
                showToast(message);
            }
        });
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
