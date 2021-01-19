package com.edusoft.sysmanage.model;

import java.io.Serializable;
import java.util.Date;

public class ResServiceInfo extends ResServiceInfoKey implements Serializable {
    private Integer spId;

    private String resName;

    private String resType;

    private String resUuid;

    private String resRoot;

    private Integer interNum;

    private Integer pxh;

    private String status;

    private String addUser;

    private Date addTime;

    private String updateUser;

    private Date updateTime;

    private String deleted;

    private String ext1;

    private String ext2;

    private String resDesc;

    private static final long serialVersionUID = 1L;

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType == null ? null : resType.trim();
    }

    public String getResUuid() {
        return resUuid;
    }

    public void setResUuid(String resUuid) {
        this.resUuid = resUuid == null ? null : resUuid.trim();
    }

    public String getResRoot() {
        return resRoot;
    }

    public void setResRoot(String resRoot) {
        this.resRoot = resRoot == null ? null : resRoot.trim();
    }

    public Integer getInterNum() {
        return interNum;
    }

    public void setInterNum(Integer interNum) {
        this.interNum = interNum;
    }

    public Integer getPxh() {
        return pxh;
    }

    public void setPxh(Integer pxh) {
        this.pxh = pxh;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser == null ? null : addUser.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted == null ? null : deleted.trim();
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc == null ? null : resDesc.trim();
    }
}