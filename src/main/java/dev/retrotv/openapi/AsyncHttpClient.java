package dev.retrotv.openapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

import dev.retrotv.openapi.exception.ConnectionFailException;
import dev.retrotv.openapi.request.Request;
import lombok.NonNull;

public class AsyncHttpClient implements Callable<String> {
    private final HttpURLConnection httpURLConnection;

    private AsyncHttpClient(Request request) {
        this.httpURLConnection = request.getHttpURLConnection();
    }

    /**
     * AsyncHttpClient 인스턴스를 생성합니다.
     * @param request Request 객체
     * @return AsyncHttpClient 인스턴스
     */
    @NonNull
    public static AsyncHttpClient getClient(@NonNull Request request) {
        return new AsyncHttpClient(request);
    }

    @Override
    public String call() throws Exception {
        try {
            this.httpURLConnection.connect();
        } catch (IOException ex) {
            this.httpURLConnection.disconnect();
            throw new ConnectionFailException("서버 연결에 실패했습니다.", ex);
        }
        
        try (
            InputStreamReader ip = new InputStreamReader(this.httpURLConnection.getInputStream());
            BufferedReader reader = new BufferedReader(ip)
        ) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return response.toString();
        } finally {
            this.httpURLConnection.disconnect();
        }
    }
}
