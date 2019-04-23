package com.bit.safetycommunity.net;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.yanzhenjie.kalle.Response;
import com.yanzhenjie.kalle.simple.Converter;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class JsonConverter implements Converter {

    private Context mContext;

    public JsonConverter(Context context) {
        this.mContext = context;
    }

    @Override
    public <S, F> SimpleResponse<S, F> convert(Type succeed, Type failed, Response response, boolean fromCache) throws Exception {
        S succeedData = null; // The data when the business successful.
        F failedData = null; // The data when the business failed.

        int code = response.code();
        String serverJson = response.body().string();
        LogUtils.e("Server Data: " + serverJson);
        HttpEntity httpEntity;
        if (code >= 200 && code < 300) { // Http is successful.
            try {
                httpEntity = JSON.parseObject(serverJson, HttpEntity.class);
            } catch (Exception e) {
                httpEntity = new HttpEntity();
                httpEntity.setSuccess(false);
                httpEntity.setErrorMsg("服务器数据格式错误");
            }

            if (httpEntity.isSuccess()) { // The server successfully processed the business.
                try {
                    if (succeed.toString().equals("class java.lang.String")) {
                            succeedData = (S) httpEntity.getData();
                    } else {
                        succeedData = JSON.parseObject(httpEntity.getData(), succeed);
                    }
                } catch (Exception e) {
                    JSONObject jsonObject = new JSONObject(serverJson);
                    httpEntity = new HttpEntity();
                    httpEntity.setSuccess(jsonObject.getBoolean("success"));
                    httpEntity.setErrorMsg(jsonObject.getString("errorMsg"));
                    httpEntity.setErrorCode(jsonObject.getInt("errorCode"));
                    httpEntity.setData("服务器数据格式错误");
                    failedData = (F) httpEntity;
                }
            } else {
                // The server failed to read the wrong information.
                JSONObject jsonObject = new JSONObject(serverJson);
                httpEntity = new HttpEntity();
                httpEntity.setSuccess(jsonObject.getBoolean("success"));
                httpEntity.setErrorMsg(jsonObject.getString("errorMsg"));
                httpEntity.setErrorCode(jsonObject.getInt("errorCode"));
                httpEntity.setData(jsonObject.getString("data"));
                failedData = (F) httpEntity;
            }

        } else if (code >= 400 && code < 500) {
            JSONObject jsonObject = new JSONObject(serverJson);
            httpEntity = new HttpEntity();
            httpEntity.setSuccess(jsonObject.getBoolean("success"));
            httpEntity.setErrorMsg(jsonObject.getString("errorMsg"));
            httpEntity.setErrorCode(jsonObject.getInt("errorCode"));
            httpEntity.setData("未知异常");
            failedData = (F) httpEntity;
        } else if (code >= 500) {
            JSONObject jsonObject = new JSONObject(serverJson);
            httpEntity = new HttpEntity();
            httpEntity.setSuccess(jsonObject.getBoolean("success"));
            httpEntity.setErrorMsg(jsonObject.getString("errorMsg"));
            httpEntity.setErrorCode(jsonObject.getInt("errorCode"));
            httpEntity.setData("服务器异常");
            failedData = (F) httpEntity;
        }

        return SimpleResponse.<S, F>newBuilder()
                .code(response.code())
                .headers(response.headers())
                .fromCache(fromCache)
                .succeed(succeedData)
                .failed(failedData)
                .build();
    }
}