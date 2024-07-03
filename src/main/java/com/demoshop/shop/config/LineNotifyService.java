package com.demoshop.shop.config;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LineNotifyService {

    private static final Logger log = LoggerFactory.getLogger(LineNotifyService.class);
    private static final String LINE_NOTIFY_API_URL = "https://notify-api.line.me/api/notify";
   // private static final String ACCESS_TOKEN = "fPzvc2uH4Y2bGZ3JMMWDNxGPa7dfuDVV6lQhcw7PZqZ";
    private String accessToken;

    public  LineNotifyService(@Value("${line.notify.token}") String accessToken){
        this.accessToken = accessToken;
    }

    public void sendNotification(String message) throws Exception {
        CloseableHttpClient httpClients = HttpClients.createDefault();
        try {

            HttpPost httpPost = new HttpPost(LINE_NOTIFY_API_URL);
            httpPost.setHeader("Authorization", "Bearer " + accessToken);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            StringEntity params = new StringEntity("message=" + message ,"UTF-8");
            httpPost.setEntity(params);

            CloseableHttpResponse response = httpClients.execute(httpPost);
            try {
                if (response.getStatusLine().getStatusCode() == 200){
                    log.info("LineNotifyService 連線成功");
                }else {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    log.warn("LineNotifyService 發送失敗，Response:" + responseBody);
                    }
                }finally{
                    response.close();
            }

        }finally {
            httpClients.close();
        }
    }

    public static void main(String[] args){
        try {
            LineNotifyService lineNotifyService = new LineNotifyService("fPzvc2uH4Y2bGZ3JMMWDNxGPa7dfuDVV6lQhcw7PZqZ");
            lineNotifyService.sendNotification("Hello,測試lineNotify");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
