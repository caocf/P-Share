package com.azhuoinfo.pshare.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.azhuoinfo.pshare.utils.GalleryUtils;
import com.azhuoinfo.pshare.view.CommonDialog;
import com.azhuoinfo.pshare.view.imageview.round.RoundedImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Handler;

import mobi.cangol.mobile.Session;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.base.BaseContentFragment;
import mobi.cangol.mobile.base.FragmentInfo;
import mobi.cangol.mobile.logging.Log;

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

    //对相册回调的处理
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private static final String TEMP_IMAGE_CAMERA = "temp_camera.jpg";
    private static final String TEMP_IMAGE_CROP = "temp_crop.jpg";
    private static final int CROP_AVATAR_HEIGHT = 240;
    private static final int CROP_AVATAR_WIDTH = 240;
    private static final int IMAGE_FROM_CAMERA = 0x0a1;
    private static final int IMAGE_FROM_PHOTOS = 0xfe2;
    private static final int IMAGE_CROP_RESULT = 0xaf3;


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
                showSelectFromPicDialog();
            }
        });
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

    private void showSelectFromPicDialog() {
        String[] from = this.getResources().getStringArray(R.array.user_face_from);
        final CommonDialog dialog = CommonDialog.creatDialog(this.getActivity());
        dialog.setTitle("修改头像");
        dialog.setListViewInfo(new ArrayAdapter<String>(app,
                        R.layout.common_dialog_textview, from),
                new CommonDialog.OnDialogItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        switch (position) {
                            case 0:
                                // 从相相册获取
                                Intent intent = new Intent(Intent.ACTION_PICK, null);
                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
                                startActivityForResult(intent, IMAGE_FROM_PHOTOS);
                                break;
                            case 1:
                                String status = Environment.getExternalStorageState();
                                if (status.equals(Environment.MEDIA_MOUNTED)) {
                                    // 从相机获取
                                    try {
                                        Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        in.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(GalleryUtils.getTempFile(getActivity(), TEMP_IMAGE_CAMERA)));
                                        startActivityForResult(in, IMAGE_FROM_CAMERA);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    // 没有SD卡;
                                    showToast(R.string.common_storage_null);
                                }
                                break;
                        }
                        dialog.dismiss();
                    }
                });
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Log.e(TAG, "ActivityResult resultCode error");
            return;
        } else {
            if (requestCode == IMAGE_FROM_CAMERA) {
                if (resultCode == Activity.RESULT_OK) {
                    //对相册返回的照片进行裁剪并存储
                    //参数对应 上下文对象,返回码,数据地址uri,相片高度，宽度，File
                    GalleryUtils.startSystemPhotoCrop(this,
                            IMAGE_CROP_RESULT,
                            Uri.fromFile(GalleryUtils.getTempFile(this.getActivity(), TEMP_IMAGE_CAMERA)),
                            CROP_AVATAR_HEIGHT,
                            CROP_AVATAR_WIDTH,
                            GalleryUtils.getTempFile(this.getActivity(), TEMP_IMAGE_CROP));
                }
            } else if (requestCode == IMAGE_FROM_PHOTOS) {
                if (resultCode == Activity.RESULT_OK) {
                    //对相机返回的照片进行裁剪并存储
                    //参数对应 上下文对象,返回码,数据地址uri,相片高度，宽度，File
                    GalleryUtils.startSystemPhotoCrop(this,
                            IMAGE_CROP_RESULT,
                            data.getData(),
                            CROP_AVATAR_HEIGHT,
                            CROP_AVATAR_WIDTH,
                            GalleryUtils.getTempFile(this.getActivity(), TEMP_IMAGE_CROP));

                }
            } else if (requestCode == IMAGE_CROP_RESULT) {
                if (resultCode == Activity.RESULT_OK) {
                    //裁剪后返回的数据
                    Log.d("changeHeadImg image=" + GalleryUtils.getTempFile(this.getActivity(), TEMP_IMAGE_CROP).getAbsolutePath());
                    Bitmap bitmap = BitmapFactory.decodeFile(GalleryUtils.getTempFile(this.getActivity(), TEMP_IMAGE_CROP).getAbsolutePath());
                    mRoundedImageView.setImageBitmap(bitmap);
                }

            }
        }
    }
}