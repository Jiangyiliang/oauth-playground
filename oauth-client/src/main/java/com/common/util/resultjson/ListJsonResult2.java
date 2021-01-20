package com.common.util.resultjson;

import java.util.List;
import java.util.Map;

/**
 * Created by lego-jspx01 on 2021/1/18.
 */
public class ListJsonResult2 extends JsonResult {
    long total;
    List<?> data;
    Map<String,String> oauthUrl;

    public ListJsonResult2() {
    }

    public ListJsonResult2(String status, String message) {
        this.message = message;
        this.status = status;
    }


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public Map<String, String> getOauthUrl() {
        return oauthUrl;
    }

    public void setOauthUrl(Map<String, String> oauthUrl) {
        this.oauthUrl = oauthUrl;
    }
}
