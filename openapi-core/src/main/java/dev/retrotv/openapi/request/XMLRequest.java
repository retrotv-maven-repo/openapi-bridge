package dev.retrotv.openapi.request;

import java.net.*;

import dev.retrotv.openapi.OpenAPI;
import dev.retrotv.openapi.enums.ContentType;
import lombok.NonNull;

public class XMLRequest extends Request {

    /**
     * XML 요청 생성자
     * @param api OpenAPI를 상속받은 구현체의 인스턴스
     */
    public XMLRequest(@NonNull OpenAPI api) {
        super(api);
        this.contentType = ContentType.XML;
    }
}
