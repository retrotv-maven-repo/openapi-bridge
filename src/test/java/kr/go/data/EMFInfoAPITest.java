package kr.go.data;

import dev.retrotv.openapi.*;
import dev.retrotv.openapi.core.DotEnv;
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

class EMFInfoAPITest {

    /*
     * SERVICE_KEY 환경 변수 로드
     * 1. GitHub Actions의 Secrets에 SERVICE_KEY로 등록된 키 값을 가져옴
     * 2. 로컬 환경에서는 .env 파일에서 SERVICE_KEY 값을 가져옴
     * 3. .env 파일이 없거나, 예외 발생시 null 값을 가져옴
     * 4. SERVICE_KEY가 설정되어 있지 않으면 IllegalArgumentException 발생
     */
    private static final String SERVICE_KEY = loadServiceKey();

    private static String loadServiceKey() {
        String env = System.getenv("SERVICE_KEY");
        if (env != null && !env.trim().isEmpty()) {
            return env;
        }

        DotEnv dotEnv = new DotEnv();
        return dotEnv.get("SERVICE_KEY");
    }

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
            queries.add(new Query("STAGE1", URLEncoder.encode("인천광역시", "UTF-8")));
            queries.add(new Query("STAGE2", URLEncoder.encode("연수구", "UTF-8")));

            // when
            System.out.println("XML 가져오기 시작");
            OpenAPI api = new EMFInfoAPI();
            api.setQueries(queries);
            Request request = new XMLRequest(api);
            AsyncHttpClient ahc = AsyncHttpClient.getClient(request);
            Future<String> future = es.submit(ahc);
            String value = future.get();
            assertNotNull(value);
            System.out.println(value);
            System.out.println("XML 가져오기 종료");
        } finally {
            es.shutdown();
        }
    }
}
