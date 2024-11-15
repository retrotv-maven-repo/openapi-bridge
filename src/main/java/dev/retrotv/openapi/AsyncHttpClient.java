package dev.retrotv.openapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

public class AsyncHttpClient implements Callable<String> {
    private final HttpURLConnection httpURLConnection;

    private AsyncHttpClient(Request request) throws IOException {
        this.httpURLConnection = request.getHttpURLConnection();
    }

    public static AsyncHttpClient getClient(Request request) throws IOException {
        if (request == null || request.getHttpURLConnection() == null) {
            throw new IllegalArgumentException("request 및 request.getHttpURLConnection()는 null일 수 없습니다.");
        }

        return new AsyncHttpClient(request);
    }

    @Override
    public String call() throws Exception {
        this.httpURLConnection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.httpURLConnection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        this.httpURLConnection.disconnect();

        return response.toString();
    }
}
