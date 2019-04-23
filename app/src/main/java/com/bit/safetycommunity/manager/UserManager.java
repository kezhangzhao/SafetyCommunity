package com.bit.safetycommunity.manager;
import android.content.Context;

import com.bit.safetycommunity.app.AppInfo;
import com.bit.safetycommunity.bean.UserInfoBean;
import com.bit.safetycommunity.contant.ConfigPF;
import com.bit.safetycommunity.utils.PreferenceUtils;

/**
 * 用户信息管理
 */
public class UserManager {

    private static UserManager instance;
    private UserInfoBean userInfo;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }
        return instance;
    }

    /**
     * 保存token
     */
    public void saveUserToken(String userToken) {
        PreferenceUtils.get().putString(ConfigPF.USER_TOKEN_KEY, userToken);
    }

    /**
     * 获取token
     */
    public String getUserToken() {
        return PreferenceUtils.get().getString(ConfigPF.USER_TOKEN_KEY, "");
    }

    /**
     * 保存用户公司id
     */
    public void saveCompanyId(String companyId) {
        PreferenceUtils.get().putString(ConfigPF.USER_COMPANY_ID, companyId);
    }

    /**
     * 获取用户公司id
     */
    public String getCompanyId() {
        return PreferenceUtils.get().getString(ConfigPF.USER_COMPANY_ID, "");
    }


    /**
     * 获取用户信息
     */
    public AppInfo getAppInfo() {
        return (AppInfo) PreferenceUtils.get().getObject(ConfigPF.APP_INFO_KEY);
    }

    public void saveAppInfo(AppInfo appInfo) {
        PreferenceUtils.get().putObject( ConfigPF.APP_INFO_KEY, appInfo);
    }

    /**
     * 保存userId
     */
    public void saveUserId(String userId) {
        PreferenceUtils.get().putString(ConfigPF.USER_ID_KEY, userId);
    }

    /**
     * 获取userId
     */
    public String getUserId() {
        return PreferenceUtils.get().getString(ConfigPF.USER_ID_KEY, "");
    }

    /**
     * 保存是否一直弹出免责声明
     */
    public void saveExclusionSetting(String show) {
        PreferenceUtils.get().putString(ConfigPF.SHOW_EXCLUSION, show);
    }

    public String getExclusionSetting(){
        return PreferenceUtils.get().getString(ConfigPF.SHOW_EXCLUSION, "true");
    }

    /**
     * 退出清空用户信息
     */
    public void clearUserInfo() {
        PreferenceUtils.get().remove(ConfigPF.USER_ID_KEY);
        PreferenceUtils.get().remove(ConfigPF.APP_INFO_KEY);
        PreferenceUtils.get().remove(ConfigPF.USER_TOKEN_KEY);
        removeUserInfo();
    }

    /**
     * 保存用户信息
     *
     * @param userInfoEntity 网络请求下来的用户bean类
     * @param userInfo       自定义用户bean类
     */
