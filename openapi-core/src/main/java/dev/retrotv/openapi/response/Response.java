package dev.retrotv.openapi.response;

import dev.retrotv.openapi.enums.ContentType;
import lombok.Getter;

import java.util.Map;

@Getter
public abstract class Response {
    protected final ContentType contentType;
    protected String body;
    protected int statusCode;

    Response(ContentType contentType) {
        this.contentType = contentType;
    }

    abstract Map<String, Object> getMap();
}
