package com.das.batchrestore.controller;

import com.das.batchrestore.model.*;
import com.das.batchrestore.service.*;
import okhttp3.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class RestoreController {

    @Autowired
    SessionKeyService sessionKeyService;     

    @GetMapping("/key")
    public String getSessionKey(){
        String sessionKey;
        String url = "http://10.150.12.104:9443/diva/service/rest/2.2/DIVArchiveWS_REST/registerClient";
        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType, "<p:registerClient xmlns:p=\"http://interaction.api.ws.diva.fpdigital.com/xsd\">\n<p:appName>MediaDatabase2</p:appName>\n<p:locName>USWest2</p:locName>\n<p:processId>5828</p:processId>\n</p:registerClient>");
        String contType = "application/xml";
        String auth = "Basic ZGI3MTg3NGYtZTNmOC00NTkwLWE4NmQtZWQyOWIxNmUxNjA1OngzR3R6dVdiOGxBWDYzcVRvMXZz";
        String cookie = "JSESSIONID=QvMZ3H1qg8dm1tW-LtaKv6RsNV_Vys2p9565s-lkufy2DA_m0L8J!745288653";
        SessionModel sessionModel = sessionKeyService.getKey(url, mediaType, body, contType, auth, cookie);
        sessionKey = sessionModel.getSession();
        return sessionKey;
    }

    @Autowired
    RestoreService restoreService;
    String pgmCd = "P00964";

    @GetMapping("/restoreView")
    public List<RestoreModel> restoreView(){
        List<RestoreModel> view = restoreService.getRestoreList(pgmCd);
        return view;
    }

    @GetMapping("/restore")
    public String exArchive(){
        String sessionKey = getSessionKey();
        List<RestoreModel> list = restoreService.getRestoreList(pgmCd);
        JSONObject reqObject = new JSONObject();
        String reqInfo = null;
        String filePath = "X:\\ARCHIVE_TEAM\\coupang\\"+pgmCd;
        for(int i=0;i<list.size();i++){
            String objectId = list.get(i).getObjectId();
            String url = "http://10.150.12.104:9443/diva/service/rest/2.2/DIVArchiveWS_REST/restoreObject";
            MediaType mediaType = MediaType.parse("application/xml");
            RequestBody body = RequestBody.create(mediaType, "<p:restoreObject xmlns:p=\"http://interaction.api.ws.diva.fpdigital.com/xsd\">\n<p:sessionCode>"+ sessionKey +"</p:sessionCode>\n<p:objectName>"+objectId+"</p:objectName>\n<p:objectCategory>cms</p:objectCategory>\n<p:destination>archive</p:destination>\n<p:filesPathRoot>"+filePath+"</p:filesPathRoot>\n<p:qualityOfService>3</p:qualityOfService>\n<p:priorityLevel>10</p:priorityLevel>\n<p:restoreOptions></p:restoreOptions>\n</p:restoreObject>");
            String contType = "application/xml";
            String auth = "Basic ZGI3MTg3NGYtZTNmOC00NTkwLWE4NmQtZWQyOWIxNmUxNjA1OngzR3R6dVdiOGxBWDYzcVRvMXZz";
            String cookie = "JSESSIONID=QvMZ3H1qg8dm1tW-LtaKv6RsNV_Vys2p9565s-lkufy2DA_m0L8J!745288653";
            RestoreModel restoreModel = restoreService.reqRestore(objectId, url, mediaType, body, contType, auth, cookie);
            String reqNo = restoreModel.getReqNo();
            String divaStatus = restoreModel.getDivaStatus();
            reqObject.put(objectId, "reqNo : "+reqNo+", divaStatus : "+ divaStatus);
            reqInfo = reqObject.toJSONString();
        }
        return reqInfo;
    }
}