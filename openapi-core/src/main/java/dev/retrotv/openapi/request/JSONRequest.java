package dev.retrotv.openapi.request;

import java.net.*;

import dev.retrotv.openapi.OpenAPI;
import lombok.NonNull;

public class JSONRequest extends Request {

    /**
     * JSON 요청 생성자
     * @param api OpenAPI를 상속받은 구현체의 인스턴스
     */
    public JSONRequest(@NonNull OpenAPI api) {
        super(api);
    }

    @NonNull
    @Override
    public HttpURLConnection getHttpURLConnection() {
        this.conn.setRequestProperty("Content-Type", "application/json");
        return this.conn;
    }
}
