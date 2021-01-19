package com.edusoft.sysmanage.vo;

import com.edusoft.sysmanage.model.ResInterfaceInfo;

/**
 * Created by lego-jspx01 on 2020/5/4.
 */
public class ResInterfaceInfoVo extends ResInterfaceInfo {
    private String spName;
    private String serviceName;

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
