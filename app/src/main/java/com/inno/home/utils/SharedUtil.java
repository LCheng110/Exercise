package com.inno.home.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by lcheng on 2018/4/9.
 */

public class SharedUtil {
    public static void openShared(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");//设置分享内容的类型
        intent.putExtra(Intent.EXTRA_SUBJECT, "title");//添加分享内容标题
        intent.putExtra(Intent.EXTRA_TEXT, "content");//添加分享内容
        //创建分享的Dialog
        intent = Intent.createChooser(intent, "content");
        context.startActivity(intent);
    }
}
