package dev.retrotv.openapi.request;

import java.io.IOException;
import java.net.*;

import dev.retrotv.openapi.OpenAPI;
import dev.retrotv.openapi.exception.ConnectionFailException;
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
        try {
            HttpURLConnection connection = (HttpURLConnection) this.buildURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);
    
            return connection;
        } catch (ProtocolException ex) {
            throw new ConnectionFailException("유효한 HTTP 메서드가 아닙니다.", ex);
        } catch (IOException ex) {
            throw new ConnectionFailException("API 연결에 실패했습니다.", ex);
        }
    }
}
