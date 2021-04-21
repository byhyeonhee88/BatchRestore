package com.das.batchrestore.model;

public class SessionModel {

    private  String session;

    public  String getSession() { //static으로 바꾸니까 getter가 값을 받아옴.. 왜지??
        return session;
    }

    public void setSession(String key) {
        this.session = key;
    }

}
