package kr.go.data;

import java.util.Set;

import dev.retrotv.openapi.OpenAPI;
import dev.retrotv.openapi.Query;
import lombok.NonNull;

public class EMFInfoAPI implements OpenAPI {
    private final String url = "https://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire?";
    private Set<Query> queries;

    @Override
    public @NonNull String getUrl() {
        return url;
    }

    @Override
    public @NonNull Set<Query> getQueries() {
        return queries;
    }

    @Override
    public void setQueries(@NonNull Set<Query> queries) {
        this.queries = queries;
    }
}
