package com.common.tools.util;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * HTTP工具类
 */
public class HttpUtil {

    /**
     * 发送GET请求
     * @param url 请求URL
     * @return 响应内容
     */
    public static String get(String url) {
        return get(url, null);
    }

    /**
     * 发送GET请求（带请求头）
     * @param url 请求URL
     * @param headers 请求头
     * @return 响应内容
     */
    public static String get(String url, Map<String, String> headers) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int statusCode = response.getCode();
                if (statusCode == 200) {
                    return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                } else {
                    throw new RuntimeException("HTTP GET request failed with status code: " + statusCode);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("HTTP GET request failed: " + e.getMessage(), e);
        }
    }

    /**
     * 发送POST请求
     * @param url 请求URL
     * @param body 请求体
     * @return 响应内容
     */
    public static String post(String url, String body) {
        return post(url, body, null);
    }

    /**
     * 发送POST请求（带请求头）
     * @param url 请求URL
     * @param body 请求体
     * @param headers 请求头
     * @return 响应内容
     */
    public static String post(String url, String body, Map<String, String> headers) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }

            if (body != null) {
                httpPost.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
            }

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getCode();
                if (statusCode == 200) {
                    return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                } else {
                    throw new RuntimeException("HTTP POST request failed with status code: " + statusCode);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("HTTP POST request failed: " + e.getMessage(), e);
        }
    }

    /**
     * 发送PUT请求
     * @param url 请求URL
     * @param body 请求体
     * @return 响应内容
     */
    public static String put(String url, String body) {
        return put(url, body, null);
    }

    /**
     * 发送PUT请求（带请求头）
     * @param url 请求URL
     * @param body 请求体
     * @param headers 请求头
     * @return 响应内容
     */
    public static String put(String url, String body, Map<String, String> headers) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut httpPut = new HttpPut(url);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPut.setHeader(entry.getKey(), entry.getValue());
                }
            }

            if (body != null) {
                httpPut.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
            }

            try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
                int statusCode = response.getCode();
                if (statusCode == 200) {
                    return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                } else {
                    throw new RuntimeException("HTTP PUT request failed with status code: " + statusCode);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("HTTP PUT request failed: " + e.getMessage(), e);
        }
    }

    /**
     * 发送DELETE请求
     * @param url 请求URL
     * @return 响应内容
     */
    public static String delete(String url) {
        return delete(url, null);
    }

    /**
     * 发送DELETE请求（带请求头）
     * @param url 请求URL
     * @param headers 请求头
     * @return 响应内容
     */
    public static String delete(String url, Map<String, String> headers) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpDelete httpDelete = new HttpDelete(url);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpDelete.setHeader(entry.getKey(), entry.getValue());
                }
            }

            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
                int statusCode = response.getCode();
                if (statusCode == 200) {
                    return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                } else {
                    throw new RuntimeException("HTTP DELETE request failed with status code: " + statusCode);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("HTTP DELETE request failed: " + e.getMessage(), e);
        }
    }
}
