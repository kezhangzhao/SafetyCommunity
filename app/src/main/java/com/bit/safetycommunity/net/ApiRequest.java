package com.bit.safetycommunity.net;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.kzz.safetycommunity.BuildConfig;
import com.yanzhenjie.kalle.JsonBody;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.RequestBody;
import com.yanzhenjie.kalle.simple.Callback;
import com.yanzhenjie.kalle.simple.Converter;
import com.yanzhenjie.kalle.simple.SimpleUrlRequest;
import com.yanzhenjie.kalle.simple.cache.CacheMode;

import java.util.HashMap;

/**
 * 网络请求管理,请求方法可以重载，需要的时候添加，也可以用原生的。
 */
public class ApiRequest {


    private static ApiRequest instance;

    private ApiRequest() {
    }

    public static ApiRequest getInstance() {
        if (instance == null) {
            synchronized (ApiRequest.class) {
                if (instance == null) {
                    instance = new ApiRequest();
                }
            }
        }
        return instance;
    }

    /**
     * 获取完整的URL
     *
     * @param url 不同接口的url
     * @return 环境URL + 不同接口的url
     */
    private String getCompleteUrl(String url) {
        return BuildConfig.BASEAPI_SERVER_URL + url;
    }

    public <S> void get(String url, DefineCallback<S> callback) {
        try {
            Kalle.get(getCompleteUrl(url)).perform(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <S> void get(String url, DefineCallback<S> callback, String... path) {
        try {
            SimpleUrlRequest.Api api = Kalle.get(getCompleteUrl(url));
            for (String urlPath : path) {
                api.path(urlPath);
            }
            api.perform(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * GET请求
     *
     * @param url      地址
     * @param param    参数, 集合类型参数自己加
     * @param callback 回调
     * @param <S>      类型
     */
    public <S> void get(String url, HashMap<String, Object> param, DefineCallback<S> callback) {
        try {
            SimpleUrlRequest.Api api = Kalle.get(getCompleteUrl(url));
            addParams(api, param);
            api.perform(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * GET请求
     *
     * @param url       地址
     * @param param     参数
     * @param cacheMode 缓存模式
     * @param callback  回调
     * @param <S>       类型
     */
    public <S> void get(String url, HashMap<String, Object> param, CacheMode cacheMode, DefineCallback<S> callback) {
        try {
            SimpleUrlRequest.Api api = Kalle.get(getCompleteUrl(url));
            addParams(api, param);
            api.cacheKey(getCompleteUrl(url)).cacheMode(cacheMode).perform(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <S> void get(String url, CacheMode cacheMode, DefineCallback<S> callback) {
        try {
            Kalle.get(getCompleteUrl(url))
                    .cacheKey(getCompleteUrl(url))
                    .cacheMode(cacheMode)
                    .perform(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * GET请求
     *
     * @param url       地址
     * @param param     参数
     * @param cacheMode 缓存模式
     * @param converter 转换器
     * @param callback  回调
     * @param <S>       类型
     */
    public <S> void get(String url, HashMap<String, Object> param, CacheMode cacheMode, Converter converter, DefineCallback<S> callback) {
        try {
            SimpleUrlRequest.Api api = Kalle.get(getCompleteUrl(url));
            addParams(api, param);
            api.cacheMode(cacheMode).converter(converter).perform(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * GET请求
     *
     * @param url       地址
     * @param param     参数
     * @param cacheKey  缓存key
     * @param cacheMode 缓存模式
     * @param converter 转换器
     * @param callback  回调
     * @param <S>       类型
     */
    public <S> void get(String url, HashMap<String, Object> param, String cacheKey, CacheMode cacheMode, Converter converter, DefineCallback<S> callback) {
        try {
            SimpleUrlRequest.Api api = Kalle.get(getCompleteUrl(url));
            addParams(api, param);
            api.cacheKey(cacheKey).cacheMode(cacheMode).converter(converter).perform(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * POST请求
     *
     * @param url      地址
     * @param callback 回调
     * @param <S>      数据类型
     */
    public <S, F> void post(String url, Callback<S, F> callback) {
        try {
            String json = "{}";
            RequestBody requestBody = new JsonBody(json);
            Kalle.post(getCompleteUrl(url)).body(requestBody).perform(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * POST请求
     *
     * @param url      地址
     * @param object   参数
     * @param callback 回调
     * @param <S>      目标数据类型
     */
    public <S, F> void post(String url, Object object, Callback<S, F> callback) {
        try {
            String json = "{}";
            if (object != null) {
                json = JSON.toJSONString(object);
            }
            RequestBody requestBody = new JsonBody(json);
            Kalle.post(getCompleteUrl(url)).body(requestBody).perform(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * POST请求
     *
     * @param url      地址
     * @param json     参数,json格式
     * @param callback 回调
     * @param <S>      目标数据类型
     */
    public <S> void post(String url, String json, DefineCallback<S> callback) {
        try {
            RequestBody requestBody = new JsonBody(json);
            if (TextUtils.isEmpty(json)) {
                requestBody = new JsonBody("{}");
            }
            Kalle.post(getCompleteUrl(url)).body(requestBody).perform(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * POST请求
     *
     * @param url       地址
     * @param object    参数
     * @param cacheKey  缓存key
     * @param cacheMode 缓存模式
     * @param callback  回调
     * @param <S>       同上
     */
    public <S> void post(String url, Object object, String cacheKey, CacheMode cacheMode, DefineCallback<S> callback) {
        try {
            String json = "{}";
            if (object != null) {
                json = JSON.toJSONString(object);
            }
            RequestBody requestBody = new JsonBody(json);
            Kalle.post(getCompleteUrl(url))
                    .cacheKey(cacheKey)
                    .cacheMode(cacheMode)
                    .body(requestBody)
                    .perform(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * POST请求
     *
     * @param url       地址
     * @param object    参数
     * @param cacheKey  缓存key
     * @param cacheMode 缓存模式
     * @param converter 转换器，可自己添加
     * @param callback  回调
     * @param <S>       同上
     */
    public <S> void post(String url, Object object, String cacheKey, CacheMode cacheMode, Converter converter, DefineCallback<S> callback) {
        try {
            String json = "{}";
            if (object != null) {
                json = JSON.toJSONString(object);
            }
            RequestBody requestBody = new JsonBody(json);
            Kalle.post(getCompleteUrl(url))
                    .cacheKey(cacheKey)
                    .cacheMode(cacheMode)
                    .converter(converter)
                    .body(requestBody)
                    .perform(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * GET 请求：添加请求参数
     *
     * @param api 请求对象
     * @param map 请求参数
     */
    private void addParams(SimpleUrlRequest.Api api, HashMap<String, Object> map) {
        HashMap<String, Object> requestParam = removeEmpty(map);
        for (String key : requestParam.keySet()) {
            Object value = requestParam.get(key);
            if (value != null)
                api.param(key, value.toString());
        }
    }

    /**
     * 去除空参
     *
     * @param map 参数
     * @return 去除空参后的所有参数
     */
    private HashMap<String, Object> removeEmpty(HashMap<String, Object> map) {
        HashMap<String, Object> resultParams = new HashMap<>();
        if (map != null) {
            for (String key : map.keySet()) {//去空操作
                Object value = map.get(key);
                if (value != null && !TextUtils.isEmpty(value.toString())) {
                    resultParams.put(key, value.toString());
                }
            }
        }
        return resultParams;
    }
}
