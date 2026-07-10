package dev.retrotv.openapi.request;

import java.net.*;

import dev.retrotv.openapi.OpenAPI;
import dev.retrotv.openapi.Query;
import dev.retrotv.openapi.enums.ContentType;
import dev.retrotv.openapi.exception.ConnectionFailException;
import lombok.NonNull;

/**
 * 추상 요청 클래스
 */
public abstract class Request {
    protected final OpenAPI api;
    protected final ContentType contentType;

    Request(@NonNull OpenAPI api, @NonNull ContentType contentType) {
        this.api = api;
        this.contentType = contentType;
    }

    /**
     * OpenAPI를 상속받은 구현체의 인스턴스를 반환합니다.
     *
     * @return OpenAPI를 상속받은 구현체의 인스턴스
     */
    @NonNull
    public OpenAPI getApi() {
        return this.api;
    }

    /**
     * 콘텐츠 타입을 반환합니다.
     * @return ContentType 콘텐츠 타입 Enums
     */
    @NonNull
    public ContentType getContentType() {
        return this.contentType;
    }

    /**
     * api의 full url 정보가 담긴 URL 객체를 반환합니다.
     * @return URL api의 full url 정보가 담긴 URL 객체
     * @throws ConnectionFailException URL 객체 생성 실패 시 발생
     */
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
