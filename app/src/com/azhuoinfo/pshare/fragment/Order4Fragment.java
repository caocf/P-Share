package com.azhuoinfo.pshare.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.view.touchgallery.GalleryViewPager;

import java.util.ArrayList;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class Order4Fragment extends BaseContentFragment implements ViewPager.OnPageChangeListener{

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
    //预付金额
    private TextView mAppointmentPayMoneyTextView;
    //预约时间
    private TextView mAppointmentTimeTextView;
    //预约取车时间
    private TextView mAppointmentMakeCarTimeTextView;
    //停车计时
    private TextView mCountdownTimeTextView;
    //用户车牌号
    private TextView mCarNumberTextView;
    private GalleryViewPager mGalleryViewPager;
    private LinearLayout mContainer;
    private GridView mGridViewPhoto;

    //完成订单支付
    private Button mFinishAppointment;
    //private RelativeLayout mToPayRelativeLayout;
    private AccountVerify mAccountVerify;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_order4,container,false);
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
       // initPagerView();
    }

    @Override
    protected void findViews(View view) {
        mParkerHeadImageView=(ImageView) view.findViewById(R.id.iv_parker_head);
        mParkerIDTextView=(TextView) view.findViewById(R.id.tv_parker_id);
        mParkerPositionTextView=(TextView) view.findViewById(R.id.tv_parker_position);
        mParkerAreaTextView=(TextView) view.findViewById(R.id.tv_parker_area);
        mParkerMobileTextView=(TextView) view.findViewById(R.id.tv_parker_mobile);
        
        mAppointmentTimeTextView=(TextView) view.findViewById(R.id.tv_appointment_time);
        mCarNumberTextView=(TextView) view.findViewById(R.id.tv_car_number);

       //mGalleryViewPager=(GalleryViewPager) view.findViewById(R.id.gallery_picture);
        mContainer=(LinearLayout) view.findViewById(R.id.container);
        mGridViewPhoto=(GridView) view.findViewById(R.id.gridview_photos);

        //mToPayRelativeLayout=(RelativeLayout) view.findViewById(R.id.rl_to_pay);/
    }

    @Override
    protected void initViews(Bundle bundle) {
        mFinishAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mToPayRelativeLayout.setVisibility(View.VISIBLE);
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

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    /* public void initPagerView(){
            //查找布局文件用LayoutInflater.inflate
            LayoutInflater inflater=getActivity().getLayoutInflater();
            mPagerView = new ArrayList<View>();
            //此处可以根据需要自由设定，这里只是简单的测试
            for (int i = 1; i <= 12; i++) {
                View view = inflater.inflate(R.layout.view_viewpager, null);
                mPagerView.add(view);
            }
            // 1.设置幕后item的缓存数目
            mViewPager.setOffscreenPageLimit(7);
            // 2.设置页与页之间的间距
            mViewPager.setPageMargin(10);
            // 3.将父类的touch事件分发至viewPgaer，否则只能滑动中间的一个view对象
            mContainer.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return mViewPager.dispatchTouchEvent(event);
                }
            });
                //数据适配器
            PagerAdapter mPagerAdapter = new PagerAdapter(){
                @Override
                //获取当前窗体界面数
                public int getCount() {
                    // TODO Auto-generated method stub
                    return mPagerView.size();
                }
                @Override
                //断是否由对象生成界面
                public boolean isViewFromObject(View arg0, Object arg1) {
                    // TODO Auto-generated method stub
                    return arg0==arg1;
                }
                //是从ViewGroup中移出当前View
                public void destroyItem(View arg0, int arg1, Object arg2) {
                    ((ViewPager) arg0).removeView(mPagerView.get(arg1));
                }
                //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
                public Object instantiateItem(View arg0, int arg1){
                    ((ViewPager)arg0).addView(mPagerView.get(arg1));
                    return mPagerView.get(arg1);
                }
            };
            //绑定适配器
            mViewPager.setAdapter(new MyAdapter(this.getActivity(),mPagerView));
            mViewPager.setOnPageChangeListener(this);// 设置监听器
        }

        @Override
        public void onPageScrolled(int i, float v, int i1) {
            if (mViewPager != null) {
                mViewPager.invalidate();
            }
        }
    */
    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    // PagerAdapter是object的子类
    class MyAdapter extends PagerAdapter {
        private Context context;
        private ArrayList<View> mViewPager;
        public MyAdapter(Context context,ArrayList<View> mViewPager){
            this.context=context;
            this.mViewPager=mViewPager;
        }
        /**
         * PagerAdapter管理数据大小
         */
        @Override
        public int getCount() {
            return mViewPager.size();
        }
        /**
         * 关联key 与 obj是否相等，即是否为同一个对象
         */
        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj; // key
        }
        /**
         * 销毁当前page的相隔2个及2个以上的item时调用
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object); // 将view 类型 的object熊容器中移除,根据key
        }
        /**
         * 当前的page的前一页和后一页也会被调用，如果还没有调用或者已经调用了destroyItem
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            System.out.println("pos:" + position);
            View view = mViewPager.get(position);
           /* // 如果访问网络下载图片，此处可以进行异步加载
            ImageView img = (ImageView) view.findViewById(R.id.icon);
            img.setImageBitmap(BitmapFactory.decodeFile(dir + getFile(position)));*/
            container.addView(view);
            return mViewPager.get(position); // 返回该view对象，作为key
        }
    }
}
