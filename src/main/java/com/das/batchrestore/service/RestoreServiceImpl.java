package com.das.batchrestore.service;

import com.das.batchrestore.model.RestoreModel;
import com.das.batchrestore.repository.JdbcRepository;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RestoreServiceImpl implements RestoreService {

    @Autowired
    private JdbcRepository jdbcRepository;

    @Override
    public List<RestoreModel> getRestoreList(String pgmCd) {
        return jdbcRepository.selectRestoreList(pgmCd);
    }

    @Override
    public RestoreModel reqRestore(String objectId, String url, MediaType mediaType, RequestBody body, String contType, String auth, String cookie) {
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

        RestoreModel restoreModel = new RestoreModel();
        try {
            response = client.newCall(request).execute();
            String returnStatus = response.body().string();
            System.out.println(returnStatus);
            Pattern pDivaStatus = Pattern.compile("(?<=\\<ns0:divaStatus\\>)(\\s*.*\\s*)(?=\\<\\/ns0:divaStatus\\>)");
            Pattern pRequestNumber = Pattern.compile("(?<=\\<ns0:requestNumber\\>)(\\s*.*\\s*)(?=\\<\\/ns0:requestNumber\\>)");
            Matcher mDivaStatus = pDivaStatus.matcher(returnStatus);
            Matcher mRequestNumber = pRequestNumber.matcher(returnStatus);

            String divaStatus = null;
            String reqNo = null;

            while (mDivaStatus.find()) {
                divaStatus = mDivaStatus.group(1);
                restoreModel.setDivaStatus(divaStatus);
                if (mDivaStatus.group(1) == null) break;
            }

            while (mRequestNumber.find()) {
                reqNo = mRequestNumber.group(1);
                restoreModel.setReqNo(reqNo);
                if (mRequestNumber.group(1) == null) break;
            }

            System.out.println("restore update info - id : "+ objectId + ", reqNo : " + reqNo + ", divaStatus : " + divaStatus );

        } catch (IOException e) {
            e.printStackTrace();
        }
        return restoreModel;
    }
}