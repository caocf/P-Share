package com.azhuoinfo.pshare.fragment;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.fragment.adapter.MyPagerAdapter;

import java.util.ArrayList;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class MineOrderFragment extends BaseContentFragment{
    //返回上一个页面
    //private ImageView activity_back;

    private ViewPager mMineOrderViewPager;
    ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    //用于存储所有选择按钮的集合对象
    private ArrayList<RadioButton> mButtons = new ArrayList<RadioButton>();

    private ArrayList<LinearLayout> mLayouts=new ArrayList<LinearLayout>();

    //用于切换预/定和历史订单的radiogroup
    private RadioGroup mMineOrderRadioGroup;
    private LinearLayout mlinesLinearLayout;
    private FragmentManager mFragmentManager;
    private AccountVerify mAccountVerify;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_mine_order,container,false);
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
        //找控件
        //activity_back=(ImageView) findViewById(R.id.activity_back);
        mMineOrderRadioGroup=(RadioGroup) view.findViewById(R.id.rg_mine_order);
        mMineOrderViewPager=(ViewPager) view.findViewById(R.id.vp_mine_order);
        mlinesLinearLayout=(LinearLayout) view.findViewById(R.id.ll_mineorder_line);
        for (int i=0;i<2;i++){
            RadioButton mButton = (RadioButton) mMineOrderRadioGroup.getChildAt(i);
            mButton.setTag(i);
            mButtons.add(mButton);
        }
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.my_reserve);
        //获取所有的radiobutton

        //设置radiogroup的改变监听
        mMineOrderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                //循环遍历所有的radiobutton，看看哪一个处于选中状态
                for (int i=0;i<mButtons.size();i++) {
                    RadioButton mButton = mButtons.get(i);
                    //将选中状态的radiobutton上的文字改为白色，未选中的改为蓝色
                    if (mButton.getId() == checkedId) {
                        mButton.setTextColor(Color.rgb(70, 130, 180));
                        mlinesLinearLayout.getChildAt(i).setVisibility(View.VISIBLE);
                        //设置下方的viewpager显示相应的item
                        mMineOrderViewPager.setCurrentItem(Integer.parseInt(mButton.getTag().toString()));
                    } else {
                        mButton.setTextColor(Color.rgb(220, 220, 220));
                        mlinesLinearLayout.getChildAt(i).setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        //初始化list要显示的数据源
        fragments.add(new Order4Fragment());
        fragments.add(new HistoryOrderFragment());
        mMineOrderViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), fragments));
        //设置viewpager改变的监听事件
        mMineOrderViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
				/*
				 * 通过monthlyRent_group的check方法选中相应的radiobutton
				 * 只不过该方法的参数是要选中的raidobutton的id值，而不是index索引位置值
				 * 因此
				 * 需要通过monthlyRent_group的getChildAt方法先获得对应位置的raidobutton对象
				 * 然后在获取该对象的id值
				 * */
                mMineOrderRadioGroup.check(mMineOrderRadioGroup.getChildAt(arg0).getId());
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
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
}
