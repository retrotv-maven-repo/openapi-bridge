package kr.go.data;

import dev.retrotv.openapi.OpenAPI;
import dev.retrotv.openapi.Query;
import lombok.NonNull;

import java.util.Set;

public abstract class WhoisAPI implements OpenAPI {
    private static final String BASE_URL = "https://apis.data.go.kr/B551505/whois";
    private Set<Query> queries;

    /**
     * API의 기본 URL을 반환합니다.
     * @return 기본 URL
     */
    @NonNull
    public String getBaseUrl() {
        return BASE_URL;
    }

    @NonNull
    @Override
    public Set<Query> getQueries() {
        return queries;
    }

    @Override
    public void setQueries(@NonNull Set<Query> queries) {
        this.queries = queries;
    }
}
