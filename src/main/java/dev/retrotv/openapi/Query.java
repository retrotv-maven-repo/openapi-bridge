package dev.retrotv.openapi;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Query {
    private final String key;
    private final String value;

    public Query(@NonNull String key, @NonNull String value) {
        this.key = key;
        this.value = value;
    }

    @NonNull
    public String getQuery() {
        return key + "=" + value;
    }
}
