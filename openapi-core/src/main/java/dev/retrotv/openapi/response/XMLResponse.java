package dev.retrotv.openapi.response;

import dev.retrotv.openapi.enums.ContentType;

public class XMLResponse extends Response {
    XMLResponse() {
        super(ContentType.XML);
    }
}
