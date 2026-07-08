package dev.retrotv.openapi.client;

import dev.retrotv.openapi.exception.ConnectionFailException;
import dev.retrotv.openapi.request.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.nio.charset.StandardCharsets;

public class SyncHttpClient {
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;
    private final Request request;

    public SyncHttpClient(Request request) {
        this.request = request;
    }

    public String get() {
        HttpURLConnection conn = createHttpURLConnection(this.request);
        try {
            conn.setRequestMethod("GET");

            // 기본 타임아웃 설정 (매우 중요!)
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);

            int responseCode = conn.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    return response.toString();
                }
            } else {
                throw new ConnectionFailException("HTTP 요청 실패. Status Code: " + responseCode);
            }
        } catch (ProtocolException e) {
            throw new ConnectionFailException("유효한 HTTP 메서드가 아닙니다.", e);
        } catch (IOException e) {
            throw new ConnectionFailException("비동기 HTTP 요청 중 예외 발생", e);
        } finally {
            conn.disconnect();
        }
    }

    private static HttpURLConnection createHttpURLConnection(Request request) {
        try {
            HttpURLConnection conn = (HttpURLConnection) request.buildURL().openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);

            return conn;
        } catch (IOException ex) {
            throw new ConnectionFailException("API 연결에 실패했습니다.", ex);
        }
    }
}
