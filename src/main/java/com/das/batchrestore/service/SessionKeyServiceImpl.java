package com.das.batchrestore.service;

import com.das.batchrestore.model.SessionModel;
import okhttp3.*;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SessionKeyServiceImpl implements SessionKeyService {

    @Override
    public SessionModel getKey(String url, MediaType mediaType, RequestBody body, String contType, String auth, String cookie) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", contType)
                .addHeader("Authorization", auth)
                .addHeader("Cookie", cookie)
                .build();
        Response response = null;
        SessionModel sessionModel = new SessionModel(); //sessionModel 값 초기화
        try {
            response = client.newCall(request).execute();
            String returnKey = response.body().string();
            Pattern p = Pattern.compile("(?<=\\<return\\>)(\\s*.*\\s*)(?=\\<\\/return\\>)");
            Matcher m = p.matcher(returnKey);
            while (m.find()) {
                String key = m.group(1);
                sessionModel.setSession(key);
                if (m.group(1) == null) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionModel;
    }
}

