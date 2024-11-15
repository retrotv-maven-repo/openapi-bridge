package kr.go.data;

import dev.retrotv.openapi.AsyncHttpClient;
import dev.retrotv.openapi.JSONRequest;
import dev.retrotv.openapi.Query;
import dev.retrotv.openapi.XMLRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class IpasCountryCodeAPITest {

    @Test
    @DisplayName("connect() 메소드 테스트")
    void connect() throws IOException {

        // given
        Set<Query> queries = new HashSet<>();
        queries.add(new Query("serviceKey", URLEncoder.encode("7kYHEqb3oI9dbwrz32fZfsHeVB9mabR0gMOL1qtD3zA8G4iMiyf8WfWGcmnccHMk2wkheW+wGJvd24StjS6fIg==", "UTF-8")));
        queries.add(new Query("query", "127.0.0.1"));
        queries.add(new Query("answer", "xml"));

        // when
        System.out.println("XML 가져오기 시작");
        CompletableFuture.supplyAsync(() -> {
            try {
                XMLRequest request = new XMLRequest(IpasCountryCodeAPI.getAPI(queries));
                AsyncHttpClient ahc = AsyncHttpClient.getClient(request);
                return ahc.call();
            } catch (Exception e) {
                return "";
            }
        }).thenAccept(result -> {
            System.out.println("XML 결과 (thenApply): " + result);
            assertNotNull(result);
        }).join();
        System.out.println("XML 가져오기 종료");

        queries.clear();
        queries.add(new Query("serviceKey", URLEncoder.encode("7kYHEqb3oI9dbwrz32fZfsHeVB9mabR0gMOL1qtD3zA8G4iMiyf8WfWGcmnccHMk2wkheW+wGJvd24StjS6fIg==", "UTF-8")));
        queries.add(new Query("query", "127.0.0.1"));
        queries.add(new Query("answer", "json"));

        System.out.println("JSON 가져오기 시작");
        CompletableFuture.supplyAsync(() -> {
            try {
                JSONRequest request = new JSONRequest(IpasCountryCodeAPI.getAPI(queries));
                AsyncHttpClient ahc = AsyncHttpClient.getClient(request);
                return ahc.call();
            } catch (Exception e) {
                return "";
            }
        }).thenAccept(result -> {
            System.out.println("JSON 결과 (thenApply): " + result);
            assertNotNull(result);
        }).join();
        System.out.println("JSON 가져오기 종료");
    }
}
