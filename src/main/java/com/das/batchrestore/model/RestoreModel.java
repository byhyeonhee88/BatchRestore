package com.das.batchrestore.model;

public class RestoreModel {
    private String title;
    private String episNo;
    private String objectId;
    private String divaStatus;
    private String reqNo;

    public RestoreModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEpisNo() {
        return episNo;
    }

    public void setEpisNo(String episNo) {
        this.episNo = episNo;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public RestoreModel(String title, String episNo, String objectId) {
        this.title = title;
        this.episNo = episNo;
        this.objectId = objectId;
    }

    public String getDivaStatus() {
        return divaStatus;
    }

    public void setDivaStatus(String divaStatus) {
        this.divaStatus = divaStatus;
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }
}

