package com.inno.home.dao;

import android.accounts.NetworkErrorException;
import android.text.TextUtils;
import android.util.Log;

import com.inno.home.R;
import com.inno.home.config.ServiceCode;
import com.inno.home.config.ServiceInterface;
import com.inno.home.listen.net.NetRequestListener;
import com.inno.home.utils.AppUtil;
import com.inno.home.utils.NetWorkUtil;
import com.inno.home.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ServerUtil {

    public static final int REQUEST_TYPE_GET = 0;
    public static final int REQUEST_TYPE_POST = 1;
    public static final int REQUEST_TYPE_PATCH = 2;
    public static final int REQUEST_TYPE_DELETE = 3;
    public static boolean TOKEN_SEND_FLAG = false;

    /**
     * GET请求
     */
    public static void get(String url, Map<String, String> map, NetRequestListener requestListener) {
        request(REQUEST_TYPE_GET, url, map, requestListener);
    }

    /**
     * POST请求
     */
    public static void post(String url,
                            Map<String, String> map, NetRequestListener requestListener) {
        request(REQUEST_TYPE_POST, url, map, requestListener);
    }

    /**
     * POST请求
     * 请求参数中带有自己的头部
     */
    public static void post(String url, Map<String, String> headMap,
                            Map<String, String> map, NetRequestListener requestListener) {
        request(REQUEST_TYPE_POST, url, headMap, map, requestListener);
    }

    /**
     * POST请求
     * 上传文件
     */
    public static void uploadFile(String url,
                                  RequestBody Bodyb, NetRequestListener requestListener) {
        request(REQUEST_TYPE_POST, url, Bodyb, null, null, requestListener);
    }

    /**
     * POST请求
     * 上传文件
     */
    public static void uploadFile(String url,
                                  RequestBody body, Map<String, String> map, NetRequestListener requestListener) {
        request(REQUEST_TYPE_POST, url, body, map, null, requestListener);
    }

    /**
     * PATCH请求
     */
    public static void patch(String url,
                             Map<String, String> map, NetRequestListener requestListener) {
        request(REQUEST_TYPE_PATCH, url, map, requestListener);
    }

    /**
     * PATCH请求
     */
    public static void patch(String url,
                             RequestBody Body, NetRequestListener requestListener) {
        request(REQUEST_TYPE_PATCH, url, Body, null, null, requestListener);
    }

    /**
     * DELETE请求
     */
    public static void delete(String url,
                              Map<String, String> map, NetRequestListener requestListener) {
        request(REQUEST_TYPE_DELETE, url, map, requestListener);
    }

    public static void request(int requestType, String url,
                               Map<String, String> map,
                               NetRequestListener requestListener) {
        request(requestType, url, null, map, requestListener);
    }

    public static void request(int requestType, String url,
                               Map<String, String> headMap, Map<String, String> map,
                               NetRequestListener requestListener) {
        request(requestType, url, null, headMap, map, requestListener);
    }

    public static void request(int requestType, String url, RequestBody Body,
                               Map<String, String> headMap, Map<String, String> map,
                               final NetRequestListener requestListener) {

        if (!checkNetworkAvailable(requestListener)) {
            return;
        }
        Observer<ResponseBody> observer = new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(ResponseBody model) {
                try {
                    if (requestListener != null && model != null) {
                        requestListener.onSuccess(successHandle(model));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                errorHandle(e);
                if (requestListener != null) {
                    requestListener.onError(e);
                }
            }

            @Override
            public void onComplete() {
            }
        };
        Map<String, String> newMap = new HashMap<>();
        if (map != null) {
            newMap.putAll(map);
        }
        Map<String, String> newHeadMap = new HashMap<>();
        addPublicHead(newHeadMap);
        if (headMap != null) {
            newHeadMap.putAll(headMap);
        }
        if (url.endsWith(ServiceInterface.FILE_UPLOAD)) {
            newHeadMap.remove("Content-Type");
        }
        switch (requestType) {
            case REQUEST_TYPE_GET:
                ServerManager.getApi().Obget(url, newHeadMap, newMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer);
                break;
            case REQUEST_TYPE_POST:
                if (null != Body) {
                    ServerManager.getApi().uplodBody(url, newHeadMap, Body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(observer);
                } else {
                    ServerManager.getApi().Obpost(url, newHeadMap, newMap)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(observer);
                }
                break;
            case REQUEST_TYPE_PATCH:
                if (null != Body) {
                    ServerManager.getApi().patchBody(url, newHeadMap, Body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(observer);
                } else {
                    ServerManager.getApi().Obpatch(url, newHeadMap, newMap)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(observer);
                }
                break;
            case REQUEST_TYPE_DELETE:
                ServerManager.getApi().Obdelete(url, newHeadMap, newMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer);
                break;
        }
    }

    private static boolean checkNetworkAvailable(NetRequestListener requestListener) {
        if (!NetWorkUtil.isNetworkAvailable(AppUtil.getContext())) {
            ToastUtil.showToast(R.string.text_net_no);
            if (requestListener != null) {
                requestListener.onError(new NetworkErrorException());
            }
            return false;
        }

        return true;
    }

    private static String successHandle(ResponseBody baseModel) throws IOException {
        String responseJson = baseModel.string().trim();
        Log.i("Response", "onNext: " + responseJson);
        return responseJson;
    }

    private static void errorHandle(Throwable e) {
        HttpException exception;
        if (e instanceof HttpException) {
            exception = (HttpException) e;
            Log.i("response", "errorHandle: " + exception.getMessage());
            ToastUtil.showToast(exception.getMessage());
            if (exception.code() == ServiceCode.TOKEN_FAILED) {
//                Session.setUserId(0);
//                ToastUtil.showToast(R.string.login_token_filed);
//                ActivityPageManager manager = ActivityPageManager.getInstance();
//                manager.finishAllActivityExceptOne(MainActivity.class);
//                if (manager.getActivity(MainActivity.class) != null) {
//                    ((MainActivity) manager.getActivity(MainActivity.class)).switchFragment(0);
//                }
//                Navigation.showLogin(ActivityPageManager.getInstance().currentActivity(),
//                        LoginActivity.FROM_SETTING);
            }
        }
    }

    public static void addPublicHead(Map<String, String> newHeadMap) {
        newHeadMap.put("Content-Type", "application/json"); // ;charset=utf-8
        newHeadMap.put("charset", "utf-8");
        newHeadMap.put("version", AppUtil.getAppVersion(AppUtil.getContext()));
        if (!TextUtils.isEmpty(Session.getAccessToken())) {
            newHeadMap.put("authorization", "Bearer " + Session.getAccessToken());
        }
    }
}
