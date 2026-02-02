package com.common.utils.http;

import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * HTTP 工具类
 * 提供 HTTP 请求方法
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 发送 GET 请求
     */
    public static String get(String url) {
        return get(url, null);
    }

    /**
     * 发送 GET 请求
     */
    public static String get(String url, Map<String, String> headers) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            }

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return readEntity(entity);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to send GET request to {}", url, e);
        }
        return null;
    }

    /**
     * 发送 POST 请求
     */
    public static String post(String url, String body) {
        return post(url, body, null);
    }

    /**
     * 发送 POST 请求
     */
    public static String post(String url, String body, Map<String, String> headers) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            }

            if (body != null) {
                request.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
            }

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return readEntity(entity);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to send POST request to {}", url, e);
        }
        return null;
    }

    /**
     * 发送 PUT 请求
     */
    public static String put(String url, String body) {
        return put(url, body, null);
    }

    /**
     * 发送 PUT 请求
     */
    public static String put(String url, String body, Map<String, String> headers) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut request = new HttpPut(url);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            }

            if (body != null) {
                request.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
            }

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return readEntity(entity);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to send PUT request to {}", url, e);
        }
        return null;
    }

    /**
     * 发送 DELETE 请求
     */
    public static String delete(String url) {
        return delete(url, null);
    }

    /**
     * 发送 DELETE 请求
     */
    public static String delete(String url, Map<String, String> headers) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpDelete request = new HttpDelete(url);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            }

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return readEntity(entity);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to send DELETE request to {}", url, e);
        }
        return null;
    }

    /**
     * 发送 HEAD 请求
     */
    public static int head(String url) {
        return head(url, null);
    }

    /**
     * 发送 HEAD 请求
     */
    public static int head(String url, Map<String, String> headers) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpHead request = new HttpHead(url);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            }

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return response.getCode();
            }
        } catch (Exception e) {
            logger.error("Failed to send HEAD request to {}", url, e);
        }
        return -1;
    }

    /**
     * 读取响应实体
     */
    private static String readEntity(HttpEntity entity) {
        try (InputStream is = entity.getContent();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("Failed to read HTTP entity", e);
            return null;
        }
    }
}
