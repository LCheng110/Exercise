package com.inno.home.controller.window;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.base.BaseDialogFragment;
import com.inno.home.utils.ToastUtil;
import com.inno.home.utils.permission.PermissionManager;

import java.io.File;

import butterknife.BindView;

/**
 * Created by liucheng on 2017/12/19
 * <p>
 * 头像选择Pop
 */

public class AvatarSelectDialog extends BaseDialogFragment implements View.OnClickListener {

    public static final int REQ_TYPE_GALLERY = 100;                       //选择相册
    public static final int REQ_TYPE_CAMERA = 200;                        //选择相机

    @BindView(R.id.tv_camera)
    TextView tv_camera;
    @BindView(R.id.tv_album)
    TextView tv_album;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    private File mPickFile;

    @Override
    public int thisLayout() {
        return R.layout.dialog_avatar;
    }

    @Override
    public void doInitView() {
        tv_camera.setOnClickListener(this);
        tv_album.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void doInitData() {
        setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                this.dismiss();
                break;
            case R.id.tv_camera:
                PermissionManager.getInstance(context).requestPermission(context, "相机",
                        new PermissionManager.OnPermissionSuccessListener() {
                            @Override
                            public void onPermissionGrantedSuccess() {
                                jumpToCamera();
                            }
                        }, PermissionManager.CAMERA, PermissionManager.READ_EXTERNAL_STORAGE);
                break;
            case R.id.tv_album:
                PermissionManager.getInstance(context).requestPermission(context, "相机",
                        new PermissionManager.OnPermissionSuccessListener() {
                            @Override
                            public void onPermissionGrantedSuccess() {
                                jumpToAlbum();
                            }
                        },
                        PermissionManager.WRITE_EXTERNAL_STORAGE, PermissionManager.READ_EXTERNAL_STORAGE);
                break;
        }
    }

    public void setFile(File file) {
        mPickFile = file;
    }

    /**
     * 跳转相册
     */
    private void jumpToAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/jpeg");
        try {
            ((Activity) context).startActivityForResult(intent, REQ_TYPE_GALLERY);
        } catch (Exception e) {
            ToastUtil.showToast("请安装相册应用后重试");
            e.printStackTrace();
        }
        this.dismiss();
    }

    /**
     * 跳转相机
     */
    private void jumpToCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPickFile));
        try {
            ((Activity) context).startActivityForResult(intent, REQ_TYPE_CAMERA);
        } catch (Exception e) {
            ToastUtil.showToast("请安装相机应用后重试");
            e.printStackTrace();
        }
        this.dismiss();
    }
}
