package dev.retrotv.openapi;

import java.io.IOException;
import java.net.*;

import dev.retrotv.openapi.exception.ConnectionFailException;

public class JSONRequest extends Request {
    public JSONRequest(OpenAPI api) {
        super(api);
    }

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
