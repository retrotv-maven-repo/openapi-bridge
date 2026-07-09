package dev.retrotv.openapi.request;

import java.net.*;

import dev.retrotv.openapi.OpenAPI;
import dev.retrotv.openapi.Query;
import dev.retrotv.openapi.enums.ContentType;
import dev.retrotv.openapi.exception.ConnectionFailException;
import lombok.NonNull;

public abstract class Request {
    protected final OpenAPI api;
    protected ContentType contentType;

    Request(@NonNull OpenAPI api) {
        this.api = api;
    }

    @NonNull
    public OpenAPI getApi() {
        return this.api;
    }

    @NonNull
    public ContentType getContentType() {
        return this.contentType;
    }

    @NonNull
    public URL buildURL() {
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
