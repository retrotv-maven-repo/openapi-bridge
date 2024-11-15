package kr.go.data;

import dev.retrotv.openapi.Query;

import java.util.Set;

public class IpasCountryCodeAPI extends WhoisAPI {
    private final String url = getBaseUrl() + "/ipas_country_code?";

    private IpasCountryCodeAPI(Set<Query> queries) {
        this.setQueries(queries);
    }

    public static WhoisAPI getAPI(Set<Query> queries) {
        return new IpasCountryCodeAPI(queries);
    }

    @Override
    public String getUrl() {
        return this.url;
    }
}
