package dev.retrotv.openapi;

import lombok.NonNull;

/**
 * 쿼리 파라미터 클래스
 */
public class Query {
    private final String key;
    private final String value;

    /**
     * 쿼리 파라미터 클래스 기본 생성자
     * @param key 쿼리 파라미터 키
     * @param value 쿼리 파라미터 값
     */
    public Query(@NonNull String key, @NonNull String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 쿼리 파라미터의 키를 반환합니다.
     * @return 쿼리 파라미터 키
     */
    @NonNull
    public String getKey() {
        return key;
    }

    /**
     * 쿼리 파라미터의 값을 반환합니다.
     * @return 쿼리 파라미터 값
     */
    @NonNull
    public String getValue() {
        return value;
    }

    /**
     * 쿼리 파라미터를 조합하여 반환합니다.
     * @return 쿼리 파라미터 조합
     */
    @NonNull
    public String getQuery() {
        return key + "=" + value;
    }
}
