package kr.go.data;

import dev.retrotv.openapi.OpenAPI;
import dev.retrotv.openapi.Query;

import java.util.Set;

public abstract class WhoisAPI implements OpenAPI {
    private static final String BASE_URL = "https://apis.data.go.kr/B551505/whois";
    private Set<Query> queries;

    public String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    public Set<Query> getQueries() {
        return queries;
    }

    @Override
    public void setQueries(Set<Query> queries) {
        this.queries = queries;
    }
}
