package com.bit.safetycommunity.net;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * http数据接收
 */
public class HttpEntity implements Parcelable {

    @JSONField(name = "success")
    private boolean success;//判断请求是否成功
    @JSONField(name = "errorMsg")
    private String errorMsg;//错误信息
    @JSONField(name = "errorCode")
    private int errorCode;//暂时没用
    @JSONField(name = "data")
    private String data;//需要的数据


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeString(this.errorMsg);
        dest.writeInt(this.errorCode);
        dest.writeString(this.data);
    }

    public HttpEntity() {
    }

    protected HttpEntity(Parcel in) {
        this.success = in.readByte() != 0;
        this.errorMsg = in.readString();
        this.errorCode = in.readInt();
        this.data = in.readString();
    }

    public static final Creator<HttpEntity> CREATOR = new Creator<HttpEntity>() {
        @Override
        public HttpEntity createFromParcel(Parcel source) {
            return new HttpEntity(source);
        }

        @Override
        public HttpEntity[] newArray(int size) {
            return new HttpEntity[size];
        }
    };

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
