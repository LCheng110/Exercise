package com.inno.home.dao;


import com.inno.home.model.BaseModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
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
    Observable<BaseModel> Obget(@Url String url, @HeaderMap Map<String, String> headMap,
                                @QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST()
    Observable<BaseModel> Obpost(@Url String url, @HeaderMap Map<String, String> headMap,
                                 @FieldMap Map<String, String> params);

    @PATCH()
    Observable<BaseModel> Obpatch(@Url String url, @HeaderMap Map<String, String> headMap,
                                  @QueryMap Map<String, String> params);

    @DELETE()
    Observable<BaseModel> Obdelete(@Url String url, @HeaderMap Map<String, String> headMap,
                                   @QueryMap Map<String, String> params);

    /**
     * 上传图片
     *
     * @param url
     * @param Bodyb
     * @return
     */
    @POST()
    Observable<BaseModel> uplodimag(@Url String url, @HeaderMap Map<String, String> headMap,
                                    @Body RequestBody Bodyb);


}
