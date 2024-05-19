package com.demoshop.shop.controller;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class LineNotifyController {
    private static final Logger logger = LoggerFactory.getLogger(LineNotifyController.class);

    private static final String LINE_NOTIFY_API_URL = "https://notify-api.line.me/api/notify";
    private static final String ACCESS_TOKEN = "fPzvc2uH4Y2bGZ3JMMWDNxGPa7dfuDVV6lQhcw7PZqZ";

    // http://localhost:8080/sendLineNotify?message=Hello
    @GetMapping("/sendLineNotify")
    public String sendLineNotifyGet(@RequestParam String message) {
        logger.info("運行了sendLineNotify GET");

        return sendLineNotify(message);
    }

    private String sendLineNotify(String message) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(LINE_NOTIFY_API_URL);

        // 设置Authorization
        httpPost.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);

        // 设置请求参数
        try {
            StringEntity params = new StringEntity("message=" + message);
            params.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(params);

            // 执行请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                return EntityUtils.toString(response.getEntity());
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
