package com.das.batchrestore.service;

import com.das.batchrestore.model.SessionModel;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public interface SessionKeyService {
    SessionModel getKey(String url, MediaType mediaType, RequestBody body, String contType, String auth, String cookie);
}
