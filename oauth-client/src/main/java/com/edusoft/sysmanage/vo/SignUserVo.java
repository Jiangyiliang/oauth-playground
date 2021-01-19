package com.edusoft.sysmanage.vo;

import com.edusoft.sysmanage.model.UserInfo;

/**
 * Created by lego-jspx01 on 2020/5/12.
 */
public class SignUserVo extends UserInfo {
    private String spName;
    private Integer SpId;

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public Integer getSpId() {
        return SpId;
    }

    public void setSpId(Integer spId) {
        SpId = spId;
    }
}
