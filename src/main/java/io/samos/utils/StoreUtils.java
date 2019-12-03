package io.samos.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class StoreUtils {
    static String accessKey = "ml7ZULMI5-qDwnrqn9wxXOjlmjWsZasDqTOejph1";
    static String secretKey = "jSD3-g8vT0T2-hzy6b4tSQR9XWKtwGvl1LGWZ9Tg";
    static String bucket = "haifun";
    static String host = "http://store.yqkkn.com/";



    private void writeBtesToFile(byte[] bytes, File file) throws Exception {
        OutputStream out ;

            out = new FileOutputStream(file);
            out.write(bytes);
            out.close();

    }

//    public static String upload64(String str) {
//
//    }
    public static String upload(byte[] uploadBytes) {
        Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传


//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(uploadBytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return host+putRet.key;

        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return "";
    }

    public static void main() {
        System.out.println("hello");
    }
}
