package dev.retrotv.openapi;

import dev.retrotv.openapi.exception.ConnectionFailException;
import dev.retrotv.openapi.request.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncHttpClient {

    // HTTP 전용 스레드 풀 정의 (Blocking I/O로 인한 스레드 고갈 방지)
    private final ExecutorService executor;

    // 기본 생성자: 유동적으로 개수가 조절되는 스레드 풀 생성
    public AsyncHttpClient() {
        this.executor = Executors.newCachedThreadPool();
    }

    // 기본 생성자: 적절한 크기의 고정 스레드 풀 생성
    public AsyncHttpClient(int poolSize) {
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    // 기본 생성자: 커스텀 스레드 풀을 주입받을 수 있는 생성자 (DI)
    public AsyncHttpClient(ExecutorService executor) {
        this.executor = executor;
    }

    /**
     * 비동기 GET 요청을 보내는 메서드
     *
     * @param request 요청 객체
     * @return CompletableFuture<String> 응답 문자열을 담은 미래 객체
     */
    public CompletableFuture<String> get(Request request) {
        return CompletableFuture.supplyAsync(() -> {
            HttpURLConnection conn = request.getHttpURLConnection();

            // 기본 타임아웃 설정 (매우 중요!)
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            try {
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
            } catch (IOException e) {
                // 여기서 발생한 예외는 CompletableFuture의 exceptionally 단계로 전달됨
                throw new ConnectionFailException("비동기 HTTP 요청 중 예외 발생", e);
            } finally {
                conn.disconnect();
            }
        }, executor);
    }

    // 애플리케이션 종료 시 스레드 풀을 닫아주기 위한 메서드
    public void shutdown() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }
}
