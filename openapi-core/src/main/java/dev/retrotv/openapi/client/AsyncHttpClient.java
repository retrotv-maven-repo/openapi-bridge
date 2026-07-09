package dev.retrotv.openapi.client;

import dev.retrotv.openapi.request.Request;
import dev.retrotv.openapi.response.Response;

import java.util.concurrent.CompletableFuture;

public class AsyncHttpClient extends HttpClient {
    public AsyncHttpClient(Request request) {
        super(request);
    }

    /**
     * 비동기 GET 요청을 보내는 메서드
     *
     * @return CompletableFuture<Response> Response 객체를 담은 미래 객체
     */
    public CompletableFuture<Response> get() {

        // supplyAsync 메서드 내부에서 발생한 예외는 CompletableFuture의 exceptionally 단계로 전달됨
        return CompletableFuture.supplyAsync(this::executeGet);
    }
}
