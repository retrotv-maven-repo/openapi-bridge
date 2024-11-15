package dev.retrotv.openapi;

import java.util.Set;

public interface OpenAPI {
    String getUrl();
    Set<Query> getQueries();
    void setQueries(Set<Query> query);
}
