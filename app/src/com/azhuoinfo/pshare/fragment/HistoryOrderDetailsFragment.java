package com.azhuoinfo.pshare.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.fragment.adapter.ImageAdapter;
import com.azhuoinfo.pshare.model.OrderList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class HistoryOrderDetailsFragment extends BaseContentFragment{

    //代泊员头像
    private ImageView mParkerHeadImageView;
    //代泊员ID
    private TextView mParkerIDTextView;
    //代泊员职务
    private TextView mParkerPositionTextView;
    //代泊员负责区域
    private TextView mParkerAreaTextView;
    //代泊员联系方式
    private TextView mParkerMobileTextView;
    //订单日期
    private TextView mOrderDateTextView;
    //停车时长
    private TextView mStopCarTimeTextView;
    //支付金额
    private TextView mPayMoneyTextView;
    //用户车牌号
    private TextView mCarNumberTextView;
    private GridView mImageGridView;
    private ImageView mLeftImageView;
    private ImageView mRightImageView;
    private int left=0,right=2;
    private ImageAdapter mImageAdapter;
    //GridView的数据的集合
    private List<Map<String, Object>> mList;
    //所有图片路径的集合
    private List<String> urls;
    //仅包括三个图片路径的集合
    private List<String> show;
    //当前相机返回的照片的存储路径
    private String path;

    private AccountVerify mAccountVerify;
    private OrderList orderList;
    private String parakerId;
    private String parkerLevel;
    private String parkerMobile;
    private String orderDate;
    private String parkerName;
    private String parkingName;
    private String orderPlanEnd;
    private String carNumber;
    private String orderDuration;
    private String orderTotalFee;
    private String order_actual_begin_start;
    private String order_actual_end_stop;
    private String orderState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
        orderList=(OrderList)this.getArguments().get("orderList");
        parakerId=orderList.getParker_id();
        parkerLevel=orderList.getParker_level();
        parkerMobile=orderList.getParker_mobile();
        orderDate=orderList.getUpdated_at();
        orderDuration=orderList.getOrder_duration();
        orderTotalFee=orderList.getOrder_total_fee();
        parkerName=orderList.getParker_name();
        parkingName=orderList.getParking_name();
        carNumber=orderList.getCar_number();
        order_actual_begin_start=orderList.getOrder_actual_begin_start();
        order_actual_end_stop=orderList.getOrder_actual_end_stop();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_history_order_details,container,false);
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
        mImageGridView=(GridView)findViewById(R.id.gridview_photos);
        mLeftImageView=(ImageView) findViewById(R.id.iv_left);
        mRightImageView=(ImageView) findViewById(R.id.iv_right);
        mParkerHeadImageView=(ImageView) view.findViewById(R.id.iv_parker_head);
        mParkerIDTextView=(TextView) view.findViewById(R.id.tv_parker_id);
        mParkerPositionTextView=(TextView) view.findViewById(R.id.tv_parker_position);
        mParkerAreaTextView=(TextView) view.findViewById(R.id.tv_parker_area);
        mParkerMobileTextView=(TextView) view.findViewById(R.id.tv_parker_mobile);

        mOrderDateTextView=(TextView) view.findViewById(R.id.tv_history_order_time);
        mStopCarTimeTextView=(TextView) view.findViewById(R.id.tv_stop_car_time);
        mPayMoneyTextView=(TextView)view.findViewById(R.id.tv_pay_money);
        mCarNumberTextView=(TextView) view.findViewById(R.id.tv_car_number);
    }

    @Override
    protected void initViews(Bundle bundle) {

    }

    @Override
    protected void initData(Bundle bundle) {
        //friendly_time(order_actual_begin_start);
        /*Log.e("order_actual_begin_start：",order_actual_begin_start);
        Log.e("order_actual_end_stop：",order_actual_end_stop);*/
        dateDiff(order_actual_begin_start, order_actual_end_stop, "yyyy-MM-dd HH:mm:ss");
        mParkerIDTextView.setText(parakerId);
        mParkerAreaTextView.setText(parkingName);
        mParkerMobileTextView.setText(parkerMobile);
        mParkerPositionTextView.setText(parkerLevel);
        mPayMoneyTextView.setText(orderTotalFee);
        mOrderDateTextView.setText(orderDate);
        mCarNumberTextView.setText(carNumber);

        mList = new ArrayList<Map<String, Object>>();
        urls = new ArrayList<String>();
        show = new ArrayList<String>();
        saveList();
        showImage(show);
        /**
         * 图册左移监听
         */
        mLeftImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLeft();
            }
        });
        /**
         * 图册右移监听
         */
        mRightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRight();
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
    /**
     * 对图片显示的数据初始化
     */
    private void saveList() {
        String order_path = orderList.getOrder_path();
        String parking_path=orderList.getParking_path();

        String[] orderPaths = order_path.split(",");
        Log.e("orderPaths",orderPaths.length+"");
        for (int i = 0; i < orderPaths.length ; i++) {
                urls.add(orderPaths[i]);
            Log.e("path", orderPaths[orderPaths.length-1]);
        }
        if(parking_path!=null){
            if(parking_path.contains(",")){
                parking_path=parking_path.replaceAll(",","");
                urls.add(parking_path);
            }
        }

        for (int j = 0; j < 3; j++) {
            show.add(urls.get(j));
        }
        Log.d("saveList",urls.size()+"");
    }

    /**
     * 对图片的显示
     *
     * @param urls
     */
    private void showImage(List<String> urls) {
       /* mShow.setVisibility(View.VISIBLE);
        mGrid.setVisibility(View.GONE);*/
        ImageAdapter adapter = new ImageAdapter(getActivity(), urls);
        mImageGridView.setAdapter(adapter);
        Log.d("showImage",urls.toString());
    }

    private void toLeft(){
        if (left == 0) {
            Toast.makeText(getActivity(),"当前位于最左方!",Toast.LENGTH_SHORT).show();
        } else {
            show.set(0, urls.get(left - 1));
            show.set(1, urls.get(left));
            show.set(2, urls.get(left + 1));
            left--;
            right--;
            showImage(show);
        }
    }

    private void toRight() {
        if (right == urls.size() - 1) {
            Toast.makeText(getActivity(),"当前位于最右方!",Toast.LENGTH_SHORT).show();
            //showToast("当前位于最右方!");
        } else {
            show.set(0, urls.get(right - 1));
            show.set(1, urls.get(right));
            show.set(2, urls.get(right + 1));
            left++;
            right++;
            showImage(show);
        }
    }
    public void dateDiff(String startTime, String endTime, String format) {
       //按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long nm = 1000 * 60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数
        long diff;
        try {
           //获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            long day = diff / nd;//计算差多少天
            long hour = diff % nd / nh;//计算差多少小时
            long min = diff % nd % nh / nm;//计算差多少分钟
            long sec = diff % nd % nh % nm / ns;//计算差多少秒
            String str=day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
            mStopCarTimeTextView.setText(str);
            Log.e(TAG,"时间相差：" + day + "天" + hour + "小时" + min + "分钟" + sec + "秒");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
