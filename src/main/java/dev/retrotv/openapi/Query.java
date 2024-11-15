package dev.retrotv.openapi;

public class Query {
    private final String key;
    private final String value;

    public Query(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getQuery() {
        return key + "=" + value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
