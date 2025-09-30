package kr.go.data;

import dev.retrotv.openapi.*;
import dev.retrotv.openapi.request.JSONRequest;
import dev.retrotv.openapi.request.Request;
import dev.retrotv.openapi.request.XMLRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class IpasCountryCodeAPITest {

    // GitHub Actions의 Secrets에 SERVICE_KEY로 등록된 키 값을 가져옴
    private static final String SERVICE_KEY = System.getenv("SERVICE_KEY");

    @Test
    @DisplayName("connect() 메소드 테스트")
    void connect() throws IOException, ExecutionException, InterruptedException {
        if (SERVICE_KEY == null || SERVICE_KEY.isEmpty()) {
            throw new IllegalArgumentException("SERVICE_KEY 환경 변수가 설정되어 있지 않습니다.");
        }
        
        ExecutorService es = Executors.newCachedThreadPool();
        try {

            // given
            Set<Query> queries = new HashSet<>();
            queries.add(new Query("serviceKey", URLEncoder.encode(SERVICE_KEY, "UTF-8")));
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
            queries.add(new Query("serviceKey", URLEncoder.encode(SERVICE_KEY, "UTF-8")));
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
        } finally {
            es.shutdown();
        }
    }
}
