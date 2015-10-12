package com.azhuoinfo.pshare.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.ModuleMenuIDS;
import com.azhuoinfo.pshare.R;
import com.azhuoinfo.pshare.api.ApiContants;
import com.azhuoinfo.pshare.api.task.ApiTask;
import com.azhuoinfo.pshare.api.task.OnDataLoader;
import com.azhuoinfo.pshare.model.CustomerInfo;
import com.azhuoinfo.pshare.model.SetUserInfo;
import com.azhuoinfo.pshare.view.imageview.round.RoundedImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Handler;

import mobi.cangol.mobile.Session;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;

/**
 * Created by Azhuo on 2015/9/22.
 */
public class UserCenterEditorFragment extends BaseContentFragment {
    /*
     *actionbar
     * */
    //个人中心是否显示可编辑状态
    private int count = 0;
    private boolean isEditor = false;
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
    /*
    *编辑信息
    * */
    //设置用户昵称
    private EditText mCustomerNickNameEditText;
    //设置用户手机号
    private EditText mCustomerMobileEditText;
    //设置用户性别
    private RelativeLayout mCustomerSexRelativeLayout;
    //选择列表中的性别设置进去
    private TextView mCustomerSexTextView;
    private ImageView mCustomerSexImageView;
    //设置用户职业
    private EditText mCustomerJobEditText;
    //设置用户家乡
    private RelativeLayout mCustomerRegionRelativeLayout;
    private TextView mCustomerRegionTextView;
    private ImageView mCustomerRegionImageView;
    private RoundedImageView mRoundedImageView;
    //设置用户邮箱
    private EditText mCustomerEmailEditText;
    private String[] sex = new String[]{"男", "女"};
    private String[] homes = new String[]{"山东", "上海", "江苏"};
    private int isFinish = R.drawable.editor;

