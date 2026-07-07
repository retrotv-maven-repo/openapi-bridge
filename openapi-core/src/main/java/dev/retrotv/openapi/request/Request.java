package dev.retrotv.openapi.request;

import java.io.IOException;
import java.net.*;

import dev.retrotv.openapi.OpenAPI;
import dev.retrotv.openapi.Query;
import dev.retrotv.openapi.exception.ConnectionFailException;
import lombok.NonNull;

public abstract class Request {
    protected final OpenAPI api;
    protected final HttpURLConnection conn;

    Request(@NonNull OpenAPI api) {
        this.api = api;
        try {
            this.conn = (HttpURLConnection) this.buildURL().openConnection();
            this.conn.setRequestMethod("GET");
            this.conn.setDoOutput(true);
            this.conn.setDoInput(true);
        } catch (ProtocolException ex) {
            throw new ConnectionFailException("유효한 HTTP 메서드가 아닙니다.", ex);
        } catch (IOException ex) {
            throw new ConnectionFailException("API 연결에 실패했습니다.", ex);
        }
    }

    @NonNull
    public OpenAPI getApi() {
        return this.api;
    }

    /**
     * HttpURLConnection 객체를 반환합니다.
     * @return HttpURLConnection 객체
     * @throws ConnectionFailException 연결 실패 시 예외 발생
     */
    @NonNull
    public abstract HttpURLConnection getHttpURLConnection();

    @NonNull
    protected URL buildURL() {
        StringBuilder sb = new StringBuilder(api.getUrl());
        sb.append('?');
        for (Query query : api.getQueries()) {
            sb.append(query.getQuery()).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);

        try {
            URI uri = new URI(sb.toString());
            return uri.toURL();
        } catch (MalformedURLException | URISyntaxException ex) {
            throw new ConnectionFailException("유효한 URL이 아닙니다.", ex);
        }
    }
}
