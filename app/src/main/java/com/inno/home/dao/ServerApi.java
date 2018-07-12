package com.inno.home.dao;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by qhm on 2017/9/22
 * <p>
 * 新闻接口 API
 */
public interface ServerApi {

    int DEFAULT_PAGE_SIZE = 7;
    int DEFAULT_PAGE_INDEX = 1;

    String PAGE_INDEX_KEY = "pageNum";
    String PAGE_SIZE_KEY = "pageSize";


    @GET()
    Observable<ResponseBody> Obget(@Url String url, @HeaderMap Map<String, String> headMap,
                                   @QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> Obpost(@Url String url, @HeaderMap Map<String, String> headMap,
                                 @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> ObpostJson(@Url String url, @HeaderMap Map<String, String> headMap,
                                 @Body Map<String, String> params);

    @PATCH()
    Observable<ResponseBody> Obpatch(@Url String url, @HeaderMap Map<String, String> headMap,
                                  @QueryMap Map<String, String> params);

    @DELETE()
    Observable<ResponseBody> Obdelete(@Url String url, @HeaderMap Map<String, String> headMap,
                                   @QueryMap Map<String, String> params);

    @POST()
    Observable<ResponseBody> uplodBody(@Url String url, @HeaderMap Map<String, String> headMap,
                                       @Body RequestBody Bodyb);

    @PATCH()
    Observable<ResponseBody> patchBody(@Url String url, @HeaderMap Map<String, String> headMap,
                                       @Body RequestBody Bodyb);

    /**
     * 上传图片
     */
    @POST()
    Observable<ResponseBody> uplodImage(@Url String url, @HeaderMap Map<String, String> headMap,
                                        @Part MultipartBody.Part file);


}