    private AccountVerify mAccountVerify;
    private CustomerInfo customerInfo;
    private SetUserInfo setUserInfo;
    private PopupWindow menuWindow;
    private String path;
    private final int IMAGE_CODE = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerInfo = (CustomerInfo) this.app.getSession().get("customerInfo");
        setUserInfo = (SetUserInfo) this.app.getSession().get("setUserInfo");
        //Log.e(TAG, customerInfo.getCustomer_Id().toString());
        mAccountVerify = AccountVerify.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_center_editor, container, false);
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
        mRoundedImageView = (RoundedImageView) view.findViewById(R.id.iv_customer_head);
        mCustomerNickNameEditText = (EditText) view.findViewById(R.id.et_customer_nickname);
        mCustomerIdTextView = (TextView) view.findViewById(R.id.tv_customer_id);
        mCustomerPointsTextView = (TextView) view.findViewById(R.id.tv_customer_points);
        mCustomerFaithPoints1TextView = (TextView) view.findViewById(R.id.tv_customer_faith_points1);
        mCustomerFaithPoints2TextView = (TextView) view.findViewById(R.id.tv_customer_faith_points2);
        //编辑信息
        mCustomerNickNameEditText = (EditText) view.findViewById(R.id.et_customer_nickname);
        mCustomerMobileEditText = (EditText) view.findViewById(R.id.et_customer_mobile);

        mCustomerSexRelativeLayout = (RelativeLayout) view.findViewById(R.id.rl_customer_sex);
        mCustomerSexTextView = (TextView) view.findViewById(R.id.tv_customer_sex);
        mCustomerSexImageView = (ImageView) view.findViewById(R.id.iv_customer_sex);

        mCustomerJobEditText = (EditText) view.findViewById(R.id.et_customer_job);
        mCustomerRegionRelativeLayout = (RelativeLayout) view.findViewById(R.id.rl_customer_region);
        mCustomerRegionTextView = (TextView) view.findViewById(R.id.tv_customer_region);
        mCustomerRegionImageView = (ImageView) view.findViewById(R.id.iv_customer_region);
        mCustomerEmailEditText = (EditText) view.findViewById(R.id.et_customer_email);
    }

    @Override
    protected void initViews(Bundle bundle) {
        this.setTitle(R.string.user_center);
        mRoundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* menuWindow = new PopupWindow (getActivity());
                menuWindow.showAtLocation(findViewById(R.id.po),
                        Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);*/
                showPopupWindow(v);
            }
        });
        mRoundedImageView.setImageResource(R.drawable.list_car);
        mCustomerSexRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSexListDialog();
            }
        });
        mCustomerRegionRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegionListDialog();
            }
        });
        mCustomerNickNameEditText.setText(customerInfo.getCustomer_nickname().toString());
        mCustomerIdTextView.setText(customerInfo.getCustomer_Id().toString());
        mCustomerMobileEditText.setText(customerInfo.getCustomer_mobile().toString());
        mCustomerMobileEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.change_phone, Toast.LENGTH_LONG).show();
            }
        });
        if (customerInfo.getCustomer_sex().equals("3")) {
            mCustomerSexTextView.setText("");
        } else if (customerInfo.getCustomer_sex().equals("1")) {
            mCustomerSexTextView.setText("先生");
        } else {
            mCustomerSexTextView.setText("女士");
        }
        mCustomerJobEditText.setText(customerInfo.getCustomer_job().toString());
        mCustomerRegionTextView.setText(customerInfo.getCustomer_region().toString());
        mCustomerEmailEditText.setText(customerInfo.getCustomer_email().toString());

    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected boolean onMenuActionCreated(final ActionMenu actionMenu) {
        super.onMenuActionCreated(actionMenu);
        actionMenu.addMenu(1, R.string.menu_edit, R.drawable.finish, 1);
        return true;
    }

    @Override
    public boolean onMenuActionSelected(final ActionMenuItem action) {
        int intSex = 3;
        switch (action.getId()) {
            case 1:
                if (mCustomerSexTextView.getText().toString().equals("")) {
                    intSex = 3;
                } else if (mCustomerSexTextView.getText().toString().equals("先生")) {
                    intSex = 1;
                } else if (mCustomerSexTextView.getText().toString().equals("女士")) {
                    intSex = 2;
                }
                postSetUserInfo(customerInfo.getCustomer_Id().toString(), mCustomerNickNameEditText.getText().toString(),
                        intSex + "", mCustomerJobEditText.getText().toString(),
                        mCustomerRegionTextView.getText().toString(),
                        mCustomerMobileEditText.getText().toString(), mCustomerEmailEditText.getText().toString(), 20 + "");
                replaceFragment(UserCenterFinishFragment.class, "UserCenterFinishFragment", null);
                break;
        }
        return super.onMenuActionSelected(action);
    }

    @Override
    protected FragmentInfo getNavigtionUpToFragment() {
        return null;
    }

    @Override
    public boolean isCleanStack() {
        return false;
    }

    private void showSexListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("请选择性别");
        builder.setItems(sex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCustomerSexTextView.setText(sex[which]);
            }
        });
        builder.show();
    }

    private void showRegionListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("请您的选择家乡");
        builder.setItems(homes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCustomerRegionTextView.setText(homes[which]);
            }
        });
        builder.show();
    }

    public void postSetUserInfo(String customerId, String customerNickmane,
                                String customerSex, String customerJob,
                                String customerRegion, String customerMobile, String customerEmail, String customerAge) {
        ApiTask apiTask = ApiTask.build(this.getActivity(), TAG);
        apiTask.setMethod("GET");
        apiTask.setUrl(ApiContants.instance(getActivity()).getActionUrl(ApiContants.API_CUSTOMER_SETUSERINFO));
        apiTask.setParams(ApiContants.instance(getActivity()).setUserInfo(customerId, customerNickmane,
                customerSex, customerJob, customerRegion, customerMobile, customerEmail, customerAge));
        //apiTask.setRoot("customerInfo");
        apiTask.execute(new OnDataLoader<CustomerInfo>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(boolean page, CustomerInfo customerInfo) {
                Session session = getSession();
                session.put("customerInfo", customerInfo);
                Log.e(TAG, "" + customerInfo);
            }

            @Override
            public void onFailure(String code, String message) {
                Log.e(TAG, "请求失败");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.popupwindow_userhead, null);
        // 设置按钮的点击事件
        Button mButtonPhoto = (Button) contentView.findViewById(R.id.button_photo);
        Button mButtonPhotograph = (Button) contentView.findViewById(R.id.button_photograph);
        mButtonPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);

                getAlbum.setType("image/*");
                startActivityForResult(getAlbum, 1);

            }
        });
        mButtonPhotograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //下面这句指定调用相机拍照后的照片存储的路径
                path = "/sdcard/Image/bitmap";
                takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), path)));
                startActivityForResult(takeIntent, 0X00);
            }
        });
        final PopupWindow popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.popupwindow_bg));
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Log.e(TAG, "ActivityResult resultCode error");
            return;
        }
        Bitmap bm = null;
        if (requestCode == RESULT_OK) {
            Uri uri = data.getData();
            //to do find the path of pic by uri

        } else if (requestCode == RESULT_OK) {
            Uri uri = data.getData();
            if (uri == null) {
                //use bundle to get data
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                    //spath :生成图片取个名字和路径包含类型
                    saveImage(photo,path);
                    //saveImage(Bitmap photo, String spath));
                } else {
                    Toast.makeText(getActivity(), "err****", Toast.LENGTH_LONG).show();
                    return;
                }
            } else {
                //to do find the path of pic by uri
            }
        }
    }
    public static void saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath,false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}