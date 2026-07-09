package dev.retrotv.openapi.client;

import dev.retrotv.openapi.exception.ConnectionFailException;
import dev.retrotv.openapi.request.Request;
import dev.retrotv.openapi.response.JSONResponse;
import dev.retrotv.openapi.response.Response;
import dev.retrotv.openapi.response.XMLResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import static dev.retrotv.openapi.enums.ContentType.*;

public class AsyncHttpClient {
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;
    private final Request request;

    public AsyncHttpClient(Request request) {
        this.request = request;
    }

    /**
     * 비동기 GET 요청을 보내는 메서드
     *
     * @return CompletableFuture<Response> Response 객체를 담은 미래 객체
     */
    public CompletableFuture<Response> get() {

        // supplyAsync 메서드 내부에서 발생한 예외는 CompletableFuture의 exceptionally 단계로 전달됨
        return CompletableFuture.supplyAsync(() -> {
            HttpURLConnection conn = createHttpURLConnection(this.request);
            try {
                conn.setRequestMethod("GET");

                // 기본 타임아웃 설정 (매우 중요!)
                conn.setConnectTimeout(CONNECT_TIMEOUT);
                conn.setReadTimeout(READ_TIMEOUT);

                int responseCode = conn.getResponseCode();
                if (responseCode >= 200 && responseCode < 300) {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                        return getResponse(request, readBody(in));
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
        });
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

    private static Response getResponse(Request request, String responseBody) {
        if (XML.equals(request.getContentType())) {
            return new XMLResponse(responseBody);
        } else if (JSON.equals(request.getContentType())) {
            return new JSONResponse(responseBody);
        } else {
            throw new ConnectionFailException("Invalid content type: " + request.getContentType());
        }
    }

    private static String readBody(BufferedReader in) throws IOException {
        StringBuilder responseBody = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            responseBody.append(inputLine);
        }

        return responseBody.toString();
    }
}
