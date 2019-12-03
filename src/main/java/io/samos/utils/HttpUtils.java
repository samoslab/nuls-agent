package io.samos.utils;

import com.google.gson.Gson;
import io.samos.nuls.common.NodeListResponse;
import io.samos.nuls.common.NulsReq;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class HttpUtils {
    private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

    private static String adminUrl;
    private static String adminSecret;

    static void init(String url, String secret) {
        adminUrl = url;
        adminSecret = secret;
    }

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    static private OkHttpClient client = new OkHttpClient();

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }


    public static NodeListResponse getNodesList(String url)throws Exception  {
        NulsReq req = new NulsReq();
        req.setId(1234);
        req.setMethod("getConsensusNodes");
        Integer[] params = {1,200,0};
        req.setParams(params);
        req.setJsonrpc("2.0");
        req.setVersion("1.0");

        String jsonStr = new Gson().toJson(req);

        RequestBody body = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection", "Keep-Alive")
                .post(body)
                .build();
        final List<String> responseContainer = new ArrayList<>();

        CountDownLatch latch = new CountDownLatch(1);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                LOG.info("POST to {} with data {} failure", url, jsonStr);
                latch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                responseContainer.add(responseBody);
                LOG.info("POST to {} success with response", url, responseBody);
                latch.countDown();
            }
        });


        latch.await();

        if (responseContainer.size() == 0) {
            return null;
        }

        NodeListResponse resp =  new Gson().fromJson(responseContainer.get(0), NodeListResponse.class);

        return resp;
    }

}
