package kr.go.data;

import dev.retrotv.openapi.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class IpasCountryCodeAPITest {

    @Test
    @DisplayName("connect() 메소드 테스트")
    void connect() throws IOException, ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        // given
        Set<Query> queries = new HashSet<>();
        queries.add(new Query("serviceKey", URLEncoder.encode("7kYHEqb3oI9dbwrz32fZfsHeVB9mabR0gMOL1qtD3zA8G4iMiyf8WfWGcmnccHMk2wkheW+wGJvd24StjS6fIg==", "UTF-8")));
        queries.add(new Query("query", "127.0.0.1"));
        queries.add(new Query("answer", "xml"));

        // when
        System.out.println("XML 가져오기 시작");
        Request request = new XMLRequest(IpasCountryCodeAPI.getAPI(queries));
        AsyncHttpClient ahc = AsyncHttpClient.getClient(request);
        Future<String> future = es.submit(ahc);
        String value = future.get();
        assertNotNull(value);
        System.out.println(value);
        System.out.println("XML 가져오기 종료");

        queries.clear();
        queries.add(new Query("serviceKey", URLEncoder.encode("7kYHEqb3oI9dbwrz32fZfsHeVB9mabR0gMOL1qtD3zA8G4iMiyf8WfWGcmnccHMk2wkheW+wGJvd24StjS6fIg==", "UTF-8")));
        queries.add(new Query("query", "127.0.0.1"));
        queries.add(new Query("answer", "json"));

        System.out.println("JSON 가져오기 시작");
        request = new JSONRequest(IpasCountryCodeAPI.getAPI(queries));
        ahc = AsyncHttpClient.getClient(request);
        future = es.submit(ahc);
        value = future.get();
        assertNotNull(value);
        System.out.println(value);
        System.out.println("JSON 가져오기 종료");
    }
}
