package com.pojo.lqrcode.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDetailsResponse {

    @SerializedName("type")
    String type;
    @SerializedName("message")
    String message;

    @SerializedName("responseData")
    List<ResponseData> responseDataList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResponseData> getResponseDataList() {
        return responseDataList;
    }

    public void setResponseDataList(List<ResponseData> responseDataList) {
        this.responseDataList = responseDataList;
    }

    public class ResponseData{

        @SerializedName("id")
        int id;
        @SerializedName("user_id")
        String user_id;
        @SerializedName("user_name")
        String userName;
        @SerializedName("user_image")
        String userImage;
        @SerializedName("status")
        int status;

        @SerializedName("address")
        String address;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }


}
