package dev.retrotv.openapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

import dev.retrotv.openapi.exception.ConnectionFailException;

public class AsyncHttpClient implements Callable<String> {
    private final HttpURLConnection httpURLConnection;

    private AsyncHttpClient(Request request) {
        this.httpURLConnection = request.getHttpURLConnection();
    }

    public static AsyncHttpClient getClient(Request request) {
        if (request == null) {
            throw new IllegalArgumentException("request는 null일 수 없습니다.");
        }

        return new AsyncHttpClient(request);
    }

    @Override
    public String call() throws Exception {
        try {
            this.httpURLConnection.connect();
        } catch (IOException ex) {
            if (this.httpURLConnection != null) {
                this.httpURLConnection.disconnect();
            }
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
            if (this.httpURLConnection != null) {
                this.httpURLConnection.disconnect();
            }
        }
    }
}
