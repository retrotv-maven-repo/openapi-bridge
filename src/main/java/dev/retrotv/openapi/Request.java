package dev.retrotv.openapi;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import dev.retrotv.openapi.exception.ConnectionFailException;

public abstract class Request {
    protected final OpenAPI api;

    public Request(OpenAPI api) {
        this.api = api;
    }

    public abstract HttpURLConnection getHttpURLConnection();

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
