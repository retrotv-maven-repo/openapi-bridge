package dev.retrotv.openapi.client;

import dev.retrotv.openapi.request.Request;
import dev.retrotv.openapi.response.Response;

/**
 * API 요청을 위한 동기 HTTP 클라이언트 클래스입니다.
 * 이 클래스는 HttpClient를 상속받아 동기 GET 요청을 수행합니다.
 * @see dev.retrotv.openapi.client.HttpClient
 */
public class SyncHttpClient extends HttpClient {

    /**
     * SyncHttpClient 생성자
     * @param request API 요청을 위한 Request 객체
     */
    public SyncHttpClient(Request request) {
        super(request);
    }

    /**
     * 동기 GET 요청을 보내는 메서드
     *
     * @return Response Response 객체를 담은 미래 객체
     */
    public Response get() {
        return this.executeGet();
    }
}
