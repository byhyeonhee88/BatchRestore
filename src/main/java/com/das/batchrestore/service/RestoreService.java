package com.das.batchrestore.service;

import com.das.batchrestore.model.RestoreModel;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.List;

public interface RestoreService {
    List<RestoreModel> getRestoreList(String pgmCd);
    RestoreModel reqRestore(String objectId, String url, MediaType mediaType, RequestBody body, String contType, String auth, String cookie);
}
