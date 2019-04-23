package com.bit.safetycommunity.net;

import com.bit.safetycommunity.contant.ConfigNetwork;
import com.yanzhenjie.kalle.exception.ConnectTimeoutError;
import com.yanzhenjie.kalle.exception.HostError;
import com.yanzhenjie.kalle.exception.NetworkError;
import com.yanzhenjie.kalle.exception.ParseError;
import com.yanzhenjie.kalle.exception.ReadTimeoutError;
import com.yanzhenjie.kalle.exception.URLError;
import com.yanzhenjie.kalle.exception.WriteException;
import com.yanzhenjie.kalle.simple.Callback;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

public abstract class DefineCallback<S> extends Callback<S, HttpEntity> {

    public DefineCallback() {
    }

    @Override
    public void onStart() {
        // 请求开始
    }

    @Override
    public void onEnd() {
        // 请求结束
    }

    @Override
    public Type getSucceed() {
        // 通过反射获取业务成功的数据类型。
        Type superClass = getClass().getGenericSuperclass();
        if (superClass != null) {
            return ((ParameterizedType) superClass).getActualTypeArguments()[0];
        } else {
            return null;
        }
    }

    @Override
    public Type getFailed() {
        // 返回失败时的数据类型，String。
        return HttpEntity.class;
    }

    @Override
    public void onException(Exception e) {
        // 发生异常了，回调到onResonse()中。
        HttpEntity httpEntity = new HttpEntity();
        if (e instanceof NetworkError) {
            httpEntity.setErrorMsg("网络未连接，请检查网络设置");
            httpEntity.setErrorCode(ConfigNetwork.NETWORK_ERROR_CODE);
        } else if (e instanceof URLError) {
            httpEntity.setErrorMsg("Url格式错误");
        } else if (e instanceof HostError) {
            httpEntity.setErrorMsg("没有找到Url指定服务器");
        } else if (e instanceof ConnectTimeoutError) {
            httpEntity.setErrorMsg("连接服务器超时，请重试");
            httpEntity.setErrorCode(ConfigNetwork.NETWORK_ERROR_CODE);
        } else if (e instanceof WriteException) {
            httpEntity.setErrorMsg("发送数据错误，请检查网络");
            httpEntity.setErrorCode(ConfigNetwork.NETWORK_ERROR_CODE);
        } else if (e instanceof ReadTimeoutError) {
            httpEntity.setErrorMsg("读取服务器数据超时，请检查网络");
            httpEntity.setErrorCode(ConfigNetwork.NETWORK_ERROR_CODE);
        } else if (e instanceof ParseError) {
            httpEntity.setErrorMsg("解析数据时发生异常");
        } else if (e instanceof ExecutionException) {
            httpEntity.setErrorMsg("网络未连接，请检查网络设置");
            httpEntity.setErrorCode(ConfigNetwork.NETWORK_ERROR_CODE);
        } else {
            httpEntity.setErrorMsg("发生未知异常，请稍后重试");
        }
        SimpleResponse<S, HttpEntity> response = SimpleResponse.<S, HttpEntity>newBuilder()
                .failed(httpEntity)
                .build();
        onResponse(response);
    }

    @Override
    public void onCancel() {
        // 请求被取消了，如果开发者需要，请自行处理。
    }
}