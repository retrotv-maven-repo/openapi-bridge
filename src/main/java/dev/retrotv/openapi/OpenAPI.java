package dev.retrotv.openapi;

import java.util.Set;

import lombok.NonNull;

/**
 * OpenAPI 인터페이스
 * API 구현체는 이 인터페이스를 상속받아 구현해야 합니다.
 */
public interface OpenAPI {

    /**
     * API의 요청 URL을 반환합니다.
     * @return 요청 URL
     */
    @NonNull
    String getUrl();

    /**
     * API 요청에 필요한 쿼리 파라미터를 반환합니다.
     * @return 쿼리 파라미터 집합
     */
    @NonNull
    Set<Query> getQueries();

    /**
     * API 요청에 필요한 쿼리 파라미터를 설정합니다.
     * @param query 쿼리 파라미터 집합
     */
    void setQueries(@NonNull Set<Query> query);
}
