package dev.retrotv.openapi.response;

import dev.retrotv.openapi.enums.ContentType;

public class JSONResponse extends Response {
    JSONResponse() {
        super(ContentType.JSON);
    }
}
