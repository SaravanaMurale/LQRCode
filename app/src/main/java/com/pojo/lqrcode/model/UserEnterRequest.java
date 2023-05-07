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
    @SerializedName("in_time")
    String inTime;
    @SerializedName("out_time")
    String outTime;



    public UserEnterRequest(String userId, List<String> sportsList, String remarks,String inTime,String outTime) {
        this.userId = userId;
        this.sportsList = sportsList;
        this.remarks = remarks;
        this.inTime=inTime;
        this.outTime=outTime;
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

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }
}
