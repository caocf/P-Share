package com.azhuoinfo.pshare.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class UserCenterFragment extends BaseContentFragment {

    //用户的头像设置
    private ImageView mCustomerHeadImageView;
    //用户名
    private TextView mCustomerNicknameTextView;
    //用户Id
    private TextView mCustomerIdTextView;
    //用户积分
    private TextView mCustomerPointsTextView;
    //用户诚信分(余)
    private TextView mCustomerFaithPoints1TextView;
    //用户诚信分(满)
    private TextView mCustomerFaithPoints2TextView;
    //到个人中心详情
    private LinearLayout mEditorInformationLinearLayout;
    //生成的二维码图片
    private ImageView mQRCodeImageView;
    private AccountVerify mAccountVerify;
    private CustomerInfo customerInfo;
    private String customer_Id;
    private String customer_mobile;
    private String mQRCodeText;
    private String customer_nickname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerInfo = (CustomerInfo) this.app.getSession().get("customerInfo");
        customer_nickname = customerInfo.getCustomer_nickname();

        customer_Id=customerInfo.getCustomer_Id();
        customer_mobile=customerInfo.getCustomer_mobile();
        mQRCodeText="customer:"+customer_Id+" "+customer_mobile;

        Log.e(TAG, customerInfo.getCustomer_Id().toString());
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_centerr, container, false);
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
        mCustomerHeadImageView = (ImageView) view.findViewById(R.id.iv_customer_head);
        mCustomerNicknameTextView = (TextView) view.findViewById(R.id.tv_customer_nickname);
        mCustomerIdTextView = (TextView) view.findViewById(R.id.tv_customer_id);
        mCustomerPointsTextView = (TextView) view.findViewById(R.id.tv_customer_points);
        mCustomerFaithPoints1TextView = (TextView) view.findViewById(R.id.tv_customer_faith_points1);
        mCustomerFaithPoints2TextView = (TextView) view.findViewById(R.id.tv_customer_faith_points2);
        mEditorInformationLinearLayout = (LinearLayout) view.findViewById(R.id.ll_user_center);
        mQRCodeImageView = (ImageView) view.findViewById(R.id.iv_QR_code);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.user_center);
        mEditorInformationLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentFragment(UserCenterFinishFragment.class, "UserCenterFinishFragment", null, ModuleMenuIDS.MODULE_HOME);
            }
        });
        mCustomerIdTextView.setText(customerInfo.getCustomer_Id().toString());
        mCustomerNicknameTextView.setText(customer_nickname);
        mQRCodeImageView.setImageBitmap(create2DCoderBitmap(mQRCodeText,150,150));
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

    /**
     * 生成一个二维码图像
     *
     * @param url       传入的字符串，通常是一个URL
     * @param QR_WIDTH  宽度（像素值px）
     * @param QR_HEIGHT 高度（像素值px）
     * @return
     */
    public static final Bitmap create2DCoderBitmap(String url, int QR_WIDTH, int QR_HEIGHT) {
        try {
            // 判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            // 显示到一个ImageView上面
            // sweepIV.setImageBitmap(bitmap);
            return bitmap;
        } catch (WriterException e) {
            Log.i("log", "生成二维码错误" + e.getMessage());
            return null;
        }
    }
}