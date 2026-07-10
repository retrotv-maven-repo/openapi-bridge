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

import static dev.retrotv.openapi.enums.ContentType.JSON;
import static dev.retrotv.openapi.enums.ContentType.XML;

/**
 * API 요청을 위한 추상 HTTP 클라이언트 클래스입니다.
 * @see dev.retrotv.openapi.client.SyncHttpClient
 * @see dev.retrotv.openapi.client.AsyncHttpClient
 */
public abstract class HttpClient {
    protected static final int CONNECT_TIMEOUT = 5000;
    protected static final int READ_TIMEOUT = 5000;
    protected final Request request;

    HttpClient(Request request) {
        this.request = request;
    }

    protected Response executeGet() {
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
    }

    protected static HttpURLConnection createHttpURLConnection(Request request) {
        try {
            HttpURLConnection conn = (HttpURLConnection) request.buildURL().openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);

            return conn;
        } catch (IOException ex) {
            throw new ConnectionFailException("API 연결에 실패했습니다.", ex);
        }
    }

    protected static Response getResponse(Request request, String responseBody) {
        if (XML.equals(request.getContentType())) {
            return new XMLResponse(responseBody);
        } else if (JSON.equals(request.getContentType())) {
            return new JSONResponse(responseBody);
        } else {
            throw new ConnectionFailException("Invalid content type: " + request.getContentType());
        }
    }

    protected static String readBody(BufferedReader in) throws IOException {
        StringBuilder responseBody = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            responseBody.append(inputLine);
        }

        return responseBody.toString();
    }
}
