package com.inno.home.controller.person;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.inno.home.R;
import com.inno.home.adapter.PicAdapter;
import com.inno.home.base.BaseActivity;
import com.inno.home.listen.click.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ContactUsActivity extends BaseActivity {
    public static final int REQUEST_ALBUM = 2;

    private static final int MAX_LENGTH_TOPIC = 50;
    private static final int MAX_LENGTH_DETAIL = 100;
    private static final int MAX_LENGTH_PIC = 3;

    @BindView(R.id.et_topic)
    EditText et_topic;
    @BindView(R.id.et_details)
    EditText et_details;
    @BindView(R.id.topic_num)
    TextView topic_num;
    @BindView(R.id.detail_num)
    TextView detail_num;
    @BindView(R.id.pic_num)
    TextView pic_num;
    @BindView(R.id.rv_pic_list)
    RecyclerView rv_pic_list;

    PicAdapter picAdapter;

    private List<Uri> uriList = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_contact_us;
    }

    @Override
    protected void initValue() {

    }

    @Override
    protected void initView() {
        topic_num.setText(String.format(getString(R.string.format_num), 0, MAX_LENGTH_TOPIC));
        detail_num.setText(String.format(getString(R.string.format_num), 0, MAX_LENGTH_DETAIL));
        pic_num.setText(String.format(getString(R.string.format_num), 0, MAX_LENGTH_PIC));
        et_topic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                topic_num.setText(String.format(getString(R.string.format_num), s.length(), MAX_LENGTH_TOPIC));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_details.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                detail_num.setText(String.format(getString(R.string.format_num), s.length(), MAX_LENGTH_DETAIL));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rv_pic_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void initData() {
        if (titleBar != null) {
            titleBar.setTitleText(R.string.main_text_contact);
        }
        uriList.add(null);
        rv_pic_list.setAdapter(picAdapter = new PicAdapter(uriList));
        picAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == uriList.size() - 1) {
                    selectAlbum();
                } else {
                    uriList.remove(position);
                    refreshPicList();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK != resultCode) {
            return;
        }
        switch (requestCode) {
//            case REQUEST_CAMERA:
//                cropImage(Uri.fromFile(mImageFile));
//                break;
            case REQUEST_ALBUM:
                Uri uri = data.getData();
                if (uri != null) {
                    uriList.add(uriList.size() - 1, uri);
                    refreshPicList();
                }
                break;
        }
    }

    @OnClick(R.id.btn_submit)
    public void onClick(View view) {
        for (int i = 0; i < uriList.size() - 1; i++) {
            Log.i("sss", "onClick: " + parseFilePath(uriList.get(i)));
        }
    }

    private void refreshPicList() {
        pic_num.setText(String.format(getString(R.string.format_num), uriList.size() - 1, MAX_LENGTH_PIC));
        picAdapter.notifyDataSetChanged();
    }

    private void selectAlbum() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(albumIntent, REQUEST_ALBUM);
    }

    private String parseFilePath(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }
}
