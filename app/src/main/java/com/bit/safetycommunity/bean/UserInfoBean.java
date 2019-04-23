package com.bit.safetycommunity.bean;

import java.io.Serializable;
import java.util.List;


public class UserInfoBean implements Serializable{

    private String id;//用户id
    private String userName;//用户名
    private String photo;//阿里云头像，需要获取签名路径
    private String photoLocal;//本地头像路径
    private String province;
    private String city;
    private String area;
    private String address;//包含省市县的详细地址
    private List<String> authIdentity;//操作证列表，需要获取签名路径
    private String company;//公司
    private String sex;//性别，（1：男 2：女 3:未知）
    private String phone;
    private String positionName;//岗位
    private String groupName;//班组名称
    private String workGroupId;//班组Id，用于请求班组信息
    private String workTeamId;
    private String employeeId;//员工ID
    private Long createTime;//创建时间
    private boolean optRead;//本地是否取消操作证红点状态
    private int oldOptAudit;//上一次操作证状态
    private boolean inspectRead;//本地是否取消质检员证红点状态
    private int oldInspectAuthAudit;//上一次质检员证状态
    private int optAudit;//操作证状态
    private int inspectAuthStatus;//质检员资格
    private int inspectAuthAudit;//质检员证状态

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getWorkGroupId() {
        return workGroupId;
    }

    public void setWorkGroupId(String workGroupId) {
        this.workGroupId = workGroupId;
    }

    public String getWorkTeamId() {
        return workTeamId;
    }

    public void setWorkTeamId(String workTeamId) {
        this.workTeamId = workTeamId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotoLocal() {
        return photoLocal;
    }

    public void setPhotoLocal(String photoLocal) {
        this.photoLocal = photoLocal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getAuthIdentity() {
        return authIdentity;
    }

    public void setAuthIdentity(List<String> authIdentity) {
        this.authIdentity = authIdentity;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isOptRead() {
        return optRead;
    }

    public void setOptRead(boolean optRead) {
        this.optRead = optRead;
    }


    public int getOldOptAudit() {
        return oldOptAudit;
    }

    public void setOldOptAudit(int oldOptAudit) {
        this.oldOptAudit = oldOptAudit;
    }

    public boolean isInspectRead() {
        return inspectRead;
    }

    public void setInspectRead(boolean inspectRead) {
        this.inspectRead = inspectRead;
    }

    public int getOldInspectAuthAudit() {
        return oldInspectAuthAudit;
    }

    public void setOldInspectAuthAudit(int oldInspectAuthAudit) {
        this.oldInspectAuthAudit = oldInspectAuthAudit;
    }

    public int getOptAudit() {
        return optAudit;
    }

    public void setOptAudit(int optAudit) {
        this.optAudit = optAudit;
    }

    public int getInspectAuthStatus() {
        return inspectAuthStatus;
    }

    public void setInspectAuthStatus(int inspectAuthStatus) {
        this.inspectAuthStatus = inspectAuthStatus;
    }

    public int getInspectAuthAudit() {
        return inspectAuthAudit;
    }

    public void setInspectAuthAudit(int inspectAuthAudit) {
        this.inspectAuthAudit = inspectAuthAudit;
    }
}
