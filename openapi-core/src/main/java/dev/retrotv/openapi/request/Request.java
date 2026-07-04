package dev.retrotv.openapi.request;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import dev.retrotv.openapi.OpenAPI;
import dev.retrotv.openapi.Query;
import dev.retrotv.openapi.exception.ConnectionFailException;
import lombok.NonNull;

public abstract class Request {
    protected final OpenAPI api;

    protected Request(@NonNull OpenAPI api) {
        this.api = api;
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
