package com.pojo.lqrcode.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserEnterRequest {

    @SerializedName("user_id")
    String userId;
    @SerializedName("sports")
    List<String> sportsList;
    @SerializedName("remarks")
    String remarks;

    public UserEnterRequest(String userId, List<String> sportsList, String remarks) {
        this.userId = userId;
        this.sportsList = sportsList;
        this.remarks = remarks;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getSportsList() {
        return sportsList;
    }

    public void setSportsList(List<String> sportsList) {
        this.sportsList = sportsList;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
