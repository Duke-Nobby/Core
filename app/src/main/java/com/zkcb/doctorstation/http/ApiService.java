package com.zkcb.doctorstation.http;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {

    @GET("{url}")
    Observable<BaseResponse> executeGet(
            @Path("url") String url,
            @QueryMap Map<String, String> maps
    );


    @POST("{url}")
    @FormUrlEncoded
    Observable<BaseResponse> executePost(
            @Path("url") String url,//  @Header("") String authorization,
            @FieldMap Map<String, String> maps);

    @POST("{url}")
    @FormUrlEncoded
    Observable<ResponseBody> json(
            @Path("url") String url,
            @Body RequestBody jsonStr);

    @Multipart
    @POST("{url}")
    Observable<ResponseBody> upLoadFile(
            @Path("url") String url,
            @Part("image\"; filename=\"image.jpg") RequestBody requestBody);

    @POST("{url}")
    Call<ResponseBody> uploadFiles(
            @Path("url") String url,
            @Path("headers") Map<String, String> headers,
            @Part("filename") String description,
            @PartMap() Map<String, RequestBody> maps);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(
            @Url String fileUrl);


    /**
     * 护士登录接口
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Observable<BaseResponse> login(
            @FieldMap Map<String, String> params);

    /**
     * 获取病人列表
     *
     * @param params
     * @return
     */
    @GET("patient/patient-list.do")
    @Headers("Cache-Control: public,max-age=360")
    Observable<BaseResponse> getPatientList(
            @QueryMap Map<String, String> params);


}
