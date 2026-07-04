package kr.go.data;

import dev.retrotv.openapi.Query;
import lombok.NonNull;

import java.util.Set;

public class IpasCountryCodeAPI extends WhoisAPI {
    private final String url = getBaseUrl() + "/ipas_country_code?";

    private IpasCountryCodeAPI(Set<Query> queries) {
        this.setQueries(queries);
    }

    /**
     * IpasCountryCodeAPI 인스턴스를 생성합니다.
     * @param queries 쿼리 파라미터 집합
     * @return IpasCountryCodeAPI 인스턴스
     */
    @NonNull
    public static WhoisAPI getAPI(@NonNull Set<Query> queries) {
        return new IpasCountryCodeAPI(queries);
    }

    @NonNull
    @Override
    public String getUrl() {
        return this.url;
    }
}