//    public void saveUserInfo(UserInfoEntity userInfoEntity, UserInfoBean userInfo) {
//        if (userInfoEntity != null) {
//            if (userInfo == null)
//                userInfo = new UserInfoBean();
//            int sex = userInfoEntity.getSex();
//            if (sex == 1) {
//                userInfo.setSex("男");
//            } else if (sex == 2) {
//                userInfo.setSex("女");
//            } else if (sex == 3) {//未知
//                userInfo.setSex("");
//            }
//            userInfo.setId(userInfoEntity.getId());
//            userInfo.setUserName(userInfoEntity.getRealName());
//            userInfo.setGroupName(userInfoEntity.getGroupName());
//            userInfo.setPositionName(userInfoEntity.getPositionName());
//            userInfo.setWorkGroupId(userInfoEntity.getWorkGroupId());
//            userInfo.setWorkTeamId(userInfoEntity.getWorkTeamId());
//            userInfo.setEmployeeId(userInfoEntity.getEmployeeId());
//            userInfo.setPhoto(userInfoEntity.getHeadImg());
//            userInfo.setOptAudit(userInfoEntity.getOptAudit());
//            userInfo.setInspectAuthStatus(userInfoEntity.getInspectAuthStatus());
//            userInfo.setInspectAuthAudit(userInfoEntity.getInspectAuthAudit());
//
//            UserManager.getInstance().putUserInfo(userInfo);
//
//
//        }
//    }

    /**
     * 保存用户信息
     */
    public void putUserInfo(UserInfoBean userInfo) {
        if (userInfo != null) {
            PreferenceUtils.get().putObject(ConfigPF.USER_INFO_KEY, userInfo);
        }
    }

    public interface UserInfoInterface {
        void requestUserInfoBean(UserInfoBean bean);
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(Context context, String content,UserInfoInterface userInterface) {
        UserInfoBean userInfoBean = (UserInfoBean) PreferenceUtils.get().getObject(ConfigPF.USER_INFO_KEY);
        if (userInfoBean!=null&& content!=null){
            userInterface.requestUserInfoBean(userInfoBean);
        }else {
//            requestUserInfo(context,userInterface);
        }
    }

    public UserInfoBean getUserInfo() {
        return (UserInfoBean) PreferenceUtils.get().getObject(ConfigPF.USER_INFO_KEY);
    }

    /**
     * 移除用户信息
     */
    private void removeUserInfo() {
        PreferenceUtils.get().remove(ConfigPF.USER_INFO_KEY);
    }



//    public void putUserEnablingKey(EnablingKeyBean enablingKey) {
//        if (enablingKey != null) {
//            PreferenceUtils.get().putObject(ConfigPF.USER_ENABLING_KEY, enablingKey);
//        }
//    }

//    public interface EnablingKeyInterface {
//        void requestEnablingKeyBean(EnablingKeyBean bean);
//    }


    /**
     * 获取当前用户在APP端的菜单集合
     */
//    private void requestEnablingKey(Context context, EnablingKeyInterface enablingKeyInterface) {
//        ApiRequest.getInstance().enablingKey(new DefineCallback<EnablingKeyBean>(context) {
//            @Override
//            public void onMyResponse(SimpleResponse<EnablingKeyBean, HttpEntity> response) {
//                if (response.isSucceed()) {
//                    EnablingKeyBean enablingKeyBean = response.succeed();
//                    if (enablingKeyBean != null) {
//                        enablingKeyInterface.requestEnablingKeyBean(enablingKeyBean);
//                    }
//                }
//            }
//        });
//    }

    /**
     * 获取个人信息
     */
//    private void requestUserInfo(Context context,UserInfoInterface userInterface) {
//        //缓存获取数据
//        Map<String, String> map = new HashMap<>();
//        NetworkDataCacheBean networkDataCacheBean = NetworkDataCacheBean.getCacheFromDB(ConfigCache.getInstance().getUserInfoKey(), map);
//
//        ApiRequest.getInstance().getUserInfo(new DefineCallback<UserInfoEntity>(context) {
//            @Override
//            public void onMyResponse(SimpleResponse<UserInfoEntity, HttpEntity> response) {
//                if (response.isSucceed() && response.succeed() != null) {
//                    UserInfoEntity userInfoEntity = response.succeed();
//                    saveUserInfo(userInfoEntity, userInfo);
//                    userInfo = getUserInfo();
//                    userInterface.requestUserInfoBean(userInfo);
//                    String resultJson = new Gson().toJson(response.succeed());
//                    if (resultJson != null) {
//                        new NetworkDataCacheBean(ConfigCache.getInstance().getUserInfoKey(), map, resultJson,
//                                ConfigCacheTime.refresh_seconds_5, ConfigCacheTime.failure_day).save();
//                    }
//                } else {
//                    if (networkDataCacheBean != null) {
//                        try {
//                            UserInfoEntity userInfoEntity = new Gson().fromJson(networkDataCacheBean.getJsonData(), UserInfoEntity.class);
//                            if (userInfoEntity != null) {
//                                saveUserInfo(userInfoEntity, userInfo);
//                                userInfo = getUserInfo();
//                                userInterface.requestUserInfoBean(userInfo);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        });
//
//    }
}
