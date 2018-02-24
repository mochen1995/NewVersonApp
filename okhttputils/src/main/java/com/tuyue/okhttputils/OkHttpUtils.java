package com.tuyue.okhttputils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by dell on 2017/1/4.
 * 莫晨
 */

public class OkHttpUtils {

    public enum DataType{
        OBJECT,   //json对象格式
        ARRAY     //json数组格式
    }

    private static final MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/*");

    private static OkHttpClient mOkHttpClient;

    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static NetWorkUtil netWorkUtil;

    //初始化网络监测服务
    public static void initNetService(Context context)
    {
        netWorkUtil = new NetWorkUtil(context);
    }

    /**
     * 用于get请求json数据
     */
    public static void get(String url, final OnRequestCallBack callBack) {
        if (netWorkUtil!=null)
        {
            if (!netWorkUtil.isNetConnected()) {
                callBack.onNotNet();
                return;
            }
        }
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build();
        }
        final Request request = new Request.Builder().url(url)
                .build();
        responseData(callBack, request);
    }
    public static <T> void get(String url,DataType type,Class<T> clazz,final OnRequestDataCallBack<T> callBack) {
        if (netWorkUtil!=null)
        {
            if (!netWorkUtil.isNetConnected()) {
                callBack.onNotNet();
                return;
            }
        }
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build();
        }
        final Request request = new Request.Builder().url(url)
                .build();
        responseData(callBack, request,type,clazz);
    }
    /**
     * POST请求，json数据
     *
     * @param url      接口
     * @param callBack 回调接口
     * @param type json数据类型，ARRAY,OBJECT
     * @param clazz Bean对象的class
     * @param params   post请求的参数以 map 形式传入
     */
    public static<T> void post(String url, @NonNull Map<String, String> params,DataType type,Class<T> clazz, OnRequestDataCallBack<T> callBack) {
        if (netWorkUtil!=null)
        {
            if (!netWorkUtil.isNetConnected()) {
                callBack.onNotNet();
                return;
            }
        }
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build();
        }
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> next : entries) {
                String key = next.getKey();
                String value = next.getValue();
                builder.add(key, value);
            }
        }
        final Request request = new Request.Builder().url(url)
                .url(url)
                .post(builder.build())
                .build();
        responseData(callBack, request,type,clazz);
    }


    /**
     * POST请求，json数据
     *
     * @param url      接口
     * @param callBack 回调接口
     * @param params   post请求的参数以 map 形式传入
     */
    public static void post(String url, @NonNull Map<String, String> params, OnRequestCallBack callBack) {
        if (netWorkUtil!=null)
        {
            if (!netWorkUtil.isNetConnected()) {
                callBack.onNotNet();
                return;
            }
        }
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build();
        }
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> next : entries) {
                String key = next.getKey();
                String value = next.getValue();
                builder.add(key, value);
            }
        }
        final Request request = new Request.Builder().url(url)
                .url(url)
                .post(builder.build())
                .build();
        responseData(callBack, request);
    }
    public static void post(String url,RequestBody requestBody, OnRequestCallBack callBack)
    {
        if (netWorkUtil!=null)
        {
            if (!netWorkUtil.isNetConnected()) {
                callBack.onNotNet();
                return;
            }
        }
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build();
        }
        final Request request = new Request.Builder().url(url)
                .url(url)
                .post(requestBody)
                .header("Content-Type","application/json")
                .build();
        responseData(callBack, request);
    }
    /**
     * POST请求，json数据
     *
     * @param url      接口
     * @param callBack 回调接口
     * @param params   post请求的参数以 map 形式传入
     */
    public static <T> void post(String url, @NonNull Map<String, String> params, TypeToken<T> typetoken, onRequestCallBacks<T> callBack) {
        if (netWorkUtil != null) {
            if (!netWorkUtil.isNetConnected()) {
                callBack.onNotNet();
                return;
            }
        }
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build();
        }
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> next : entries) {
                String key = next.getKey();
                String value = next.getValue();
                builder.add(key, value);
            }
        }
        final Request request = new Request.Builder().url(url)
                .url(url)
                .post(builder.build())
                .build();
        responseData(callBack, request, typetoken);
    }

    /**
     *
     * @param url  地址
     * @param sendData   发送的对象
     * @param typetoken  返回的对象
     * @param callBack   回调
     */
    public static <S,T> void post(String url,S sendData , TypeToken<T> typetoken, onRequestCallBacks<T> callBack) {
        if (netWorkUtil != null) {
            if (!netWorkUtil.isNetConnected()) {
                callBack.onNotNet();
                return;
            }
        }
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build();
        }
        FormBody.Builder builder = new FormBody.Builder();
        if (sendData!=null)
        {
            Class<?> sendClass = sendData.getClass();
            Field[] fields = sendClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(sendData);
                    if (value!=null)
                    {
                       builder.add(field.getName(), String.valueOf(value));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        final Request request = new Request.Builder().url(url)
                .url(url)
                .post(builder.build())
                .build();
        responseData(callBack, request, typetoken);
    }

    public static <T> void get(String url,TypeToken<T> typetoken ,final onRequestCallBacks<T> callBack) {
        if (netWorkUtil != null) {
            if (!netWorkUtil.isNetConnected()) {
                callBack.onNotNet();
                return;
            }
        }
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build();
        }
        final Request request = new Request.Builder().url(url)
                .build();
        responseData(callBack, request,typetoken);
    }

    private static <T> void responseData(final onRequestCallBacks<T> callback, Request request, final TypeToken<T> typetoken) {
        mOkHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure(e.getMessage());
                            }
                        });
                    }
                    @Override
                    public void onResponse(final Call call, Response response) throws IOException {
                        final String json = response.body()
                                .string();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (json.endsWith("</html>")) {
                                    callback.onFailure("服务器繁忙");
                                } else {
                                    //对json进行转换
                                    BaseResponse<T> t = new Gson().fromJson(json, typetoken.getType());
                                    callback.onSucceed((T) t);
                                }
                            }
                        });
                    }
                });
    }


    /**
     * 以表单形式上传单个图片
     * 上传参数为 : image
     * @param imgParams 图片对应的键，为空时默认是 file
     * @param url
     * @param formParams url的参数，该参数表示图片的键
     * @param path       图片路径
     */
    public static void upLoadingImage(String url, @Nullable Map<String, String> formParams,@Nullable String imgParams, @Nullable String path, OnRequestCallBack callBack) {
        if (netWorkUtil!=null)
        {
            if (!netWorkUtil.isNetConnected()) {
                callBack.onNotNet();
                return;
            }
        }
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build();
        }
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (formParams!=null) {
            for (Map.Entry<String, String> param : formParams.entrySet()) {
                String key = param.getKey();
                String value = param.getValue();
                builder.addFormDataPart(key, value);
            }
        }
        if (path != null && !("".equals(path))) {
            File file = new File(path);
            builder.addFormDataPart(imgParams==null?"file":imgParams, file.getName(), RequestBody.create(MEDIA_TYPE_IMAGE, file));
        }
        final Request request = new Request.Builder().url(url)
                .url(url)
                .post(builder.build())
                .build();
        responseData(callBack, request);
    }

    //文件上传
    public static void upLoadingFile(String url,@Nullable Map<String, String> formParams,@Nullable String fileParams,@Nullable File file, OnRequestCallBack callBack) {
        if (netWorkUtil!=null)
        {
            if (!netWorkUtil.isNetConnected()) {
                callBack.onNotNet();
                return;
            }
        }
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build();
        }
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (formParams!=null) {
            for (Map.Entry<String, String> param : formParams.entrySet()) {  //添加表单参数
                String key = param.getKey();
                String value = param.getValue();
                builder.addFormDataPart(key, value);
            }
        }
        if (file!=null)
        {
            builder.addFormDataPart(fileParams==null?"file":fileParams, file.getName(), RequestBody.create(null, file)); //添加文件
        }
        RequestBody body = builder.build();
        final Request request = new Request.Builder().url(url)
                .url(url)
                .post(body)
                .build();
        responseData(callBack, request);
    }

    /**
     * 多个图片上传
     *
     * @param url
     * @param callBack
     * @param imgParams 图片对应的key，默认是files
     * @param formParams 表单集合
     * @param imagePaths 图片路径集合
     *                   注意：用map来存储所有的post的表单参数，如果是图片类型，key值请以files开头
     */
    public static void upLoadingImage(String url, @Nullable Map<String, String> formParams,@Nullable String imgParams, @Nullable List<String> imagePaths, OnRequestCallBack callBack) {
        if (netWorkUtil!=null)
        {
            if (!netWorkUtil.isNetConnected()) {
                callBack.onNotNet();
                return;
            }
        }
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build();
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (formParams!=null) {
            for (Map.Entry<String, String> param : formParams.entrySet()) {
                String key = param.getKey();
                String value = param.getValue();
                builder.addFormDataPart(key, value);
            }
        }
        if (imagePaths != null) {
            for (String path : imagePaths) {
                File file = new File(path);
                builder.addFormDataPart(imgParams==null?"files":imgParams, file.getName(), RequestBody.create(MEDIA_TYPE_IMAGE, file));
               /* builder.addFormDataPart(key, file.getName(), new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addPart(RequestBody.create(MEDIA_TYPE_IMAGE, file))
                        .build());*/
             /*   builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + key + "\"; filename=\"" + file.getName() + "\""),RequestBody.create(MEDIA_TYPE_IMAGE, file)); */
            }
        }

        final Request request = new Request.Builder().url(url)
                .url(url)
                .post(builder.build())
                .build();
        responseData(callBack, request);
    }

    private static <T> void  responseData(final OnRequestDataCallBack<T> callback, Request request, final DataType type, final Class<T> clazz) {
        mOkHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure(e.getMessage());
                            }
                        });
                    }
                    @Override
                    public void onResponse(final Call call, Response response) throws IOException {
                        final String json = response.body()
                                .string();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (json.endsWith("</html>")) {
                                    callback.onFailure("服务器繁忙");
                                } else {
                                    //对json进行转换
                                    if (type== DataType.OBJECT)  //json是对象格式
                                    {
                                        T t = new Gson().fromJson(json, clazz);
                                        callback.onSucceed(t);
                                    }else{    //json 是数组格式
                                        TypeToken<List<JsonObject>> typeToken = new TypeToken<List<JsonObject>>() {};

                                        List<JsonObject> jsonObjects = new Gson().fromJson(json,typeToken.getType());
                                        List<T> datas = new ArrayList<T>();
                                        for (JsonObject jsonObject : jsonObjects) {
                                            datas.add(new Gson().fromJson(jsonObject,clazz));
                                        }
                                        callback.onSucceed(datas);
                                    }
                                }
                            }
                        });
                    }
                });
    }

    private static void responseData(final OnRequestCallBack callback, Request request) {
        mOkHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure(e.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String json = response.body()
                                .string();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (json.endsWith("</html>")) {
                                    callback.onFailure("服务器繁忙");
                                } else {
                                    callback.onSucceed(json);
                                }
                            }
                        });
                    }
                });
    }


    //上传进度体
    class CustomRequestBody extends RequestBody {

        private MediaType mMediaType;
        private File mFile;
        private OnProgressListenter listenter;

        public CustomRequestBody(MediaType mediaType, File file, OnProgressListenter listenter) {
            this.mMediaType = mediaType;
            this.mFile = file;
            this.listenter = listenter;
        }

        @Override
        public MediaType contentType() {
            return mMediaType;
        }

        @Override
        public long contentLength() throws IOException {
            return mFile.length();
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            Source source;
            try {
                source = Okio.source(mFile);
                Buffer buffer = new Buffer();
                Long len = contentLength();
                long readCount = 0;
                while ((readCount = source.read(buffer, 2048)) != -1) {
                    sink.write(buffer, readCount);
                    if (!(len == 0)) {
                        len -= readCount;
                        listenter.onProgress(contentLength(), len, (int) ((len * 1.0F / contentLength()) * 100));
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //下载进度体
    static class CustomResponseBody extends ResponseBody {

        private ResponseBody responseBody;
        private BufferedSource bufferedSource;
        private OnProgressListenter listenter;

        public CustomResponseBody(ResponseBody responseBody, OnProgressListenter listenter) {
            this.responseBody = responseBody;
            this.listenter = listenter;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                ForwardingSource forwardingSource = new ForwardingSource(responseBody.source()) {
                    long totalByteRead = 0L;

                    @Override
                    public long read(Buffer sink, long byteCount) throws IOException {
                        long byteRead = super.read(sink, byteCount);
                        totalByteRead += (byteRead != -1 ? byteRead : 0);
                        if (!(byteRead == -1))
                            listenter.onProgress(contentLength(), totalByteRead, (int) ((totalByteRead * 1.0F / contentLength()) * 100));
                        return byteRead;
                    }
                };
                bufferedSource = Okio.buffer(forwardingSource);
            }
            return bufferedSource;
        }
    }

    /**
     * @param url         下载apk路径
     * @param filePath apk的存放路径
     * @param listenter
     */
    public static void download(String url, final String filePath, final OnProgressListenter listenter) {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build();
        }

        mOkHttpClient = mOkHttpClient.newBuilder()
                .addInterceptor(new Interceptor() {  //添加拦截器
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        return response.newBuilder()
                                .body(new CustomResponseBody(response.body(), listenter))
                                .build();
                    }
                })
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();
        mOkHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        listenter.onFailure(e.getMessage());
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final InputStream inputStream = response.body()
                                .byteStream();
                        if (filePath!=null)
                        {
                            FileOutputStream fos = new FileOutputStream(filePath);
                            int len = 0;
                            byte[] b = new byte[1024];
                            while ((len = inputStream.read(b)) != -1) {
                                fos.write(b, 0, len);
                            }
                            fos.flush();
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listenter.onFinished(filePath!=null?new File(filePath):null,inputStream);
                            }
                        });

                    }
                });
    }

    public interface OnProgressListenter {
        void onProgress(long totalBytes, long currentBytes, int percent);

        void onFinished(File file, InputStream inputStream);

        void onFailure(String error);
    }

   public interface OnRequestCallBack {
        void onSucceed(String json);
        void onFailure(String error);
        void onNotNet();
    }
    public interface OnRequestDataCallBack<T>{
        void onSucceed(T data);
        void onSucceed(List<T> datas);
        void onFailure(String error);
        void onNotNet();
    }
    public interface onRequestCallBacks<T>{
        void onSucceed(T data);
        void onFailure(String error);
        void onNotNet();
    }
}
