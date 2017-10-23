package com.yyp.newsclient.base;

import com.google.gson.GsonBuilder;
import com.yyp.newsclient.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AppClient {

    private static Retrofit mRetrofit;

    /**
     * 获取 API 接口的实现类的实例对象
     * @return
     */
    public static ApiService getApiService() {
        return retrofit().create(ApiService.class);
    }

    /**
     * 配置、创建 retrofit
     * @return retrofit instance
     */
    public static Retrofit retrofit() {
        if (mRetrofit == null) {

            // 配置 client
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
            }
            builder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request.Builder builder = chain.request().newBuilder();
                    builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.108 Safari/537.36 2345Explorer/8.0.0.13547");
                    builder.addHeader("Cache-Control", "max-age=0");
                    builder.addHeader("Upgrade-Insecure-Requests", "1");
                    builder.addHeader("X-Requested-With", "XMLHttpRequest");
                    builder.addHeader("Cookie", "uuid=\"w:f2e0e469165542f8a3960f67cb354026\"; __tasessionId=4p6q77g6q1479458262778; csrftoken=7de2dd812d513441f85cf8272f015ce5; tt_webid=36385357187");
                    return chain.proceed(builder.build());
                }
            });
            OkHttpClient okHttpClient = builder.build();

            // 创建 retrofit
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(ApiService.API_SERVER_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())) //使用工厂模式创建Gson的解析器
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 返回Observable<T>
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }
}
